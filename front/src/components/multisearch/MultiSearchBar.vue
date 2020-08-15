<template>
  <v-container class="multi-search-container">
    <v-row class="multi-search-row">
      <v-col cols=1 class="multi-search-col1">
        KR
      </v-col>

      <v-col>
        <textarea
          tabindex="1"
          class="multi-search-col2"
          v-model="inputSummonerID"
          @paste="onPaste"
          placeholder="폭주기관차 사이온님이 방에 참가했습니다.
대전싸피화이팅님이 방에 참가했습니다.
롤롤바디바디님이 방에 참가했습니다.
사람이 5명모이면 1명은님이 방에 참가했습니다.
졸음운전은 대단해님이 방에 참가했습니다."
        ></textarea>
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
  name: "MultiSearchBar",
  data() {
    return {
      inputSummonerID: '',
    }
  },
  copmuted: {
    ...mapState(['searchSummernerIDs']),
  },
  methods: {
    onClickSearchButton() {

      this.parseInputSummonerID()

    },
    parseInputSummonerID() {
      // 개행문자가 존재 할 경우 따옴표로 바꾸고 따옴표 기준으로 Array로 split
      // 혹시 op.gg처럼 멀티서치 검색창이 따로 존재 할 수도 있으므로
      // console.log(0, this.inputSummonerID) 
      let tmpSearchSummernerIDs = this.inputSummonerID.replace(/(\n|\r\n)/g, ',').split(',')
      if ( tmpSearchSummernerIDs.length === 2 ) {
        tmpSearchSummernerIDs.pop()
      }
      // console.log(1, tmpSearchSummernerIDs)
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

      // this.searchSummernerIDs = tmpSearchSummernerIDs

      this.$store.commit('changeSearchSummonerIDs', tmpSearchSummernerIDs)
      // 1개면 유저프로필. 1개이상이면 멀티서치.
      // this.getMultiSearchDatas(tmpSearchSummernerIDs)

      for ( var temp of tmpSearchSummernerIDs) {
        this.getMultiSearch(temp)
      }


    },
    async getMultiSearch(temp) {
      
      await this.$store.dispatch('getMultiUserDatas', temp)
      await this.$store.dispatch('getMultiSearchDatas', temp)
      await this.$store.dispatch('getMultiSearchRadarChartDatas', temp)
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
      this.inputSummonerID = this.$store.state.searchSummonerIDs.join('\n')
      // console.log(3)
    },
  },
}
</script>

<style scoped>

.multi-search-container {
  border: 0.01px solid silver;
  width: 1000px;
  max-width: 1000px;
  margin-bottom: 25px;
  padding: 0px;
}

.multi-search-row {
  height: 100px;
  width: 1000px;
  max-width: 1000px;
  margin: 0;
}

.multi-search-col1 {
  color: #7676f9;
  font-size: 16px;
  font-weight: 700;
}
.multi-search-col2 {
  height: 80px;
  width: 100%;
  resize: none;
  padding: 5px;
}
.multi-search-col3 {
  background: #fd9797;
  padding: 0px;
  height: 100%;
  width: 100%;
  transition: 0.2s;

}

.multi-search-col3:hover {
  background: #fbb5b5;
  cursor: pointer;

}

.icon-place {
  position: relative;
  top: 32%;
  left: 30%;
  font-size: 40px;
  color: white;
}

</style>