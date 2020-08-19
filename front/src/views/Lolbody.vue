<template>

  <!-- 나중에 만들때 전체 넓이랑 높이 고정값 주고 퍼센트 값으로 높이 정하자.. -->
  <v-container class='card-border mb-10'>

    <!-- 1번줄 -->
    <v-row class='justify-space-around' :style="{ backgroundImage: 'url(\'' + require('@/assets/cities/background/Ionia.png') + '\')', height: '15em', backgroundSize: '100vw'}">
      <!-- 랭크 -->
      <v-col cols="5" class="align-self-center card-border">

        <!-- 유저 프로필, 랭크, 아이디, 레벨, 챔피언 라인정보. -->
        <v-row class='justify-space-around align-content-center' style='height: 10rem;'>
          <!-- 랭크 -->
          <v-col cols="3">
            <v-row>
              <v-col class="center">
                <img class="icon big" :src="getLolbodyData.userCardReference.soloRank.tier !== 'UNRANKED' ? require(`@/assets/images/tier/${getLolbodyData.userCardReference.soloRank.tier}.png`) : require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <!-- <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily"> -->
            </v-row>
            <!-- 많이가는 라인 -->
            <v-row>
              <v-col :tooltip="tooltip_content" class="center">
                <img class="icon small" :src="require(`@/assets/images/position/${getLolbodyData.lineList[0].name}.png`)" alt="temporarily">
              </v-col>
              <v-col :tooltip="tooltip_content" class="center">
                <img class="icon small" :src="require(`@/assets/images/position/${getLolbodyData.lineList[1].name}.png`)" alt="temporarily">
              </v-col>
              <!-- <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.mainLane}.png`)" alt="mainLane">
              <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.subLane}.png`)" alt="subLane"> -->
            </v-row>
          </v-col>

          <!-- 유저 텍스트정보 -->
          <v-col cols="8">
            <v-row>
              <v-col>
                <v-row>
                  <!-- 유저 이름 -->
                  {{ getLolbodyData.userCardReference.summonerName  }}
                </v-row>
                <v-row>
                  <!-- 계급 -->
                  {{ getLolbodyData.userCardReference.soloRank.tier }} {{ getLolbodyData.userCardReference.soloRank.rank }}
                </v-row>
                <v-row>
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
                <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily">

              </v-col>
              <v-col>
                <v-row>

                  데마시아의 용맹한 전사
                  <br>
                  (데마시아 // 용맹한 // fighter 느낌)
                  <br>
                  당신은 데마시아의 용맹한 어쩌구저쩌구입니다. ~~ 하면서 text를 짜야함.
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
              <v-col class="border-box center" @click="selectMostChamp()">
                Most Champ
              </v-col>
              
              <v-col class="border-box center" @click="selectSecondChamp()">
                Second Champ
              </v-col>
            </v-row>

            <v-row class='align-content-center' style="height: 90%;">
              <v-col >
                
                <!-- 선택한 라인에서 높은 숙련도 챔피언 정보 -->
                <v-row class="justify-space-around">
                  <v-col class="center" :tooltip="tooltip_content" cols="4">
                    <img class="icon big" :src="require(`@/assets/images/champion/${mostChamp.name}.png`)" alt="temporarily">
                  </v-col>

                  <v-col cols="7">
                    <v-row>
                      <v-col>
                        <b> {{ champTitle }} {{ mostChamp.name }} </b>를(을) 주로 하시는군요. 
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
                      <v-col :tooltip="tooltip_content" class="center" v-for="( recommendChamp, index ) in recommendChamps" :key="index">
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
            <d  iv>
              안정성:  ????????????????????
            </d>
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
        <v-row class="justify-space-between">
          <v-col class="bar-rank" @click="totalData()">
            전체
          </v-col>
          <v-col class="bar-rank" @click="DiaData()">
            다이아
          </v-col>
          <v-col class="bar-rank" @click="PlaData()">
            플레
          </v-col>
          <v-col class="bar-rank" @click="GoldData()">
            골드
          </v-col>
          <v-col class="bar-rank" @click="SilverData()">
            실버
          </v-col>
          <v-col class="bar-rank"  @click="BronzeData()">
            브론즈
          </v-col>
          <v-col class="bar-rank" @click="IronData()">
            아이언
          </v-col>
        </v-row>
        <v-row style="height: 30em;">
          <v-col>
            <!-- 우측 막대 그래프 분석 데이터 산출물 이용. 전체랑 최근 20게임 평균 비교 col 하나 먹이고 row로 3~4개로 나눠서하면될듯 -->
            <!-- 동티어 동일챔프 기준? -->
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
      tooltip_content: 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
      getLolbodyData: {},
      getOtherData: {},
      mostChamp: "error",
      champTitle: "",
      champInfo: {},
      recommendChamps: [],
      champType: "",
    }
  },
  computed: {
    // ...mapState(['lolbodyData']),
    // ...mapGetters(['getLolbodyData']),
  },
  watch: {
    getLolbodyData: {
      deep: true,
      immediate: true,
      handler() {
        this.selectMostChamp()
      }
    },
    mostChamp: {
      deep: true,
      immediate: true,
      handler() {
        this.changeChampTitle()
        this.changeChampInfo()
        this.getRecommendChamps(this.mostChamp)
        this.changeChampType()

      }
    }
  },
  methods: {
    fetchLolbodyData() {
      axios
        .get(`https://lolbody.gq` + `/api/lolbody/${this.$route.params.userName}`)
        .then(res => {
          this.getLolbodyData = res.data
          this.getOtherData = res.data.stastics.stastics.total.total
        })
        .catch(err => {
          console.log(err)
        })
    },
    totalData() {
      this.getOtherData = this.getLolbodyData.stastics.stastics.total.total
    },
    DiaData() {
      this.getOtherData = this.getLolbodyData.stastics.stastics.diamond.total
    },
    PlaData() {
      this.getOtherData = this.getLolbodyData.stastics.stastics.platinum.total
    },
    GoldData() {
      this.getOtherData = this.getLolbodyData.stastics.stastics.gold.total
    },
    SilverData() {
      this.getOtherData = this.getLolbodyData.stastics.stastics.silver.total
    },
    BronzeData() {
      this.getOtherData = this.getLolbodyData.stastics.stastics.bronze.total
    },
    IronData() {
      this.getOtherData = this.getLolbodyData.stastics.stastics.iron.total
    },

    selectMostChamp() {
      this.mostChamp = this.getLolbodyData.champList[0]
    },
    selectSecondChamp() {
      this.mostChamp = this.getLolbodyData.champList[1]
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
        for ( var recommendChamp in champion.data ) {
          for ( var tempType of champion.data[recommendChamp].tags ) {
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
  created() {
    this.$store.commit('toggleNavSearch', false)
    this.fetchLolbodyData()

    // this.$store.dispatch('getLolbodyData', this.$route.params.userName)
  },

}
</script>

<style scoped>

.bar-rank {
  text-align: right;
  font-size: 12px;
}

.bar-rank:hover {
  cursor: pointer;
  background: green;
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
  border: 0.5px solid white;
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