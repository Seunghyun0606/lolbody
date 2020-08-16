import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import queues from "@/assets/data/queues.json";

Vue.use(Vuex)

const SERVER_URL = 'https://lolbody.gq'

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
        getProfileTotalWinRateChart(state){
            let win = 0;
            let lose = 0;
            for(let matchData of state.matchDatas){
                if(matchData.noGame)
                    continue;
                if(matchData[matchData.myTeam].win)
                    win++;
                else
                    lose++;
            }
            return {'win': win, 'lose': lose, 'total': win+lose};
        },
        getProfileEachWinRateChart(state){
            let arr = [];
            let result = {};
            for(let matchData of state.matchDatas){
                if(matchData.noGame)
                    continue;
                result[queues[matchData.queue].shortName]= {
                    name : queues[matchData.queue].shortName,
                    win : 0,
                    lose : 0,
                    total: 0
                };
            }
            for(let matchData of state.matchDatas){
                if(matchData.noGame)
                    continue;
                if(matchData[matchData.myTeam].win)
                    result[queues[matchData.queue].shortName].win++;
                else
                    result[queues[matchData.queue].shortName].lose++;
                result[queues[matchData.queue].shortName].total++;
            }
            for(let idx in result){
                arr.push(result[idx]);
            }
            arr.sort(function(a, b){
                return b.total - a.total;
            })
            let ans = {data : [], labels: []};

            for(let a in arr){
                ans.data.push(Math.round(arr[a].win/arr[a].total*10000)/100);
                ans.labels.push(arr[a].name);
            }
            return ans;
        },
        getProfileChampRate(state){
            let arr = [];
            let result = {};
            for(let matchData of state.matchDatas){
                if(matchData.noGame)
                    continue;
                result[matchData[matchData.myTeam].teammate[matchData.myIndex].champ]= {
                    name : matchData[matchData.myTeam].teammate[matchData.myIndex].champ,
                    win : 0,
                    lose : 0,
                    total: 0,
                    triger: true
                };
            }
            for(let matchData of state.matchDatas){
                if(matchData.noGame)
                    continue;
                if(matchData[matchData.myTeam].win)
                    result[matchData[matchData.myTeam].teammate[matchData.myIndex].champ].win++;
                else
                    result[matchData[matchData.myTeam].teammate[matchData.myIndex].champ].lose++;
                result[matchData[matchData.myTeam].teammate[matchData.myIndex].champ].total++;
            }
            
            let ans = [];
            for(let idx in result){
                arr.push(result[idx]);
            }
            arr.sort(function(a, b){
                return b.total - a.total;
            })
            for(let idx = 0; idx < arr.length; idx++){
                ans.push(arr[idx]);
                if(idx == 2)
                    break;
            }
            console.log(ans)
            return ans;
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
        // console.log(state.profileLineChartOption)
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
