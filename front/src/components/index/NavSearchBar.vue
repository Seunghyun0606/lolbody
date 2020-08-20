<template>
<div>
  <v-container class="pa-0 mr-10 pr-16">
    <v-row align="center" class="nav-search-border" max-width="238.4px">
      <v-col class="pa-2 pb-9 pl-2">
        <input type="text"
          style="color:white;"
          tabindex="1" 
          v-on:input="typing"
          v-bind:value="inputSummonerID"
          @paste="onPaste"
          placeholder="Summoner ID"
          id="paste"
          @keyup.enter="onClickSearchButton"
          autocomplete="off"
          @focus="onFocusInput"
          @blur="offFocusInput"
          @keyup="onPressKey"
        >

      </v-col>
      <v-col cols=2 @click="onClickSearchButton" class="nav-serach-btn pa-2 mb-7">
        <v-icon class="icon-place">search</v-icon>
      </v-col>
      <!-- <v-row no-gutters v-show='this.historyIsVisible' >
        <template v-for="(h, n) in this.searchHistory">
          <v-col :key="n" class='col-12' @mousedown="onClickHistoryButton(h)">
            <v-card
              class="pa-0"
              outlined
              tile
              max-width="238.4px"
            >
              <v-btn 
                text 
                large 
                color="primary"
                >
                  {{ h }}
                </v-btn>
            </v-card>
          </v-col>
          <v-responsive
            v-if="(n+1)%1 === 0"
            :key="`width-${n}`"
            width="100%"
            max-width="238.4px"
          ></v-responsive>
        </template>
      </v-row>
      <v-row no-gutters v-show='this.autoCompleteIsVisible' >
        <template v-for="(h, n) in this.autoComplete">
          <v-col :key="n" class='col-12' @mousedown="onClickHistoryButton(h)">
            <v-card
              class="pa-0"
              outlined
              tile
              max-width="238.4px"
            >
              <v-btn 
                text 
                large 
                color="primary"
                >
                  {{ h }}
                </v-btn>
            </v-card>
          </v-col>
          <v-responsive
            v-if="(n+1)%1 === 0"
            :key="`width-${n}`"
            width="100%"
            max-width="238.4px"
          ></v-responsive>
        </template>
      </v-row> -->
    </v-row>
  </v-container>

</div>
</template>

<script>
import axios from 'axios'

const SEVER_URL = 'https://lolbody.gq'

export default {
  name: "NavSearchBar",
  data() {
    return {
      inputSummonerID: '',  // 한글기준 3 ~ 8글자 영어 * 2
      focusInput: false,
      autoComplete: []
    }
  },
  computed: {
    historyIsVisible() {
      if (this.focusInput === false) {
        return false
      } else {
        if (this.inputSummonerID.length === 0) {
          return true
        } else {
          return false
        }
      }
    },
    autoCompleteIsVisible() {
      if (this.focusInput === false) {
        return false
      } else {
        if (this.inputSummonerID.length !== 0) {
          return true
        } else {
          return false
        }
      }
    },
    searchHistory() {
      let tmp = []
      if (window.localStorage.getItem('searchHistory') !== null && window.localStorage.getItem('searchHistory') !== '') {
        tmp = JSON.parse(window.localStorage.getItem('searchHistory'))
      } else {
        window.localStorage.setItem('searchHistory', '')
      }
      return tmp
    }
  },
  methods: {
    typing(e) {
      this.inputSummonerID = e.target.value
    },

    onClickSearchButton() {
      this.parseInputSummonerID()
    },

    parseInputSummonerID() {
      // 개행문자가 존재 할 경우 따옴표로 바꾸고 따옴표 기준으로 Array로 split
      // 혹시 op.gg처럼 멀티서치 검색창이 따로 존재 할 수도 있으므로
      let tmpSearchSummernerIDs = this.inputSummonerID.replace(/(\n|\r\n)/g, ',').split(',')
      // console.log(tmpSearchSummernerIDs)
      // // 따옴표로 구분된 아이디 Array
      // searchSummernerID = this.inputSummonerID.split('\n')

      // 특수문자 정규표현식
      const regExp = /[{}[\]/?.,;:|)*~`!^-_+<>@#$%&\\=('"]/gi;

      // 복붙시 딸려오는 문자
      const trashInput = /님이 로비에 참가하셨습니다/

      // 아이디 Array에 대해
      tmpSearchSummernerIDs.forEach((ID, idx) => {
      // 특수문자 제거
        if (regExp.test(ID)) {  // 특수문자가 존재하면
          ID = ID.replace(regExp, "")  // 특수문자를 빈칸으로 치환
        }
        // console.log(ID, '특문 제거')

      // 복붙 문자 제거
        if (trashInput.test(ID)) {
            ID = ID.replace(trashInput, "")
          }
        // console.log(ID, '복붙제거')
        
      // 아이디(문자열) 맨 앞(뒤)에 공백 제거
        ID = ID.trim()
        tmpSearchSummernerIDs[idx] = ID
        // console.log(ID + '공백제거')
      })

      this.$store.commit('changeSearchSummonerIDs', tmpSearchSummernerIDs)
      // 1개면 유저프로필. 1개이상이면 멀티서치.
      if (tmpSearchSummernerIDs.length > 1) {
        this.getData(tmpSearchSummernerIDs)
        this.$router.push('/MultiSearch/')
      }
      else {
        this.$router.push('/Profile/'+tmpSearchSummernerIDs);

      }
      this.inputSummonerID = ""

    },
    async getData(tmpSearchSummernerIDs) {
      await this.$store.dispatch('initMultiSearchData')
      await this.$store.commit('toggleMultiSearchLoading', true)
      
      // await this.$store.dispatch('getMultiSearchRadarDatas', ID)
      // await this.$store.dispatch('getMultiUserDatas', ID)

      for ( var ID of tmpSearchSummernerIDs ) {
        await this.$store.dispatch('getMultiSearchDatas', ID)
      }
      await this.$store.commit('toggleMultiSearchLoading', false)

    },
    onPaste (e) {
        var clipboardData, pastedData;

        // 이벤트가 발생한 태그의 부모 태그로 이벤트의 전파(propagation, bubble up)를 막음
        e.stopPropagation();

        // 이벤트가 발생한 태그의 별도의 브라우저 행동을 막기 위해
        e.preventDefault();

        // Get pasted data via clipboard API
        clipboardData = e.clipboardData || window.clipboardData;
        pastedData = clipboardData.getData('Text');

        // inputSummonerID에 붙여넣기 한 데이터를 넣고
        this.inputSummonerID = pastedData
        // 붙여넣기한 데이터를 parse
        this.parseInputSummonerID()
        // 사용자가 수정이 가능하도록 input 창에 띄워줌
        // console.log(2)
        this.inputSummonerID = this.$store.state.searchSummonerIDs.join(', ')
    },

    onFocusInput() {
      this.focusInput = true
    },

    offFocusInput() {
      this.focusInput = false
    },
    
    onClickHistoryButton(h) {
      this.$router.push('/Profile/'+h);
    },

    onPressKey() {
      if (this.inputSummonerID.length !== 0) {
        axios.get(SEVER_URL + '/api/auto/' + this.inputSummonerID)
          .then(res => {
            let tmp = []
            res.data.forEach(e => tmp.push(e.name))
            this.autoComplete = tmp
            })
          .catch(err => console.log(err))

      }
    }
  },
}
</script>

<style scoped>

.nav-serach-btn:hover {
    cursor: pointer;
    background: rgba(255,255,255, 0.1) !important;
    border-radius: 70%;
}

.nav-search-border {
  border: 0.5px solid white;
  border-radius: 5px;
  height: 38px;
  /* padding: 6px; */
}

input::placeholder {
  color: white;
}

input:focus {
  outline:none;
}


</style>