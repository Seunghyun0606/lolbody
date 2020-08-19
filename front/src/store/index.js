import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import queues from "@/assets/data/queues.json";

Vue.use(Vuex)

const SERVER_URL = 'https://lolbody.gq'

export default new Vuex.Store({
  state: {
    // 승현
    isIndex: false,
    isMultiSearchLoading: false,
    multiSearchDatas: [],
    multiUserDatas: [],
    multiSearchRadarData: [],
    
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
    profileDatas: {
        timestamp: null,
        summonerName: null,
        profileIconId: null,
        summonerLevel: null,
        soloRank: {
          tier: "UNRANKED",
          rank: null,
          leaguePoints: 0,
          wins: 0,
          losses: 0,
          winRate: 0
        },
        freeRank: {
          tier: "UNRANKED",
          rank: null,
          leaguePoints: 0,
          wins: 0,
          losses: 0,
          winRate: 0
        }
    },
    matchDatas: [],
    loadAllMatchDatas: false,
    badgeMap: {},
    badgeSet: [],
    ProfileRadarChart: [
        {aggressiveness : [], influence: [], stability: []},
        {aggressiveness : [], influence: [], stability: []},
        {aggressiveness : [], influence: [], stability: []}
    ],
    ProfileTotalWinRateChart: {'win': 0, 'lose': 0, 'total': 0},
    ProfileEachWinRateChart: {},
    error: 0,
    linechartdata:[
        {
            yaxis:{
                title: {
                    text: 'KDA'
                },
                tickAmount: 5,
                max: 10,
                forceNiceScale: true,
                labels: {
                    show : true,
                    formatter: (value) => { 
                        if(value >= 10 )
                            return '10+';
                        else
                            return value;
                    },
                },
            },
            series:{
                name: [],
                data: []
            },
            category:[],
        },
        {
            yaxis:{
                title: {
                    text: '총점'
                },
                tickAmount: 5,
                min: 0,
                max: 1,
                forceNiceScale: true,
            },
            series:{
                name: [],
                data: []
            },
            category:[],
        }
    ],
  },
  getters: {
    // 승현
    multiSearchRadarData(state) {
      return state.multiSearchRadarData
    },

    // 형래
    getProfileDatas(state){
        return state.profileDatas;
    },
    getBadgeSet(state){
        state.badgeSet.sort(function(a, b){
            if ( a.description < b.description ) return -1; 
            else if ( a.description == b.description ) return 0; 
            else return 1; 
        });
        return state.badgeSet;
    },
    getProfileRadarChart(state){
        let tmp = state.ProfileRadarChart;
        let result = [[0, 0, 0], [0, 0, 0], [0, 0, 0]];
        for(let idx in tmp){
            for(let t in tmp[idx].aggressiveness)
                result[idx][0] += tmp[idx].aggressiveness[t];
            for(let t in tmp[idx].stability)
                result[idx][1] += tmp[idx].stability[t];
            for(let t in tmp[idx].influence)
                result[idx][2] += tmp[idx].influence[t];
        }
        for(let idx in tmp){
            result[idx][0] = Math.round(result[idx][0]/tmp[idx].aggressiveness.length*100)/100;
            result[idx][1] = Math.round(result[idx][1]/tmp[idx].stability.length*100)/100;
            result[idx][2] = Math.round(result[idx][2]/tmp[idx].influence.length*100)/100;
        }
        return result;
    },
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
        let ans = {data : [], labels: [], win: [], lose: []};
        let count = 0;
        for(let a in arr){
            ans.lose.push(arr[a].lose);
            ans.win.push(arr[a].win);
            ans.data.push(Math.round(arr[a].win/arr[a].total*10000)/100);
            ans.labels.push(arr[a].name);
            count++;
            if(count == 4)
                break;
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
        return ans;
    }
  },
  mutations: {
    // 승현
    toggleMultiSearchLoading(state, toggle) {
      state.isMultiSearchLoading = toggle
    },
    setInitMultiSearchData(state) {
      state.multiSearchDatas = []
      state.multiUserDatas = []
      state.multiSearchRadarData = []
    },
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
      var Options = {
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
      }

      Options.series[0].name = Datas.lane1.lane
      Options.series[1].name = Datas.lane2.lane

      Options.series[0].data = []
      Options.series[1].data = []
      for ( var obj in Datas.lane1 ) {
        if (obj === "lane") {
          continue
        }
        Options.series[0].data.push((Datas.lane1[obj]*100).toFixed(0))
      }
      for ( var obj2 in Datas.lane2 ) {
        if (obj2 === "lane" ) {
          continue
        }
        Options.series[1].data.push((Datas.lane2[obj2]*100).toFixed(0))
      }

      state.multiSearchRadarData.push(Options)
    },
    
    // NavSearch toggle 용도
    toggleNavSearch(state, toggle) {
      state.isIndex = toggle
    },

    //// Radar Chart Data
    //setRadarChartDatas(state, datas) {
    //    state.profileRadarChartOption.series[0].name = datas.lane1["lane"];
    //    state.profileRadarChartOption.series[1].name = datas.lane2["lane"];
    //    state.profileRadarChartOption.series[0].data = [];
    //    state.profileRadarChartOption.series[1].data = [];
    //    state.profileRadarChartOption.series[0].data.push(Math.round((datas.lane1["aggressiveness"]*100)*10)/10);
    //    state.profileRadarChartOption.series[0].data.push(Math.round((datas.lane1["stability"]*100)*10)/10);
    //    state.profileRadarChartOption.series[0].data.push(Math.round((datas.lane1["influence"]*100)*10)/10);

    //    state.profileRadarChartOption.series[1].data.push(Math.round((datas.lane2["aggressiveness"]*100)*10)/10);
    //    state.profileRadarChartOption.series[1].data.push(Math.round((datas.lane2["stability"]*100)*10)/10);
    //    state.profileRadarChartOption.series[1].data.push(Math.round((datas.lane2["influence"]*100)*10)/10);
    //},


    // 호철
    changeSearchSummonerIDs(state, arr) {
      state.searchSummonerIDs = arr
      // console.log(arr)
    },

    // 형래
    setProfileDatas(state, profileDatas){
        state.profileDatas = profileDatas;
    },
    setMatchDatas(state, datas){
        let matchDatas = datas.matchRecordList;
        if(matchDatas == null)
            return;
        for(let idx = 0; idx < matchDatas.length; idx++)
            matchDatas[idx].display = false;
        
        state.matchDatas = state.matchDatas.concat(matchDatas);

        state.badgeSet = [];
        for(let badge in datas.badgeMap){
            if(state.badgeMap[badge] == null)
                state.badgeMap[badge] = datas.badgeMap[badge];
            else
                state.badgeMap[badge].cnt += datas.badgeMap[badge].cnt;
            state.badgeSet.push(state.badgeMap[badge]);
        }
    },
    setProfileRadarChart(state, matchDatas){
        if(matchDatas == null)
            return;
        for(let matchData of matchDatas){
            if(matchData.noGame)
                continue;
            switch(queues[matchData.queue].shortName){
                case '솔랭':
                case '자유 랭크 게임':
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.aggressiveness != null)
                        state.ProfileRadarChart[0].aggressiveness.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.aggressiveness);
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.stability != null)
                        state.ProfileRadarChart[0].stability.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.stability);
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.influence != null)
                        state.ProfileRadarChart[0].influence.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.influence);
                    break;
                case '일반':
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.aggressiveness != null)
                        state.ProfileRadarChart[1].aggressiveness.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.aggressiveness);
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.stability != null)
                        state.ProfileRadarChart[1].stability.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.stability);
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.influence != null)
                        state.ProfileRadarChart[1].influence.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.influence);
                    break;
                case '무작위 총력전':
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.aggressiveness != null)
                        state.ProfileRadarChart[2].aggressiveness.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.aggressiveness);
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.stability != null)
                        state.ProfileRadarChart[2].stability.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.stability);
                    if(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.influence != null)
                        state.ProfileRadarChart[2].influence.push(matchData[matchData.myTeam].teammate[matchData.myIndex].radar.influence);
                    break;
            }
        }
    },
    setProfileTotalWinRateChart(state, matchDatas){
        if(matchDatas == null)
            return;
        for(let matchData of matchDatas){
            if(matchData.noGame)
                continue;
            if(matchData[matchData.myTeam].win)
                state.ProfileTotalWinRateChart.win++;
            else
                state.ProfileTotalWinRateChart.lose++;
            state.ProfileTotalWinRateChart.total++;
        }
    },
    setProfileLinechartdata(state, matchDatas) {
        for ( let matchData of matchDatas ) {
            if(matchData.noGame)
                continue;
            if(matchData[matchData.myTeam].teammate[matchData.myIndex].kda == 'Infinity' || matchData[matchData.myTeam].teammate[matchData.myIndex].kda >= 10)
                state.linechartdata[0].series.data.unshift({x: matchData.timestamp+'', y: 10});
            else
                state.linechartdata[0].series.data.unshift({x: matchData.timestamp+'', y: Math.round(matchData[matchData.myTeam].teammate[matchData.myIndex].kda*100)/100});

            state.linechartdata[0].series.name.unshift(matchData[matchData.myTeam].teammate[matchData.myIndex].champ);
            state.linechartdata[1].series.name.unshift(matchData[matchData.myTeam].teammate[matchData.myIndex].champ);
            state.linechartdata[1].series.data.unshift({x: matchData.timestamp+'', y: Math.round(matchData[matchData.myTeam].teammate[matchData.myIndex].matchGrade*100)/100});
            state.linechartdata[0].category.unshift(matchData.timestamp)
            state.linechartdata[1].category.unshift(matchData.timestamp)
        }
    },
    setError(state, err){
        state.error = err;
    },
    setLoadAllMatchDatas(state, bool){
        state.loadAllMatchDatas = bool;
    }
  },

  actions: {
    // 승현, multiSearch 데이터 init
    initMultiSearchData( { commit } ) {
      commit('setInitMultiSearchData')
    },
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
      return axios
      .get(SERVER_URL + `/api/summonervalue/${userName}`)
      .then(res => {
          commit('setMultiSearchRadarData', res.data)
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
    getProfileDatas( { commit, state }, userName){
        state.profileDatas= {
            timestamp: null,
            summonerName: null,
            profileIconId: null,
            summonerLevel: null,
            soloRank: {
              tier: "UNRANKED",
              rank: null,
              leaguePoints: 0,
              wins: 0,
              losses: 0,
              winRate: 0
            },
            freeRank: {
              tier: "UNRANKED",
              rank: null,
              leaguePoints: 0,
              wins: 0,
              losses: 0,
              winRate: 0
            }
        };
        commit('setLoadAllMatchDatas', true)
        return axios.get(SERVER_URL + `/api/profile/${userName}`)
        // return axios.get(`http://localhost:8888/profile/${userName}`)
        .then(res => {
            commit('setError', 0)
            commit('setProfileDatas', res.data)
            //commit('setProfileLinechartdata', res.data)
        }).catch(function (error) {
            if (error.response) {
                console.log(error.response.status);
                commit('setError', error.response.status)
            }
            commit('setProfileDatas', {
                timestamp: null,
                summonerName: null,
                profileIconId: null,
                summonerLevel: null,
                soloRank: {
                  tier: "UNRANKED",
                  rank: null,
                  leaguePoints: 0,
                  wins: 0,
                  losses: 0,
                  winRate: 0
                },
                freeRank: {
                  tier: "UNRANKED",
                  rank: null,
                  leaguePoints: 0,
                  wins: 0,
                  losses: 0,
                  winRate: 0
                }
            })
        });
    },
    getMatchDatas( { commit, state }, {userName, num}){
        if(num == 1){
            state.matchDatas = [];
            state.badgeMap = {};
            state.ProfileRadarChart = [
                {aggressiveness : [], influence: [], stability: []},
                {aggressiveness : [], influence: [], stability: []},
                {aggressiveness : [], influence: [], stability: []}
            ];
            state.ProfileTotalWinRateChart = {'win': 0, 'lose': 0, 'total': 0};
            state.linechartdata[0].series.data = [];
            state.linechartdata[0].category = [];
            state.linechartdata[1].series.data = [];
            state.linechartdata[1].category = [];
        }
        return axios.get(SERVER_URL + `/api/profile/${userName}/${num}`)
        // return axios.get(`http://localhost:8888/profile/${userName}/${num}`)
        .then(res => {
            if(res.data.matchRecordList == null){
                commit('setLoadAllMatchDatas', false)
            }else if(res.data.matchRecordList.length%10 != 0){
                commit('setLoadAllMatchDatas', false)
                commit('setError', 0)
                commit('setMatchDatas', res.data)
                commit('setProfileRadarChart', res.data.matchRecordList)
                commit('setProfileLinechartdata', res.data.matchRecordList)
                commit('setProfileTotalWinRateChart', res.data.matchRecordList)
            }else{
                commit('setError', 0)
                commit('setMatchDatas', res.data)
                commit('setProfileRadarChart', res.data.matchRecordList)
                commit('setProfileLinechartdata', res.data.matchRecordList)
                commit('setProfileTotalWinRateChart', res.data.matchRecordList)
            }
        }).catch(function (error) {
            if (error.response) {
                console.log(error.response.status);
                commit('setError', error.response.status)
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
                commit('setError', error.response.status)
            }
            commit('setRadarChartDatas', '')
		})
    }
  },
  modules: {
  }
})
