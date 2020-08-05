import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'

Vue.use(Vuex)

const SERVER_URL = 'http://13.125.220.135:8888'


export default new Vuex.Store({
  state: {
    // 승현
    multiSearchDatas: [],
    userDatas: [],
    isIndex: '',
    radarChartDatas: [],

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
      var recentGameWin = 0
      var recentGameTotal = multiSearchDatas.recentMatchResults.length
      for (var i of multiSearchDatas.recentMatchResults) {
        if (i) {
          recentGameWin++
        }
      }
      multiSearchDatas.recentMatchResults = { wins: recentGameWin, fails: recentGameTotal - recentGameWin, rate: Math.round((recentGameWin/recentGameTotal)*100)}
      state.multiSearchDatas.push(multiSearchDatas)
    },
    setUserDatas(state, userDatas) {
      // userData는 array로 오기 때문에 sperad시킴
      // 20.07.30 userData가 자유랭크 데이터도 넘기기때문에 스프레드 시키면안됨. 단일 오브젝트만 넣습니다.
      state.userDatas = [ ...state.userDatas, userDatas ]
    },

    // NavSearch toggle 용도
    toggleNavSearch(state, toggle) {
      state.isIndex = toggle
    },

    // Radar Chart Data
    setRadarChartDatas(state, datas) {
      state.radarChartDatas = datas
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
    async getMultiSearchDatas( { commit }, userNames ) {
      for ( var userName of userNames ) {
        await axios
          .get(SERVER_URL + `/api/multisearch/${userName}`)
          // .get(`http://localhost:8888/api/multisearch/${userName}`)
          .then(res => {
            commit('setMultiSearchDatas', res.data)
          })
          .catch(err => {
            console.log(err)
          })
      }

    },
    async getUserDatas( { commit }, userNames ) {
      for ( var userName of userNames ) {
        await axios
          .get(SERVER_URL + `/user/${userName}`)
          // .get(`http://localhost:8888/user/${userName}`)
          .then(res => {
            // 자유랭크가 같이와서 솔로만 넣게 처리했습니다.
            // 무조건 0번이 솔로라서 0번 기준으로 처리. 만약 없으면 그냥 널 값처리.
              if (res.data[0].queueType === "RANKED_SOLO_5x5") {
                commit('setUserDatas', res.data[0])
              }
              else {
                commit('setUserDatas', { tier: "unranked", rank: ""})

              }
            }
          )
          .catch(err => {
            console.log(err)
          })
      }
    },
    // 승현, RadarChartData
    getRadarChartDatas( { commit }, userName ) {
      axios
        .get(SERVER_URL + `/api/radar/${userName}`)
        .then(res => {
          commit('setRadarChartDatas', res)
        })
        .catch(err => {
          console.log(err)
        })
    },

    // 형래, profile
    getProfileDatas( { commit }, userName){
        return axios.get(SERVER_URL + `/profile/${userName}`)
        // return axios.get(`http://localhost:8888/profile/${userName}`)
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
        return axios.get(SERVER_URL + `/profile/${userName}/${num}`)
        // return axios.get(`http://localhost:8888/profile/${userName}/${num}`)
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
