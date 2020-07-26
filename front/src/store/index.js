import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    multiSearchData: {},
    userData: [{tier: "Gold"}],
  },
  getters: {

  },
  mutations: {
    setMultiSearchData(state, multiSearchData) {
      state.multiSearchData = multiSearchData
    },
    setUserData(state, userData) {
      state.userData = userData
    },
  },
  actions: {
    getMultiSearchData( { commit }, userName ) {
      axios
        .get(`http://localhost:8888/api/multisearch/${userName}`)
        .then(res => {
          commit('setMultiSearchData', res.data)
        })
        .catch(err => {
          console.log(err)
        })

    },
    getUserData( { commit }, userName ) {
      axios
        .get(`http://localhost:8888/user/${userName}`)
        .then(res => {
          commit('setUserData', res.data)
        })
        .catch(err => {
          console.log(err)
        })
    }

  },
  modules: {
  }
})
