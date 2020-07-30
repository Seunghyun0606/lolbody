<template>
  <v-container class="multi-search-container">
    <v-row class="multi-search-row">
      <v-col cols=1 class="multi-search-col1">
        KR
      </v-col>

      <v-col>
        <textarea
          class="multi-search-col2"
          v-model="userName"
          @keyup.enter="getMultiSearchDatas(userName), getUserDatas(userName)"
          
          placeholder="폭주기관차 사이온님이 방에 참가했습니다.
대전싸피화이팅님이 방에 참가했습니다.
롤롤바디바디님이 방에 참가했습니다.
사람이 5명모이면 1명은님이 방에 참가했습니다.
졸음운전은 대단해님이 방에 참가했습니다.">
          </textarea>
      </v-col>

      <v-col cols=1 class="multi-search-col3">

      </v-col>
    </v-row>
  </v-container>

</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'


export default {
  name: "MultiSearchBar",
  data() {
    return {
      userName: '',
    }
  },
  copmuted: {
    ...mapState([])
  },
  methods: {
    ...mapActions([
      "getMultiSearchDatas",
      "getUserDatas",
    ]),
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
  height: 150px;
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
  height: 130px;
  width: 100%;
  resize: none;
}
.multi-search-col3 {
  background: #fd9797;
  padding: 0px;
  height: 100%;
  width: 100%;

}

</style>