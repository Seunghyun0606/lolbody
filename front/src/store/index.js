import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    searchSummonerIDs: [],
  },
  mutations: {
    changeSearchSummonerIDs(state, arr) {
      state.searchSummonerIDs = arr
    }
  },
  actions: {
  },
  modules: {
  }
})
