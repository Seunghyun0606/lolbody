import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'

Vue.use(Vuex)

const SERVER_URL = 'http://13.125.220.135:8888'

function calcDate(timestamp) {
    let month = new Date(timestamp).getMonth() + 1 + '월 '
    let day = new Date(timestamp).getDate() + '일 '
    // let hour = new Date(timestamp).getHours() + '시'
    return month + day // + hour
}
export default new Vuex.Store({
  state: {
    // 승현
    multiSearchDatas: [],
    userDatas: [],
    isIndex: '',
    profileLineChartOption: {
        series: [
            {
                name: "KDA",
                data: [] 
            },
            // {
            //   name: "Lane2",
            //   data: [3, 4, 5, 6, 5, 6.3, 7.5] 
            // }
        ],
        chartOptions: {
            chart: {
                height: 150,
                type: 'line',
                zoom: {
                    enabled: false,
                },
                toolbar: {
                    show: false
                },
            },
            colors: ['#77B6EA', '#545454'],
            grid: {
                borderColor: '#e7e7e7'
            },
            markers: {
                size: 5,
                shape: "circle",
                radius: 1,
            },
            labels: [],
            xaxis: {
                labels: {
                    showDuplicates: false,
                    style: {
                        fontSize: '10px',
                    }
                },
                tickAmount: 3,
                categories: [],
                type: 'category',
                title: {
                    // text: '최근 10게임 KDA'
                }
            },
            yaxis: {
                title: {
                    text: 'KDA'
                },
            // tickAmount: 5,
            // min: 0,
            // max: 10
            }
        },
    },
    profileRadarChartOption: {
        series: [{
            name: 'Lane1',
            data: []
        },
        {
            name: 'Lane2',
            data: []
        }]
    },

    // 호철
    searchSummonerIDs: [],

    // 형래
    profileDatas: {},
    matchDatas: [],
    nowProfileDatas:{},
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
    setMultiUserDatas(state, userDatas) {
      // userData는 array로 오기 때문에 sperad시킴
      // 20.07.30 userData가 자유랭크 데이터도 넘기기때문에 스프레드 시키면안됨. 단일 오브젝트만 넣습니다.
      state.userDatas = [ ...state.userDatas, userDatas ]
    },
    setProfileLineChartOption(state, matchDatas) {
        state.profileLineChartOption.series[0].data = []
        state.profileLineChartOption.chartOptions.xaxis.categories = []

        for ( let matchData of matchDatas ) {
            if ( matchData.myTeam === 'blueTeam' ) {
                state.profileLineChartOption.series[0].data.unshift(Math.round(matchData.blueTeam.teammate[matchData.myIndex].kda*100)/100)
            }
            else {
                state.profileLineChartOption.series[0].data.unshift(Math.round(matchData.redTeam.teammate[matchData.myIndex].kda*100)/100)
            }
            state.profileLineChartOption.chartOptions.xaxis.categories.unshift(calcDate(matchData.timestamp))
        }
        console.log(state.profileLineChartOption)
    },
    // 나중에 리팩토링하자
    setProfileRadarChartOption(state, Datas) {
      if ( state.profileDatas.rankedRecord.mostLine === undefined) {
        state.profileRadarChartOption.series[0].name = 'Unknown'
        
      }
      else {
        state.profileRadarChartOption.series[0].name = state.profileDatas.rankedRecord.mostLine

      }
      if ( state.profileDatas.rankedRecord.secondLine === undefined) {

        state.profileRadarChartOption.series[1].name = 'Unknown'
      }
      else {
        state.profileRadarChartOption.series[1].name = state.profileDatas.rankedRecord.secondLine

      }
      state.profileRadarChartOption.series[0].data = []
      state.profileRadarChartOption.series[1].data = []
      for ( let index1 in Datas.lane1) {
        state.profileRadarChartOption.series[0].data.push(Math.round((Datas.lane1[index1]*100)*10)/10)

      }
      for ( let index2 in Datas.lane2) {
        state.profileRadarChartOption.series[1].data.push(Math.round((Datas.lane2[index2]*100)*10)/10)

      }
    },

    // NavSearch toggle 용도
    toggleNavSearch(state, toggle) {
      state.isIndex = toggle
    },

    // Radar Chart Data
    setRadarChartDatas(state, datas) {
		state.profileRadarChartOption.series[0].name = datas.lane1["lane"];
		state.profileRadarChartOption.series[1].name = datas.lane2["lane"];
		state.profileRadarChartOption.series[0].data = [];
		state.profileRadarChartOption.series[1].data = [];
		state.profileRadarChartOption.series[0].data.push(Math.round((datas.lane1["aggressiveness"]*100)*10)/10);
		state.profileRadarChartOption.series[0].data.push(Math.round((datas.lane1["stability"]*100)*10)/10);
		state.profileRadarChartOption.series[0].data.push(Math.round((datas.lane1["influence"]*100)*10)/10);

		state.profileRadarChartOption.series[1].data.push(Math.round((datas.lane2["aggressiveness"]*100)*10)/10);
		state.profileRadarChartOption.series[1].data.push(Math.round((datas.lane2["stability"]*100)*10)/10);
		state.profileRadarChartOption.series[1].data.push(Math.round((datas.lane2["influence"]*100)*10)/10);
    },


    // 호철
    changeSearchSummonerIDs(state, arr) {
      state.searchSummonerIDs = arr
    },

    // 형래
    setProfileDatas(state, profileDatas){
        state.profileDatas = profileDatas;
        state.matchDatas = [];
    },
    setMatchDatas(state, matchDatas){
        state.matchDatas = state.matchDatas.concat(matchDatas);
    },
    setNowProfileDatas(state, data){
        state.nowProfileDatas = data;
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
          .get(SERVER_URL + `/api/user/${userName}`)
          // .get(`http://localhost:8888/user/${userName}`)
          .then(res => {
            // 자유랭크가 같이와서 솔로만 넣게 처리했습니다.
            // 무조건 0번이 솔로라서 0번 기준으로 처리. 만약 없으면 그냥 널 값처리.
              if (res.data[0].queueType === "RANKED_SOLO_5x5") {
                commit('setMultiUserDatas', res.data[0])
              }
              else {
                commit('setMultiUserDatas', { tier: "unranked", rank: ""})

              }
            }
          )
          .catch(err => {
            console.log(err)
          })
      }
    },
    // 승현, RadarChartData
    async getRadarChartDatas( { commit }, userNames ) {
      console.log(1)
      for ( let userName of userNames) {
        await axios
          .get(SERVER_URL + `/api/summonervalue/${userName}`)
          .then(res => {
            commit('setProfileRadarChartOption', res.data)
          })
          .catch(err => {
            console.log(err)
          })
        }
    },

    // 승현, renewalUserData

    renewalUserData( { commit }, userName ) {
      return axios.put(SERVER_URL + `/api/profile/${userName}`)
        .then(res => {
          commit('setProfileDatas', res.data)
        })
        .catch(err => {
          console.log(err)
        })
    },

    // 형래, profile
    getProfileDatas( { commit }, userName){
        return axios.get(SERVER_URL + `/api/profile/${userName}`)
        // return axios.get(`http://localhost:8888/profile/${userName}`)
        .then(res => {
            commit('setProfileDatas', res.data)
        }).catch(function (error) {
            if (error.response) {
                console.log(error.response.status);
            }
            commit('setProfileDatas', '')
        });
    },
    getMatchDatas( { commit }, {userName, num}){
        return axios.get(SERVER_URL + `/api/profile/${userName}/${num}`)
        // return axios.get(`http://localhost:8888/profile/${userName}/${num}`)
        .then(res => {
            commit('setMatchDatas', res.data)
            // 라인차트 데이터
            commit('setProfileLineChartOption', res.data)
        }).catch(function (error) {
            if (error.response) {
                console.log(error.response.status);
            }
            console.log(error)
            commit('setProfileLineChartOption', '')
        });
    },
    getProfileRadarChartDatas( { commit }, userNames ) {
		return axios.get(SERVER_URL + `/api/summonervalue/${userNames}`)
		.then(res => {
			commit('setRadarChartDatas', res.data)
		})
		.catch(function (error) {
            if (error.response) {
                console.log(error.response.status);
            }
            commit('setRadarChartDatas', '')
		})
    }
  },
  modules: {
  }
})
