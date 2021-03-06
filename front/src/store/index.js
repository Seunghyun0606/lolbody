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
    // multiUserDatas: [],
    // multiSearchRadarData: [],
    // lolbodyData: {
    //   userCardReference: {},
    //   radarList: [],
    //   champList: [],
    //   lineList: [],
    //   stastics: {},
    //   radar: {},
    //   analysis: {},
    // },


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
                    text: 'BP'
                },
                tickAmount: 5,
                min: 0,
                max: 100,
                labels: {
                    show : true,
                    formatter: (value) => { 
                        return parseInt(value);
                    },
                },
            },
            series:{
                name: [],
                data: []
            },
            category:[],
        }
    ],
    LolbodyData: {
        userCardReference: {
          "timestamp": 0,
          "summonerName": undefined,
          "profileIconId": undefined,
          "summonerLevel": undefined,
          "soloRank": {
            "tier": undefined,
            "rank": undefined,
            "leaguePoints": undefined,
            "wins": 0,
            "losses": 0,
            "winRate": 0
          },
        },
        radarList: [],
        champList: [],
        lineList: [],
        radar: {
            "aggressiveness": 0,
            "stability": 0,
            "influence": 0
        },
        analysis: {
          "totalDamageDealtToChampionsPerMin": 0,
          "damageDealtToObjectivesPerMin": 0,
          "visionScorePerMin": 0,
          "totalDamageTakenPerMin": 0,
          "totalMinionsKilledPerMin": 0,
          "killsRatio": 0,
          "deathsRatio": 0,
          "killAssistPerMin": 0,
          "killsPerMin": 0,
          "deathsPerMin": 0,
          "assistsPerMin": 0,
          "totalHealPerMin": 0,
          "damageSelfMitigatedPerMin": 0,
          "damageDealtToTurretsPerMin": 0,
          "timeCCingOthersPerMin": 0,
          "neutralMinionsKilledPerMin": 0,
          "totalTimeCrowdControlDealtPerMin": 0,
          "visionWardsBoughtInGamePerMin": 0,
          "neutralMinionsKilledEnemyJunglePerMin": 0,
          "wardsPlacedPerMin": 0,
          "wardsKilledPerMin": 0
        },
    },
  },
  getters: {
    // 승현
    // multiSearchRadarData(state) {
    //   return state.multiSearchRadarData
    // },

    // 형래
    getProfileDatas(state){
        return state.profileDatas;
    },
    getBadgeSet(state){
        state.badgeSet.sort(function(a, b){
            if ( a.name < b.name ) return -1; 
            else if ( a.name == b.name ) return 0; 
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
            result[idx][0] = Math.round(result[idx][0]/tmp[idx].aggressiveness.length*100);
            result[idx][1] = Math.round(result[idx][1]/tmp[idx].stability.length*100);
            result[idx][2] = Math.round(result[idx][2]/tmp[idx].influence.length*100);
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
    },
    getLolbodyData(state){
        return state.LolbodyData;
    }
  },
  mutations: {
    // 승현
    toggleMultiSearchLoading(state, toggle) {
      state.isMultiSearchLoading = toggle
    },
    setInitMultiSearchData(state) {
      state.multiSearchDatas = []
      // state.multiUserDatas = []
      // state.multiSearchRadarData = []
    },
    setMultiSearchDatas(state, multiSearchData) {
      // 계속해서 집어넣게 만듬.

      // 초기 오브젝트를 만든다. 여기에 값을 넣는 for문을돌린다,
      // for문이도는 속도는 응답속도보다 현저히 빠르기 때문에 비동기를 고민안해도 될듯하다.
      // 받아야할 object key를 정리한다
      // 404 요청일 경우  에러페이지를 만든다.
      var multiData = {
        userCard: {},
        mostLane: [],
        radarChart: {
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
            },
            tooltip: {
                y: {
                    title: {
                        formatter: function() {
                            return ''
                        }
                    }
                },
            }
          },
          series: [
            {
              data: [40, 40, 40]
            },
          ]
        },
        lineChart: {
          TOP: 0,
          JUNGLE: 0,
          MID: 0,
          BOT: 0,
          BOTTOM: 0,
          SUP: 0,
          SUPPORT: 0,
        },
        mostChamp: [],
        latestGames: [],
        averageMatchGrade: 0,
        totalWin: 0,
        totalWinRate: 0,
        badges: {
          contWins: 0,
          contFails: 0,
        },
      }


      multiData.userCard = multiSearchData.userCard
        
      var totalMatchGrade = 0
      var selectMostChamp = {}
      var selectMostLane = {}
      // 공격성, 안정성, 영향력
      var radarData = {
        aggressiveness: 0,
        stability: 0,
        influence: 0
      }


      // 연승, 연패 뱃지를 위한 플래그
      var flagWin = true
      var flagFail = true

      for ( var match of multiSearchData.matchResult.matchRecordList ) {

        var mT = match[match.myTeam].teammate[match.myIndex]
        var latestGame = {
          gameLane: "",
          champName: "",
          spell1: "",
          spell2: "",
          kills: 0,
          deaths: 0,
          assists: 0,
          win: false, 
        }

        // radar chart 값
        for ( var tempRadar in mT.radar) {
          radarData[tempRadar] += mT.radar[tempRadar]
        }



        // line 차트를 위한 라인 + latest 게임을 위한 자리
        latestGame.gameLane = mT.line
        latestGame.spell1 = mT.spell1
        latestGame.spell2 = mT.spell2
        latestGame.kills = mT.kills
        latestGame.deaths = mT.deaths
        latestGame.assists = mT.assists
        latestGame.champName = mT.champ

        // 나중에 kda평균대신에 쓸꺼임.
        totalMatchGrade += mT.matchGrade

        // lineChart rate 구하기위함.
        if ( mT.line !== 'None' ) {
          multiData.lineChart[mT.line]++
        }

        // mostLine을 구하기 위함. 나중에 None은 제외시켜야함.
        if ( selectMostLane[mT.line] === undefined ) {
          selectMostLane[mT.line] = 1
        }
        else {
          selectMostLane[mT.line]++
        }

        // mostChamp 고르기 위함, 나중에 제일 높은 값 골라내자.
        if ( selectMostChamp[mT.champ] === undefined ) {
          selectMostChamp[mT.champ] = 1
        }
        else {
          selectMostChamp[mT.champ]++
        }
        
        // total game 토탈 win + latestgame 승패 결정
        if ( match[match.myTeam].win ) {
          latestGame.win = true
          multiData.totalWin++

          flagFail = false
          if ( flagWin ) {
            multiData.badges.contWins++
          }
        }
        else {
          flagWin = false
          if ( flagFail ) {
            multiData.badges.contFails++            
          }
        }


        multiData.latestGames.push(latestGame)
      
      }

      multiData.latestGames = multiData.latestGames.slice(0,5)

      multiData.averageMatchGrade = ( totalMatchGrade ).toFixed(1)
      multiData.totalWinRate = ( multiData.totalWin / 10 ) * 100
      
      // most 챔프 정렬해서 3개만 뽑아서 넣음
      multiData.mostChamp = Object.keys(selectMostChamp).sort(function(a, b) { return selectMostChamp[a] - selectMostChamp[b] }).slice(0, 3)
      
      // most lane 큰 순서대로 정렬해서 3개 뽑을건데 만약 None이 있으면 없애야함 
      multiData.mostLane = Object.keys(selectMostLane).sort(function(a, b) { return selectMostLane[a] - selectMostLane[b] }).slice(0, 3).filter(function(n) {
        return n !== "None"
      }).slice(0, 2)


      // radar chart series data 넣어주기
      var radarValue = []
      
      for ( var x in radarData ) {
        radarValue.push((radarData[x]*10).toFixed(0))
      }
      
      state.multiSearchDatas.push(multiData)
      
      multiData.radarChart.series[0].data = radarValue


      // var recentGameWin = 0
      // var recentGameTotal = multiSearchDatas.recentMatchResults.length
      // for (var i of multiSearchDatas.recentMatchResults) {
      //   if (i) {
      //     recentGameWin++
      //   }
      // }
      // multiSearchDatas.recentMatchResults = { wins: recentGameWin, fails: recentGameTotal - recentGameWin, rate: Math.round((recentGameWin/recentGameTotal)*100)}
      // state.multiSearchDatas.push(multiSearchDatas)
    },
    // setLolbodyData(state, lolbodyData) {
    //   state.lolbodyData.userCardReference = lolbodyData.userCardReference
    //   state.lolbodyData.radarList = lolbodyData.radarList.slice(0, 10)
    //   state.lolbodyData.champList = lolbodyData.champList
    //   state.lolbodyData.lineList = lolbodyData.lineList
    //   state.lolbodyData.stastics = lolbodyData.stastics
    //   state.lolbodyData.radar = lolbodyData.radar
    //   state.lolbodyData.analysis = lolbodyData.analysis
    // },
    // setMultiUserDatas(state, userDatas) {
    //   // userData는 array로 오기 때문에 sperad시킴
    //   // 20.07.30 userData가 자유랭크 데이터도 넘기기때문에 스프레드 시키면안됨. 단일 오브젝트만 넣습니다.
    //   state.multiUserDatas = [ ...state.multiUserDatas, userDatas ]
    // },
    // setMultiSearchRadarData(state, Datas) {
    //   var Options = {
      //   options: {
      //     chart: {
      //       type: "radar",
      //       toolbar: {
      //         show: false,
      //       },
      //     },
      //     legend: {
      //       show: false,
      //     },
      //     xaxis: {
      //       categories: ['공격력', '안정성', '영향력']
      //     },
      //     yaxis: {
      //       show: false,
      //       tickAmount: 5,
      //       min: 0,
      //       max: 100,
      //     },
      //     markers: {
      //       size: 3
      //     }
      //   },
      // series: [
      //   {
      //     name: '초반',
      //     data: [40, 40, 40]
      //   },
      //   {
      //     name: '후반',
      //     data: [90, 80, 80]
      //   }
      // ]
      // }

    //   Options.series[0].name = Datas.lane1.lane
    //   Options.series[1].name = Datas.lane2.lane

    //   Options.series[0].data = []
    //   Options.series[1].data = []
    //   for ( var obj in Datas.lane1 ) {
    //     if (obj === "lane") {
    //       continue
    //     }
    //     Options.series[0].data.push((Datas.lane1[obj]*100).toFixed(0))
    //   }
    //   for ( var obj2 in Datas.lane2 ) {
    //     if (obj2 === "lane" ) {
    //       continue
    //     }
    //     Options.series[1].data.push((Datas.lane2[obj2]*100).toFixed(0))
    //   }

    //   state.multiSearchRadarData.push(Options)
    // },
    
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
        for(let idx = 0; idx < matchDatas.length; idx++){
            matchDatas[idx].display = false;
            matchDatas[idx].KDAToggle = true;
        }
        
        state.matchDatas = state.matchDatas.concat(matchDatas);

        state.badgeSet = [];
        for(let badge in datas.badgeMap){
            if(state.badgeMap[badge] == null)
                state.badgeMap[badge] = datas.badgeMap[badge];
            else
                state.badgeMap[badge].cnt += datas.badgeMap[badge].cnt;
        }
        for(let badge in state.badgeMap)
            state.badgeSet.push(state.badgeMap[badge]);
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
            state.linechartdata[1].series.data.unshift({x: matchData.timestamp+'', y: Math.round(matchData[matchData.myTeam].teammate[matchData.myIndex].matchGrade*100)});
            state.linechartdata[0].category.unshift(matchData.timestamp)
            state.linechartdata[1].category.unshift(matchData.timestamp)
        }
    },
    setError(state, err){
        state.error = err;
    },
    setLoadAllMatchDatas(state, bool){
        state.loadAllMatchDatas = bool;
    },
    setLolbodyData(state, data){
        state.LolbodyData = data;
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
    // getLolbodyData( { commit }, userName ) {
      // axios
      //   .get(SERVER_URL + `/api/lolbody/${userName}`)
      //   .then(res => {
      //     commit('setLolbodyData', res.data)
      //   })
      //   .catch(err => {
      //     console.log(err)
      //   })
    // },
    // getMultiUserDatas( { commit }, userName ) {
    //   return axios
    //     .get(SERVER_URL + `/api/profile/${userName}`)
    //     .then(res => {
    //       commit('setMultiUserDatas', res.data)
    //     })

    //     .catch(err => {
    //       console.log(err)
    //     })
    // },
    // // 승현, multiSearchRadarChartData
    // getMultiSearchRadarDatas( { commit }, userName ) {
    //   return axios
    //   .get(SERVER_URL + `/api/summonervalue/${userName}`)
    //   .then(res => {
    //       commit('setMultiSearchRadarData', res.data)
    //     })
    //     .catch((err) => {
    //       console.log(err)
    //     })
    // },

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

    //lolbody
    fetchLolbodyData( { commit }, userName) {
        return axios
            .get(SERVER_URL + `/api/lolbody/${userName}`)
            .then(res => {
                commit('setLolbodyData', res.data);
            })
            .catch(err => {
                console.log(err)
            })
    },
    fetchRenewalLolbody({ commit }, userName) {
        return axios
            .put(SERVER_URL + `/api/lolbody/${userName}`)
            .then(res => {
                commit('setLolbodyData', res.data);
            })
            .catch(err => {
                console.log(err)
            })
    },
    // 형래, profile
    getProfileDatas( { commit, state }, userName){
        commit('setError', 0)
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
            state.badgeSet = [];
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
                commit('setMatchDatas', res.data)
                commit('setProfileRadarChart', res.data.matchRecordList)
                commit('setProfileLinechartdata', res.data.matchRecordList)
                commit('setProfileTotalWinRateChart', res.data.matchRecordList)
            }else{
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
