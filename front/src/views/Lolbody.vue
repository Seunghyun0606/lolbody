<template>

  <!-- 나중에 만들때 전체 넓이랑 높이 고정값 주고 퍼센트 값으로 높이 정하자.. -->
  <v-container class='card-border mb-10'>

    <!-- 1번줄 -->
    <v-row class='justify-space-around' :style="{ backgroundImage: 'url(\'' + require(`@/assets/cities/background/${ background }.png`) + '\')', height: '15em', backgroundSize: '100%' }">
    <!-- <v-row class='justify-space-around'> -->
    <!-- <v-row :class='["justify-space-around", { backgroundImage: true } ]' > -->
      <!-- 랭크 -->
      <v-col cols="5" class="align-self-center card-border">

        <!-- 유저 프로필, 랭크, 아이디, 레벨, 챔피언 라인정보. -->
        <v-row class='justify-space-around align-content-center' style='height: 10rem;'>
          <!-- 랭크 -->
          <v-col cols="3">
            <v-row>
              <v-col class="center">
                <img class="icon big" :src="getLolbodyData.userCardReference.soloRank.tier !== 'UNRANKED' && getLolbodyData.userCardReference.soloRank.tier !== undefined ? require(`@/assets/images/tier/${getLolbodyData.userCardReference.soloRank.tier}.png`) : require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <!-- <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily"> -->
            </v-row>
            <!-- 많이가는 라인 -->
            <v-row class="px-3">
              <v-col class="center">
                <img class="icon small" :src="getLolbodyData.lineList[0].name.length > 0 ? require(`@/assets/images/position/${getLolbodyData.lineList[0].name}.png`) : require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <v-col class="center">
                <img class="icon small" :src="getLolbodyData.lineList[1].name.length > 0 ? require(`@/assets/images/position/${getLolbodyData.lineList[1].name}.png`) : require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <!-- <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.mainLane}.png`)" alt="mainLane">
              <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.subLane}.png`)" alt="subLane"> -->
            </v-row>
          </v-col>

          <!-- 유저 텍스트정보 -->
          <v-col cols="8">
            <v-row>
              <v-col>
                <v-row class="justfiy-space-between" style="font-weight: 900; color: white; font-size: 18px;">
                  <!-- 유저 이름 -->
                  <v-col>
                    {{ getLolbodyData.userCardReference.summonerName  }}

                  </v-col>
                  <!-- 갱신버튼 -->
                  <v-col cols="2 " class="renewal-lolbody" @click="renewalLolbody">
                    갱신
                  </v-col>
                </v-row>
                <v-row style="font-weight: 500; color: white; font-size: 15px">
                  <!-- 계급 -->
                  {{ getLolbodyData.userCardReference.soloRank.tier }} {{ getLolbodyData.userCardReference.soloRank.rank }}
                </v-row>
                <v-row style="font-weight: 500; color: white; font-size: 12px">
                  <!-- 승패 -->
                  {{ Math.round(getLolbodyData.userCardReference.soloRank.winRate) }}% ({{ getLolbodyData.userCardReference.soloRank.wins }}승 {{ getLolbodyData.userCardReference.soloRank.losses }}패)
                </v-row>
              </v-col>
            </v-row>
            <v-row class="mt-2 justify-start mr-8">
              <v-col cols=2 class="mr-1">
                <KaKaoButton/>
              </v-col>
              <v-col cols=2>
                <TwitterButton/>
              </v-col>
              <v-col cols=2>
                <FacebookButton/>
              </v-col>
              <!-- <MultiSearchBadge :index="index"/> -->
            </v-row>
          </v-col>
        </v-row>
      </v-col>

      <v-col cols="6" class="align-self-center card-border">
        <!-- 롤비티아이 구역 -->  
        <v-row class='align-content-center' style='height: 10rem;'>
          <v-col>
            <v-row>
              <v-col cols='4' class="center align-self-center" >
                <img class="icon big" :src="require(`@/assets/images/champion/${getLolbodyData.champList[0].name}.png`)" alt="temporarily">

              </v-col>
              <v-col>
                <v-row>
                  <v-col>
                    <v-row>
                      <v-col style="color: white">
                        {{ background }}의 {{ lolbti }} 소환사 <span style="font-weight:900;">{{ getLolbodyData.userCardReference.summonerName }}</span>

                      </v-col>

                    </v-row>
                    <v-row>
                      <v-col style="color: white">
                        공격성: {{ Math.round(getLolbodyData.radar.aggressiveness*100) }}점
                        
                        영향력: {{ Math.round(getLolbodyData.radar.influence*100) }}점
                        
                        안정성: {{ Math.round(getLolbodyData.radar.stability*100) }}점
                        <br>
                        당신은 {{ background }}의 {{ lolbti }} {{ lolbtiChampType }} 이시군요. 

                      </v-col>

                    </v-row>
                  </v-col>
                  <br>
                  
                </v-row>
              </v-col>

            </v-row>
          </v-col>

        </v-row>

      </v-col>
    </v-row>

    <!-- 2번줄 -->
    <v-row class='justify-space-around my-5 '>
      <v-col cols="5" class="card-border card-background">
        <v-row class='test-height2'>
          <v-col>
            <!-- 라인 선택해서 볼수있게. -->
            <v-row>
              <v-col :class="[{ btn_click: btnTrigger.champClick.main }, 'champ-select']"  @click="selectMostChamp()">
                주챔
              </v-col>
              
              <v-col :class="[{ btn_click: btnTrigger.champClick.sub }, 'champ-select']" @click="selectSecondChamp()">
                부챔
              </v-col>
            </v-row>

            <v-row class='align-content-center' style="height: 90%;">
              <v-col >
                
                <!-- 선택한 라인에서 높은 숙련도 챔피언 정보 -->
                <v-row class="justify-space-around">
                  <v-col class="center" cols="4">
                    <img class="icon big" :src="require(`@/assets/images/champion/${mostChamp.name}.png`)" alt="temporarily">
                  </v-col>

                  <v-col cols="7">
                    <v-row>
                      <v-col>
                        <b> {{ champTitle }} {{ korChampName }}</b>를(을) 주로 하시는군요. 
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col>
                        Difficulty: {{ champInfo.difficulty }}단계
                      </v-col>
                    </v-row>
                  </v-col>
                </v-row>

                <!-- 추천 챔피언 정보 -->
                <v-row class="justify-space-around">
                  <!-- for 문 돌려서 추천하는 챔피언 몇개 나오게한다. col에서 돌려야함 -->
                  <v-col cols="4">
                    <v-row class="justify-space-around">
                      <!-- 여기서 for문 돌릴 예정 -->
                      <v-col class="center" v-for="( recommendChamp, index ) in recommendChamps" :key="index">
                        <img class="icon small" :src="require(`@/assets/images/champion/${recommendChamp.id}.png`)" alt="temporarily">
                      </v-col>
                    </v-row>
                  </v-col>

                  <v-col cols="7">
                    {{ champType }} type 을 주로하는 당신, 이런 챔프들은 어떤가요?
                  </v-col>
                </v-row>

              </v-col>
            </v-row>
          </v-col>
          <!-- <div>
            상단에 현재 나의 높은 숙련도 챔피언, 및 라인 챔피언 mastery api에서 가져오면됨 puid? or encrypted usernmae
            <br>
            하단에 성향에 맞는 추천 챔피언과 이유. 로 2구역
          </div> -->
        </v-row>

      </v-col>

      <v-col cols="6" class="card-border card-background">

        <!-- 레이더 차트 구역 -->
        <v-row class='test-height2'>
          <!-- <div>
            레이더 차트 컴포넌트
            좌측에 레이더 차트 넣고 우측에는 레이더 차트 설명
            구획 2개로 나눠서하면될듯
          </div> -->
          <v-col cols="7" style="position: relative;">
            <LolbodyRadarChart :radarData="getLolbodyData.radar" />
          </v-col>
          <v-col class="align-self-center">
            <div>
              공격성:  ????????????????????
            </div>
            <div>
              안정성:  ????????????????????
            </div>
            <div>
              영향력:  ????????????????????
            </div>
          </v-col>
        </v-row>
      </v-col>

    </v-row>

    <!-- 3번줄 -->
    <v-row class='justify-space-around mb-5'>
      <v-col cols="5" class="card-border card-background">
        <v-row class="justify-space-around">
          <!-- <v-col class="bar-rank" @click="totalData()">
            전체
          </v-col> -->
          <v-col :class="[{btn_click: btnTrigger.barChartClick.dia}, 'bar-rank']" @click="DiaData()">
            다이아
          </v-col>
          <v-col :class="[{btn_click: btnTrigger.barChartClick.pla}, 'bar-rank']" @click="PlaData()">
            플레
          </v-col>
          <v-col :class="[{btn_click: btnTrigger.barChartClick.gold}, 'bar-rank']" @click="GoldData()">
            골드
          </v-col>
          <v-col :class="[{btn_click: btnTrigger.barChartClick.silver}, 'bar-rank']" @click="SilverData()">
            실버
          </v-col>
          <v-col :class="[{btn_click: btnTrigger.barChartClick.bronze}, 'bar-rank']"  @click="BronzeData()">
            브론즈
          </v-col>
          <v-col :class="[{btn_click: btnTrigger.barChartClick.iron}, 'bar-rank']" @click="IronData()">
            아이언
          </v-col>
        </v-row>
        <v-row style="height: 30em;">
          <v-col>
            <!-- 우측 막대 그래프 분석 데이터 산출물 이용. 전체랑 최근 20게임 평균 비교 col 하나 먹이고 row로 3~4개로 나눠서하면될듯 -->
            <!-- 동티어 동일챔프 기준? -->
            <!-- <LolbodyBarChart :barSeries="barSeries"/> -->
            <LolbodyBarChart :getMyData="getLolbodyData.analysis" :getOtherData="getOtherData"/>
          </v-col>
        
        </v-row>


      </v-col>

      <v-col cols="6" class="card-border card-background">
        <!-- 레이더차트 변화, Column 차트 구역 -->
        <v-row class='test-height2'>
          <!-- <div>
            레이더 차트의 변화 // 전체 // 현재 // 이전 막대그래프
          </div> -->
          <v-col>
            <LolbodyColumnChart :radarData="getLolbodyData.radarList.slice(0, 10)"/>
          </v-col>
        </v-row>

        <!-- 워드 클라우드? -->
        <v-row class='test-height2' style="border-top: 1px solid #e6e6e6">
          <!-- <div>
            워드 클라우드 형태로 뱃지 획득? 표현
          </div> -->
          <LolbodyWordCloud :champList="getLolbodyData.champList"/>

        </v-row>

      </v-col>

    </v-row>

  </v-container>
  
</template>

<script>
// import { mapState, mapGetters } from 'vuex'
import LolbodyBarChart from '@/components/lolbody/LolbodyBarChart'
import LolbodyColumnChart from '@/components/lolbody/LolbodyColumnChart'
import LolbodyRadarChart from '@/components/lolbody/LolbodyRadarChart'
import LolbodyWordCloud from '@/components/lolbody/LolbodyWordCloud'

import KaKaoButton from '@/components/lolbody/KakaoButton'
import TwitterButton from '@/components/lolbody/TwitterButton'
import FacebookButton from '@/components/lolbody/FacebookButton'

import champion from '@/assets/data/champion.json'
import lolbti from '@/assets/data/lolbti.json'

import axios from 'axios'

export default {
  name: 'Lolbody',
  components: {
    LolbodyBarChart,
    LolbodyColumnChart,
    LolbodyRadarChart,
    LolbodyWordCloud,
    KaKaoButton,
    TwitterButton,
    FacebookButton,
  },
  data() {
    return {
      btnTrigger: {
        barChartClick: {
          dia: false,
          pla: false,
          gold: false,
          silver: false,
          bronze: false,
          iron: false,
        },
        champClick: {
          main: true,
          sub: false,
        },

      },
      tooltip_content: 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
      getLolbodyData: {
        userCardReference: {
          "timestamp": 1597825234114,
          "summonerName": "재료페인",
          "profileIconId": 4086,
          "summonerLevel": 201,
          "soloRank": {
            "tier": "PLATINUM",
            "rank": "III",
            "leaguePoints": 32,
            "wins": 66,
            "losses": 53,
            "winRate": 55.46218487394958
          },
        },
        radarList: [
          {
            "timestamp": 1594060552449,
            "name": "TwistedFate",
            "radarReference": {
              "aggressiveness": 0.5287902452747564,
              "stability": 0.6730255075161797,
              "influence": 0.504803296988731
            }
          },
          {
            "timestamp": 1594058425193,
            "name": "Jax",
            "radarReference": {
              "aggressiveness": 0.37390242667293955,
              "stability": 0.4310051680067268,
              "influence": 0.3031385651678081
            }
          },
          {
            "timestamp": 1594056983278,
            "name": "Orianna",
            "radarReference": {
              "aggressiveness": 0.12238816226275566,
              "stability": 0.5091945524345002,
              "influence": 0.12000449578248656
            }
          },
          {
            "timestamp": 1594055159121,
            "name": "Orianna",
            "radarReference": {
              "aggressiveness": 0.22806715096095465,
              "stability": 0.5254654385290459,
              "influence": 0.2193302750652961
            }
          },
          {
            "timestamp": 1594053134899,
            "name": "Orianna",
            "radarReference": {
              "aggressiveness": 0.2901309138940865,
              "stability": 0.5946994096901721,
              "influence": 0.31574316472451325
            }
          },
          {
            "timestamp": 1594051149111,
            "name": "Orianna",
            "radarReference": {
              "aggressiveness": 0.3653485013278112,
              "stability": 0.48374294432745907,
              "influence": 0.32514938494076384
            }
          },
          {
            "timestamp": 1594049332679,
            "name": "Orianna",
            "radarReference": {
              "aggressiveness": 0.6525656931029792,
              "stability": 0.46818454118961733,
              "influence": 0.5690111665081311
            }
          },
          {
            "timestamp": 1594046379961,
            "name": "Graves",
            "radarReference": {
              "aggressiveness": 0.468612237283427,
              "stability": 0.4221981915745073,
              "influence": 0.7439142968083056
            }
          },
          {
            "timestamp": 1594044251027,
            "name": "Ezreal",
            "radarReference": {
              "aggressiveness": 0.4883628522405608,
              "stability": 0.4420279678502079,
              "influence": 0.3394182825191822
            }
          },
          {
            "timestamp": 1594042080076,
            "name": "Vayne",
            "radarReference": {
              "aggressiveness": 0.2489906606546086,
              "stability": 0.8168628321386745,
              "influence": 0.44028859524597586
            }
          },

        ],
        champList: [
          {
            "name": "Orianna",
            "games": 5,
            "wins": 3,
            "losses": 2
          },
          {
            "name": "Ezreal",
            "games": 5,
            "wins": 3,
            "losses": 2
          },
        ],
        lineList: [
          {
            "name": "BOTTOM",
            "games": 22,
            "wins": 13,
            "losses": 9
          },
          {
            "name": "JUNGLE",
            "games": 8,
            "wins": 2,
            "losses": 6
          },
          {
            "name": "TOP",
            "games": 6,
            "wins": 4,
            "losses": 2
          },
          {
            "name": "MID",
            "games": 4,
            "wins": 3,
            "losses": 1
          },
          {
            "name": "SUPPORT",
            "games": 1,
            "wins": 0,
            "losses": 1
          }
        ],
        radar: {
          "aggressiveness": 0.45649899054273085,
          "stability": 0.46422986811258116,
          "influence": 0.42943992455013963
        },
        analysis: {
          "totalDamageDealtToChampionsPerMin": 0.4867824887872988,
          "damageDealtToObjectivesPerMin": 0.563701684684821,
          "visionScorePerMin": 0.2912816723922004,
          "totalDamageTakenPerMin": 0.4729416777189834,
          "totalMinionsKilledPerMin": 0.6389355471119424,
          "killsRatio": 0.4333364165733978,
          "deathsRatio": 0.4521519272401263,
          "killAssistPerMin": 0.40977280512191083,
          "killsPerMin": 0.4730457064944947,
          "deathsPerMin": 0.520559122806781,
          "assistsPerMin": 0.38440696307502353,
          "totalHealPerMin": 0.0,
          "damageSelfMitigatedPerMin": 0.4306140184137884,
          "damageDealtToTurretsPerMin": 0.6064815854204757,
          "timeCCingOthersPerMin": 0.5889592408994767,
          "neutralMinionsKilledPerMin": 0.4591647865999153,
          "totalTimeCrowdControlDealtPerMin": 0.49119109346869805,
          "visionWardsBoughtInGamePerMin": 0.4108086044276818,
          "neutralMinionsKilledEnemyJunglePerMin": 0.4913651945781554,
          "wardsPlacedPerMin": 0.34619754025181293,
          "wardsKilledPerMin": 0.3436652132791967
        },

      },
      getOtherData: [
        {
          "totalDamageDealtToChampionsPerMin": 0.5,
          "damageDealtToObjectivesPerMin": 0.5,
          "visionScorePerMin": 0.5,
          "totalDamageTakenPerMin": 0.5,
          "totalMinionsKilledPerMin": 0.5,
          "killsRatio": 0.5,
          "deathsRatio": 0.5,
          "killAssistPerMin": 0.5,
          "killsPerMin": 0.5,
          "deathsPerMin": 0.5,
          "assistsPerMin": 0.5,
          "totalHealPerMin": 0.0,
          "damageSelfMitigatedPerMin": 0.5,
          "damageDealtToTurretsPerMin": 0.5,
          "timeCCingOthersPerMin": 0.5,
          "neutralMinionsKilledPerMin": 0.5,
          "totalTimeCrowdControlDealtPerMin": 0.5,
          "visionWardsBoughtInGamePerMin": 0.5,
          "neutralMinionsKilledEnemyJunglePerMin": 0.5,
          "wardsPlacedPerMin": 0.5,
          "wardsKilledPerMin": 0.5
        },
      ],
      mostChamp: {
        name: "error",
        games: 0,
        wins: 0,
      },
      champTitle: "",
      champInfo: {},
      recommendChamps: [],
      champType: "",
      background: "아이오니아",
      lolbti: "",
      lolbtiChampType: "",
      korChampName: "",
      barSeries: [
        {
          name: "",
          data: [],

        }, 
        {
          name: "",
          data: [],
        }
      ],

    }
  },
  computed: {
    // ...mapState(['lolbodyData']),
    // ...mapGetters(['getLolbodyData']),
  },
  watch: {
    // getLolbodyData: {
    //   deep: true,
    //   // immediate: true,
    //   handler() {
    //     this.selectMostChamp()
    //     this.selectLolbti()
    //     this.selectlolbtiChampType()
    //     if ( this.getLolbodyData.userCardReference.soloRank.tier === "DIAMOND" ) {
    //       this.DiaData()
    //     }
    //     else if ( this.getLolbodyData.userCardReference.soloRank.tier === "PLATINUM" ) {
    //       this.PlaData()
    //     }
    //     else if ( this.getLolbodyData.userCardReference.soloRank.tier === "GOLD" ) {
    //       this.GoldData()
    //     }
    //     else if ( this.getLolbodyData.userCardReference.soloRank.tier === "SILVER" ) {
    //       this.SilverData()
    //     }
    //     else if ( this.getLolbodyData.userCardReference.soloRank.tier === "BRONZE" ) {
    //       this.BronzeData()
    //     }
    //     else {
    //       this.IronData()
    //     }
    //   }
    // },
    mostChamp: {
      deep: true,
      handler() {
        this.changeChampTitle()
        this.changeChampInfo()
        this.getRecommendChamps(this.mostChamp)
        // this.changeChampType()
        // this.changeBackground()

      }
    }
  },
  methods: {
    // setBarSeriesMyData() {
    //   var tempSeries = {
    //     name: "나",
    //     data: [],
    //   }
    //   for ( var temp in this.getLolbodyData.analysis ) {
    //     tempSeries.data.push(this.getLolbodyData.analysis[temp])
    //   }
    //   this.barSeries[1] = tempSeries
    // },
    async renewalLolbody() {
      // await this.init()
      await this.fetchRenewalLolbody()
      await this.setLolbodyData()
      await this.setMostChamp()
    },

    // init() {
    //   this.getLolbodyData.champList = []

    // },

    fetchRenewalLolbody() {
      return axios
        .put(`https://lolbody.gq` + `/api/lolbody/${this.$route.params.userName}`)
        .then(res => {
          this.getLolbodyData = res.data
        })
        .catch(err => {
          console.log(err)
        })
    },


    setLolbodyData() {
      this.selectMostChamp()
      this.selectLolbti()
      this.selectlolbtiChampType()


      if ( this.getLolbodyData.userCardReference.soloRank.tier === "DIAMOND" ) {
        this.DiaData()
      }
      else if ( this.getLolbodyData.userCardReference.soloRank.tier === "PLATINUM" ) {
        this.PlaData()
      }
      else if ( this.getLolbodyData.userCardReference.soloRank.tier === "GOLD" ) {
        this.GoldData()
      }
      else if ( this.getLolbodyData.userCardReference.soloRank.tier === "SILVER" ) {
        this.SilverData()
      }
      else if ( this.getLolbodyData.userCardReference.soloRank.tier === "BRONZE" ) {
        this.BronzeData()
      }
      else {
        this.IronData()
      }

    },
    setMostChamp() {
      this.changeChampTitle()
      this.changeChampInfo()
      this.getRecommendChamps(this.mostChamp)
      this.changeChampType()
      this.changeBackground()

    },
    fetchLolbodyData() {
      return axios
        .get(`https://lolbody.gq` + `/api/lolbody/${this.$route.params.userName}`)
        .then(res => {
          this.getLolbodyData = res.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    // totalData() {
    //   this.getOtherData = this.getLolbodyData.stastics.stastics.total.total
    // },
    DiaData() {
      // this.barSeries
      // var tempSeries = {
      //   name: "다이아",
      //   data: [],
      // }
      // var tempData = this.getLolbodyData.stastics.tierAnalysis.diamond.total
      // for ( var temp in tempData ) {
      //   tempSeries.data.push(tempData[temp])
      // }
      // this.barSeries[0] = tempSeries
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.platinum.total, "플레티넘"]
      this.btnTrigger.barChartClick.dia = true
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    PlaData() {
      // var tempSeries = {
      //   name: "플레티넘",
      //   data: [],
      // }
      // var tempData = this.getLolbodyData.stastics.tierAnalysis.platinum.total
      // for ( var temp in tempData ) {
      //   tempSeries.data.push(tempData[temp])
      // }
      // this.barSeries[0] = tempSeries

      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.platinum.total, "플레티넘"]
      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = true
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    GoldData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.gold.total, "골드"]
      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = true
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    SilverData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.silver.total, "실버"]
      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = true
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    BronzeData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.bronze.total, "브론즈"]
      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = true
      this.btnTrigger.barChartClick.iron = false

    },
    IronData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.iron.total, "아이언"]
      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = true

    },

    selectlolbtiChampType() {
      this.lolbtiChampType = champion.data[this.getLolbodyData.champList[0].name].tags[0]
    },

    selectLolbti() {
      var radar = this.getLolbodyData.radar

      var a = radar.aggressiveness
      var s = radar.stability
      var i = radar.influence

      
      if ( a < 0.34 ) {
        if ( s < 0.34 ) {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[0]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[1]
          }
          else {
            this.lolbti = lolbti[2]
          }
        }
        else if ( s < 0.67 ) {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[3]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[4]
          }
          else {
            this.lolbti = lolbti[5]
          }
        }
        else {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[6]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[7]
          }
          else {
            this.lolbti = lolbti[8]
          }
        }
      }
      else if ( a < 0.67 ) {
        if ( s < 0.34 ) {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[9]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[10]
          }
          else {
            this.lolbti = lolbti[11]
          }
        }
        else if ( s < 0.67 ) {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[12]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[13]
          }
          else {
            this.lolbti = lolbti[14]
          }
        }
        else {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[15]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[16]
          }
          else {
            this.lolbti = lolbti[17]
          }
        }
      }
      else {
        if ( s < 0.34 ) {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[18]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[19]
          }
          else {
            this.lolbti = lolbti[20]
          }
        }
        else if ( s < 0.67 ) {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[21]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[22]
          }
          else {
            this.lolbti = lolbti[23]
          }
        }
        else {
          if ( i < 0.34 ) {
            this.lolbti = lolbti[24]
          }
          else if ( i < 0.67 ) {
            this.lolbti = lolbti[25]
          }
          else {
            this.lolbti = lolbti[26]
          }
        }

      }


    },

    selectMostChamp() {
      this.mostChamp = this.getLolbodyData.champList[0]
      this.korChampName = champion.data[this.getLolbodyData.champList[0].name].name
      
      this.btnTrigger.champClick.main = true
      this.btnTrigger.champClick.sub = false
    },
    selectSecondChamp() {
      this.mostChamp = this.getLolbodyData.champList[1]
      this.korChampName = champion.data[this.getLolbodyData.champList[1].name].name
      
      this.btnTrigger.champClick.main = false
      this.btnTrigger.champClick.sub = true
    },

    changeChampTitle() {
      this.champTitle = champion.data[this.mostChamp.name].title
    },
    changeChampInfo() {
      this.champInfo = champion.data[this.mostChamp.name].info
    },
    changeChampType() {
      this.champType = champion.data[this.mostChamp.name].tags[0]
    },
    changeBackground() {
      this.background = champion.data[this.mostChamp.name].region
      
    },




    getRecommendChamps(mostChamp) {
      var champList = []

      var winRate = mostChamp.games / mostChamp.wins
      var champType = champion.data[mostChamp.name].tags[0]
      var champDiff = champion.data[mostChamp.name].info.difficulty

      // 승률에 따라 같은 타입이면서 어려움이 다른 챔피언을 고른다.
      if ( winRate > 0.55 ) {
        for ( var recommendChamp in champion.data ) {
          for ( var tempType of champion.data[recommendChamp].tags ) {
            if ( tempType === champType ) {
              if (  champion.data[recommendChamp].info.difficulty >= champDiff+1 ) {
                champList.push(champion.data[recommendChamp])
              }
            }
          }
        }
      } else if ( 0.5 > winRate ) {
        for ( recommendChamp in champion.data ) {
          for ( tempType of champion.data[recommendChamp].tags ) {
            if ( tempType === champType ) {
              if ( champDiff+1 > champion.data[recommendChamp].info.difficulty && champion.data[recommendChamp].info.difficulty > champDiff-1 ) {
                champList.push(champion.data[recommendChamp])
              }
            }
          }
        }
      } else {
        for ( recommendChamp in champion.data ) {
          for ( tempType of champion.data[recommendChamp].tags ) {
            if ( tempType === champType ) {
              if ( champDiff-1 >= champion.data[recommendChamp].info.difficulty ) {
                champList.push(champion.data[recommendChamp])
              }
            }
          }
        }
      }
      this.recommendChamps = champList.slice(0, 3)
    },



  },
  async created() {
    this.$store.commit('toggleNavSearch', false)
    await this.fetchLolbodyData()
    await this.setLolbodyData()
    await this.setMostChamp()

    // this.$store.dispatch('getLolbodyData', this.$route.params.userName)
  },

}
</script>

<style scoped>

/* .getBackground {
  background-image: 'url(\'' + require('@/assets/images/test.png') + '\')';
  height: '15em';
  background-size: '100%';
  opacity: '0.7'
  
} */

.renewal-lolbody {
  border: 1px solid #33A39E;
  border-radius: 3px;
  color: #33A39E;
  font-size: 5px;
  font-weight: 900;
  text-align: center;
  padding-top: 2px !important;
  height: 20px;
}

.renewal-lolbody {
  cursor: pointer;
}

.champ-select {
  text-align: center;
  font-size: 16px;
  background: #272727;
  color: rgba(255, 255, 255, 0.6);

}

.champ-select:hover {
  cursor: pointer;
  background: rgb(160, 160, 161) ;
}

.bar-rank {
  text-align: center;
  font-size: 12px;
  background: #272727;
  color: rgba(255, 255, 255, 0.6);
}

.bar-rank:hover {
  cursor: pointer;
  background: rgb(160, 160, 161) ;
}

.btn_click {
  background: #33A39E !important;
}

.card-border {
  border: 1px solid #e6e6e6;
  /* padding: 2px !important; */
}

.card-background {
  background-color: #fff;
}

.container {
  /* border: 0.5px solid black; */
  padding: 0px;
  margin-top: 12px;
}

.row {
  padding: 0px;
  margin: 0;
  /* border: 0.5px solid red; */
}
.col {
  padding: 0px;
  margin: 0;
  /* border: 0.5px solid blue; */
}

.test-height {
  height: 20em;
}
.test-height2 {
  height: 15em;
}

.icon {
  border-radius: 70%;
  height: 5em;
  /* border: 0.5px solid white; */
}

.big {
  height: 5em;
}

.small {
  height: 2em;
  /* border: 0.5px solid white; */
}

.border-box {
  border-right: 1px solid #e6e6e6;
  background-color: #3b8efa;
  transition: 0.5s;
}

.border-box:hover {
  background-color: #96bff5;
  cursor: pointer;
}

.center {
  text-align: center ;
}

</style>