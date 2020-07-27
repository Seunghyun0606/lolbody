import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 승현
    multiSearchDatas: [],
    userDatas: [],
    // 호철
    searchSummonerIDs: [],
  },
  getters: {

  },
  mutations: {
    // 승현
    setMultiSearchDatas(state, multiSearchDatas) {
      // 계속해서 집어넣게 만듬.
      var recentGameCount = 0
      for (var i of multiSearchDatas.recentMatchResults) {
        if (i) {
          recentGameCount++
        }
      }
      multiSearchDatas.recentMatchResults = { wins: recentGameCount, fails: 20-recentGameCount, rate: Math.round((recentGameCount/20)*100)}



      state.multiSearchDatas.push(multiSearchDatas)
    },
    setUserDatas(state, userDatas) {
      // userData는 array로 오기 때문에 sperad시킴
      state.userDatas = [ ...state.userDatas, ...userDatas ]
    },

    // 호철
    changeSearchSummonerIDs(state, arr) {
      state.searchSummonerIDs = arr
    }

  },
  actions: {
    getMultiSearchDatas( { commit }, userName ) {
      axios
        .get(`http://localhost:8888/api/multisearch/${userName}`)
        .then(res => {
          commit('setMultiSearchDatas', res.data)
        })
        .catch(err => {
          console.log(err)
        })

    },
    getUserDatas( { commit }, userName ) {
      axios
        .get(`http://localhost:8888/user/${userName}`)
        .then(res => {
          commit('setUserDatas', res.data)
        })
        .catch(err => {
          console.log(err)
        })
    }

  },
  modules: {
  }
})
