<template>

<div> 
  <LolbodyLoading v-show="isLolbodyLoading" :loading="isLolbodyLoading" :color="loadingColor" :size="loadingSize"></LolbodyLoading>
  <div v-show="timeWait" style="text-align: center; font-size: 50px;">
    사용량이 많아서 느립니다. 잠시만 기다려주세요.
  </div>
  <!-- 나중에 만들때 전체 넓이랑 높이 고정값 주고 퍼센트 값으로 높이 정하자.. -->
  <v-container class='card-border mb-10 lolbody-main' >
    <!-- 1번줄 -->
    <v-row v-show="!isLolbodyLoading" class='justify-space-around' :style="{ backgroundImage: 'url(\'' + require(`@/assets/cities/background/${ background }.png`) + '\')', height: '240px', backgroundSize: '100%' }">
    <!-- <v-row class='justify-space-around'> -->
    <!-- <v-row :class='["justify-space-around", { backgroundImage: true } ]' > -->
      <!-- 랭크 -->
      <v-col cols="5" class="align-self-center lolbti-text">

        <!-- 유저 프로필, 랭크, 아이디, 레벨, 챔피언 라인정보. -->
        <v-row class='justify-space-around align-content-center' style='height: 10rem;'>
          <!-- 랭크 -->
          <v-col cols="3" style="position: relative;">
            <v-row>
              <v-col class="center" style="position: relative;">
                <!--<img class="icon big profile-rank" :src="getLolbodyData.userCardReference.soloRank.tier !== 'UNRANKED' && getLolbodyData.userCardReference.soloRank.tier !== undefined ? require(`@/assets/images/tier/${getLolbodyData.userCardReference.soloRank.tier}.png`) : require(`@/assets/images/tier/IRON.png`)" alt="temporarily">-->
                <img class="icon big profile-rank" :src="imageload('tier/'+getLolbodyData.userCardReference.soloRank.tier+'.png')" :class="{'unrankedicon': getLolbodyData.userCardReference.soloRank.tier == 'UNRANKED' ? true : false}" alt="temporarily">
                
                <img class="profilebanner" :src="getLolbodyData.userCardReference.soloRank.tier !== 'UNRANKED' && getLolbodyData.userCardReference.soloRank.tier !== undefined ? require(`@/assets/images/tier_banner/${getLolbodyData.userCardReference.soloRank.tier}.png`) : require(`@/assets/images/tier_banner/UNRANKED.png`)" alt="temporarily">
              </v-col>
              <!-- <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily"> -->
            </v-row>
            <!-- 많이가는 라인 -->
            <v-row class="px-3" style="position: absolute; top: 105px; left: 5px;">
              <v-col class="center">
                <!-- <img class="icon small" :src="getLolbodyData.lineList[0].name.length > 0 && getLolbodyData.lineList[0] !== undefined ? require(`@/assets/images/position/${getLolbodyData.lineList[0].name}.png`) : require(`@/assets/images/error.png`)" alt="temporarily"> -->
                <img class="icon small" :src="getLolbodyData.lineList[0] !== undefined ? require(`@/assets/images/position/${getLolbodyData.lineList[0].name}.png`) : require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <v-col class="center">
                <!-- <img class="icon small" :src="getLolbodyData.lineList[1].name.length > 0 && getLolbodyData.lineList[1] !== undefined ? require(`@/assets/images/position/${getLolbodyData.lineList[1].name}.png`) : require(`@/assets/images/error.png`)" alt="temporarily"> -->
                <img class="icon small" :src="getLolbodyData.lineList[1] !== undefined ? require(`@/assets/images/position/${getLolbodyData.lineList[1].name}.png`) : require(`@/assets/images/error.png`)" alt="temporarily">
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
                  <!-- 뒤로가기 -->
                  <!-- 갱신버튼 -->
                  <v-col cols="2 " class="renewal-lolbody" @click="backBtn">
                    뒤로가기
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
                <KaKaoButton :username="this.$route.params.userName" />
              </v-col>
              <v-col cols=2>
                <TwitterButton :username="this.$route.params.userName" />
              </v-col>
              <v-col cols=2>
                <FacebookButton :username="this.$route.params.userName" />
              </v-col>
              <!-- <MultiSearchBadge :index="index"/> -->
            </v-row>
          </v-col>
        </v-row>
      </v-col>

      <v-col cols="6" class="align-self-center">
        <!-- 롤비티아이 구역 -->  
        <v-row class='align-content-center lolbti-text' style='height: 10rem;' >
          <v-col>
            <v-row>
              <v-col cols='4' class="center align-self-center" style="position: relative;" >
                
                <img class="lolbti-banner" :src="require(`@/assets/cities/${ background }.png`) !== undefined ? require(`@/assets/cities/${ background }.png`) : require(`@/assets/cities/else.png`)" alt="temporarily">
                <!-- <img class="icon big" :src="require(`@/assets/images/champion/${getLolbodyData.champList[0].name}.png`)" alt="temporarily"> -->

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
    <v-row v-show="!isLolbodyLoading" class='justify-space-around my-5 '>
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
                    <img class="icon big" :src="imageload('champion/'+mostChamp.name+'.png')" alt="temporarily">
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
                        <img class="icon small" :src="imageload('champion/'+recommendChamp.id+'.png')" alt="temporarily">
                      </v-col>
                    </v-row>
                  </v-col>

                  <v-col cols="7" v-show="!noRecommendChamps">
                    {{ champType }} type 을 주로하는 당신, 이런 챔프들은 어떤가요?
                  </v-col>
                  <v-col cols="7" v-show="noRecommendChamps">
                    추천이 필요없으신 최고의 소환사시네요.
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



    <!-- *********************************** 보실 부분* ************************************************ -->
    <!-- 레이더 차트 부분 -->
    <!-- 여기서 getLolBodyData.radar 으로 받아온 값을 바로 넣습니다.  -->
    <!-- 바로 반영이 안되는 것이 문제입니다. 그리고 크기가 가끔 어긋나는 것이 문제입니다. -->




      <v-col cols="6" class="card-border card-background">
          
        <v-row>
            <div class="index-explanation ml-5">
                <small>*최근 100 게임 기준</small>
            </div>
        </v-row>
        <!-- 레이더 차트 구역 -->
        <v-row class='test-height2'>
          <!-- <div>
            레이더 차트 컴포넌트
            좌측에 레이더 차트 넣고 우측에는 레이더 차트 설명
            구획 2개로 나눠서하면될듯
          </div> -->
          <v-col>
                <LolbodyRadarChart :radarData="getLolbodyData.radar" :key="RadarKey"/>
          </v-col>

    <!-- *********************************** 여기 까지* ************************************************ -->

            <v-col class="align-self-center mb-16 ml-16">
                <div class='multi-explain' :data-tooltip-text="tooltip.agg">
                공격성이란?
                <!-- <span style="font-size: 10px;">
                    킬, 데미지, 전투 참여 횟수 등 게임을 공격적으로 플레이하는 능력을 의미합니다.
                </span> -->
                </div>
                <div class="my-5 multi-explain" :data-tooltip-text="tooltip.sta">
                안정성이란?
                <!-- <span style="font-size: 10px;">
                    데스나 시에스 등 게임을 안정적으로 풀어나가는 능력을 의미합니다.
                    
                </span> -->
                </div>
                <div class='multi-explain' :data-tooltip-text="tooltip.inf">
                영향력이란?

                </div>
            </v-col>
              <!-- <span style="font-size: 10px;">
                오브젝트나 시야, 어시스트 등 게임 전반에 영향을 미치는 능력을 의미합니다.
              </span> -->
        </v-row>
      </v-col>

    </v-row>







    <!-- *********************************** 보실 부분* ************************************************ -->
    <!-- 바 차트랑, 컬럼차트가 있는 HTML부분입니다. -->




    <!-- 3번줄 -->
    <v-row v-show="!isLolbodyLoading" class='justify-space-around mb-5'>
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
            <LolbodyBarChart :barSeries="getLolbodyData.analysis" :othersSeries="othersSeries" />
            <!-- <LolbodyBarChart :getMyData="getLolbodyData.analysis" :getOtherData="getOtherData"/> -->
            <!--<BarChart type="bar" height="450" :options="computedBarChartOptions" :series="computedBarSeries"></BarChart>-->

          </v-col>
        
        </v-row>


      </v-col>

      <v-col cols="6" class="card-border card-background">
        <!-- 레이더차트 변화, Column 차트 구역 -->
        <v-row class='test-height2'>
          <!-- <div>
            레이더 차트의 변화 // 전체 // 현재 // 이전 막대그래프
          </div> -->
          <v-col class="text-center">
            <LolbodyColumnChart :radarData="getLolbodyData.radarList.slice(0, 10)"/>
            <!-- <ColumnChart type="bar" height="200" :options="computedColumnChartOptions" :series="computedColumnSeries"></ColumnChart> -->
            <!--<ColumnChart type="bar" height="200" :options="columnChartOptions" :series="columnSeries"></ColumnChart>-->
          </v-col>
        </v-row>



    <!-- *********************************** 여기 까지* ************************************************ -->





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
</div>
  
</template>

<script>
import LolbodyLoading from '@/components/multisearch/MultiLoading.vue'

import LolbodyBarChart from '@/components/lolbody/LolbodyBarChart'
import LolbodyColumnChart from '@/components/lolbody/LolbodyColumnChart'
import LolbodyRadarChart from '@/components/lolbody/LolbodyRadarChart'
import LolbodyWordCloud from '@/components/lolbody/LolbodyWordCloud'

import KaKaoButton from '@/components/lolbody/KakaoButton'
import TwitterButton from '@/components/lolbody/TwitterButton'
import FacebookButton from '@/components/lolbody/FacebookButton'

import champion from '@/assets/data/champion.json'
import lolbti from '@/assets/data/lolbti.json'

export default {
  name: 'Lolbody',
  components: {
    LolbodyLoading,
    //ColumnChart,
    //BarChart,
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
        othersSeries:[],

        tooltip: {
            agg: '킬, 데미지, 전투 참여 횟수 등 게임을 공격적으로 플레이하는 능력을 의미합니다.',
            sta: '데스나 시에스 등 게임을 안정적으로 풀어나가는 능력을 의미합니다.',
            inf: '오브젝트나 시야, 어시스트 등 게임 전반에 영향을 미치는 능력을 의미합니다.'
        },
        RadarKey: 0,
        noRecommendChamps: false,
        timeWait: false,
        isLolbodyLoading: true,
        loadingColor: 'grey',
        loadingSize: '80px',


      // *********************************** 보실 부분* ************************************************
      // 바 차트에서 랭크 버튼을 누르는 곳 + 모스트 챔프 버튼


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
    }
  },
    computed: {
        getLolbodyData(){
            return this.$store.getters.getLolbodyData;
        },

    },

    methods: {
        async getActionLolbodydata(userName){
            var myTimeWait = setTimeout(() => {
                this.startTimeWait(true)
            }, 30000);
            //var myTimeOut = setTimeout(() => {
            //    this.startTimeOut()
            //}, 60000);

            myTimeWait;
            //myTimeOut;

            await this.$store.dispatch('fetchLolbodyData', userName);
            //데이터 받고 초기화?
            this.fixEmptyData();
            this.selectMostChamp();
            this.selectLolbti();
            this.selectlolbtiChampType();
            this.setMostChamp();

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
            
            this.isLolbodyLoading = false;
            clearTimeout(myTimeWait);
            //clearTimeout(myTimeOut);
            this.timeWait = false;
        },
    DiaData() {
      this.othersSeries = [this.getLolbodyData.stastics.tierAnalysis.diamond.total, "다이아"];
      
      this.btnTrigger.barChartClick.dia = true
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    PlaData() {
      this.othersSeries = [this.getLolbodyData.stastics.tierAnalysis.platinum.total, "플레티넘"];
      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = true
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    GoldData() {
      this.othersSeries = [this.getLolbodyData.stastics.tierAnalysis.gold.total, "골드"];

      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = true
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    SilverData() {
      this.othersSeries = [this.getLolbodyData.stastics.tierAnalysis.silver.total, "실버"];

      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = true
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = false

    },
    BronzeData() {
      this.othersSeries = [this.getLolbodyData.stastics.tierAnalysis.bronze.total, "브론즈"];

      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = true
      this.btnTrigger.barChartClick.iron = false

    },
    IronData() {
      this.othersSeries = [this.getLolbodyData.stastics.tierAnalysis.iron.total, "아이언"];

      this.btnTrigger.barChartClick.dia = false
      this.btnTrigger.barChartClick.pla = false
      this.btnTrigger.barChartClick.gold = false
      this.btnTrigger.barChartClick.silver = false
      this.btnTrigger.barChartClick.bronze = false
      this.btnTrigger.barChartClick.iron = true
    },


    // column 차트에서 형래님이 사용하신 함수입니다. (챔피언 이미지 넣기 위함.)
    imageload(URL){
      try{
          return require('@/assets/images/'+ URL);
      }catch{
          return require('@/assets/images/error.png');
      }
    },


// *********************************** 여기 까지* ************************************************


    // 갱신을위해서 만든건데 혹시 안되면 지우시고 뒤로가기 만 남기면 될거같습니다.
    // 현재는 시작하자말자 get이 아니라 put으로 데이터를 가져오고 있습니다.
    async renewalLolbody() {
        this.isLolbodyLoading = true;
        const userName = this.$route.params.userName;
        var myTimeWait = setTimeout(() => {
            this.startTimeWait(true)
        }, 30000)
        //var myTimeOut = setTimeout(() => {
        //    this.startTimeOut()
        //}, 60000);
        
        myTimeWait;
        //myTimeOut;

        await this.$store.dispatch('fetchRenewalLolbody', userName);
        //데이터 받고 초기화?
        this.fixEmptyData();
        this.selectMostChamp();
        this.selectLolbti();
        this.selectlolbtiChampType();
        this.setMostChamp();

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
        clearTimeout(myTimeWait);
        //clearTimeout(myTimeOut);
        this.RadarKey++;
        this.isLolbodyLoading = false;
        this.timeWait = false;
    },

    // init() {
    //   this.getLolbodyData.champList = []
    // },

    

// *********************************** 여기 까지* ************************************************

    // 시작시 데이터를 불러옵니다.현재는 get이 아니라 put으로 불러옵니다.
    // 혹시나 갱신 버튼이 안될 때의 상황에 대비하기 위함입니다.
   
    // 시작시 모스트 챔프를 사용하는 값들을 세팅합니다.
    setMostChamp() {
        this.changeChampTitle()
        this.changeChampInfo()
        this.getRecommendChamps(this.mostChamp)
        this.changeChampType()
        this.changeBackground()
        
    },


    // 롤비티아이에서 쓰일 챔피언 type을 정합니다 (tag값)
    selectlolbtiChampType() {
      this.lolbtiChampType = champion.data[this.getLolbodyData.champList[0].name].tags[0]
    },


    // 롤비티아이에서 들어갈 형용사를 정합니다.
    selectLolbti() {
      var radar = this.getLolbodyData.radar

      var a = radar.aggressiveness
      var s = radar.influence // 영향력 변수이름 바뀌었지만 나중에 고쳐야함
      var i = radar.stability // 안정성

      
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


    // 가장 많이한 챔프를 고르고, 한글 이름을 추출해냅니다. 챔피언 클릭시 반응하는 함수입니다.
    selectMostChamp() {
      // console.log('test1')
      this.mostChamp = this.getLolbodyData.champList[0];
      this.korChampName = champion.data[this.getLolbodyData.champList[0].name].name;
      this.changeChampTitle()
      this.changeChampInfo()
      this.getRecommendChamps(this.mostChamp);
      // console.log('test2')
      
      this.btnTrigger.champClick.main = true;
      this.btnTrigger.champClick.sub = false;
    },

    // 두번째로 많이한 챔프를 고르고, 한글 이름을 추출합니다.
    selectSecondChamp() {
      this.mostChamp = this.getLolbodyData.champList[1];
      this.korChampName = champion.data[this.getLolbodyData.champList[1].name].name;
      this.changeChampTitle()
      this.changeChampInfo()
      this.getRecommendChamps(this.mostChamp);
      this.btnTrigger.champClick.main = false;
      this.btnTrigger.champClick.sub = true;
    },



    // 시작할때 들어갈 바뀌게 될 값들 입니다.
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

    // 모스트 챔피언에 따라서 추천해주는 로직입니다. 시작할때 실행됩니다.
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
      this.noRecommendChamps = false
      this.recommendChamps = champList.slice(0, 3)
      if ( this.recommendChamps.length === 0 ) {
        this.noRecommendChamps = true
      }
    },


    // 칼바람만 하는 사람은 클래식 게임 데이터가 오지 않기 때문에 예외처리해둔겁니다.
    fixEmptyData() {
        var emptyData = this.getLolbodyData
        var check = false

        if ( emptyData.radarList.length === 0) {
            check = true
        }
        else if ( emptyData.champList.length === 0 ) {
            check = true        
        }
        else if ( emptyData.lineList.length === 0 ) {
            check = true
        }

        if ( check ) {
            alert("솔랭, 자유랭, 일반 게임을 플레이하지 않은 사용자는 롤바디 데이터가 없습니다.")
            this.$router.push('/Profile/'+this.$route.params.userName)
        }

    },


    // 로딩 및 시간, 백 버튼 함수입니다.
    startLoading() {
      this.isLolbodyLoading = true
    },
    endLoading() {
      this.isLolbodyLoading = false
    },
    startTimeWait(boo) {
      this.timeWait = boo
    },
    startTimeOut() {
      alert('사용자가 너무 많아서 느립니다. 잠시 후에 다시 시도해주세요.')
      this.$router.push('/Profile/'+this.$route.params.userName)
    },
    backBtn() {
        this.$router.go(-1)
    },
  },

  // *********************************** 보실 부분* ************************************************
    mounted(){
        this.isLolbodyLoading = true;
        const userName = this.$route.params.userName;
        this.getActionLolbodydata(userName);
    },
    beforeDestroy(){
        this.$store.state.LolbodyData = {
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
        };
    }
  // 시작할때 timeout을 걸어주고, fetch Lolbody로 데이터를 불러옵니다.
  // 각 데이터를 세팅한 후, 시간내에 끝나지 못하면, 타임아웃에 의해서 글자가 나오고, 이후 뒤로가기 됩니다.
//  async created() {
//    this.timeOut = false,
//    this.timeWait = false,
//    this.$store.commit('toggleNavSearch', false)
//    var myTimeWait = setTimeout(() => {
//      this.startTimeWait(true)
//    }, 10000)
//    var myTimeOut = setTimeout(() => {
//      this.startTimeOut(true)
//    }, 30000)

//    myTimeWait
//    myTimeOut

//    await this.startLoading()
//    // await console.log("1 start")
//    await this.fetchLolbodyData()
//    // await console.log("2 start")
//    await this.setLolbodyData()
//    // await console.log("3 start")
//    await this.setMostChamp()
//    // await console.log("4 start")

//    // await console.log('end')

//    await this.endLoading()
//    await clearTimeout(myTimeWait)
//    await clearTimeout(myTimeOut)
//    // this.$store.dispatch('getLolbodyData', this.$route.params.userName)
//  },




}
</script>

<style scoped>

/* .getBackground {
  background-image: 'url(\'' + require('@/assets/images/test.png') + '\')';
  height: '15em';
  background-size: '100%';
  opacity: '0.7'
  
} */
.lolbody-main{
    min-width: 1200px !important;
    max-width: 1200px !important;
    width: 1200px !important;
}

.unrankedicon{
    transform: translate(8px, 15px);
    width: 50px !important;
    height: 50px !important;
}

[data-tooltip-text]:hover {
	position: relative;
}


[data-tooltip-text]:hover:after {
	content: attr(data-tooltip-text);

  position: absolute;
	bottom: 150%;
	left: -25%;
  padding: 14px;
  width: 160%;

  border-radius: 20px;
    
  background-color: rgba(0, 0, 0, 0.8);
	color: #FFFFFF;
	font-size: 12px;

	z-index: 9999;
}



.multi-explain  {
    width:150px !important;
  font-weight: 900;
  font-size: 20px;
  border-radius: 20px;
  border: 0.1px solid grey;
  text-align: center;
  width: 70%;
}

.multi-explain:hover {
  background-color: #e6e6e6;
}




[data-tooltip-text]:hover {
	position: relative;
}


[data-tooltip-text]:hover:after {
	content: attr(data-tooltip-text);

  position: absolute;
	bottom: 150%;
	left: -25%;
  padding: 14px;
  width: 160%;

  border-radius: 20px;
    
  background-color: rgba(0, 0, 0, 0.8);
	color: #FFFFFF;
	font-size: 12px;

	z-index: 9999;
}



.multi-explain  {
  font-weight: 900;
  font-size: 20px;
  border-radius: 20px;
  border: 0.1px solid grey;
  text-align: center;
  width: 70%;
}

.multi-explain:hover {
  background-color: #e6e6e6;
}



.lolbti-text {
  background-color: rgb(0, 0, 0, 0.5);
  border-radius: 15px;
  /* padding: 9px !important; */
  /* margin-right: 5px !important; */
  /* border-radius: 10px; */
}


.lolbti-banner {
  position: absolute;
  width: 80px;
  top: -60px;
  left: 50px;
  /* background-color: rgb(0, 0, 0, 0.3); */
  /* padding: 5px; */
  /* border-radius: 10px; */
}

.profile-rank {
  position: absolute;
  z-index: 1;
  left : 12px;
}

.profilebanner{
  position: absolute;
  width: 115px;
  left: -11px;
  z-index: 0;
  /* top: 10px; */
}

.renewal-lolbody {
  background-color: #e6e6e6;
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
  height: 240px;
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