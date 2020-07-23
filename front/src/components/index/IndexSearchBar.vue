<template>
  <v-container>
    <v-row 
        class="text-center"
        justify="center">
      <v-col cols="8">
        <v-card>
          <v-toolbar
            color="primary"
            dark
            flat
          >
            <v-text-field
              id="paste"
              append-icon=""
              class="mx-4"
              flat
              hide-details
              label="Summoner ID"
              prepend-inner-icon="search"
              solo-inverted
              v-model="inputSummonerID"
              @paste="handlePaste"
            ></v-text-field>
            <v-btn 
            small 
            color="primary lighten-2"
            @click="parseInputSummonerID"
            >Search</v-btn>
          </v-toolbar>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "IndexSearchBar",
  data() {
    return {
      inputSummonerID: '',  // 한글기준 3 ~ 8글자 영어 * 2
    }
  },
  methods: {
    parseInputSummonerID() {
      // 개행문자가 존재 할 경우 따옴표로 바꿈
      let searchSummernerID = this.inputSummonerID.replace(/(\n|\r\n)/g, ',').split(',')

      // 따옴표로 구분된 아이디 Array
      searchSummernerID = this.inputSummonerID.split('\n')

      // 특수문자 정규표현식
      const regExp = /[{}[\]/?.,;:|)*~`!^-_+<>@#$%&\\=('"]/gi;

      // 아이디 Array에 대해
      searchSummernerID.forEach((ID, idx) => {
      // 특수문자 제거
        if (regExp.test(ID)) {  // 특수문자가 존재하면
          ID = ID.replace(regExp, "")  // 특수문자를 빈칸으로 치환
        }

      // 아이디(문자열) 맨 앞(뒤)에 공백 제거
        ID = ID.trim()
        searchSummernerID[idx] = ID
      })
      console.log(searchSummernerID)
    },
    handlePaste (e) {
        var clipboardData, pastedData;

        // 이벤트가 발생한 태그의 부모 태그로 이벤트의 전파(propagation, bubble up)를 막음
        e.stopPropagation();

        // 이벤트가 발생한 태그의 별도의 브라우저 행동을 막기 위해
        e.preventDefault();

        // Get pasted data via clipboard API
        clipboardData = e.clipboardData // || window.clipboardData;
        pastedData = clipboardData.getData('Text');

        // inputSummonerID에 붙여넣기 한 데이터를 넣고
        this.inputSummonerID = pastedData
        // 붙여넣기한 데이터를 parse
        this.parseInputSummonerID()
    },
  },
}
</script>

<style>

</style>