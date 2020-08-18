<template>

  <v-container class="multi-search-container">
    <v-row class="multi-search-row">
      <v-col style="padding: 6px;">
        <input type="text"
          tabindex="1" 
          class="multi-search-col2"
          v-model="inputSummonerID"
          @paste="onPaste"
          placeholder="Summoner ID"
          id="paste"
          @keyup.enter="onClickSearchButton"
          autocomplete="off"     
        >

        <!-- <textarea
          tabindex="1"
          class="multi-search-col2"
          v-model="inputSummonerID"
          @paste="onPaste"
          placeholder="Summoner ID"
          id="paste"
          @keyup.enter="onClickSearchButton"


        ></textarea> -->
      </v-col>

      <v-col cols=1 class="multi-search-col3" @keyup.enter='onClickSearchButton' @click="onClickSearchButton" tabindex="2" >
        <v-icon class="icon-place">search</v-icon>
      </v-col>
    </v-row>
  </v-container>

</template>

<script>

import { mapState } from 'vuex'

export default {
  name: "IndexSearchBar",
  data() {
    return {
      inputSummonerID: '',  // 한글기준 3 ~ 8글자 영어 * 2
      searchHistory: []
    }
  },
  computed: {
    ...mapState(['searchSummernerIDs'])
  },
  mounted() {
    // localStorage에서 가져오기만 함
    this.$nextTick(function() {
      if (window.localStorage.getItem('searchHistory') !== null && window.localStorage.getItem('searchHistory') !== '') {
        // console.log('history 있음')
        this.searchHistory = JSON.parse(window.localStorage.getItem('searchHistory'))
        // console.log(this.searchHistory)
      } else {
        // console.log('history 없음')
        window.localStorage.setItem('searchHistory', '')
      }
    })
  },
  methods: {
    onClickSearchButton() {
      this.parseInputSummonerID()
    },

    parseInputSummonerID() {
      // 개행문자가 존재 할 경우 따옴표로 바꾸고 따옴표 기준으로 Array로 split
      // 혹시 op.gg처럼 멀티서치 검색창이 따로 존재 할 수도 있으므로
      let tmpSearchSummernerIDs = this.inputSummonerID.replace(/(\n|\r\n)/g, ',').split(',')
      // console.log(1, tmpSearchSummernerIDs)
      // // 따옴표로 구분된 아이디 Array
      // searchSummernerID = this.inputSummonerID.split('\n')

      // 인덱스에서 엔터 거르는로직 input으로 만들면 필요없음
      // if ( tmpSearchSummernerIDs.length === 2 ) {
      //   tmpSearchSummernerIDs.pop()
      // }

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

      // console.log(2, tmpSearchSummernerIDs)
      // this.searchSummernerIDs = tmpSearchSummernerIDs

      this.$store.commit('changeSearchSummonerIDs', tmpSearchSummernerIDs)

      // 1개면 유저프로필. 1개이상이면 멀티서치.
      if (tmpSearchSummernerIDs.length > 1) {
        this.getData(tmpSearchSummernerIDs)
        this.$router.push('/MultiSearch/')
      }
      else {
        this.$router.push('/Profile/'+tmpSearchSummernerIDs);

      }

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

        // console.log(this.inputSummonerID)
        this.inputSummonerID = this.$store.state.searchSummonerIDs.join(', ')
        // console.log(3)
    },
  },
}
</script>

<style scoped>


.multi-search-container {
  border: 2px solid #33A39E;
  /* margin-bottom: 25px; */
  padding: 0px;
  width: 690px;
  margin: auto
}

.multi-search-row {
  height: 45px;

  margin: 0;
}

.multi-search-col2 {
  height: 35px;
  width: 100%;
  resize: none;
  padding: 5px;
}
.multi-search-col3 {
  background: #33A39E;
  padding: 0px;
  height: 100%;
  width: 100%;
  transition: 0.3s;

}

.multi-search-col3:hover {
  background:rgb(142, 162, 248);
  cursor: pointer;

}

.icon-place {
  position: relative;
  top: 12%;
  left: 20%;
  font-size: 40px;
  color: white;
}


input:focus {
  outline:none;
}



</style>