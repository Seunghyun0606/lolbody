<template>
<div>
  <v-container class="pa-0 mr-16 pr-16">
    <v-row align="center">
      <v-col>
        <v-text-field
          id="paste"
          class="nav-search-size"
          flat
          hide-details
          dense
          label="Search ID"
          outlined
          v-model="inputSummonerID"
          @paste="onPaste"
          @keyup.enter="onClickSearchButton"
        ></v-text-field>

      </v-col>
      <v-col cols=2>
        <v-btn 
          small 
          color="primary lighten-1"
          @click="onClickSearchButton"
          >
          Search
        </v-btn>

      </v-col>

    </v-row>
  </v-container>

</div>
</template>

<script>
export default {
  name: "NavSearchBar",
  data() {
    return {
      inputSummonerID: '',  // 한글기준 3 ~ 8글자 영어 * 2
    }
  },
  methods: {
    onClickSearchButton() {
      this.parseInputSummonerID()
      console.log('axios요청', this.searchSummernerIDs)
    },
    parseInputSummonerID() {
      // 개행문자가 존재 할 경우 따옴표로 바꾸고 따옴표 기준으로 Array로 split
      // 혹시 op.gg처럼 멀티서치 검색창이 따로 존재 할 수도 있으므로
      let tmpSearchSummernerIDs = this.inputSummonerID.replace(/(\n|\r\n)/g, ',').split(',')

      // // 따옴표로 구분된 아이디 Array
      // searchSummernerID = this.inputSummonerID.split('\n')

      // 특수문자 정규표현식
      const regExp = /[{}[\]/?.,;:|)*~`!^-_+<>@#$%&\\=('"]/gi;

      // 복붙시 딸려오는 문자
      const trashInput = /님이 방에 참가했습니다/

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

      this.searchSummernerIDs = tmpSearchSummernerIDs
      this.$store.commit('changeSearchSummonerIDs', tmpSearchSummernerIDs)
      console.log(1)
      console.log(this.searchSummernerIDs)
      this.$router.push('/Profile/'+this.searchSummernerIDs);
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
        console.log(2)
        this.inputSummonerID = this.$store.state.searchSummonerIDs.join(', ')
    },
  },
}
</script>

<style scoped>

</style>