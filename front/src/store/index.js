import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'

Vue.use(Vuex)

const SERVER_URL = 'https://lolbody.gq'

export default new Vuex.Store({
  state: {
    // 승현
    multiSearchDatas: [],
    multiUserDatas: [],
    isIndex: '',
    multiSearchRadarData: {
        options: {
          chart: {
            type: "radar",
            toolbar: {
              show: false,
            },
          },
          legend: {
            show: false,
          },
          xaxis: {
            categories: ['공격력', '안정성', '영향력']
          },
          yaxis: {
            show: false,
            tickAmount: 5,
            min: 0,
            max: 100,
          },
          markers: {
            size: 3
          }
        },
      series: [
        {
          name: '초반',
          data: [40, 40, 40]
        },
        {
          name: '후반',
          data: [90, 80, 80]
        }
      ]
    },
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
    },
    multiSearchRadarData(state) {
      return state.multiSearchRadarData
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
      state.multiUserDatas = [ ...state.multiUserDatas, userDatas ]
    },
    setMultiSearchRadarData(state, Datas) {
      state.multiSearchRadarData.series[0].name = Datas.lane1.lane
      state.multiSearchRadarData.series[1].name = Datas.lane2.lane

      state.multiSearchRadarData.series[0].data = []
      state.multiSearchRadarData.series[1].data = []
      for ( var obj in Datas.lane1 ) {
        if (obj === "lane") {
          continue
            state.multiSearchRadarData.series[0].data.push((Datas.lane1[obj]*100).toFixed(0))
        // console.log(this.multiSearchRadarData.lane1[obj])
        }
      }
      for ( var obj2 in Datas.lane2 ) {
        if (obj2 === "lane" ) {
          continue
        }
        state.multiSearchRadarData.series[1].data.push((Datas.lane2[obj2]*100).toFixed(0))
      }
    },
    


    setProfileLineChartOption(state, matchDatas) {
        state.profileLineChartOption.series[0].data = [];
        state.profileLineChartOption.chartOptions.xaxis.categories = [];
        for ( let matchData of matchDatas ) {
            if(matchData.noGame)
                continue;
            if(matchData[matchData.myTeam].teammate[matchData.myIndex].kda == 'Infinity' || matchData[matchData.myTeam].teammate[matchData.myIndex].kda >= 10)
                state.profileLineChartOption.series[0].data.unshift({x: matchData.timestamp +matchData[matchData.myTeam].teammate[matchData.myIndex].champ,y: 10});
            else
                state.profileLineChartOption.series[0].data.unshift({x: matchData.timestamp +matchData[matchData.myTeam].teammate[matchData.myIndex].champ,y: Math.round(matchData[matchData.myTeam].teammate[matchData.myIndex].kda*100)/100});
            state.profileLineChartOption.chartOptions.xaxis.categories.unshift(matchData.timestamp + matchData[matchData.myTeam].teammate[matchData.myIndex].champ)
        }

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
      // console.log(state.profileLineChartOption)
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
      // console.log(arr)
    },

    // 형래
    setProfileDatas(state, profileDatas){
        state.profileDatas = profileDatas;
        state.matchDatas = [];
    },
    setMatchDatas(state, matchDatas){
        for(let idx = 0; idx < matchDatas.length; idx++)
            matchDatas[idx].display = false;
        
        state.matchDatas = state.matchDatas.concat(matchDatas);
    },
    setNowProfileDatas(state, data){
        state.nowProfileDatas = data;
    }
  },

  
  actions: {
    // 승현, multisearch
    getMultiSearchDatas( { commit }, userName ) {
      return axios
        .get(SERVER_URL + `/api/multisearch/${userName}`)
        .then(res => {
          commit('setMultiSearchDatas', res.data)
        })
        .catch(err => {
          console.log(err)
        })
    },
    getMultiUserDatas( { commit }, userName ) {
      return axios
        .get(SERVER_URL + `/api/profile/${userName}`)
        .then(res => {
          commit('setMultiUserDatas', res.data)
        })

        .catch(err => {
          console.log(err)
        })
    },
    // 승현, multiSearchRadarChartData
    getMultiSearchRadarDatas( { commit }, userName ) {
      // console.log(userNames)
      return axios
      .get(SERVER_URL + `/api/summonervalue/${userName}`)
      .then(res => {
          // console.log(res.data)
          commit('setMultiSearchRadarData', res.data)
          // console.log(55)
        })
        .catch((err) => {
          console.log(err)
        })        
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
        }).catch(function (error) {
            if (error.response) {
                console.log(error.response.status);
            }
            console.log(error)
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
