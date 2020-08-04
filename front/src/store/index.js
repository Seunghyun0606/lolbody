import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 승현
    multiSearchDatas: [],
    userDatas: [],
    isIndex: '',

    // 호철
    searchSummonerIDs: [],

    // 형래
    profileDatas: {},
    matchDatas: []
  },
  // 형래
  getters: {
    profileDatas(state){
      return state.profileDatas;
    }
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
      // 20.07.30 userData가 자유랭크 데이터도 넘기기때문에 스프레드 시키면안됨. 단일 오브젝트만 넣습니다.
      state.userDatas = [ ...state.userDatas, userDatas ]
    },

    toggleNavSearch(state, toggle) {
      state.isIndex = toggle
    },

    // 호철
    changeSearchSummonerIDs(state, arr) {
      state.searchSummonerIDs = arr
    },

    // 형래
    setProfileDatas(state, profileDatas){
      state.profileDatas = profileDatas;
    },
    setMatchDatas(state, matchDatas){
        state.matchDatas = matchDatas;
    }

  },
  actions: {
    // 승현, multisearch
    getMultiSearchDatas( { commit }, userName ) {
      axios
        // .get(`http://13.125.220.135:8888/api/multisearch/${userName}`)
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
        // .get(`http://13.125.220.135:8888/user/${userName}`)
        .get(`http://localhost:8888/user/${userName}`)
        .then(res => {
          //자유랭크가 같이와서 솔로만 넣게 처리했습니다.
          for (var data_i of res.data) {
            console.log(data_i)
            if (data_i.queueType == "RANKED_SOLO_5x5") {
              commit('setUserDatas', data_i)
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    // 형래, profile
    },
    getProfileDatas( { commit }, userName){
        // return axios.get(`http://13.125.220.135:8888/profile/${userName}`)
        return axios.get(`http://localhost:8888/profile/${userName}`)
        .then(res => {
            commit('setProfileDatas', res.data)
        }).catch(function (error) {
            if (error.response) {
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
            }
        });
    },
    getMatchDatas( { commit }, {userName, num}){
        // return axios.get(`http://13.125.220.135:8888/profile/${userName}/${num}`)
        return axios.get(`http://localhost:8888/profile/${userName}/${num}`)
            .then(res => {
                commit('setMatchDatas', res.data)
            }).catch(function (error) {
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                }
            });
      },
  },
  modules: {
  }
})
