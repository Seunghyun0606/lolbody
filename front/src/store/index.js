import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    multiSearchDatas: [],
    userDatas: [],
  },
  getters: {

  },
  mutations: {
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
