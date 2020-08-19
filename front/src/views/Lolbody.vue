<template>

  <!-- 나중에 만들때 전체 넓이랑 높이 고정값 주고 퍼센트 값으로 높이 정하자.. -->
  <v-container class='card-border mb-10'>

    <!-- 1번줄 -->
    <v-row class='justify-space-around' :style="{ backgroundImage: 'url(\'' + require(`@/assets/cities/background/${ this.background }.png`) + '\')', height: '15em', backgroundSize: '100%' }">
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
                <img class="icon big" :src="require(`@/assets/images/champion/${mostChamp.name}.png`)" alt="temporarily">

              </v-col>
              <v-col>
                <v-row>
                  <v-col>
                    <v-row>
                      <v-col style="color: white">
                        {{ background }}의 {{ lolbti }} 소환사 {{ getLolbodyData.userCardReference.summonerName }}

                      </v-col>

                    </v-row>
                    <v-row>
                      <v-col style="color: white">
                        공격성: {{ Math.round(getLolbodyData.radar.aggressiveness*100) }}점
                        
                        영향력: {{ Math.round(getLolbodyData.radar.influence*100) }}점
                        
                        안정성: {{ Math.round(getLolbodyData.radar.stability*100) }}점
                        <br>
                        당신은 {{ background }}의 {{ lolbti }} {{ lolbtiChamp }} 이시군요. 

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
              <v-col class="border-box center" @click="selectMostChamp()">
                주챔
              </v-col>
              
              <v-col class="border-box center" @click="selectSecondChamp()">
                부챔
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
      "radarList": [
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
        {
          "timestamp": 1594040182698,
          "name": "Ashe",
          "radarReference": {
            "aggressiveness": 0.30680272983080187,
            "stability": 0.45619970375522484,
            "influence": 0.3374594914344873
          }
        },
        {
          "timestamp": 1593965120700,
          "name": "Ashe",
          "radarReference": {
            "aggressiveness": 0.7696734637892333,
            "stability": 0.3450420723917498,
            "influence": 0.5842323756487487
          }
        },
        {
          "timestamp": 1592744187205,
          "name": "LeeSin",
          "radarReference": {
            "aggressiveness": 0.3608116562407692,
            "stability": 0.2504481268925041,
            "influence": 0.4381472854805352
          }
        },
        {
          "timestamp": 1592729849109,
          "name": "Ezreal",
          "radarReference": {
            "aggressiveness": 0.4039367122406035,
            "stability": 0.35020844431794496,
            "influence": 0.20019704899694768
          }
        },
        {
          "timestamp": 1592574410281,
          "name": "Trundle",
          "radarReference": {
            "aggressiveness": 0.5705912931896341,
            "stability": 0.4658039350578547,
            "influence": 0.5502503390325287
          }
        },
        {
          "timestamp": 1592572606394,
          "name": "Ashe",
          "radarReference": {
            "aggressiveness": 0.6699436490517785,
            "stability": 0.6680602054601726,
            "influence": 0.7582238323529494
          }
        },
        {
          "timestamp": 1592570473375,
          "name": "Ezreal",
          "radarReference": {
            "aggressiveness": 0.5099556339296432,
            "stability": 0.3809395107278179,
            "influence": 0.3790890164587355
          }
        },
        {
          "timestamp": 1592567894294,
          "name": "Trundle",
          "radarReference": {
            "aggressiveness": 0.6482683058843413,
            "stability": 0.506481616690711,
            "influence": 0.772434354006427
          }
        },
        {
          "timestamp": 1591816336751,
          "name": "Aphelios",
          "radarReference": {
            "aggressiveness": 0.48042181886568996,
            "stability": 0.4293797534681684,
            "influence": 0.3286297285509294
          }
        },
        {
          "timestamp": 1591814654523,
          "name": "Draven",
          "radarReference": {
            "aggressiveness": 0.254945288226035,
            "stability": 0.24894820956084704,
            "influence": 0.07781017644929353
          }
        },
        {
          "timestamp": 1591812192441,
          "name": "LeeSin",
          "radarReference": {
            "aggressiveness": 0.36884043225781626,
            "stability": 0.23734537718486173,
            "influence": 0.4915256071039935
          }
        },
        {
          "timestamp": 1591809793017,
          "name": "Jhin",
          "radarReference": {
            "aggressiveness": 0.7935910397075535,
            "stability": 0.6153720588496313,
            "influence": 0.5575686707289982
          }
        },
        {
          "timestamp": 1591806748845,
          "name": "Vayne",
          "radarReference": {
            "aggressiveness": 0.6526435714651238,
            "stability": 0.3424953444099928,
            "influence": 0.30645255305818914
          }
        },
        {
          "timestamp": 1591804997219,
          "name": "Draven",
          "radarReference": {
            "aggressiveness": 0.24270858088032765,
            "stability": 0.35329035815725046,
            "influence": 0.3328256953067931
          }
        },
        {
          "timestamp": 1591802664706,
          "name": "Kalista",
          "radarReference": {
            "aggressiveness": 0.9120443580728069,
            "stability": 0.40917548847944674,
            "influence": 0.4774634197215084
          }
        },
        {
          "timestamp": 1591799316426,
          "name": "Hecarim",
          "radarReference": {
            "aggressiveness": 0.32641268278766616,
            "stability": 0.1958381276625589,
            "influence": 0.4611272668179917
          }
        },
        {
          "timestamp": 1591797228497,
          "name": "Graves",
          "radarReference": {
            "aggressiveness": 0.42969638402108085,
            "stability": 0.4543813529098985,
            "influence": 0.4703704383621696
          }
        },
        {
          "timestamp": 1591785214754,
          "name": "Ezreal",
          "radarReference": {
            "aggressiveness": 0.45950895506598716,
            "stability": 0.6097175500872057,
            "influence": 0.5002918948086775
          }
        },
        {
          "timestamp": 1590746104579,
          "name": "Ezreal",
          "radarReference": {
            "aggressiveness": 0.6295784776311107,
            "stability": 0.38187702192481404,
            "influence": 0.4163909469933282
          }
        },
        {
          "timestamp": 1590744202784,
          "name": "Kaisa",
          "radarReference": {
            "aggressiveness": 0.4853459544292112,
            "stability": 0.3055371241128648,
            "influence": 0.4930745769464322
          }
        },
        {
          "timestamp": 1590741404136,
          "name": "Jax",
          "radarReference": {
            "aggressiveness": 0.11650937335576021,
            "stability": 0.4011393555444358,
            "influence": 0.2183469371425407
          }
        },
        {
          "timestamp": 1590409815976,
          "name": "Cassiopeia",
          "radarReference": {
            "aggressiveness": 0.4980315216019054,
            "stability": 0.7459893530733638,
            "influence": 0.7154597878532186
          }
        },
        {
          "timestamp": 1590407885248,
          "name": "Vayne",
          "radarReference": {
            "aggressiveness": 0.34208914755114267,
            "stability": 0.6386425921096929,
            "influence": 0.5737960915795122
          }
        },
        {
          "timestamp": 1590405864473,
          "name": "Caitlyn",
          "radarReference": {
            "aggressiveness": 0.6540926599386937,
            "stability": 0.7213046755614547,
            "influence": 0.6750134623073238
          }
        },
        {
          "timestamp": 1589565213332,
          "name": "Volibear",
          "radarReference": {
            "aggressiveness": 0.45716276516685905,
            "stability": 0.6009638191802159,
            "influence": 0.4770994089360805
          }
        },
        {
          "timestamp": 1589563193938,
          "name": "Sett",
          "radarReference": {
            "aggressiveness": 0.23598120047539198,
            "stability": 0.45875535345543045,
            "influence": 0.43632624362754946
          }
        },
        {
          "timestamp": 1589560998358,
          "name": "Darius",
          "radarReference": {
            "aggressiveness": 0.3642044227818575,
            "stability": 0.38231916455404846,
            "influence": 0.167711620965419
          }
        },
        {
          "timestamp": 1589559021591,
          "name": "Trundle",
          "radarReference": {
            "aggressiveness": 0.5950446077403516,
            "stability": 0.3939352042995649,
            "influence": 0.509189360194511
          }
        },
        {
          "timestamp": 1589556826643,
          "name": "Jax",
          "radarReference": {
            "aggressiveness": 0.4646261426760406,
            "stability": 0.4185768130372445,
            "influence": 0.36089948053388765
          }
        },
        {
          "timestamp": 1589555061044,
          "name": "Fiora",
          "radarReference": {
            "aggressiveness": 0.3221508358838366,
            "stability": 0.36917255462723775,
            "influence": 0.19976157093709654
          }
        },
        {
          "timestamp": 1589552299707,
          "name": "Jax",
          "radarReference": {
            "aggressiveness": 0.6236861038380357,
            "stability": 0.5296777718145235,
            "influence": 0.4590667004677293
          }
        }
      ],
      "champList": [
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
        {
          "name": "Jax",
          "games": 4,
          "wins": 3,
          "losses": 1
        },
        {
          "name": "Vayne",
          "games": 3,
          "wins": 3,
          "losses": 0
        },
        {
          "name": "Ashe",
          "games": 3,
          "wins": 2,
          "losses": 1
        },
        {
          "name": "Trundle",
          "games": 3,
          "wins": 2,
          "losses": 1
        },
        {
          "name": "LeeSin",
          "games": 2,
          "wins": 0,
          "losses": 2
        },
        {
          "name": "Draven",
          "games": 2,
          "wins": 1,
          "losses": 1
        },
        {
          "name": "Graves",
          "games": 2,
          "wins": 0,
          "losses": 2
        },
        {
          "name": "Jhin",
          "games": 1,
          "wins": 0,
          "losses": 1
        },
        {
          "name": "Hecarim",
          "games": 1,
          "wins": 0,
          "losses": 1
        },
        {
          "name": "Cassiopeia",
          "games": 1,
          "wins": 1,
          "losses": 0
        },
        {
          "name": "TwistedFate",
          "games": 1,
          "wins": 0,
          "losses": 1
        },
        {
          "name": "Darius",
          "games": 1,
          "wins": 0,
          "losses": 1
        },
        {
          "name": "Aphelios",
          "games": 1,
          "wins": 0,
          "losses": 1
        },
        {
          "name": "Kaisa",
          "games": 1,
          "wins": 1,
          "losses": 0
        },
        {
          "name": "Fiora",
          "games": 1,
          "wins": 1,
          "losses": 0
        },
        {
          "name": "Kalista",
          "games": 1,
          "wins": 0,
          "losses": 1
        },
        {
          "name": "Volibear",
          "games": 1,
          "wins": 0,
          "losses": 1
        },
        {
          "name": "Sett",
          "games": 1,
          "wins": 1,
          "losses": 0
        },
        {
          "name": "Caitlyn",
          "games": 1,
          "wins": 1,
          "losses": 0
        }
      ],
      "lineList": [
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
      "stastics": {
        "queueId": 420,
        "stastics": {
          "bronze": {
            "total": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 556.902031246775,
                "damageDealtToObjectivesPerMin": 278.63965270284234,
                "visionScorePerMin": 0.7754622411706265,
                "totalDamageTakenPerMin": 738.3541279229306,
                "totalMinionsKilledPerMin": 3.6004518941570733,
                "killsRatio": 0.4697051128648474,
                "deathsRatio": 0.20019399872399124,
                "killAssistPerMin": 0.4539973360332231,
                "killsPerMin": 0.19322071656849327,
                "deathsPerMin": 0.19421217766933047,
                "assistsPerMin": 0.2607766194647298,
                "totalHealPerMin": 56.96310716687433,
                "damageSelfMitigatedPerMin": 570.3620187570089,
                "damageDealtToTurretsPerMin": 90.1949151032512,
                "timeCCingOthersPerMin": 0.7826028685491971,
                "neutralMinionsKilledPerMin": 0.9765651720603046,
                "totalTimeCrowdControlDealtPerMin": 6.90807552245372,
                "visionWardsBoughtInGamePerMin": 0.06109344184387421,
                "neutralMinionsKilledEnemyJunglePerMin": 0.10087298693117916,
                "wardsPlacedPerMin": 0.34209051547457675,
                "wardsKilledPerMin": 0.07742938916567871
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 278.85998170619627,
                "damageDealtToObjectivesPerMin": 290.07718531181183,
                "visionScorePerMin": 0.4659637356687442,
                "totalDamageTakenPerMin": 266.1488295413078,
                "totalMinionsKilledPerMin": 2.2625473297773,
                "killsRatio": 0.16113382189614808,
                "deathsRatio": 0.08238403393107163,
                "killAssistPerMin": 0.21404049501369485,
                "killsPerMin": 0.14679339568803718,
                "deathsPerMin": 0.09860211321956827,
                "assistsPerMin": 0.1618125413545529,
                "totalHealPerMin": 142.92318741536482,
                "damageSelfMitigatedPerMin": 378.0264698471189,
                "damageDealtToTurretsPerMin": 100.26748059805321,
                "timeCCingOthersPerMin": 0.7120241383667295,
                "neutralMinionsKilledPerMin": 1.5035804927774503,
                "totalTimeCrowdControlDealtPerMin": 6.530797021696798,
                "visionWardsBoughtInGamePerMin": 0.07009514281000631,
                "neutralMinionsKilledEnemyJunglePerMin": 0.2022971471016192,
                "wardsPlacedPerMin": 0.20989167044080007,
                "wardsKilledPerMin": 0.07967487503281839
              }
            },
            "top": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 609.7395512314838,
                "damageDealtToObjectivesPerMin": 254.52872273023144,
                "visionScorePerMin": 0.5480030724116973,
                "totalDamageTakenPerMin": 845.2821245261247,
                "totalMinionsKilledPerMin": 5.2187035049710975,
                "killsRatio": 0.3966257821276104,
                "deathsRatio": 0.19554061297196584,
                "killAssistPerMin": 0.3795694191838233,
                "killsPerMin": 0.187355505985788,
                "deathsPerMin": 0.18742841905633095,
                "assistsPerMin": 0.19221391319803532,
                "totalHealPerMin": 14.886133462009829,
                "damageSelfMitigatedPerMin": 781.6959633202182,
                "damageDealtToTurretsPerMin": 143.1914996003839,
                "timeCCingOthersPerMin": 0.7793940228673859,
                "neutralMinionsKilledPerMin": 0.31191417076413663,
                "totalTimeCrowdControlDealtPerMin": 7.44373043578653,
                "visionWardsBoughtInGamePerMin": 0.03706336702752625,
                "neutralMinionsKilledEnemyJunglePerMin": 0.08173223084206148,
                "wardsPlacedPerMin": 0.25673113155505195,
                "wardsKilledPerMin": 0.04558675212120043
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 275.37717222036275,
                "damageDealtToObjectivesPerMin": 200.8626438245906,
                "visionScorePerMin": 0.24688512179350586,
                "totalDamageTakenPerMin": 258.75351096441887,
                "totalMinionsKilledPerMin": 1.2542554152978695,
                "killsRatio": 0.1593593076779166,
                "deathsRatio": 0.08417011451041359,
                "killAssistPerMin": 0.19371811531746227,
                "killsPerMin": 0.13399037276046527,
                "deathsPerMin": 0.09578232724799612,
                "assistsPerMin": 0.12788548629745916,
                "totalHealPerMin": 76.73704835313175,
                "damageSelfMitigatedPerMin": 425.51774124986565,
                "damageDealtToTurretsPerMin": 119.26957881130812,
                "timeCCingOthersPerMin": 0.5946110502250725,
                "neutralMinionsKilledPerMin": 0.34077378600876085,
                "totalTimeCrowdControlDealtPerMin": 7.9975935101936075,
                "visionWardsBoughtInGamePerMin": 0.04807976925440162,
                "neutralMinionsKilledEnemyJunglePerMin": 0.1463004784466043,
                "wardsPlacedPerMin": 0.08774361019171119,
                "wardsKilledPerMin": 0.04438005189160441
              }
            },
            "mid": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 642.8148350731208,
                "damageDealtToObjectivesPerMin": 175.01427536131874,
                "visionScorePerMin": 0.5998233452657725,
                "totalDamageTakenPerMin": 745.0670835492419,
                "totalMinionsKilledPerMin": 4.979995023905392,
                "killsRatio": 0.4685214162560585,
                "deathsRatio": 0.21091681210925,
                "killAssistPerMin": 0.4551941908977535,
                "killsPerMin": 0.2398827750211921,
                "deathsPerMin": 0.20346658815521465,
                "assistsPerMin": 0.21531141587656136,
                "totalHealPerMin": 8.97074416170059,
                "damageSelfMitigatedPerMin": 530.2656242906477,
                "damageDealtToTurretsPerMin": 89.93896155813844,
                "timeCCingOthersPerMin": 0.7156544221607417,
                "neutralMinionsKilledPerMin": 0.3801343320117444,
                "totalTimeCrowdControlDealtPerMin": 5.524775221619304,
                "visionWardsBoughtInGamePerMin": 0.05331009957222585,
                "neutralMinionsKilledEnemyJunglePerMin": 0.07930359887323514,
                "wardsPlacedPerMin": 0.2836104918111651,
                "wardsKilledPerMin": 0.05695761810314071
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 272.9670600175012,
                "damageDealtToObjectivesPerMin": 174.99058047073032,
                "visionScorePerMin": 0.2371019034740689,
                "totalDamageTakenPerMin": 210.70553957939708,
                "totalMinionsKilledPerMin": 1.2831828792163211,
                "killsRatio": 0.14867469334512806,
                "deathsRatio": 0.0801001325644276,
                "killAssistPerMin": 0.20898090303402206,
                "killsPerMin": 0.15822111634643202,
                "deathsPerMin": 0.09629315961474866,
                "assistsPerMin": 0.11882534259420058,
                "totalHealPerMin": 40.65941872304405,
                "damageSelfMitigatedPerMin": 321.9505420553038,
                "damageDealtToTurretsPerMin": 104.70288676747307,
                "timeCCingOthersPerMin": 0.7776605765031414,
                "neutralMinionsKilledPerMin": 0.4043511513708824,
                "totalTimeCrowdControlDealtPerMin": 5.447367341365425,
                "visionWardsBoughtInGamePerMin": 0.05538033375869925,
                "neutralMinionsKilledEnemyJunglePerMin": 0.17104876926440604,
                "wardsPlacedPerMin": 0.10057846806589543,
                "wardsKilledPerMin": 0.05522249736871383
              }
            },
            "jungle": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 509.5573514300778,
                "damageDealtToObjectivesPerMin": 594.5424123323085,
                "visionScorePerMin": 0.7207862764567631,
                "totalDamageTakenPerMin": 969.7602362370134,
                "totalMinionsKilledPerMin": 1.23444870433578,
                "killsRatio": 0.4887757478880439,
                "deathsRatio": 0.2040412026199444,
                "killAssistPerMin": 0.4711304549852187,
                "killsPerMin": 0.21276670016719948,
                "deathsPerMin": 0.20081214677984988,
                "assistsPerMin": 0.2583637548180192,
                "totalHealPerMin": 63.83042172917575,
                "damageSelfMitigatedPerMin": 727.8732799353222,
                "damageDealtToTurretsPerMin": 45.463087221538,
                "timeCCingOthersPerMin": 0.7720207700654882,
                "neutralMinionsKilledPerMin": 3.747683887168487,
                "totalTimeCrowdControlDealtPerMin": 10.587540413294194,
                "visionWardsBoughtInGamePerMin": 0.0735255467488573,
                "neutralMinionsKilledEnemyJunglePerMin": 0.2744790520875803,
                "wardsPlacedPerMin": 0.23483053130655565,
                "wardsKilledPerMin": 0.0858112753582697
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 241.59355113220929,
                "damageDealtToObjectivesPerMin": 382.8962363539778,
                "visionScorePerMin": 0.26468701526055205,
                "totalDamageTakenPerMin": 224.62584434560736,
                "totalMinionsKilledPerMin": 0.6066611499304938,
                "killsRatio": 0.16746565688478282,
                "deathsRatio": 0.08304707501896923,
                "killAssistPerMin": 0.221203406409366,
                "killsPerMin": 0.15044188904213246,
                "deathsPerMin": 0.10825937310266974,
                "assistsPerMin": 0.1531424846687992,
                "totalHealPerMin": 211.74659235186402,
                "damageSelfMitigatedPerMin": 382.2185885011619,
                "damageDealtToTurretsPerMin": 56.89112079234006,
                "timeCCingOthersPerMin": 0.9287048446315688,
                "neutralMinionsKilledPerMin": 1.0998064182084446,
                "totalTimeCrowdControlDealtPerMin": 6.061561618106272,
                "visionWardsBoughtInGamePerMin": 0.0790303431244794,
                "neutralMinionsKilledEnemyJunglePerMin": 0.31360370764621065,
                "wardsPlacedPerMin": 0.1139207852984434,
                "wardsKilledPerMin": 0.07248956681842854
              }
            },
            "bottom": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 659.3344610003612,
                "damageDealtToObjectivesPerMin": 288.4473261109095,
                "visionScorePerMin": 0.5797015933258827,
                "totalDamageTakenPerMin": 592.3276448735953,
                "totalMinionsKilledPerMin": 5.5287968747073375,
                "killsRatio": 0.4956837109813063,
                "deathsRatio": 0.19391601076753792,
                "killAssistPerMin": 0.4811929441407392,
                "killsPerMin": 0.22802036544132576,
                "deathsPerMin": 0.1873410978525292,
                "assistsPerMin": 0.25317257869941345,
                "totalHealPerMin": 85.39502614757494,
                "damageSelfMitigatedPerMin": 323.5420435965365,
                "damageDealtToTurretsPerMin": 131.7958330976127,
                "timeCCingOthersPerMin": 0.4940099058157354,
                "neutralMinionsKilledPerMin": 0.3981456511870972,
                "totalTimeCrowdControlDealtPerMin": 5.858147150566505,
                "visionWardsBoughtInGamePerMin": 0.038071352231093834,
                "neutralMinionsKilledEnemyJunglePerMin": 0.061222012789828174,
                "wardsPlacedPerMin": 0.2692044026728977,
                "wardsKilledPerMin": 0.0605218599303111
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 292.8458655093712,
                "damageDealtToObjectivesPerMin": 219.29890370914063,
                "visionScorePerMin": 0.2445889539292836,
                "totalDamageTakenPerMin": 166.8945725216769,
                "totalMinionsKilledPerMin": 1.1519696597362628,
                "killsRatio": 0.15618825409973738,
                "deathsRatio": 0.07573046452582925,
                "killAssistPerMin": 0.21740810251906345,
                "killsPerMin": 0.1465783501035872,
                "deathsPerMin": 0.09169719174885806,
                "assistsPerMin": 0.1376031653180589,
                "totalHealPerMin": 61.30508417691794,
                "damageSelfMitigatedPerMin": 157.45907908055506,
                "damageDealtToTurretsPerMin": 105.6407163579972,
                "timeCCingOthersPerMin": 0.45570444771666796,
                "neutralMinionsKilledPerMin": 0.3641161240571307,
                "totalTimeCrowdControlDealtPerMin": 6.683459749062039,
                "visionWardsBoughtInGamePerMin": 0.04609640746269216,
                "neutralMinionsKilledEnemyJunglePerMin": 0.11636043723805622,
                "wardsPlacedPerMin": 0.08225764730328362,
                "wardsKilledPerMin": 0.0572942912708509
              }
            },
            "support": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 363.8492251875954,
                "damageDealtToObjectivesPerMin": 81.41356804106933,
                "visionScorePerMin": 1.4269909953081517,
                "totalDamageTakenPerMin": 539.1595700991343,
                "totalMinionsKilledPerMin": 1.049574168998108,
                "killsRatio": 0.4991442107313703,
                "deathsRatio": 0.1965618520588977,
                "killAssistPerMin": 0.4831342554425533,
                "killsPerMin": 0.09851632441985478,
                "deathsPerMin": 0.192019450115511,
                "assistsPerMin": 0.3846179310226985,
                "totalHealPerMin": 111.78398062267999,
                "damageSelfMitigatedPerMin": 487.2195533403552,
                "damageDealtToTurretsPerMin": 40.70846518233096,
                "timeCCingOthersPerMin": 1.149823233222637,
                "neutralMinionsKilledPerMin": 0.048231202503326034,
                "totalTimeCrowdControlDealtPerMin": 5.126817642118942,
                "visionWardsBoughtInGamePerMin": 0.1033633100377574,
                "neutralMinionsKilledEnemyJunglePerMin": 0.007862668220660961,
                "wardsPlacedPerMin": 0.6650711418548431,
                "wardsKilledPerMin": 0.13812135985308224
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 187.23778087532867,
                "damageDealtToObjectivesPerMin": 75.52338749953248,
                "visionScorePerMin": 0.5380976335310057,
                "totalDamageTakenPerMin": 197.17105024294827,
                "totalMinionsKilledPerMin": 0.5771352222447746,
                "killsRatio": 0.15144811208994205,
                "deathsRatio": 0.08763141791784207,
                "killAssistPerMin": 0.21174040222769774,
                "killsPerMin": 0.09016963775344604,
                "deathsPerMin": 0.09965889500320144,
                "assistsPerMin": 0.18991136261416236,
                "totalHealPerMin": 195.51426952705,
                "damageSelfMitigatedPerMin": 347.85740481113874,
                "damageDealtToTurretsPerMin": 42.437448420278,
                "timeCCingOthersPerMin": 0.5381439248793787,
                "neutralMinionsKilledPerMin": 0.10579812559831812,
                "totalTimeCrowdControlDealtPerMin": 4.323466547048781,
                "visionWardsBoughtInGamePerMin": 0.08801102824836554,
                "neutralMinionsKilledEnemyJunglePerMin": 0.03680816722756102,
                "wardsPlacedPerMin": 0.2250514834940096,
                "wardsKilledPerMin": 0.11289421528493596
              }
            }
          },
          "silver": {
            "total": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 532.2308111924759,
                "damageDealtToObjectivesPerMin": 291.2521436556092,
                "visionScorePerMin": 0.8288937757341767,
                "totalDamageTakenPerMin": 720.6735834529775,
                "totalMinionsKilledPerMin": 3.882429144010504,
                "killsRatio": 0.4678902545381336,
                "deathsRatio": 0.2,
                "killAssistPerMin": 0.4390664530153812,
                "killsPerMin": 0.1862917282711153,
                "deathsPerMin": 0.1918193712962151,
                "assistsPerMin": 0.2527747247442658,
                "totalHealPerMin": 62.993304890582245,
                "damageSelfMitigatedPerMin": 531.4397879950192,
                "damageDealtToTurretsPerMin": 90.21889239562991,
                "timeCCingOthersPerMin": 0.7682589588715077,
                "neutralMinionsKilledPerMin": 1.0194292969601348,
                "totalTimeCrowdControlDealtPerMin": 7.371494216457398,
                "visionWardsBoughtInGamePerMin": 0.0761515171848409,
                "neutralMinionsKilledEnemyJunglePerMin": 0.09885181032210807,
                "wardsPlacedPerMin": 0.35682836698437875,
                "wardsKilledPerMin": 0.0959082287586584
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 263.4183497030461,
                "damageDealtToObjectivesPerMin": 311.13184527682614,
                "visionScorePerMin": 0.4831868479119267,
                "totalDamageTakenPerMin": 260.83003645481966,
                "totalMinionsKilledPerMin": 2.4503807200548464,
                "killsRatio": 0.16297939837007944,
                "deathsRatio": 0.08761126529992871,
                "killAssistPerMin": 0.2199478019600021,
                "killsPerMin": 0.14182969640637125,
                "deathsPerMin": 0.10132154981508038,
                "assistsPerMin": 0.16498219193145894,
                "totalHealPerMin": 158.99958018872474,
                "damageSelfMitigatedPerMin": 332.9137095801872,
                "damageDealtToTurretsPerMin": 108.12914501300806,
                "timeCCingOthersPerMin": 0.6330516977010535,
                "neutralMinionsKilledPerMin": 1.5860755896827845,
                "totalTimeCrowdControlDealtPerMin": 6.726658053778915,
                "visionWardsBoughtInGamePerMin": 0.07712600942924297,
                "neutralMinionsKilledEnemyJunglePerMin": 0.20287163405297026,
                "wardsPlacedPerMin": 0.21678627684121865,
                "wardsKilledPerMin": 0.09009458110952832
              }
            },
            "top": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 583.9073947700143,
                "damageDealtToObjectivesPerMin": 240.41081943415276,
                "visionScorePerMin": 0.5925320937997418,
                "totalDamageTakenPerMin": 819.8636974817379,
                "totalMinionsKilledPerMin": 5.691110665304681,
                "killsRatio": 0.3704741695945365,
                "deathsRatio": 0.1997612487680692,
                "killAssistPerMin": 0.3482908753181593,
                "killsPerMin": 0.180127731135796,
                "deathsPerMin": 0.19092738772616066,
                "assistsPerMin": 0.16816314418236333,
                "totalHealPerMin": 22.239277353637632,
                "damageSelfMitigatedPerMin": 707.0482886495705,
                "damageDealtToTurretsPerMin": 138.21469916941228,
                "timeCCingOthersPerMin": 0.7653913972220371,
                "neutralMinionsKilledPerMin": 0.2622070360090403,
                "totalTimeCrowdControlDealtPerMin": 7.951807043114421,
                "visionWardsBoughtInGamePerMin": 0.048622110475995725,
                "neutralMinionsKilledEnemyJunglePerMin": 0.07373260493102972,
                "wardsPlacedPerMin": 0.2620326711931791,
                "wardsKilledPerMin": 0.05700667857425201
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 240.9274359689756,
                "damageDealtToObjectivesPerMin": 208.24313133625301,
                "visionScorePerMin": 0.2696225524559738,
                "totalDamageTakenPerMin": 241.10446609934127,
                "totalMinionsKilledPerMin": 1.2232873765969745,
                "killsRatio": 0.15667401257944286,
                "deathsRatio": 0.09300978753233126,
                "killAssistPerMin": 0.1953943943406506,
                "killsPerMin": 0.13286083879576274,
                "deathsPerMin": 0.10127351115394323,
                "assistsPerMin": 0.124434232774599,
                "totalHealPerMin": 96.2400132994768,
                "damageSelfMitigatedPerMin": 364.6950784076706,
                "damageDealtToTurretsPerMin": 124.08048507606176,
                "timeCCingOthersPerMin": 0.5959789174334199,
                "neutralMinionsKilledPerMin": 0.2919227923629949,
                "totalTimeCrowdControlDealtPerMin": 8.34570349771116,
                "visionWardsBoughtInGamePerMin": 0.050282834430159636,
                "neutralMinionsKilledEnemyJunglePerMin": 0.13533054124736776,
                "wardsPlacedPerMin": 0.09160102944615377,
                "wardsKilledPerMin": 0.05517406534380283
              }
            },
            "mid": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 637.8918552942031,
                "damageDealtToObjectivesPerMin": 203.44823663389724,
                "visionScorePerMin": 0.6492789633872197,
                "totalDamageTakenPerMin": 718.7465723641702,
                "totalMinionsKilledPerMin": 5.49226986144272,
                "killsRatio": 0.4785534073536094,
                "deathsRatio": 0.2129162035575365,
                "killAssistPerMin": 0.445593304341743,
                "killsPerMin": 0.23500132382063765,
                "deathsPerMin": 0.20291601713724056,
                "assistsPerMin": 0.2105919805211054,
                "totalHealPerMin": 6.508406314074702,
                "damageSelfMitigatedPerMin": 497.3626527619467,
                "damageDealtToTurretsPerMin": 93.60159224500612,
                "timeCCingOthersPerMin": 0.6869851675356531,
                "neutralMinionsKilledPerMin": 0.3892562083705684,
                "totalTimeCrowdControlDealtPerMin": 5.8747845684752455,
                "visionWardsBoughtInGamePerMin": 0.0682896101999707,
                "neutralMinionsKilledEnemyJunglePerMin": 0.07127934371407288,
                "wardsPlacedPerMin": 0.3032276860052999,
                "wardsKilledPerMin": 0.07083748282715907
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 255.463965123932,
                "damageDealtToObjectivesPerMin": 195.7947549322168,
                "visionScorePerMin": 0.2654960178064047,
                "totalDamageTakenPerMin": 233.45722443889255,
                "totalMinionsKilledPerMin": 1.2087036093893202,
                "killsRatio": 0.15580523683492353,
                "deathsRatio": 0.09672025147685707,
                "killAssistPerMin": 0.2094873455692756,
                "killsPerMin": 0.15018505598499535,
                "deathsPerMin": 0.10867603434750908,
                "assistsPerMin": 0.12753134227085494,
                "totalHealPerMin": 32.48205115930459,
                "damageSelfMitigatedPerMin": 306.3728656047618,
                "damageDealtToTurretsPerMin": 104.07596997522484,
                "timeCCingOthersPerMin": 0.6106234063603725,
                "neutralMinionsKilledPerMin": 0.40290523137463297,
                "totalTimeCrowdControlDealtPerMin": 5.383050224848568,
                "visionWardsBoughtInGamePerMin": 0.06371585756603852,
                "neutralMinionsKilledEnemyJunglePerMin": 0.14036780410107544,
                "wardsPlacedPerMin": 0.09440599400318538,
                "wardsKilledPerMin": 0.0701294803919512
              }
            },
            "jungle": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 469.27756952419617,
                "damageDealtToObjectivesPerMin": 640.5357488191837,
                "visionScorePerMin": 0.7940598474137797,
                "totalDamageTakenPerMin": 955.6648257906646,
                "totalMinionsKilledPerMin": 1.1939585289880392,
                "killsRatio": 0.5052600276940807,
                "deathsRatio": 0.1996558083304618,
                "killAssistPerMin": 0.4698349258785006,
                "killsPerMin": 0.20760945702305056,
                "deathsPerMin": 0.1895740823537884,
                "assistsPerMin": 0.26222546885545,
                "totalHealPerMin": 97.56700105720616,
                "damageSelfMitigatedPerMin": 699.7451161404097,
                "damageDealtToTurretsPerMin": 41.60206031587595,
                "timeCCingOthersPerMin": 0.7318212055349561,
                "neutralMinionsKilledPerMin": 3.9940492956566316,
                "totalTimeCrowdControlDealtPerMin": 11.314246624035635,
                "visionWardsBoughtInGamePerMin": 0.09006518027943365,
                "neutralMinionsKilledEnemyJunglePerMin": 0.2871103646320152,
                "wardsPlacedPerMin": 0.2518090680808039,
                "wardsKilledPerMin": 0.10767276863955093
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 228.28255473220332,
                "damageDealtToObjectivesPerMin": 401.84453345682994,
                "visionScorePerMin": 0.3183930582845101,
                "totalDamageTakenPerMin": 213.29725142625668,
                "totalMinionsKilledPerMin": 0.5904052913035704,
                "killsRatio": 0.1552524262909971,
                "deathsRatio": 0.08107605690606119,
                "killAssistPerMin": 0.21434927596968248,
                "killsPerMin": 0.13871579999362765,
                "deathsPerMin": 0.09491966624519654,
                "assistsPerMin": 0.1445008263772395,
                "totalHealPerMin": 277.18477186131656,
                "damageSelfMitigatedPerMin": 305.69317290941115,
                "damageDealtToTurretsPerMin": 54.35312432596039,
                "timeCCingOthersPerMin": 0.7318727201318798,
                "neutralMinionsKilledPerMin": 1.0084222102519884,
                "totalTimeCrowdControlDealtPerMin": 5.610485555004908,
                "visionWardsBoughtInGamePerMin": 0.08311258326863236,
                "neutralMinionsKilledEnemyJunglePerMin": 0.33071369389351235,
                "wardsPlacedPerMin": 0.11951162400352637,
                "wardsKilledPerMin": 0.0854299118719607
              }
            },
            "bottom": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 625.3153574515921,
                "damageDealtToObjectivesPerMin": 288.2159238836589,
                "visionScorePerMin": 0.6070619274978211,
                "totalDamageTakenPerMin": 580.2134136432308,
                "totalMinionsKilledPerMin": 5.969266321279414,
                "killsRatio": 0.4858070349837894,
                "deathsRatio": 0.1962495591310522,
                "killAssistPerMin": 0.4602842157024301,
                "killsPerMin": 0.21785640873268686,
                "deathsPerMin": 0.1901263435353328,
                "assistsPerMin": 0.24242780696974325,
                "totalHealPerMin": 89.83056356613834,
                "damageSelfMitigatedPerMin": 301.468165349787,
                "damageDealtToTurretsPerMin": 137.5700989077075,
                "timeCCingOthersPerMin": 0.5105151121813354,
                "neutralMinionsKilledPerMin": 0.3795296278192974,
                "totalTimeCrowdControlDealtPerMin": 6.552067229383723,
                "visionWardsBoughtInGamePerMin": 0.04155128758111843,
                "neutralMinionsKilledEnemyJunglePerMin": 0.052431427313645966,
                "wardsPlacedPerMin": 0.2777259626009617,
                "wardsKilledPerMin": 0.07822897914377493
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 277.3667683661737,
                "damageDealtToObjectivesPerMin": 241.18346449516903,
                "visionScorePerMin": 0.27204088130498816,
                "totalDamageTakenPerMin": 164.93912898910682,
                "totalMinionsKilledPerMin": 1.0635710952058088,
                "killsRatio": 0.16116866812692726,
                "deathsRatio": 0.08704748738864586,
                "killAssistPerMin": 0.2340898820160183,
                "killsPerMin": 0.1482749211434478,
                "deathsPerMin": 0.101882393354327,
                "assistsPerMin": 0.14035500551960226,
                "totalHealPerMin": 62.68486866911417,
                "damageSelfMitigatedPerMin": 138.42067173940774,
                "damageDealtToTurretsPerMin": 134.4665357003071,
                "timeCCingOthersPerMin": 0.4677080076516671,
                "neutralMinionsKilledPerMin": 0.3374094699715098,
                "totalTimeCrowdControlDealtPerMin": 7.635818048898563,
                "visionWardsBoughtInGamePerMin": 0.046259744759255664,
                "neutralMinionsKilledEnemyJunglePerMin": 0.0988162211412271,
                "wardsPlacedPerMin": 0.08618149302895299,
                "wardsKilledPerMin": 0.06997012913412275
              }
            },
            "support": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 345.30755622260045,
                "damageDealtToObjectivesPerMin": 82.23546681399725,
                "visionScorePerMin": 1.5010675000220737,
                "totalDamageTakenPerMin": 528.112682245839,
                "totalMinionsKilledPerMin": 1.0794507356993297,
                "killsRatio": 0.4992702038726116,
                "deathsRatio": 0.19146009411329146,
                "killAssistPerMin": 0.4712504919864392,
                "killsPerMin": 0.09095236746152073,
                "deathsPerMin": 0.18559620351102385,
                "assistsPerMin": 0.3802981245249184,
                "totalHealPerMin": 98.52658815297691,
                "damageSelfMitigatedPerMin": 450.91975764497215,
                "damageDealtToTurretsPerMin": 40.27429461499909,
                "timeCCingOthersPerMin": 1.146436811436957,
                "neutralMinionsKilledPerMin": 0.0604383198989028,
                "totalTimeCrowdControlDealtPerMin": 5.146962180204958,
                "visionWardsBoughtInGamePerMin": 0.1321589262871052,
                "neutralMinionsKilledEnemyJunglePerMin": 0.009006828751433788,
                "wardsPlacedPerMin": 0.6895128503359033,
                "wardsKilledPerMin": 0.16567602656385488
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 184.2770121303061,
                "damageDealtToObjectivesPerMin": 81.17974011268417,
                "visionScorePerMin": 0.5094408869448139,
                "totalDamageTakenPerMin": 182.46381605295159,
                "totalMinionsKilledPerMin": 0.5164198536344046,
                "killsRatio": 0.14800791710586947,
                "deathsRatio": 0.0779543583945552,
                "killAssistPerMin": 0.22123725353931156,
                "killsPerMin": 0.08017747002421703,
                "deathsPerMin": 0.09922396054575837,
                "assistsPerMin": 0.1965500458337236,
                "totalHealPerMin": 165.40675371811628,
                "damageSelfMitigatedPerMin": 309.7570860930183,
                "damageDealtToTurretsPerMin": 43.820679032292226,
                "timeCCingOthersPerMin": 0.5549123096283847,
                "neutralMinionsKilledPerMin": 0.1346320631566829,
                "totalTimeCrowdControlDealtPerMin": 3.8138447859048,
                "visionWardsBoughtInGamePerMin": 0.09385792450096957,
                "neutralMinionsKilledEnemyJunglePerMin": 0.039361683484127614,
                "wardsPlacedPerMin": 0.2371832907074697,
                "wardsKilledPerMin": 0.11420786447773412
              }
            }
          },
          "platinum": {
            "total": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 557.5141517983478,
                "damageDealtToObjectivesPerMin": 310.47543467325687,
                "visionScorePerMin": 0.9664537775539208,
                "totalDamageTakenPerMin": 750.3777276472235,
                "totalMinionsKilledPerMin": 4.08542034252162,
                "killsRatio": 0.495420382536196,
                "deathsRatio": 0.19971008165342585,
                "killAssistPerMin": 0.4794189941371033,
                "killsPerMin": 0.1922077154024878,
                "deathsPerMin": 0.1968393075867477,
                "assistsPerMin": 0.2872112787346155,
                "totalHealPerMin": 67.21745917287171,
                "damageSelfMitigatedPerMin": 565.4691849876289,
                "damageDealtToTurretsPerMin": 84.31424071419863,
                "timeCCingOthersPerMin": 0.7676874667602261,
                "neutralMinionsKilledPerMin": 1.1086939730973588,
                "totalTimeCrowdControlDealtPerMin": 7.421766676383761,
                "visionWardsBoughtInGamePerMin": 0.11150139357263256,
                "neutralMinionsKilledEnemyJunglePerMin": 0.10953058134038116,
                "wardsPlacedPerMin": 0.40117025064649775,
                "wardsKilledPerMin": 0.13308665692535054
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 257.782772911724,
                "damageDealtToObjectivesPerMin": 315.2706795270413,
                "visionScorePerMin": 0.5376135585259056,
                "totalDamageTakenPerMin": 264.08347492102826,
                "totalMinionsKilledPerMin": 2.6083800103314725,
                "killsRatio": 0.15619342787488796,
                "deathsRatio": 0.07771910758829115,
                "killAssistPerMin": 0.22589330255121254,
                "killsPerMin": 0.1393776767580945,
                "deathsPerMin": 0.09555303395238976,
                "assistsPerMin": 0.17914702376763564,
                "totalHealPerMin": 164.63492712134445,
                "damageSelfMitigatedPerMin": 331.62872369519306,
                "damageDealtToTurretsPerMin": 98.57744694237095,
                "timeCCingOthersPerMin": 0.6460070857994904,
                "neutralMinionsKilledPerMin": 1.6783095944710629,
                "totalTimeCrowdControlDealtPerMin": 6.306924693206488,
                "visionWardsBoughtInGamePerMin": 0.09292480074225937,
                "neutralMinionsKilledEnemyJunglePerMin": 0.21008428146411134,
                "wardsPlacedPerMin": 0.2763421801740111,
                "wardsKilledPerMin": 0.10420809871060703
              }
            },
            "top": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 595.6444162767127,
                "damageDealtToObjectivesPerMin": 253.71128863537697,
                "visionScorePerMin": 0.6452802354189269,
                "totalDamageTakenPerMin": 854.5768651944736,
                "totalMinionsKilledPerMin": 5.962312816398593,
                "killsRatio": 0.38570323102080817,
                "deathsRatio": 0.20007106384121773,
                "killAssistPerMin": 0.3745832162509951,
                "killsPerMin": 0.17370027624297807,
                "deathsPerMin": 0.195532916875108,
                "assistsPerMin": 0.20088294000801693,
                "totalHealPerMin": 17.871228433860953,
                "damageSelfMitigatedPerMin": 770.6415170551794,
                "damageDealtToTurretsPerMin": 133.02608528627815,
                "timeCCingOthersPerMin": 0.7120896969467276,
                "neutralMinionsKilledPerMin": 0.2869908786688409,
                "totalTimeCrowdControlDealtPerMin": 6.554498139223251,
                "visionWardsBoughtInGamePerMin": 0.06247525492401122,
                "neutralMinionsKilledEnemyJunglePerMin": 0.07255862870463038,
                "wardsPlacedPerMin": 0.283382231436032,
                "wardsKilledPerMin": 0.07260634426135675
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 205.45712863218395,
                "damageDealtToObjectivesPerMin": 190.45971922182758,
                "visionScorePerMin": 0.2482426855001636,
                "totalDamageTakenPerMin": 225.08897203968857,
                "totalMinionsKilledPerMin": 1.0821297625751454,
                "killsRatio": 0.1451508760560237,
                "deathsRatio": 0.08256798725761508,
                "killAssistPerMin": 0.1950807267527402,
                "killsPerMin": 0.11811242373917467,
                "deathsPerMin": 0.0932283126665331,
                "assistsPerMin": 0.1394065202033257,
                "totalHealPerMin": 75.73759440399894,
                "damageSelfMitigatedPerMin": 367.8212529918076,
                "damageDealtToTurretsPerMin": 112.10299536776563,
                "timeCCingOthersPerMin": 0.6286023795436864,
                "neutralMinionsKilledPerMin": 0.2851049671476372,
                "totalTimeCrowdControlDealtPerMin": 5.355132747164376,
                "visionWardsBoughtInGamePerMin": 0.05704954560396686,
                "neutralMinionsKilledEnemyJunglePerMin": 0.1377594680622785,
                "wardsPlacedPerMin": 0.09493067848230707,
                "wardsKilledPerMin": 0.06300712105787404
              }
            },
            "mid": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 656.7701031508336,
                "damageDealtToObjectivesPerMin": 181.16546653632184,
                "visionScorePerMin": 0.7248852734328058,
                "totalDamageTakenPerMin": 752.4128543514304,
                "totalMinionsKilledPerMin": 5.830383334645754,
                "killsRatio": 0.4922519727909028,
                "deathsRatio": 0.20930065826872565,
                "killAssistPerMin": 0.4718509770894557,
                "killsPerMin": 0.2378666161177907,
                "deathsPerMin": 0.20562726933216247,
                "assistsPerMin": 0.2339843609716648,
                "totalHealPerMin": 14.865211537914794,
                "damageSelfMitigatedPerMin": 515.8603998274714,
                "damageDealtToTurretsPerMin": 82.63183350860798,
                "timeCCingOthersPerMin": 0.7780380428692668,
                "neutralMinionsKilledPerMin": 0.39062931752296176,
                "totalTimeCrowdControlDealtPerMin": 5.879880574272464,
                "visionWardsBoughtInGamePerMin": 0.08995502352566878,
                "neutralMinionsKilledEnemyJunglePerMin": 0.058723292691848124,
                "wardsPlacedPerMin": 0.30682524498531644,
                "wardsKilledPerMin": 0.09818208939792744
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 224.3642439078936,
                "damageDealtToObjectivesPerMin": 169.98518058255834,
                "visionScorePerMin": 0.25641197963349743,
                "totalDamageTakenPerMin": 206.37433178022002,
                "totalMinionsKilledPerMin": 1.0180220167180984,
                "killsRatio": 0.1448084421828603,
                "deathsRatio": 0.07988098017853035,
                "killAssistPerMin": 0.20770365434145246,
                "killsPerMin": 0.14615302705901242,
                "deathsPerMin": 0.09774747569339476,
                "assistsPerMin": 0.13262401131196236,
                "totalHealPerMin": 54.444536801866185,
                "damageSelfMitigatedPerMin": 243.80606983044711,
                "damageDealtToTurretsPerMin": 93.6711162054513,
                "timeCCingOthersPerMin": 0.9188289563719091,
                "neutralMinionsKilledPerMin": 0.3284958734755961,
                "totalTimeCrowdControlDealtPerMin": 4.99951920427072,
                "visionWardsBoughtInGamePerMin": 0.06274864176380243,
                "neutralMinionsKilledEnemyJunglePerMin": 0.1190391516386498,
                "wardsPlacedPerMin": 0.10870935552452483,
                "wardsKilledPerMin": 0.07525671210870875
              }
            },
            "jungle": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 521.4272788195052,
                "damageDealtToObjectivesPerMin": 662.2139825657345,
                "visionScorePerMin": 0.912504041951262,
                "totalDamageTakenPerMin": 1003.72256369526,
                "totalMinionsKilledPerMin": 1.1466370350115616,
                "killsRatio": 0.5396810095267994,
                "deathsRatio": 0.2007278227404524,
                "killAssistPerMin": 0.5177391173039895,
                "killsPerMin": 0.2167722101422709,
                "deathsPerMin": 0.2004143894668426,
                "assistsPerMin": 0.3009669071617185,
                "totalHealPerMin": 94.56159706712302,
                "damageSelfMitigatedPerMin": 734.2289455070543,
                "damageDealtToTurretsPerMin": 32.81251224124681,
                "timeCCingOthersPerMin": 0.7210688102471169,
                "neutralMinionsKilledPerMin": 4.301072513421894,
                "totalTimeCrowdControlDealtPerMin": 12.190591121285456,
                "visionWardsBoughtInGamePerMin": 0.14366735752170434,
                "neutralMinionsKilledEnemyJunglePerMin": 0.32181152272168234,
                "wardsPlacedPerMin": 0.24101183686574704,
                "wardsKilledPerMin": 0.1564444866617378
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 211.15973166345643,
                "damageDealtToObjectivesPerMin": 381.8192581520154,
                "visionScorePerMin": 0.3001830327102741,
                "totalDamageTakenPerMin": 222.47935808135966,
                "totalMinionsKilledPerMin": 0.5539394180990458,
                "killsRatio": 0.14817926561299866,
                "deathsRatio": 0.07162359660741385,
                "killAssistPerMin": 0.2284279412760208,
                "killsPerMin": 0.1324670707598897,
                "deathsPerMin": 0.09713690437487747,
                "assistsPerMin": 0.16898771725985726,
                "totalHealPerMin": 281.14356956123584,
                "damageSelfMitigatedPerMin": 346.0112268489289,
                "damageDealtToTurretsPerMin": 46.775585172600096,
                "timeCCingOthersPerMin": 0.5369490164606364,
                "neutralMinionsKilledPerMin": 0.9154518527519692,
                "totalTimeCrowdControlDealtPerMin": 6.31053708038607,
                "visionWardsBoughtInGamePerMin": 0.08870137536418632,
                "neutralMinionsKilledEnemyJunglePerMin": 0.3118205256789049,
                "wardsPlacedPerMin": 0.12946868726522334,
                "wardsKilledPerMin": 0.09902653529297163
              }
            },
            "bottom": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 683.8835456162367,
                "damageDealtToObjectivesPerMin": 365.9556550109999,
                "visionScorePerMin": 0.7618051764832117,
                "totalDamageTakenPerMin": 608.4409933001027,
                "totalMinionsKilledPerMin": 6.476748156443285,
                "killsRatio": 0.5200385107481923,
                "deathsRatio": 0.19412316717550346,
                "killAssistPerMin": 0.5049161694219986,
                "killsPerMin": 0.2386448491070749,
                "deathsPerMin": 0.19190495759302956,
                "assistsPerMin": 0.2662713203149236,
                "totalHealPerMin": 90.92295921176633,
                "damageSelfMitigatedPerMin": 337.1121644659792,
                "damageDealtToTurretsPerMin": 137.32418714340807,
                "timeCCingOthersPerMin": 0.5405473559695321,
                "neutralMinionsKilledPerMin": 0.5274468759073401,
                "totalTimeCrowdControlDealtPerMin": 7.418434579389673,
                "visionWardsBoughtInGamePerMin": 0.06180423721155726,
                "neutralMinionsKilledEnemyJunglePerMin": 0.08272205850657287,
                "wardsPlacedPerMin": 0.3146133523002154,
                "wardsKilledPerMin": 0.12950146291282366
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 302.4188949252585,
                "damageDealtToObjectivesPerMin": 289.24753596446527,
                "visionScorePerMin": 0.3050683632997312,
                "totalDamageTakenPerMin": 177.37684458085943,
                "totalMinionsKilledPerMin": 1.0455492639804598,
                "killsRatio": 0.146534018620952,
                "deathsRatio": 0.07188899332308109,
                "killAssistPerMin": 0.22372094268193185,
                "killsPerMin": 0.14958572660555028,
                "deathsPerMin": 0.09314183930912916,
                "assistsPerMin": 0.1377682610462137,
                "totalHealPerMin": 69.19890646354641,
                "damageSelfMitigatedPerMin": 159.4781952716573,
                "damageDealtToTurretsPerMin": 117.26472545188898,
                "timeCCingOthersPerMin": 0.4506883374987287,
                "neutralMinionsKilledPerMin": 0.4300089241767943,
                "totalTimeCrowdControlDealtPerMin": 8.001447848555129,
                "visionWardsBoughtInGamePerMin": 0.0553443155281007,
                "neutralMinionsKilledEnemyJunglePerMin": 0.14834268356990665,
                "wardsPlacedPerMin": 0.089336911697261,
                "wardsKilledPerMin": 0.08894068003848858
              }
            },
            "support": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 333.3574547803568,
                "damageDealtToObjectivesPerMin": 95.60256070351849,
                "visionScorePerMin": 1.7768591739062525,
                "totalDamageTakenPerMin": 535.7976018059825,
                "totalMinionsKilledPerMin": 1.0367951242774869,
                "killsRatio": 0.5400540556849472,
                "deathsRatio": 0.19436807420140795,
                "killAssistPerMin": 0.528500162476337,
                "killsPerMin": 0.09603258559335684,
                "deathsPerMin": 0.1908013764896984,
                "assistsPerMin": 0.43246757688298015,
                "totalHealPerMin": 117.86005869874896,
                "damageSelfMitigatedPerMin": 468.99883567236037,
                "damageDealtToTurretsPerMin": 36.12171815290315,
                "timeCCingOthersPerMin": 1.0807683556611738,
                "neutralMinionsKilledPerMin": 0.07591548359085255,
                "totalTimeCrowdControlDealtPerMin": 5.137023682643044,
                "visionWardsBoughtInGamePerMin": 0.1986036243966325,
                "neutralMinionsKilledEnemyJunglePerMin": 0.01472896619281,
                "wardsPlacedPerMin": 0.8527755152097874,
                "wardsKilledPerMin": 0.20820774886316828
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 160.21295108741882,
                "damageDealtToObjectivesPerMin": 110.7904480926765,
                "visionScorePerMin": 0.5119646817415336,
                "totalDamageTakenPerMin": 183.93293524478145,
                "totalMinionsKilledPerMin": 0.4195926670968517,
                "killsRatio": 0.14193364820473625,
                "deathsRatio": 0.08130373177688753,
                "killAssistPerMin": 0.2381508754490515,
                "killsPerMin": 0.08819834805970317,
                "deathsPerMin": 0.09628180408570963,
                "assistsPerMin": 0.2090401298218574,
                "totalHealPerMin": 185.75061678084418,
                "damageSelfMitigatedPerMin": 276.5672037368246,
                "damageDealtToTurretsPerMin": 36.19967698462249,
                "timeCCingOthersPerMin": 0.4537029035475818,
                "neutralMinionsKilledPerMin": 0.39767856010545743,
                "totalTimeCrowdControlDealtPerMin": 3.384776520371209,
                "visionWardsBoughtInGamePerMin": 0.10500474671664152,
                "neutralMinionsKilledEnemyJunglePerMin": 0.10505425167394054,
                "wardsPlacedPerMin": 0.2709709301718986,
                "wardsKilledPerMin": 0.1258236408674344
              }
            }
          },
          "diamond": {
            "total": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 529.3586105963162,
                "damageDealtToObjectivesPerMin": 320.7658844204141,
                "visionScorePerMin": 1.0126455967061752,
                "totalDamageTakenPerMin": 719.6904457332944,
                "totalMinionsKilledPerMin": 4.274375456782853,
                "killsRatio": 0.4988386552488599,
                "deathsRatio": 0.20008926643170946,
                "killAssistPerMin": 0.4558379213356265,
                "killsPerMin": 0.18062346395957452,
                "deathsPerMin": 0.18824273541828934,
                "assistsPerMin": 0.2752144573760519,
                "totalHealPerMin": 62.819292091083575,
                "damageSelfMitigatedPerMin": 535.0500498039884,
                "damageDealtToTurretsPerMin": 86.0504334786487,
                "timeCCingOthersPerMin": 0.7373402281958774,
                "neutralMinionsKilledPerMin": 1.1254369305214202,
                "totalTimeCrowdControlDealtPerMin": 7.330754714913738,
                "visionWardsBoughtInGamePerMin": 0.132228592139149,
                "neutralMinionsKilledEnemyJunglePerMin": 0.11899058356750995,
                "wardsPlacedPerMin": 0.4381222547232828,
                "wardsKilledPerMin": 0.14946552037298858
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 251.8435283523871,
                "damageDealtToObjectivesPerMin": 328.6250002202849,
                "visionScorePerMin": 0.5951243305596627,
                "totalDamageTakenPerMin": 257.4807901754993,
                "totalMinionsKilledPerMin": 2.7837519945390587,
                "killsRatio": 0.1679428404939644,
                "deathsRatio": 0.07847861976189043,
                "killAssistPerMin": 0.23119016054001584,
                "killsPerMin": 0.1408678553834186,
                "deathsPerMin": 0.0951753535898971,
                "assistsPerMin": 0.178818359266857,
                "totalHealPerMin": 141.66660712008678,
                "damageSelfMitigatedPerMin": 333.3325375611592,
                "damageDealtToTurretsPerMin": 104.0217024183714,
                "timeCCingOthersPerMin": 0.5739186284440286,
                "neutralMinionsKilledPerMin": 1.7748745687066003,
                "totalTimeCrowdControlDealtPerMin": 6.184826144219708,
                "visionWardsBoughtInGamePerMin": 0.11116321847097327,
                "neutralMinionsKilledEnemyJunglePerMin": 0.23792310539340944,
                "wardsPlacedPerMin": 0.3272286643690493,
                "wardsKilledPerMin": 0.11457474796523805
              }
            },
            "top": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 581.953111410374,
                "damageDealtToObjectivesPerMin": 267.299520529258,
                "visionScorePerMin": 0.6756112689259864,
                "totalDamageTakenPerMin": 829.7033129477649,
                "totalMinionsKilledPerMin": 6.203019829017613,
                "killsRatio": 0.408827752522199,
                "deathsRatio": 0.19576977132814408,
                "killAssistPerMin": 0.3735275700052008,
                "killsPerMin": 0.17602527991520933,
                "deathsPerMin": 0.18259688162884105,
                "assistsPerMin": 0.19750229008999132,
                "totalHealPerMin": 14.695842537549193,
                "damageSelfMitigatedPerMin": 730.7592818680794,
                "damageDealtToTurretsPerMin": 139.58020494444276,
                "timeCCingOthersPerMin": 0.7041787012616724,
                "neutralMinionsKilledPerMin": 0.2777161014010709,
                "totalTimeCrowdControlDealtPerMin": 7.066882728819027,
                "visionWardsBoughtInGamePerMin": 0.0714465414267887,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06597937779345238,
                "wardsPlacedPerMin": 0.30458307975198673,
                "wardsKilledPerMin": 0.08102356303670145
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 216.9987189475009,
                "damageDealtToObjectivesPerMin": 203.2040014054652,
                "visionScorePerMin": 0.2698122491808258,
                "totalDamageTakenPerMin": 252.12298536281585,
                "totalMinionsKilledPerMin": 1.3108215568420492,
                "killsRatio": 0.16383862366911506,
                "deathsRatio": 0.0866771691347094,
                "killAssistPerMin": 0.1949087440584244,
                "killsPerMin": 0.11977411616477004,
                "deathsPerMin": 0.09384536879313488,
                "assistsPerMin": 0.14384134416757235,
                "totalHealPerMin": 72.95839016652185,
                "damageSelfMitigatedPerMin": 409.6734093899236,
                "damageDealtToTurretsPerMin": 124.59468612757955,
                "timeCCingOthersPerMin": 0.5679190048848698,
                "neutralMinionsKilledPerMin": 0.29939657760910265,
                "totalTimeCrowdControlDealtPerMin": 6.502964879351113,
                "visionWardsBoughtInGamePerMin": 0.06690801825207349,
                "neutralMinionsKilledEnemyJunglePerMin": 0.13924132912279438,
                "wardsPlacedPerMin": 0.09607143949318336,
                "wardsKilledPerMin": 0.06591180459621558
              }
            },
            "mid": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 630.9722011434025,
                "damageDealtToObjectivesPerMin": 205.19829073626425,
                "visionScorePerMin": 0.7519319505733828,
                "totalDamageTakenPerMin": 724.4132543983949,
                "totalMinionsKilledPerMin": 6.2120130127401385,
                "killsRatio": 0.4970736347131906,
                "deathsRatio": 0.2090952352576204,
                "killAssistPerMin": 0.4545327300991856,
                "killsPerMin": 0.22903338567325274,
                "deathsPerMin": 0.19789772838814496,
                "assistsPerMin": 0.2254993444259329,
                "totalHealPerMin": 16.683646406829073,
                "damageSelfMitigatedPerMin": 498.2192484785321,
                "damageDealtToTurretsPerMin": 88.43405708704522,
                "timeCCingOthersPerMin": 0.7139699566409667,
                "neutralMinionsKilledPerMin": 0.33666971283196,
                "totalTimeCrowdControlDealtPerMin": 6.093212578218778,
                "visionWardsBoughtInGamePerMin": 0.09986558916358504,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06635458247369325,
                "wardsPlacedPerMin": 0.3091710139304558,
                "wardsKilledPerMin": 0.11679351845480382
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 239.01046553630425,
                "damageDealtToObjectivesPerMin": 169.30225311238917,
                "visionScorePerMin": 0.2658706140852665,
                "totalDamageTakenPerMin": 196.2215319477132,
                "totalMinionsKilledPerMin": 1.1813897625770609,
                "killsRatio": 0.16283737767720946,
                "deathsRatio": 0.08421264891084243,
                "killAssistPerMin": 0.2352814841590364,
                "killsPerMin": 0.1578782224556206,
                "deathsPerMin": 0.1007673419036262,
                "assistsPerMin": 0.14334861976791266,
                "totalHealPerMin": 56.989714985158436,
                "damageSelfMitigatedPerMin": 264.2221780104408,
                "damageDealtToTurretsPerMin": 93.12990403216558,
                "timeCCingOthersPerMin": 0.7584748037939838,
                "neutralMinionsKilledPerMin": 0.33027753762626805,
                "totalTimeCrowdControlDealtPerMin": 4.839956472983904,
                "visionWardsBoughtInGamePerMin": 0.06901160950473356,
                "neutralMinionsKilledEnemyJunglePerMin": 0.12890000058128492,
                "wardsPlacedPerMin": 0.10905222881305213,
                "wardsKilledPerMin": 0.0832848784653335
              }
            },
            "jungle": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 514.6162141580504,
                "damageDealtToObjectivesPerMin": 723.3821990203846,
                "visionScorePerMin": 0.9472599561508438,
                "totalDamageTakenPerMin": 962.1094220345008,
                "totalMinionsKilledPerMin": 1.1555072825647772,
                "killsRatio": 0.5698624837563725,
                "deathsRatio": 0.20534717457594892,
                "killAssistPerMin": 0.5176704460631638,
                "killsPerMin": 0.2266776709366748,
                "deathsPerMin": 0.1932539056527041,
                "assistsPerMin": 0.290992775126489,
                "totalHealPerMin": 82.75663407417942,
                "damageSelfMitigatedPerMin": 682.4677165424067,
                "damageDealtToTurretsPerMin": 38.75562889031779,
                "timeCCingOthersPerMin": 0.6659560969282734,
                "neutralMinionsKilledPerMin": 4.523039590778878,
                "totalTimeCrowdControlDealtPerMin": 11.565987212240687,
                "visionWardsBoughtInGamePerMin": 0.16960274864653554,
                "neutralMinionsKilledEnemyJunglePerMin": 0.39170799564508857,
                "wardsPlacedPerMin": 0.2679006861065651,
                "wardsKilledPerMin": 0.1635637849468068
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 218.07554995253082,
                "damageDealtToObjectivesPerMin": 410.6030519383369,
                "visionScorePerMin": 0.3114699746456398,
                "totalDamageTakenPerMin": 208.1067738594272,
                "totalMinionsKilledPerMin": 0.6220733025951838,
                "killsRatio": 0.15345684627866074,
                "deathsRatio": 0.07262318124724472,
                "killAssistPerMin": 0.24073161885791444,
                "killsPerMin": 0.14957485292223427,
                "deathsPerMin": 0.0950130578640178,
                "assistsPerMin": 0.16982658323068314,
                "totalHealPerMin": 234.31668424075227,
                "damageSelfMitigatedPerMin": 313.8995161199575,
                "damageDealtToTurretsPerMin": 59.85051075790309,
                "timeCCingOthersPerMin": 0.3615365449887501,
                "neutralMinionsKilledPerMin": 0.9373577542197796,
                "totalTimeCrowdControlDealtPerMin": 5.464076182976591,
                "visionWardsBoughtInGamePerMin": 0.10876365436957808,
                "neutralMinionsKilledEnemyJunglePerMin": 0.3674506179014507,
                "wardsPlacedPerMin": 0.16881943470435354,
                "wardsKilledPerMin": 0.10579656677380353
              }
            },
            "bottom": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 633.0713468803189,
                "damageDealtToObjectivesPerMin": 329.89213859382687,
                "visionScorePerMin": 0.7328978297716049,
                "totalDamageTakenPerMin": 570.7084424580132,
                "totalMinionsKilledPerMin": 6.8101309624232265,
                "killsRatio": 0.4934713121889796,
                "deathsRatio": 0.19314015155098904,
                "killAssistPerMin": 0.450714632001589,
                "killsPerMin": 0.1946377325149559,
                "deathsPerMin": 0.18072052682474055,
                "assistsPerMin": 0.25607689948663315,
                "totalHealPerMin": 85.88292832732267,
                "damageSelfMitigatedPerMin": 304.5603097153939,
                "damageDealtToTurretsPerMin": 131.02844277761287,
                "timeCCingOthersPerMin": 0.5453172424046671,
                "neutralMinionsKilledPerMin": 0.46529932940672203,
                "totalTimeCrowdControlDealtPerMin": 7.256525910821755,
                "visionWardsBoughtInGamePerMin": 0.06978047573785058,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06813918108222845,
                "wardsPlacedPerMin": 0.3207558354354213,
                "wardsKilledPerMin": 0.1330096268565524
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 256.0649810825005,
                "damageDealtToObjectivesPerMin": 245.869351788682,
                "visionScorePerMin": 0.2914969012366455,
                "totalDamageTakenPerMin": 153.60000293019232,
                "totalMinionsKilledPerMin": 1.03475994795492,
                "killsRatio": 0.16034414062992786,
                "deathsRatio": 0.07289642270273901,
                "killAssistPerMin": 0.2258713865680636,
                "killsPerMin": 0.13359382338762166,
                "deathsPerMin": 0.0881113342811127,
                "assistsPerMin": 0.1443102518222843,
                "totalHealPerMin": 67.38433872273215,
                "damageSelfMitigatedPerMin": 128.77982280776732,
                "damageDealtToTurretsPerMin": 124.88313692280596,
                "timeCCingOthersPerMin": 0.5247326113572899,
                "neutralMinionsKilledPerMin": 0.3647509811954619,
                "totalTimeCrowdControlDealtPerMin": 7.951068203794942,
                "visionWardsBoughtInGamePerMin": 0.058942696069621464,
                "neutralMinionsKilledEnemyJunglePerMin": 0.12720431386774794,
                "wardsPlacedPerMin": 0.09139904937212534,
                "wardsKilledPerMin": 0.08438958359550977
              }
            },
            "support": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 290.87858533576116,
                "damageDealtToObjectivesPerMin": 80.94478377009662,
                "visionScorePerMin": 1.9381721575689397,
                "totalDamageTakenPerMin": 513.8046357888933,
                "totalMinionsKilledPerMin": 1.0689191196260084,
                "killsRatio": 0.5242292608077801,
                "deathsRatio": 0.1971037505440996,
                "killAssistPerMin": 0.4820347660092451,
                "killsPerMin": 0.07845931106618581,
                "deathsPerMin": 0.18672687799588367,
                "assistsPerMin": 0.40357545494305935,
                "totalHealPerMin": 113.18598555024485,
                "damageSelfMitigatedPerMin": 459.2476515004422,
                "damageDealtToTurretsPerMin": 33.72234007696365,
                "timeCCingOthersPerMin": 1.051221631658373,
                "neutralMinionsKilledPerMin": 0.02955549847119068,
                "totalTimeCrowdControlDealtPerMin": 4.702814744999452,
                "visionWardsBoughtInGamePerMin": 0.248014798182894,
                "neutralMinionsKilledEnemyJunglePerMin": 0.003678559713014065,
                "wardsPlacedPerMin": 0.9787022891348908,
                "wardsKilledPerMin": 0.25100784061558645
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 139.07331111749156,
                "damageDealtToObjectivesPerMin": 74.82682694888405,
                "visionScorePerMin": 0.557986608384751,
                "totalDamageTakenPerMin": 165.042399888543,
                "totalMinionsKilledPerMin": 0.44654834836316826,
                "killsRatio": 0.15803483982806008,
                "deathsRatio": 0.07427447585612487,
                "killAssistPerMin": 0.2329765635017133,
                "killsPerMin": 0.06997795330384264,
                "deathsPerMin": 0.0972041290465021,
                "assistsPerMin": 0.20787728756193966,
                "totalHealPerMin": 156.1324918679556,
                "damageSelfMitigatedPerMin": 289.6520929844056,
                "damageDealtToTurretsPerMin": 33.85115046245934,
                "timeCCingOthersPerMin": 0.4541078568067697,
                "neutralMinionsKilledPerMin": 0.0685202481538239,
                "totalTimeCrowdControlDealtPerMin": 2.640129425757716,
                "visionWardsBoughtInGamePerMin": 0.11664989204507642,
                "neutralMinionsKilledEnemyJunglePerMin": 0.02340244592030289,
                "wardsPlacedPerMin": 0.3207540318308124,
                "wardsKilledPerMin": 0.1390234485036415
              }
            }
          },
          "total": {
            "total": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 543.6718919440355,
                "damageDealtToObjectivesPerMin": 293.80409397768454,
                "visionScorePerMin": 0.8623907978534879,
                "totalDamageTakenPerMin": 733.7894765811225,
                "totalMinionsKilledPerMin": 3.8842681806273722,
                "killsRatio": 0.4757649902593261,
                "deathsRatio": 0.1999984686217818,
                "killAssistPerMin": 0.4482151046085079,
                "killsPerMin": 0.1872316405512848,
                "deathsPerMin": 0.19368423161305154,
                "assistsPerMin": 0.26098346405722306,
                "totalHealPerMin": 61.6574622073511,
                "damageSelfMitigatedPerMin": 553.3329356960613,
                "damageDealtToTurretsPerMin": 87.02500866478313,
                "timeCCingOthersPerMin": 0.7629640108495456,
                "neutralMinionsKilledPerMin": 1.041197762202249,
                "totalTimeCrowdControlDealtPerMin": 7.139099119780876,
                "visionWardsBoughtInGamePerMin": 0.0866358867876005,
                "neutralMinionsKilledEnemyJunglePerMin": 0.10700010607499602,
                "wardsPlacedPerMin": 0.3730049978710581,
                "wardsKilledPerMin": 0.1059579670176218
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 269.33334955781714,
                "damageDealtToObjectivesPerMin": 308.8638148042487,
                "visionScorePerMin": 0.5187014783287343,
                "totalDamageTakenPerMin": 264.3024419493448,
                "totalMinionsKilledPerMin": 2.4886194995596704,
                "killsRatio": 0.1649216266257313,
                "deathsRatio": 0.08174855444610774,
                "killAssistPerMin": 0.2209205899836768,
                "killsPerMin": 0.14196589085894776,
                "deathsPerMin": 0.09787578092336972,
                "assistsPerMin": 0.16761024645172432,
                "totalHealPerMin": 151.30333670089217,
                "damageSelfMitigatedPerMin": 355.20642265304235,
                "damageDealtToTurretsPerMin": 101.62345865063723,
                "timeCCingOthersPerMin": 0.6543649419542302,
                "neutralMinionsKilledPerMin": 1.6164383681079633,
                "totalTimeCrowdControlDealtPerMin": 6.317156166796937,
                "visionWardsBoughtInGamePerMin": 0.08940113608625644,
                "neutralMinionsKilledEnemyJunglePerMin": 0.2150032666792604,
                "wardsPlacedPerMin": 0.2511698602139906,
                "wardsKilledPerMin": 0.09815522677672696
              }
            },
            "top": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 591.7387767679231,
                "damageDealtToObjectivesPerMin": 250.98531993152685,
                "visionScorePerMin": 0.5932068260230294,
                "totalDamageTakenPerMin": 839.8726274826047,
                "totalMinionsKilledPerMin": 5.644824628567197,
                "killsRatio": 0.3902824100236769,
                "deathsRatio": 0.19742714601131872,
                "killAssistPerMin": 0.3659919761178105,
                "killsPerMin": 0.1787909939396726,
                "deathsPerMin": 0.1900544675269272,
                "assistsPerMin": 0.18720098217813785,
                "totalHealPerMin": 16.541779414536627,
                "damageSelfMitigatedPerMin": 755.4212523606749,
                "damageDealtToTurretsPerMin": 136.2212844053113,
                "timeCCingOthersPerMin": 0.7365071850182916,
                "neutralMinionsKilledPerMin": 0.289285168894532,
                "totalTimeCrowdControlDealtPerMin": 6.985670795256786,
                "visionWardsBoughtInGamePerMin": 0.050511611985469086,
                "neutralMinionsKilledEnemyJunglePerMin": 0.07295126449573021,
                "wardsPlacedPerMin": 0.2725283034340769,
                "wardsKilledPerMin": 0.06075010116264985
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 243.65825452371757,
                "damageDealtToObjectivesPerMin": 201.31443151356066,
                "visionScorePerMin": 0.2528920608045674,
                "totalDamageTakenPerMin": 250.38842214820727,
                "totalMinionsKilledPerMin": 1.2901562292118594,
                "killsRatio": 0.15613885558929125,
                "deathsRatio": 0.08581580422002537,
                "killAssistPerMin": 0.19140113021354951,
                "killsPerMin": 0.12381138398527032,
                "deathsPerMin": 0.09641193649988944,
                "assistsPerMin": 0.13094776506422093,
                "totalHealPerMin": 81.22326663360336,
                "damageSelfMitigatedPerMin": 407.47754337326865,
                "damageDealtToTurretsPerMin": 116.7286406930668,
                "timeCCingOthersPerMin": 0.5766594741538571,
                "neutralMinionsKilledPerMin": 0.3118024176731541,
                "totalTimeCrowdControlDealtPerMin": 6.824080435920926,
                "visionWardsBoughtInGamePerMin": 0.05488565658660536,
                "neutralMinionsKilledEnemyJunglePerMin": 0.1397550906612097,
                "wardsPlacedPerMin": 0.09160300545324952,
                "wardsKilledPerMin": 0.05803322370032205
              }
            },
            "mid": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 644.1033800915087,
                "damageDealtToObjectivesPerMin": 189.63288081163608,
                "visionScorePerMin": 0.6659743749384193,
                "totalDamageTakenPerMin": 738.1399942382144,
                "totalMinionsKilledPerMin": 5.4939528394432315,
                "killsRatio": 0.4780620846454772,
                "deathsRatio": 0.21032075424834729,
                "killAssistPerMin": 0.4499872222748733,
                "killsPerMin": 0.23496432991030894,
                "deathsPerMin": 0.20336134675975984,
                "assistsPerMin": 0.2150228923645643,
                "totalHealPerMin": 10.360156961880039,
                "damageSelfMitigatedPerMin": 513.6989295648965,
                "damageDealtToTurretsPerMin": 90.38641880448809,
                "timeCCingOthersPerMin": 0.7148359664355469,
                "neutralMinionsKilledPerMin": 0.3622563484840441,
                "totalTimeCrowdControlDealtPerMin": 5.717626900146718,
                "visionWardsBoughtInGamePerMin": 0.07221478150283188,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06948267729022327,
                "wardsPlacedPerMin": 0.2984476855210571,
                "wardsKilledPerMin": 0.08262291111359499
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 256.6214270279993,
                "damageDealtToObjectivesPerMin": 180.67065329819798,
                "visionScorePerMin": 0.2633727983675145,
                "totalDamageTakenPerMin": 220.103602733654,
                "totalMinionsKilledPerMin": 1.2943417709208414,
                "killsRatio": 0.15828952335750596,
                "deathsRatio": 0.08546631211912199,
                "killAssistPerMin": 0.21620093216609554,
                "killsPerMin": 0.1552784562200067,
                "deathsPerMin": 0.10171916486409714,
                "assistsPerMin": 0.12958577441818578,
                "totalHealPerMin": 45.003699377680796,
                "damageSelfMitigatedPerMin": 299.43194246738625,
                "damageDealtToTurretsPerMin": 102.74988830829331,
                "timeCCingOthersPerMin": 0.7140880305216096,
                "neutralMinionsKilledPerMin": 0.3703525073269244,
                "totalTimeCrowdControlDealtPerMin": 4.944788780170293,
                "visionWardsBoughtInGamePerMin": 0.06442666356114223,
                "neutralMinionsKilledEnemyJunglePerMin": 0.14897330556679333,
                "wardsPlacedPerMin": 0.10157219705552804,
                "wardsKilledPerMin": 0.07551633931611411
              }
            },
            "jungle": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 500.81967588121057,
                "damageDealtToObjectivesPerMin": 637.0915014370038,
                "visionScorePerMin": 0.8184974172097516,
                "totalDamageTakenPerMin": 968.2463218028772,
                "totalMinionsKilledPerMin": 1.2059519814528723,
                "killsRatio": 0.5129441382224051,
                "deathsRatio": 0.20191352473749247,
                "killAssistPerMin": 0.4813236419355849,
                "killsPerMin": 0.21517166927489625,
                "deathsPerMin": 0.1963323346987155,
                "assistsPerMin": 0.2661519726606887,
                "totalHealPerMin": 81.09317307621471,
                "damageSelfMitigatedPerMin": 712.1698569887898,
                "damageDealtToTurretsPerMin": 39.5358270410105,
                "timeCCingOthersPerMin": 0.7509130969477045,
                "neutralMinionsKilledPerMin": 4.0663149575114685,
                "totalTimeCrowdControlDealtPerMin": 11.406384377241853,
                "visionWardsBoughtInGamePerMin": 0.10712598969734502,
                "neutralMinionsKilledEnemyJunglePerMin": 0.3160757519386695,
                "wardsPlacedPerMin": 0.2467945298086402,
                "wardsKilledPerMin": 0.11940791671801147
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 233.3419886706321,
                "damageDealtToObjectivesPerMin": 401.0925455967688,
                "visionScorePerMin": 0.3207414502825193,
                "totalDamageTakenPerMin": 218.51651030924907,
                "totalMinionsKilledPerMin": 0.627144544890756,
                "killsRatio": 0.16492733750430116,
                "deathsRatio": 0.07869035294187067,
                "killAssistPerMin": 0.2277205369658104,
                "killsPerMin": 0.14336055138774273,
                "deathsPerMin": 0.0986221677399058,
                "assistsPerMin": 0.1561723458895217,
                "totalHealPerMin": 246.09620224532364,
                "damageSelfMitigatedPerMin": 347.3802991224605,
                "damageDealtToTurretsPerMin": 55.87099652391668,
                "timeCCingOthersPerMin": 0.8050841446854226,
                "neutralMinionsKilledPerMin": 1.0577618884277022,
                "totalTimeCrowdControlDealtPerMin": 5.995675211665446,
                "visionWardsBoughtInGamePerMin": 0.09673885934978424,
                "neutralMinionsKilledEnemyJunglePerMin": 0.3361521061235748,
                "wardsPlacedPerMin": 0.13397550969257485,
                "wardsKilledPerMin": 0.09377402349536268
              }
            },
            "bottom": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 646.4912742701198,
                "damageDealtToObjectivesPerMin": 307.6817152968187,
                "visionScorePerMin": 0.6462412726385943,
                "totalDamageTakenPerMin": 589.6484983861585,
                "totalMinionsKilledPerMin": 6.0414990118091225,
                "killsRatio": 0.4919607891987451,
                "deathsRatio": 0.1949439245750976,
                "killAssistPerMin": 0.4643460786691448,
                "killsPerMin": 0.2162393102320069,
                "deathsPerMin": 0.18863022855640066,
                "assistsPerMin": 0.24810676843713786,
                "totalHealPerMin": 88.25509565065765,
                "damageSelfMitigatedPerMin": 321.07687858860214,
                "damageDealtToTurretsPerMin": 130.59846403633244,
                "timeCCingOthersPerMin": 0.5196747552478088,
                "neutralMinionsKilledPerMin": 0.4415217875510069,
                "totalTimeCrowdControlDealtPerMin": 6.583815242578974,
                "visionWardsBoughtInGamePerMin": 0.049172324059331377,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06920600495033068,
                "wardsPlacedPerMin": 0.2892026825113743,
                "wardsKilledPerMin": 0.09216027319665213
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 290.56094978030336,
                "damageDealtToObjectivesPerMin": 248.3404320238435,
                "visionScorePerMin": 0.2854597225723323,
                "totalDamageTakenPerMin": 165.8811372641277,
                "totalMinionsKilledPerMin": 1.2056353299197171,
                "killsRatio": 0.16170150563103655,
                "deathsRatio": 0.07580259216152023,
                "killAssistPerMin": 0.22322492484082285,
                "killsPerMin": 0.14481140505469356,
                "deathsPerMin": 0.09306928511688314,
                "assistsPerMin": 0.13665895051437765,
                "totalHealPerMin": 68.21795848645597,
                "damageSelfMitigatedPerMin": 168.20616481916662,
                "damageDealtToTurretsPerMin": 117.64601021135698,
                "timeCCingOthersPerMin": 0.4733406489494739,
                "neutralMinionsKilledPerMin": 0.3886246869964476,
                "totalTimeCrowdControlDealtPerMin": 7.409907795903098,
                "visionWardsBoughtInGamePerMin": 0.051898039156837265,
                "neutralMinionsKilledEnemyJunglePerMin": 0.12785022625457007,
                "wardsPlacedPerMin": 0.09083017117253747,
                "wardsKilledPerMin": 0.07934243499810623
              }
            },
            "support": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 336.9073635362285,
                "damageDealtToObjectivesPerMin": 85.01287772475814,
                "visionScorePerMin": 1.5827202412928882,
                "totalDamageTakenPerMin": 533.9581852165893,
                "totalMinionsKilledPerMin": 1.0590792110930842,
                "killsRatio": 0.5055093601407326,
                "deathsRatio": 0.19542132653626335,
                "killAssistPerMin": 0.4793465456433882,
                "killsPerMin": 0.09177163685415322,
                "deathsPerMin": 0.19007101905745366,
                "assistsPerMin": 0.38757490878923495,
                "totalHealPerMin": 111.74932214775879,
                "damageSelfMitigatedPerMin": 464.1038225458908,
                "damageDealtToTurretsPerMin": 38.75865648086608,
                "timeCCingOthersPerMin": 1.0900843155721316,
                "neutralMinionsKilledPerMin": 0.05206843189835414,
                "totalTimeCrowdControlDealtPerMin": 5.013772797058958,
                "visionWardsBoughtInGamePerMin": 0.15363799952800627,
                "neutralMinionsKilledEnemyJunglePerMin": 0.007870214853121199,
                "wardsPlacedPerMin": 0.7552874521276203,
                "wardsKilledPerMin": 0.1743761790381606
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 176.72403046736622,
                "damageDealtToObjectivesPerMin": 85.41144255494451,
                "visionScorePerMin": 0.5872259873552169,
                "totalDamageTakenPerMin": 189.44444219198468,
                "totalMinionsKilledPerMin": 0.5147038489386767,
                "killsRatio": 0.15309150415786707,
                "deathsRatio": 0.08158953853225165,
                "killAssistPerMin": 0.22355045589114794,
                "killsPerMin": 0.08250705609609121,
                "deathsPerMin": 0.09866822157666837,
                "assistsPerMin": 0.19880789444653496,
                "totalHealPerMin": 179.67908970677914,
                "damageSelfMitigatedPerMin": 311.0911501454229,
                "damageDealtToTurretsPerMin": 40.54690313326947,
                "timeCCingOthersPerMin": 0.5080541933850762,
                "neutralMinionsKilledPerMin": 0.2231230052971938,
                "totalTimeCrowdControlDealtPerMin": 3.5806923451488624,
                "visionWardsBoughtInGamePerMin": 0.11341658335586273,
                "neutralMinionsKilledEnemyJunglePerMin": 0.05262235412912058,
                "wardsPlacedPerMin": 0.2907961476343582,
                "wardsKilledPerMin": 0.1276959492972917
              }
            }
          },
          "gold": {
            "total": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 568.0064534474059,
                "damageDealtToObjectivesPerMin": 307.40448095560095,
                "visionScorePerMin": 0.926014514362095,
                "totalDamageTakenPerMin": 764.9403013889572,
                "totalMinionsKilledPerMin": 3.998067797216049,
                "killsRatio": 0.480351770467503,
                "deathsRatio": 0.20003270111183785,
                "killAssistPerMin": 0.4594318838815458,
                "killsPerMin": 0.19073687203992007,
                "deathsPerMin": 0.19957843885238916,
                "assistsPerMin": 0.2686950118416257,
                "totalHealPerMin": 67.60412935803986,
                "damageSelfMitigatedPerMin": 586.1111558699954,
                "damageDealtToTurretsPerMin": 84.64830997051064,
                "timeCCingOthersPerMin": 0.755938008150986,
                "neutralMinionsKilledPerMin": 1.0873545263521516,
                "totalTimeCrowdControlDealtPerMin": 6.9882584850072895,
                "visionWardsBoughtInGamePerMin": 0.09470482028093972,
                "neutralMinionsKilledEnemyJunglePerMin": 0.10962457446672554,
                "wardsPlacedPerMin": 0.38806742973305497,
                "wardsKilledPerMin": 0.12280211695884635
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 270.22112300808874,
                "damageDealtToObjectivesPerMin": 322.6818019123474,
                "visionScorePerMin": 0.5285470196744342,
                "totalDamageTakenPerMin": 264.2354612585178,
                "totalMinionsKilledPerMin": 2.5050477382338263,
                "killsRatio": 0.1605669401584387,
                "deathsRatio": 0.0772832351327857,
                "killAssistPerMin": 0.20829250606359284,
                "killsPerMin": 0.1364590899753632,
                "deathsPerMin": 0.09394287919345548,
                "assistsPerMin": 0.1609521080997782,
                "totalHealPerMin": 158.63869830732241,
                "damageSelfMitigatedPerMin": 381.63906776247313,
                "damageDealtToTurretsPerMin": 97.3478997864431,
                "timeCCingOthersPerMin": 0.6301502239752462,
                "neutralMinionsKilledPerMin": 1.6545307778865719,
                "totalTimeCrowdControlDealtPerMin": 5.982289833156423,
                "visionWardsBoughtInGamePerMin": 0.08597433558134923,
                "neutralMinionsKilledEnemyJunglePerMin": 0.21280384757876,
                "wardsPlacedPerMin": 0.2499203434096353,
                "wardsKilledPerMin": 0.09680152109037264
              }
            },
            "top": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 605.1833329694513,
                "damageDealtToObjectivesPerMin": 253.87550825491346,
                "visionScorePerMin": 0.6121083334080909,
                "totalDamageTakenPerMin": 883.2540107972902,
                "totalMinionsKilledPerMin": 5.798517737537364,
                "killsRatio": 0.3970354418817707,
                "deathsRatio": 0.19983024949301406,
                "killAssistPerMin": 0.37764064643462747,
                "killsPerMin": 0.17942388571220366,
                "deathsPerMin": 0.19715499484821555,
                "assistsPerMin": 0.19821676072242386,
                "totalHealPerMin": 12.391569395452011,
                "damageSelfMitigatedPerMin": 815.0802500570787,
                "damageDealtToTurretsPerMin": 124.36322054059623,
                "timeCCingOthersPerMin": 0.7264512836780586,
                "neutralMinionsKilledPerMin": 0.3249676204226855,
                "totalTimeCrowdControlDealtPerMin": 6.529774276420051,
                "visionWardsBoughtInGamePerMin": 0.05422222012311674,
                "neutralMinionsKilledEnemyJunglePerMin": 0.07618156286226188,
                "wardsPlacedPerMin": 0.278926527786807,
                "wardsKilledPerMin": 0.07253478574905511
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 233.9375384945201,
                "damageDealtToObjectivesPerMin": 206.67857661376453,
                "visionScorePerMin": 0.2292387756705675,
                "totalDamageTakenPerMin": 238.01077265953614,
                "totalMinionsKilledPerMin": 1.0821221237739358,
                "killsRatio": 0.14924424678981638,
                "deathsRatio": 0.07988786303316639,
                "killAssistPerMin": 0.17714925672365245,
                "killsPerMin": 0.10886605857511046,
                "deathsPerMin": 0.09209689172282842,
                "assistsPerMin": 0.1240887452219194,
                "totalHealPerMin": 66.24841118882156,
                "damageSelfMitigatedPerMin": 430.30597143516974,
                "damageDealtToTurretsPerMin": 93.35786051922892,
                "timeCCingOthersPerMin": 0.5303620321223724,
                "neutralMinionsKilledPerMin": 0.3227381774392217,
                "totalTimeCrowdControlDealtPerMin": 5.936798455274899,
                "visionWardsBoughtInGamePerMin": 0.0518913234021113,
                "neutralMinionsKilledEnemyJunglePerMin": 0.13944556091709753,
                "wardsPlacedPerMin": 0.08530936505371127,
                "wardsKilledPerMin": 0.06047643345861349
              }
            },
            "mid": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 673.0249733285043,
                "damageDealtToObjectivesPerMin": 204.3333091571145,
                "visionScorePerMin": 0.7272721139673579,
                "totalDamageTakenPerMin": 774.0601822297681,
                "totalMinionsKilledPerMin": 5.651594464256562,
                "killsRatio": 0.4771068783318034,
                "deathsRatio": 0.21363293252196425,
                "killAssistPerMin": 0.4544839530364522,
                "killsPerMin": 0.238932636300472,
                "deathsPerMin": 0.21210345820925824,
                "assistsPerMin": 0.2155513167359802,
                "totalHealPerMin": 8.886560895108264,
                "damageSelfMitigatedPerMin": 547.103282253554,
                "damageDealtToTurretsPerMin": 96.98617324003115,
                "timeCCingOthersPerMin": 0.6809293833737853,
                "neutralMinionsKilledPerMin": 0.3803997727010914,
                "totalTimeCrowdControlDealtPerMin": 5.735196040108176,
                "visionWardsBoughtInGamePerMin": 0.07868797318854248,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06804656553393337,
                "wardsPlacedPerMin": 0.3100903513134479,
                "wardsKilledPerMin": 0.10581019399858736
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 254.2630562898447,
                "damageDealtToObjectivesPerMin": 190.1760283813718,
                "visionScorePerMin": 0.2684659848555727,
                "totalDamageTakenPerMin": 227.75404040195983,
                "totalMinionsKilledPerMin": 1.1930359603743563,
                "killsRatio": 0.15619304204022694,
                "deathsRatio": 0.08341977882102278,
                "killAssistPerMin": 0.2029966636989906,
                "killsPerMin": 0.14735000377007787,
                "deathsPerMin": 0.10099820783335033,
                "assistsPerMin": 0.12543368429124888,
                "totalHealPerMin": 42.89540976299126,
                "damageSelfMitigatedPerMin": 329.02240359367005,
                "damageDealtToTurretsPerMin": 121.45815056816816,
                "timeCCingOthersPerMin": 0.4875597611305056,
                "neutralMinionsKilledPerMin": 0.3769293725496695,
                "totalTimeCrowdControlDealtPerMin": 4.613639060755649,
                "visionWardsBoughtInGamePerMin": 0.0626408744829476,
                "neutralMinionsKilledEnemyJunglePerMin": 0.1545922517182539,
                "wardsPlacedPerMin": 0.102785743440679,
                "wardsKilledPerMin": 0.08401373186181132
              }
            },
            "jungle": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 537.9482363968998,
                "damageDealtToObjectivesPerMin": 683.2083768625196,
                "visionScorePerMin": 0.8982459283202341,
                "totalDamageTakenPerMin": 992.4577064519195,
                "totalMinionsKilledPerMin": 1.3233211189140028,
                "killsRatio": 0.5207690215947531,
                "deathsRatio": 0.1978380255335532,
                "killAssistPerMin": 0.4982277747185978,
                "killsPerMin": 0.2275979850101307,
                "deathsPerMin": 0.19686419994021231,
                "assistsPerMin": 0.27062978970846696,
                "totalHealPerMin": 80.01035548258001,
                "damageSelfMitigatedPerMin": 756.6718758045914,
                "damageDealtToTurretsPerMin": 39.44337127673341,
                "timeCCingOthersPerMin": 0.8173709002785271,
                "neutralMinionsKilledPerMin": 4.22812287152078,
                "totalTimeCrowdControlDealtPerMin": 11.465148339988867,
                "visionWardsBoughtInGamePerMin": 0.1193645937164697,
                "neutralMinionsKilledEnemyJunglePerMin": 0.3295065427130227,
                "wardsPlacedPerMin": 0.2568329509605774,
                "wardsKilledPerMin": 0.14169636734095506
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 244.02501161107784,
                "damageDealtToObjectivesPerMin": 419.57478678500985,
                "visionScorePerMin": 0.3492856693810888,
                "totalDamageTakenPerMin": 213.48336766444646,
                "totalMinionsKilledPerMin": 0.637528906997886,
                "killsRatio": 0.15981973813965314,
                "deathsRatio": 0.07622665262178582,
                "killAssistPerMin": 0.2122279279303068,
                "killsPerMin": 0.13799621346276758,
                "deathsPerMin": 0.08893953529143342,
                "assistsPerMin": 0.14488309831415766,
                "totalHealPerMin": 240.6455531751466,
                "damageSelfMitigatedPerMin": 368.0964891197985,
                "damageDealtToTurretsPerMin": 54.0027273370699,
                "timeCCingOthersPerMin": 0.9359864295302309,
                "neutralMinionsKilledPerMin": 0.9357019931608797,
                "totalTimeCrowdControlDealtPerMin": 5.950324606333737,
                "visionWardsBoughtInGamePerMin": 0.09225488962664677,
                "neutralMinionsKilledEnemyJunglePerMin": 0.32481560599748144,
                "wardsPlacedPerMin": 0.1483368494002826,
                "wardsKilledPerMin": 0.08854079352836487
              }
            },
            "bottom": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 673.2821494314844,
                "damageDealtToObjectivesPerMin": 313.08892168562187,
                "visionScorePerMin": 0.6828295916351985,
                "totalDamageTakenPerMin": 618.5043018252011,
                "totalMinionsKilledPerMin": 6.198064500454098,
                "killsRatio": 0.4936672063020953,
                "deathsRatio": 0.19756508123631208,
                "killAssistPerMin": 0.4732057255400682,
                "killsPerMin": 0.21503535021319528,
                "deathsPerMin": 0.1985781084768296,
                "assistsPerMin": 0.2581703753268728,
                "totalHealPerMin": 93.64565451596806,
                "damageSelfMitigatedPerMin": 347.79239943652686,
                "damageDealtToTurretsPerMin": 123.32829183807699,
                "timeCCingOthersPerMin": 0.5018222116475973,
                "neutralMinionsKilledPerMin": 0.4749757462073608,
                "totalTimeCrowdControlDealtPerMin": 6.409445015899609,
                "visionWardsBoughtInGamePerMin": 0.05442962259280807,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06997529009953746,
                "wardsPlacedPerMin": 0.2981988907342028,
                "wardsKilledPerMin": 0.10390902791707296
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 286.292974870026,
                "damageDealtToObjectivesPerMin": 239.23595906735167,
                "visionScorePerMin": 0.2729700531691807,
                "totalDamageTakenPerMin": 151.10925250580334,
                "totalMinionsKilledPerMin": 1.1105194500661408,
                "killsRatio": 0.1625237041875444,
                "deathsRatio": 0.06785569793747807,
                "killAssistPerMin": 0.21478277174510635,
                "killsPerMin": 0.1411048021470823,
                "deathsPerMin": 0.09261515368256737,
                "assistsPerMin": 0.12680686676196445,
                "totalHealPerMin": 72.84501106469436,
                "damageSelfMitigatedPerMin": 205.30536573250993,
                "damageDealtToTurretsPerMin": 110.00008024547272,
                "timeCCingOthersPerMin": 0.4439898984334956,
                "neutralMinionsKilledPerMin": 0.3623295666298301,
                "totalTimeCrowdControlDealtPerMin": 7.10527879005649,
                "visionWardsBoughtInGamePerMin": 0.048823595716694736,
                "neutralMinionsKilledEnemyJunglePerMin": 0.11928235856419732,
                "wardsPlacedPerMin": 0.08286533169853276,
                "wardsKilledPerMin": 0.07704249572781348
              }
            },
            "support": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 351.105941378558,
                "damageDealtToObjectivesPerMin": 85.69705749520199,
                "visionScorePerMin": 1.7068832518339876,
                "totalDamageTakenPerMin": 558.5867056597419,
                "totalMinionsKilledPerMin": 1.0111204264025635,
                "killsRatio": 0.513336675459975,
                "deathsRatio": 0.19131137355995104,
                "killAssistPerMin": 0.4937427605440346,
                "killsPerMin": 0.0932539967584417,
                "deathsPerMin": 0.19319455502925625,
                "assistsPerMin": 0.4004887637855928,
                "totalHealPerMin": 142.92145788580117,
                "damageSelfMitigatedPerMin": 465.4171725276751,
                "damageDealtToTurretsPerMin": 38.97429732071738,
                "timeCCingOthersPerMin": 1.0525484687171225,
                "neutralMinionsKilledPerMin": 0.05221731991789719,
                "totalTimeCrowdControlDealtPerMin": 4.838016405526418,
                "visionWardsBoughtInGamePerMin": 0.16674543991199695,
                "neutralMinionsKilledEnemyJunglePerMin": 0.006188075945179982,
                "wardsPlacedPerMin": 0.7941037700341421,
                "wardsKilledPerMin": 0.1899642176319962
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 182.7082595556776,
                "damageDealtToObjectivesPerMin": 82.50130062872769,
                "visionScorePerMin": 0.5157557053034351,
                "totalDamageTakenPerMin": 207.17102808657117,
                "totalMinionsKilledPerMin": 0.4934600272313551,
                "killsRatio": 0.1440804042241849,
                "deathsRatio": 0.07688982601351879,
                "killAssistPerMin": 0.21007763996149612,
                "killsPerMin": 0.08308271362946965,
                "deathsPerMin": 0.09409058797636703,
                "assistsPerMin": 0.1902200581165718,
                "totalHealPerMin": 209.3038680978557,
                "damageSelfMitigatedPerMin": 323.09582188381677,
                "damageDealtToTurretsPerMin": 40.41248839033844,
                "timeCCingOthersPerMin": 0.4883637342513187,
                "neutralMinionsKilledPerMin": 0.308609131264581,
                "totalTimeCrowdControlDealtPerMin": 3.2114970017466833,
                "visionWardsBoughtInGamePerMin": 0.10090968672994867,
                "neutralMinionsKilledEnemyJunglePerMin": 0.03303392886915707,
                "wardsPlacedPerMin": 0.2385232092280299,
                "wardsKilledPerMin": 0.11991392366136555
              }
            }
          },
          "iron": {
            "total": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 514.7155235524775,
                "damageDealtToObjectivesPerMin": 253.72786347212246,
                "visionScorePerMin": 0.6654609618491839,
                "totalDamageTakenPerMin": 705.8133290194901,
                "totalMinionsKilledPerMin": 3.471742546091472,
                "killsRatio": 0.4423371816804517,
                "deathsRatio": 0.1999455249859588,
                "killAssistPerMin": 0.3992326010673149,
                "killsPerMin": 0.1791967334906443,
                "deathsPerMin": 0.1908896021278296,
                "assistsPerMin": 0.2200358675766705,
                "totalHealPerMin": 51.84014956443242,
                "damageSelfMitigatedPerMin": 528.2838216550514,
                "damageDealtToTurretsPerMin": 86.31852997155553,
                "timeCCingOthersPerMin": 0.7639365225629666,
                "neutralMinionsKilledPerMin": 0.9308612502979076,
                "totalTimeCrowdControlDealtPerMin": 6.816023227157278,
                "visionWardsBoughtInGamePerMin": 0.04581656215852696,
                "neutralMinionsKilledEnemyJunglePerMin": 0.10528688526564098,
                "wardsPlacedPerMin": 0.3134865813022907,
                "wardsKilledPerMin": 0.058003267173148985
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 288.6597040059473,
                "damageDealtToObjectivesPerMin": 277.44767788548165,
                "visionScorePerMin": 0.4025027117860814,
                "totalDamageTakenPerMin": 268.85396287583717,
                "totalMinionsKilledPerMin": 2.2024559257364973,
                "killsRatio": 0.1751589208146105,
                "deathsRatio": 0.08640783441876405,
                "killAssistPerMin": 0.21928596249622775,
                "killsPerMin": 0.1459256826773178,
                "deathsPerMin": 0.10212781948812896,
                "assistsPerMin": 0.15133333752284658,
                "totalHealPerMin": 137.2303826884425,
                "damageSelfMitigatedPerMin": 363.83190036416016,
                "damageDealtToTurretsPerMin": 100.88368710640478,
                "timeCCingOthersPerMin": 0.7193892611353474,
                "neutralMinionsKilledPerMin": 1.4823816730442283,
                "totalTimeCrowdControlDealtPerMin": 6.081327469336148,
                "visionWardsBoughtInGamePerMin": 0.06303983253830639,
                "neutralMinionsKilledEnemyJunglePerMin": 0.22457017919259606,
                "wardsPlacedPerMin": 0.1858360906267447,
                "wardsKilledPerMin": 0.06470841345098181
              }
            },
            "top": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 570.9875124884946,
                "damageDealtToObjectivesPerMin": 236.15254767644225,
                "visionScorePerMin": 0.4851551284044676,
                "totalDamageTakenPerMin": 802.6866733595157,
                "totalMinionsKilledPerMin": 4.999804484921636,
                "killsRatio": 0.3837913734249372,
                "deathsRatio": 0.19314817511164614,
                "killAssistPerMin": 0.3408198994397019,
                "killsPerMin": 0.17502159753485266,
                "deathsPerMin": 0.18595165736812005,
                "assistsPerMin": 0.1657983019048492,
                "totalHealPerMin": 17.113765378179142,
                "damageSelfMitigatedPerMin": 722.6699919001794,
                "damageDealtToTurretsPerMin": 139.26787410043192,
                "timeCCingOthersPerMin": 0.7250948427126886,
                "neutralMinionsKilledPerMin": 0.2684776723372926,
                "totalTimeCrowdControlDealtPerMin": 6.253283133826628,
                "visionWardsBoughtInGamePerMin": 0.02978983533997316,
                "neutralMinionsKilledEnemyJunglePerMin": 0.06601294141863215,
                "wardsPlacedPerMin": 0.25100470293496274,
                "wardsKilledPerMin": 0.03581312192101965
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 279.0430040851149,
                "damageDealtToObjectivesPerMin": 197.1025705878677,
                "visionScorePerMin": 0.2014047279476032,
                "totalDamageTakenPerMin": 279.9717994754564,
                "totalMinionsKilledPerMin": 1.3881815347382291,
                "killsRatio": 0.1607712041391338,
                "deathsRatio": 0.0885443494477022,
                "killAssistPerMin": 0.1899049803265218,
                "killsPerMin": 0.12735129505785842,
                "deathsPerMin": 0.10193277371607733,
                "assistsPerMin": 0.1218168122914744,
                "totalHealPerMin": 95.52425672082418,
                "damageSelfMitigatedPerMin": 434.8341072416314,
                "damageDealtToTurretsPerMin": 124.97513768825942,
                "timeCCingOthersPerMin": 0.534778407145315,
                "neutralMinionsKilledPerMin": 0.3231389944876809,
                "totalTimeCrowdControlDealtPerMin": 5.927380643482804,
                "visionWardsBoughtInGamePerMin": 0.04197766139908325,
                "neutralMinionsKilledEnemyJunglePerMin": 0.14060148880122197,
                "wardsPlacedPerMin": 0.08349839788825686,
                "wardsKilledPerMin": 0.04243511601296642
              }
            },
            "mid": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 620.3483814554114,
                "damageDealtToObjectivesPerMin": 167.04735742946946,
                "visionScorePerMin": 0.5416299687505326,
                "totalDamageTakenPerMin": 711.0451997243891,
                "totalMinionsKilledPerMin": 4.815826486146212,
                "killsRatio": 0.4551342153409771,
                "deathsRatio": 0.20532273349607216,
                "killAssistPerMin": 0.4165403614717665,
                "killsPerMin": 0.22793900556588545,
                "deathsPerMin": 0.19716397467748747,
                "assistsPerMin": 0.18860135590588095,
                "totalHealPerMin": 6.764530741737069,
                "damageSelfMitigatedPerMin": 489.4994594571457,
                "damageDealtToTurretsPerMin": 89.95869624279835,
                "timeCCingOthersPerMin": 0.7182410309339313,
                "neutralMinionsKilledPerMin": 0.28775170967567315,
                "totalTimeCrowdControlDealtPerMin": 5.1856845876325615,
                "visionWardsBoughtInGamePerMin": 0.04400657090998362,
                "neutralMinionsKilledEnemyJunglePerMin": 0.07237855726852788,
                "wardsPlacedPerMin": 0.2770937803972552,
                "wardsKilledPerMin": 0.04781416983305629
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 287.2383350282097,
                "damageDealtToObjectivesPerMin": 177.73973439948531,
                "visionScorePerMin": 0.2177216886944514,
                "totalDamageTakenPerMin": 237.57702062698962,
                "totalMinionsKilledPerMin": 1.3338156513848964,
                "killsRatio": 0.17899121151010847,
                "deathsRatio": 0.08747475738075336,
                "killAssistPerMin": 0.2318333599659137,
                "killsPerMin": 0.17279130913699328,
                "deathsPerMin": 0.10549333050088347,
                "assistsPerMin": 0.12660117095610007,
                "totalHealPerMin": 38.2153149029717,
                "damageSelfMitigatedPerMin": 314.63004384178464,
                "damageDealtToTurretsPerMin": 94.54978043776501,
                "timeCCingOthersPerMin": 0.6705147698491912,
                "neutralMinionsKilledPerMin": 0.3557098224839737,
                "totalTimeCrowdControlDealtPerMin": 4.1689452353385565,
                "visionWardsBoughtInGamePerMin": 0.05500821314183893,
                "neutralMinionsKilledEnemyJunglePerMin": 0.1712344942807757,
                "wardsPlacedPerMin": 0.08823953576548077,
                "wardsKilledPerMin": 0.052720873330962745
              }
            },
            "jungle": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 450.4430326937195,
                "damageDealtToObjectivesPerMin": 516.7631109759188,
                "visionScorePerMin": 0.6404809536100594,
                "totalDamageTakenPerMin": 923.8650129258127,
                "totalMinionsKilledPerMin": 1.1702378895566918,
                "killsRatio": 0.4548559896834556,
                "deathsRatio": 0.20428860839290527,
                "killAssistPerMin": 0.4128079016976968,
                "killsPerMin": 0.19935713209400568,
                "deathsPerMin": 0.19725931982170505,
                "assistsPerMin": 0.2134507696036912,
                "totalHealPerMin": 67.61182152453156,
                "damageSelfMitigatedPerMin": 667.0415074483321,
                "damageDealtToTurretsPerMin": 38.36627780867726,
                "timeCCingOthersPerMin": 0.7923150399683337,
                "neutralMinionsKilledPerMin": 3.621932582202136,
                "totalTimeCrowdControlDealtPerMin": 11.395884164613767,
                "visionWardsBoughtInGamePerMin": 0.04957581784500555,
                "neutralMinionsKilledEnemyJunglePerMin": 0.2977766162656841,
                "wardsPlacedPerMin": 0.2280172362651775,
                "wardsKilledPerMin": 0.06288102069254385
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 243.40973935905757,
                "damageDealtToObjectivesPerMin": 377.97815081630546,
                "visionScorePerMin": 0.2548289515519322,
                "totalDamageTakenPerMin": 221.55742076388643,
                "totalMinionsKilledPerMin": 0.7320204570031399,
                "killsRatio": 0.18146654954496366,
                "deathsRatio": 0.08648795690223775,
                "killAssistPerMin": 0.2360312817181147,
                "killsPerMin": 0.14944936453148433,
                "deathsPerMin": 0.10668417138988084,
                "assistsPerMin": 0.14145972135105706,
                "totalHealPerMin": 222.9369387233918,
                "damageSelfMitigatedPerMin": 354.9801704401101,
                "damageDealtToTurretsPerMin": 62.13702178234805,
                "timeCCingOthersPerMin": 1.0832436965900594,
                "neutralMinionsKilledPerMin": 1.1557308391843375,
                "totalTimeCrowdControlDealtPerMin": 6.489507806360573,
                "visionWardsBoughtInGamePerMin": 0.07272338274470304,
                "neutralMinionsKilledEnemyJunglePerMin": 0.3582384812670727,
                "wardsPlacedPerMin": 0.11343474874342313,
                "wardsKilledPerMin": 0.05999365727622408
              }
            },
            "bottom": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 601.0108947374421,
                "damageDealtToObjectivesPerMin": 262.4937358999979,
                "visionScorePerMin": 0.5170788842913294,
                "totalDamageTakenPerMin": 564.8581615338419,
                "totalMinionsKilledPerMin": 5.2950316025882485,
                "killsRatio": 0.4622774531403092,
                "deathsRatio": 0.1943775219524484,
                "killAssistPerMin": 0.4120228510760208,
                "killsPerMin": 0.20129272055416744,
                "deathsPerMin": 0.18192530241292126,
                "assistsPerMin": 0.21073013052185344,
                "totalHealPerMin": 83.32454067392173,
                "damageSelfMitigatedPerMin": 310.7794057139278,
                "damageDealtToTurretsPerMin": 122.12669074129083,
                "timeCCingOthersPerMin": 0.5312164495559454,
                "neutralMinionsKilledPerMin": 0.4099581356079669,
                "totalTimeCrowdControlDealtPerMin": 6.085502686710867,
                "visionWardsBoughtInGamePerMin": 0.03041819506266292,
                "neutralMinionsKilledEnemyJunglePerMin": 0.08336128487160421,
                "wardsPlacedPerMin": 0.25631925500500824,
                "wardsKilledPerMin": 0.05030116807546423
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 319.9059746196572,
                "damageDealtToObjectivesPerMin": 241.98951392920705,
                "visionScorePerMin": 0.2482131746924431,
                "totalDamageTakenPerMin": 175.3251384313955,
                "totalMinionsKilledPerMin": 1.1427127731435294,
                "killsRatio": 0.17888707863512782,
                "deathsRatio": 0.0780601933165868,
                "killAssistPerMin": 0.2136463160144517,
                "killsPerMin": 0.14543080485957374,
                "deathsPerMin": 0.08901485194106168,
                "assistsPerMin": 0.12644268708416154,
                "totalHealPerMin": 75.6775452049573,
                "damageSelfMitigatedPerMin": 201.27632202041192,
                "damageDealtToTurretsPerMin": 111.10983319175173,
                "timeCCingOthersPerMin": 0.4997674214222719,
                "neutralMinionsKilledPerMin": 0.4526052521182259,
                "totalTimeCrowdControlDealtPerMin": 6.972647199296995,
                "visionWardsBoughtInGamePerMin": 0.044628915797847,
                "neutralMinionsKilledEnemyJunglePerMin": 0.15208146054713054,
                "wardsPlacedPerMin": 0.09621355949551502,
                "wardsKilledPerMin": 0.05352570094647119
              }
            },
            "support": {
              "mean": {
                "totalDamageDealtToChampionsPerMin": 332.5128056808533,
                "damageDealtToObjectivesPerMin": 84.4997253105928,
                "visionScorePerMin": 1.1402992351989658,
                "totalDamageTakenPerMin": 526.048701476429,
                "totalMinionsKilledPerMin": 1.1136998507747553,
                "killsRatio": 0.4553211455917314,
                "deathsRatio": 0.20254421736156256,
                "killAssistPerMin": 0.4136701918947773,
                "killsPerMin": 0.09270437483412518,
                "deathsPerMin": 0.19210171577044532,
                "assistsPerMin": 0.3209658170606521,
                "totalHealPerMin": 83.86284846063437,
                "damageSelfMitigatedPerMin": 451.2648947169809,
                "damageDealtToTurretsPerMin": 42.5892543599716,
                "timeCCingOthersPerMin": 1.0512509844316755,
                "neutralMinionsKilledPerMin": 0.045011749180069935,
                "totalTimeCrowdControlDealtPerMin": 5.124501421389429,
                "visionWardsBoughtInGamePerMin": 0.07509300596622216,
                "neutralMinionsKilledEnemyJunglePerMin": 0.0056003724125481035,
                "wardsPlacedPerMin": 0.5543817597643045,
                "wardsKilledPerMin": 0.09292562502438273
              },
              "std": {
                "totalDamageDealtToChampionsPerMin": 191.81096072473443,
                "damageDealtToObjectivesPerMin": 83.1827034481066,
                "visionScorePerMin": 0.5473099426431313,
                "totalDamageTakenPerMin": 195.20019512943333,
                "totalMinionsKilledPerMin": 0.6075483508091971,
                "killsRatio": 0.16341202472459382,
                "deathsRatio": 0.09078803685376208,
                "killAssistPerMin": 0.21360681912143525,
                "killsPerMin": 0.08032880026246328,
                "deathsPerMin": 0.1062280615146112,
                "assistsPerMin": 0.18325914133055665,
                "totalHealPerMin": 150.22470606736937,
                "damageSelfMitigatedPerMin": 311.86585584572447,
                "damageDealtToTurretsPerMin": 44.81858963103126,
                "timeCCingOthersPerMin": 0.5379925716523734,
                "neutralMinionsKilledPerMin": 0.10213867341612323,
                "totalTimeCrowdControlDealtPerMin": 3.8179708881859673,
                "visionWardsBoughtInGamePerMin": 0.08033551493403755,
                "neutralMinionsKilledEnemyJunglePerMin": 0.03444710388698883,
                "wardsPlacedPerMin": 0.2486143021831112,
                "wardsKilledPerMin": 0.08955904503246513
              }
            }
          }
        },
        "tierAnalysis": {
          "bronze": {
            "total": {
              "totalDamageDealtToChampionsPerMin": 0.5195888853065179,
              "damageDealtToObjectivesPerMin": 0.48042079769430523,
              "visionScorePerMin": 0.43345339267669103,
              "totalDamageTakenPerMin": 0.5068896143331664,
              "totalMinionsKilledPerMin": 0.4546007937664029,
              "killsRatio": 0.4853445695930991,
              "deathsRatio": 0.500954208316147,
              "killAssistPerMin": 0.5104404629901413,
              "killsPerMin": 0.5168250775289875,
              "deathsPerMin": 0.502151900912388,
              "assistsPerMin": 0.4995076731034086,
              "totalHealPerMin": 0.48762435563526707,
              "damageSelfMitigatedPerMin": 0.5191185161970177,
              "damageDealtToTurretsPerMin": 0.512442054975545,
              "timeCCingOthersPerMin": 0.5119712933395849,
              "neutralMinionsKilledPerMin": 0.48405271463049543,
              "totalTimeCrowdControlDealtPerMin": 0.4854136040885286,
              "visionWardsBoughtInGamePerMin": 0.3875516374688457,
              "neutralMinionsKilledEnemyJunglePerMin": 0.48863256309266717,
              "wardsPlacedPerMin": 0.45102109239775323,
              "wardsKilledPerMin": 0.3856604382589765
            },
            "top": {
              "totalDamageDealtToChampionsPerMin": 0.5294459262692848,
              "damageDealtToObjectivesPerMin": 0.5070215542643881,
              "visionScorePerMin": 0.42906809379681854,
              "totalDamageTakenPerMin": 0.5086182467975986,
              "totalMinionsKilledPerMin": 0.37059176438001507,
              "killsRatio": 0.5162031632296211,
              "deathsRatio": 0.49123055264952437,
              "killAssistPerMin": 0.5282760959552658,
              "killsPerMin": 0.5275743872863797,
              "deathsPerMin": 0.48913503579232115,
              "assistsPerMin": 0.5152685444385975,
              "totalHealPerMin": 0.4918685684333579,
              "damageSelfMitigatedPerMin": 0.5257065302903587,
              "damageDealtToTurretsPerMin": 0.5238078838901388,
              "timeCCingOthersPerMin": 0.5296424745326284,
              "neutralMinionsKilledPerMin": 0.5289277645516154,
              "totalTimeCrowdControlDealtPerMin": 0.5267585099401471,
              "visionWardsBoughtInGamePerMin": 0.40321933443244506,
              "neutralMinionsKilledEnemyJunglePerMin": 0.525049500483505,
              "wardsPlacedPerMin": 0.43154088100822274,
              "wardsKilledPerMin": 0.39693544851702534
            },
            "mid": {
              "totalDamageDealtToChampionsPerMin": 0.49799684331153676,
              "damageDealtToObjectivesPerMin": 0.46775556874353974,
              "visionScorePerMin": 0.4008418028141609,
              "totalDamageTakenPerMin": 0.5125534186540642,
              "totalMinionsKilledPerMin": 0.34565406723829883,
              "killsRatio": 0.4759688916658104,
              "deathsRatio": 0.5027822746614584,
              "killAssistPerMin": 0.5096071701307239,
              "killsPerMin": 0.5126343840309464,
              "deathsPerMin": 0.500412756385298,
              "assistsPerMin": 0.5008882466709083,
              "totalHealPerMin": 0.48768529086012763,
              "damageSelfMitigatedPerMin": 0.5220610553467254,
              "damageDealtToTurretsPerMin": 0.49826268375607435,
              "timeCCingOthersPerMin": 0.500457249677871,
              "neutralMinionsKilledPerMin": 0.5192506175022571,
              "totalTimeCrowdControlDealtPerMin": 0.4844447979812835,
              "visionWardsBoughtInGamePerMin": 0.38459695012874207,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5262808479413331,
              "wardsPlacedPerMin": 0.4419309547989907,
              "wardsKilledPerMin": 0.3669794349831599
            },
            "jungle": {
              "totalDamageDealtToChampionsPerMin": 0.5149352192005667,
              "damageDealtToObjectivesPerMin": 0.45775826081703425,
              "visionScorePerMin": 0.3803196232381034,
              "totalDamageTakenPerMin": 0.5027639085221983,
              "totalMinionsKilledPerMin": 0.5181212398797324,
              "killsRatio": 0.44174770530325175,
              "deathsRatio": 0.5107855311242925,
              "killAssistPerMin": 0.4821485766860031,
              "killsPerMin": 0.49330779038495737,
              "deathsPerMin": 0.5181153191356234,
              "assistsPerMin": 0.48011323963674835,
              "totalHealPerMin": 0.47203858614959193,
              "damageSelfMitigatedPerMin": 0.5180281562874037,
              "damageDealtToTurretsPerMin": 0.5422438610919881,
              "timeCCingOthersPerMin": 0.5104582591662727,
              "neutralMinionsKilledPerMin": 0.381619032423691,
              "totalTimeCrowdControlDealtPerMin": 0.4456843829400694,
              "visionWardsBoughtInGamePerMin": 0.36417118376984875,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4507591114790376,
              "wardsPlacedPerMin": 0.4644217900806566,
              "wardsKilledPerMin": 0.36006974769030997
            },
            "bottom": {
              "totalDamageDealtToChampionsPerMin": 0.5176280476945245,
              "damageDealtToObjectivesPerMin": 0.4691321059295709,
              "visionScorePerMin": 0.40784321308270366,
              "totalDamageTakenPerMin": 0.5064430372292528,
              "totalMinionsKilledPerMin": 0.33532549223953645,
              "killsRatio": 0.509184204535688,
              "deathsRatio": 0.49459034712068556,
              "killAssistPerMin": 0.5300797602808455,
              "killsPerMin": 0.5324199719228817,
              "deathsPerMin": 0.494474306991288,
              "assistsPerMin": 0.5147850041834916,
              "totalHealPerMin": 0.4832790588005266,
              "damageSelfMitigatedPerMin": 0.5058465356862683,
              "damageDealtToTurretsPerMin": 0.5040602558148929,
              "timeCCingOthersPerMin": 0.4783796743717631,
              "neutralMinionsKilledPerMin": 0.4555645513045532,
              "totalTimeCrowdControlDealtPerMin": 0.46099309121771576,
              "visionWardsBoughtInGamePerMin": 0.415312660969181,
              "neutralMinionsKilledEnemyJunglePerMin": 0.47510303169010915,
              "wardsPlacedPerMin": 0.4128685180432337,
              "wardsKilledPerMin": 0.3450358443567988
            },
            "support": {
              "totalDamageDealtToChampionsPerMin": 0.5605846201295136,
              "damageDealtToObjectivesPerMin": 0.4831932129020128,
              "visionScorePerMin": 0.395429721445798,
              "totalDamageTakenPerMin": 0.5109519796154633,
              "totalMinionsKilledPerMin": 0.4926331468895663,
              "killsRatio": 0.48341778788203027,
              "deathsRatio": 0.5055765609531956,
              "killAssistPerMin": 0.5067591241626076,
              "killsPerMin": 0.5325759678816169,
              "deathsPerMin": 0.5078775212576647,
              "assistsPerMin": 0.49406653360840846,
              "totalHealPerMin": 0.5000769523652259,
              "damageSelfMitigatedPerMin": 0.5296162822691226,
              "damageDealtToTurretsPerMin": 0.5191768392155457,
              "timeCCingOthersPerMin": 0.546801259685009,
              "neutralMinionsKilledPerMin": 0.49313940046012844,
              "totalTimeCrowdControlDealtPerMin": 0.5125927818997357,
              "visionWardsBoughtInGamePerMin": 0.32878356638326744,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4999427872279273,
              "wardsPlacedPerMin": 0.37818960192007134,
              "wardsKilledPerMin": 0.38823768925109436
            }
          },
          "silver": {
            "total": {
              "totalDamageDealtToChampionsPerMin": 0.4830583234070796,
              "damageDealtToObjectivesPerMin": 0.4967038246348762,
              "visionScorePerMin": 0.4742547566883093,
              "totalDamageTakenPerMin": 0.4802107862304841,
              "totalMinionsKilledPerMin": 0.49970519020885673,
              "killsRatio": 0.4809583997346298,
              "deathsRatio": 0.5000074733005694,
              "killAssistPerMin": 0.48348392505441207,
              "killsPerMin": 0.4973587457748167,
              "deathsPerMin": 0.4923992778509875,
              "assistsPerMin": 0.4804695442078419,
              "totalHealPerMin": 0.5035221774646483,
              "damageSelfMitigatedPerMin": 0.4754267521523213,
              "damageDealtToTurretsPerMin": 0.512536136284415,
              "timeCCingOthersPerMin": 0.5032281001800893,
              "neutralMinionsKilledPerMin": 0.4946276339103024,
              "totalTimeCrowdControlDealtPerMin": 0.5146729508620648,
              "visionWardsBoughtInGamePerMin": 0.45332172641510426,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4848843147985834,
              "wardsPlacedPerMin": 0.47432381731087064,
              "wardsKilledPerMin": 0.4592250782431757
            },
            "top": {
              "totalDamageDealtToChampionsPerMin": 0.4871798656289858,
              "damageDealtToObjectivesPerMin": 0.47905427756884633,
              "visionScorePerMin": 0.4989355976949178,
              "totalDamageTakenPerMin": 0.46815379691940306,
              "totalMinionsKilledPerMin": 0.514309504707204,
              "killsRatio": 0.44952442397486825,
              "deathsRatio": 0.51084948730006,
              "killAssistPerMin": 0.4631576673867307,
              "killsPerMin": 0.5043071211059376,
              "deathsPerMin": 0.5036120010616016,
              "assistsPerMin": 0.4422034620752946,
              "totalHealPerMin": 0.5279613252464719,
              "damageSelfMitigatedPerMin": 0.4527512886161909,
              "damageDealtToTurretsPerMin": 0.5068125421020979,
              "timeCCingOthersPerMin": 0.5199742090918651,
              "neutralMinionsKilledPerMin": 0.4653978026253133,
              "totalTimeCrowdControlDealtPerMin": 0.556293129854088,
              "visionWardsBoughtInGamePerMin": 0.48626866801964197,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5022303882435808,
              "wardsPlacedPerMin": 0.454390055414608,
              "wardsKilledPerMin": 0.4742841350652741
            },
            "mid": {
              "totalDamageDealtToChampionsPerMin": 0.4903445400333411,
              "damageDealtToObjectivesPerMin": 0.5304762449362024,
              "visionScorePerMin": 0.4747276577905779,
              "totalDamageTakenPerMin": 0.4648944555208974,
              "totalMinionsKilledPerMin": 0.49948127232908296,
              "killsRatio": 0.5012382947569487,
              "deathsRatio": 0.5121132562346002,
              "killAssistPerMin": 0.4918927316362685,
              "killsPerMin": 0.5000950449609506,
              "deathsPerMin": 0.49825342404417927,
              "assistsPerMin": 0.48636166897513433,
              "totalHealPerMin": 0.4658971984467502,
              "damageSelfMitigatedPerMin": 0.4782454744458112,
              "damageDealtToTurretsPerMin": 0.5124813696324204,
              "timeCCingOthersPerMin": 0.4844444319014072,
              "neutralMinionsKilledPerMin": 0.5290584015175168,
              "totalTimeCrowdControlDealtPerMin": 0.5126772421804655,
              "visionWardsBoughtInGamePerMin": 0.4757096128877388,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5048112567692933,
              "wardsPlacedPerMin": 0.5187673468895475,
              "wardsKilledPerMin": 0.43799104555076485
            },
            "jungle": {
              "totalDamageDealtToChampionsPerMin": 0.4462365818209078,
              "damageDealtToObjectivesPerMin": 0.5034257406007145,
              "visionScorePerMin": 0.4696336235598256,
              "totalDamageTakenPerMin": 0.47704283834993916,
              "totalMinionsKilledPerMin": 0.492371130889866,
              "killsRatio": 0.4814196486389571,
              "deathsRatio": 0.48855545895533997,
              "killAssistPerMin": 0.47988152030269654,
              "killsPerMin": 0.4789657080429316,
              "deathsPerMin": 0.4726831806562807,
              "assistsPerMin": 0.4899708020864987,
              "totalHealPerMin": 0.5266855051906981,
              "damageSelfMitigatedPerMin": 0.48573408503658394,
              "damageDealtToTurretsPerMin": 0.5147504072867788,
              "timeCCingOthersPerMin": 0.49054030695886086,
              "neutralMinionsKilledPerMin": 0.4727656890051167,
              "totalTimeCrowdControlDealtPerMin": 0.493869548070534,
              "visionWardsBoughtInGamePerMin": 0.43000579035513864,
              "neutralMinionsKilledEnemyJunglePerMin": 0.46566663208564163,
              "wardsPlacedPerMin": 0.5149284324120318,
              "wardsKilledPerMin": 0.4502052322778338
            },
            "bottom": {
              "totalDamageDealtToChampionsPerMin": 0.47095103087195145,
              "damageDealtToObjectivesPerMin": 0.46876150046502973,
              "visionScorePerMin": 0.4454165977365062,
              "totalDamageTakenPerMin": 0.47732095578703004,
              "totalMinionsKilledPerMin": 0.47611264102797074,
              "killsRatio": 0.4848214135481712,
              "deathsRatio": 0.5068710984515178,
              "killAssistPerMin": 0.4927411356181962,
              "killsPerMin": 0.5044548670427041,
              "deathsPerMin": 0.5064128333563738,
              "assistsPerMin": 0.48342643540237995,
              "totalHealPerMin": 0.5092126019284122,
              "damageSelfMitigatedPerMin": 0.45359823741238997,
              "damageDealtToTurretsPerMin": 0.5236272606150434,
              "timeCCingOthersPerMin": 0.4922805259706593,
              "neutralMinionsKilledPerMin": 0.43663086848098254,
              "totalTimeCrowdControlDealtPerMin": 0.49829072285643794,
              "visionWardsBoughtInGamePerMin": 0.44162665830316433,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4478066053179539,
              "wardsPlacedPerMin": 0.44972601130676115,
              "wardsKilledPerMin": 0.43031022843225325
            },
            "support": {
              "totalDamageDealtToChampionsPerMin": 0.5189557159520557,
              "damageDealtToObjectivesPerMin": 0.48702947327698465,
              "visionScorePerMin": 0.44470601379435204,
              "totalDamageTakenPerMin": 0.487692178979597,
              "totalMinionsKilledPerMin": 0.5157856622743103,
              "killsRatio": 0.4837458362506848,
              "deathsRatio": 0.48063866394820237,
              "killAssistPerMin": 0.48555515265416566,
              "killsPerMin": 0.49603869239452647,
              "deathsPerMin": 0.48191331201250803,
              "assistsPerMin": 0.4854011388975799,
              "totalHealPerMin": 0.47066798253880276,
              "damageSelfMitigatedPerMin": 0.4830978577222753,
              "damageDealtToTurretsPerMin": 0.5149089402005934,
              "timeCCingOthersPerMin": 0.5441594240459449,
              "neutralMinionsKilledPerMin": 0.5149617886561616,
              "totalTimeCrowdControlDealtPerMin": 0.5148358532116206,
              "visionWardsBoughtInGamePerMin": 0.4248966676986669,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5086162638016054,
              "wardsPlacedPerMin": 0.4105275980193286,
              "wardsKilledPerMin": 0.4728403657047085
            }
          },
          "platinum": {
            "total": {
              "totalDamageDealtToChampionsPerMin": 0.5204944267114371,
              "damageDealtToObjectivesPerMin": 0.521522995763505,
              "visionScorePerMin": 0.5795029618334573,
              "totalDamageTakenPerMin": 0.525022139563463,
              "totalMinionsKilledPerMin": 0.532210953491108,
              "killsRatio": 0.5474337031219209,
              "deathsRatio": 0.498592641583161,
              "killAssistPerMin": 0.5561617311429194,
              "killsPerMin": 0.5139805429314416,
              "deathsPerMin": 0.5128578820548964,
              "assistsPerMin": 0.5621730426178803,
              "totalHealPerMin": 0.5146567736402049,
              "damageSelfMitigatedPerMin": 0.5136279099268283,
              "damageDealtToTurretsPerMin": 0.48935962494597607,
              "timeCCingOthersPerMin": 0.5028796926414604,
              "neutralMinionsKilledPerMin": 0.5166534462259584,
              "totalTimeCrowdControlDealtPerMin": 0.517845121625355,
              "visionWardsBoughtInGamePerMin": 0.6095452996855878,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5046952322286038,
              "wardsPlacedPerMin": 0.5446423226539299,
              "wardsKilledPerMin": 0.6088740369285897
            },
            "top": {
              "totalDamageDealtToChampionsPerMin": 0.5063944396838507,
              "damageDealtToObjectivesPerMin": 0.5054018528656803,
              "visionScorePerMin": 0.5815700187532972,
              "totalDamageTakenPerMin": 0.5234147092269399,
              "totalMinionsKilledPerMin": 0.5971918104646585,
              "killsRatio": 0.48830165456092756,
              "deathsRatio": 0.5122891555406651,
              "killAssistPerMin": 0.5179009307554239,
              "killsPerMin": 0.48360142362394337,
              "deathsPerMin": 0.5226570438745841,
              "assistsPerMin": 0.5416074025164385,
              "totalHealPerMin": 0.5065295298339239,
              "damageSelfMitigatedPerMin": 0.514897987763386,
              "damageDealtToTurretsPerMin": 0.48908116422997827,
              "timeCCingOthersPerMin": 0.48311263627588974,
              "neutralMinionsKilledPerMin": 0.4970645477283167,
              "totalTimeCrowdControlDealtPerMin": 0.47480999519915157,
              "visionWardsBoughtInGamePerMin": 0.5862752928227548,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4988791906536787,
              "wardsPlacedPerMin": 0.5471598060832193,
              "wardsKilledPerMin": 0.5809408386402742
            },
            "mid": {
              "totalDamageDealtToChampionsPerMin": 0.5196836259747697,
              "damageDealtToObjectivesPerMin": 0.48130978503423877,
              "visionScorePerMin": 0.5884963592199167,
              "totalDamageTakenPerMin": 0.52585173169637,
              "totalMinionsKilledPerMin": 0.6025388023878754,
              "killsRatio": 0.5357153980483261,
              "deathsRatio": 0.49523847766565005,
              "killAssistPerMin": 0.5402751832733639,
              "killsPerMin": 0.507456135864671,
              "deathsPerMin": 0.5088862070557051,
              "assistsPerMin": 0.5581670671587362,
              "totalHealPerMin": 0.53986915764486,
              "damageSelfMitigatedPerMin": 0.5028797675350095,
              "damageDealtToTurretsPerMin": 0.469920184664218,
              "timeCCingOthersPerMin": 0.5352632992875453,
              "neutralMinionsKilledPerMin": 0.5305333812154228,
              "totalTimeCrowdControlDealtPerMin": 0.5130881701464902,
              "visionWardsBoughtInGamePerMin": 0.6084784705167913,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4712119923047796,
              "wardsPlacedPerMin": 0.5328670375725544,
              "wardsKilledPerMin": 0.5816190898059103
            },
            "jungle": {
              "totalDamageDealtToChampionsPerMin": 0.5351868502391957,
              "damageDealtToObjectivesPerMin": 0.5249714700961364,
              "visionScorePerMin": 0.6152739312622882,
              "totalDamageTakenPerMin": 0.5644850344528873,
              "totalMinionsKilledPerMin": 0.4623244617204447,
              "killsRatio": 0.5643915793945388,
              "deathsRatio": 0.4939889866808777,
              "killAssistPerMin": 0.5635251980783315,
              "killsPerMin": 0.5044538762776872,
              "deathsPerMin": 0.5165078440468294,
              "assistsPerMin": 0.5882035946014279,
              "totalHealPerMin": 0.5218225339745914,
              "damageSelfMitigatedPerMin": 0.5253163241234982,
              "damageDealtToTurretsPerMin": 0.4521083337733525,
              "timeCCingOthersPerMin": 0.48521468644930066,
              "neutralMinionsKilledPerMin": 0.5878189250374037,
              "totalTimeCrowdControlDealtPerMin": 0.5520314194244547,
              "visionWardsBoughtInGamePerMin": 0.6471851725219795,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5068068306089274,
              "wardsPlacedPerMin": 0.48278607329602385,
              "wardsKilledPerMin": 0.6535621494388512
            },
            "bottom": {
              "totalDamageDealtToChampionsPerMin": 0.5511985022414002,
              "damageDealtToObjectivesPerMin": 0.5927611406514591,
              "visionScorePerMin": 0.6572003645388815,
              "totalDamageTakenPerMin": 0.5450992494361802,
              "totalMinionsKilledPerMin": 0.6409548618746777,
              "killsRatio": 0.5689254921717155,
              "deathsRatio": 0.49568051142367897,
              "killAssistPerMin": 0.5721087104137279,
              "killsPerMin": 0.5614798353438316,
              "deathsPerMin": 0.5140342578929522,
              "assistsPerMin": 0.5528712243394279,
              "totalHealPerMin": 0.5155978331393574,
              "damageSelfMitigatedPerMin": 0.537974096446323,
              "damageDealtToTurretsPerMin": 0.5227947760341904,
              "timeCCingOthersPerMin": 0.5175862037633492,
              "neutralMinionsKilledPerMin": 0.5874928852321004,
              "totalTimeCrowdControlDealtPerMin": 0.5448402660980788,
              "visionWardsBoughtInGamePerMin": 0.5961517088538956,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5420968990702936,
              "wardsPlacedPerMin": 0.6101692680782659,
              "wardsKilledPerMin": 0.6810486779963595
            },
            "support": {
              "totalDamageDealtToChampionsPerMin": 0.49198686530433483,
              "damageDealtToObjectivesPerMin": 0.5493361704975863,
              "visionScorePerMin": 0.6295279770000778,
              "totalDamageTakenPerMin": 0.5038734813768727,
              "totalMinionsKilledPerMin": 0.4827332010953826,
              "killsRatio": 0.5892621531968183,
              "deathsRatio": 0.49485013366029723,
              "killAssistPerMin": 0.5870165484065784,
              "killsPerMin": 0.5205935999051803,
              "deathsPerMin": 0.5029530054773864,
              "assistsPerMin": 0.5893251216110814,
              "totalHealPerMin": 0.5135650804882782,
              "damageSelfMitigatedPerMin": 0.5062770899077066,
              "damageDealtToTurretsPerMin": 0.4740733564380566,
              "timeCCingOthersPerMin": 0.4926851857885132,
              "neutralMinionsKilledPerMin": 0.5425573142412821,
              "totalTimeCrowdControlDealtPerMin": 0.5137292671042473,
              "visionWardsBoughtInGamePerMin": 0.6541187256477814,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5518509346993412,
              "wardsPlacedPerMin": 0.6312800013145592,
              "wardsKilledPerMin": 0.6044715701136838
            }
          },
          "diamond": {
            "total": {
              "totalDamageDealtToChampionsPerMin": 0.4788088387371816,
              "damageDealtToObjectivesPerMin": 0.5347808730308866,
              "visionScorePerMin": 0.6139675198430253,
              "totalDamageTakenPerMin": 0.47872878890956566,
              "totalMinionsKilledPerMin": 0.5622816205375996,
              "killsRatio": 0.5556332048051563,
              "deathsRatio": 0.5004431035893623,
              "killAssistPerMin": 0.5137626853064619,
              "killsPerMin": 0.481436883642406,
              "deathsPerMin": 0.4778318488425778,
              "assistsPerMin": 0.5338316491787085,
              "totalHealPerMin": 0.5030633726815973,
              "damageSelfMitigatedPerMin": 0.4794750419918656,
              "damageDealtToTurretsPerMin": 0.4961741777653863,
              "timeCCingOthersPerMin": 0.48438211191210084,
              "neutralMinionsKilledPerMin": 0.5207810955814471,
              "totalTimeCrowdControlDealtPerMin": 0.5121016150715064,
              "visionWardsBoughtInGamePerMin": 0.6949669261436733,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5222370110190272,
              "wardsPlacedPerMin": 0.6022810810992688,
              "wardsKilledPerMin": 0.6712084591915932
            },
            "top": {
              "totalDamageDealtToChampionsPerMin": 0.4839822112727056,
              "damageDealtToObjectivesPerMin": 0.5322942952467362,
              "visionScorePerMin": 0.627730425641413,
              "totalDamageTakenPerMin": 0.48380174915172924,
              "totalMinionsKilledPerMin": 0.6673680350843385,
              "killsRatio": 0.5472730651762497,
              "deathsRatio": 0.492295641348599,
              "killAssistPerMin": 0.5157025747042171,
              "killsPerMin": 0.49108911902984476,
              "deathsPerMin": 0.46917205205393886,
              "assistsPerMin": 0.5313513745761351,
              "totalHealPerMin": 0.490934138596365,
              "damageSelfMitigatedPerMin": 0.47586934661178626,
              "damageDealtToTurretsPerMin": 0.5114781642823641,
              "timeCCingOthersPerMin": 0.4776463458833449,
              "neutralMinionsKilledPerMin": 0.4852011045213351,
              "totalTimeCrowdControlDealtPerMin": 0.5047476153780602,
              "visionWardsBoughtInGamePerMin": 0.648557177976378,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4801064337196863,
              "wardsPlacedPerMin": 0.6368049436886126,
              "wardsKilledPerMin": 0.6365838403395037
            },
            "mid": {
              "totalDamageDealtToChampionsPerMin": 0.47959524506413853,
              "damageDealtToObjectivesPerMin": 0.534327804335368,
              "visionScorePerMin": 0.6279286389089044,
              "totalDamageTakenPerMin": 0.47513612193048854,
              "totalMinionsKilledPerMin": 0.7104735267064604,
              "killsRatio": 0.5478004817577062,
              "deathsRatio": 0.4942796807908871,
              "killAssistPerMin": 0.5083869280880708,
              "killsPerMin": 0.4847658890719815,
              "deathsPerMin": 0.4785820032141855,
              "assistsPerMin": 0.5322176663916972,
              "totalHealPerMin": 0.5558716464314112,
              "damageSelfMitigatedPerMin": 0.47938513309554287,
              "damageDealtToTurretsPerMin": 0.4924201107852352,
              "timeCCingOthersPerMin": 0.4995161831275073,
              "neutralMinionsKilledPerMin": 0.4724600855575124,
              "totalTimeCrowdControlDealtPerMin": 0.5302728918385621,
              "visionWardsBoughtInGamePerMin": 0.6661048541406203,
              "neutralMinionsKilledEnemyJunglePerMin": 0.49162375045556783,
              "wardsPlacedPerMin": 0.5420396090454951,
              "wardsKilledPerMin": 0.6745430323608075
            },
            "jungle": {
              "totalDamageDealtToChampionsPerMin": 0.5235740565547178,
              "damageDealtToObjectivesPerMin": 0.5851705729831094,
              "visionScorePerMin": 0.6559565923880346,
              "totalDamageTakenPerMin": 0.4887974277790458,
              "totalMinionsKilledPerMin": 0.4679454379422594,
              "killsRatio": 0.6349947821711254,
              "deathsRatio": 0.5174023050524517,
              "killAssistPerMin": 0.5634064189044495,
              "killsPerMin": 0.5319844422411197,
              "deathsPerMin": 0.487549289091033,
              "assistsPerMin": 0.5631892723166216,
              "totalHealPerMin": 0.5026965872062622,
              "damageSelfMitigatedPerMin": 0.4659306606126247,
              "damageDealtToTurretsPerMin": 0.49442923997689153,
              "timeCCingOthersPerMin": 0.45797937255526866,
              "neutralMinionsKilledPerMin": 0.6670507863871502,
              "totalTimeCrowdControlDealtPerMin": 0.5106184537582462,
              "visionWardsBoughtInGamePerMin": 0.7408049414462816,
              "neutralMinionsKilledEnemyJunglePerMin": 0.589008071556789,
              "wardsPlacedPerMin": 0.5625893408782446,
              "wardsKilledPerMin": 0.6811350939477032
            },
            "bottom": {
              "totalDamageDealtToChampionsPerMin": 0.48158089162426515,
              "damageDealtToObjectivesPerMin": 0.5356320503508694,
              "visionScorePerMin": 0.6192716432417706,
              "totalDamageTakenPerMin": 0.4545481558455115,
              "totalMinionsKilledPerMin": 0.7381110423849295,
              "killsRatio": 0.5037266364334544,
              "deathsRatio": 0.4905077992928391,
              "killAssistPerMin": 0.47565333509326585,
              "killsPerMin": 0.44070958119572223,
              "deathsPerMin": 0.4661357672456577,
              "assistsPerMin": 0.5232536591834781,
              "totalHealPerMin": 0.4861302334962407,
              "damageSelfMitigatedPerMin": 0.46088975423338774,
              "damageDealtToTurretsPerMin": 0.501458071696328,
              "timeCCingOthersPerMin": 0.5216015058534953,
              "neutralMinionsKilledPerMin": 0.5243935912536833,
              "totalTimeCrowdControlDealtPerMin": 0.5361684028217127,
              "visionWardsBoughtInGamePerMin": 0.6543491639895542,
              "neutralMinionsKilledEnemyJunglePerMin": 0.49667113449616535,
              "wardsPlacedPerMin": 0.6358494206272681,
              "wardsKilledPerMin": 0.6966706426739087
            },
            "support": {
              "totalDamageDealtToChampionsPerMin": 0.3972561475492427,
              "damageDealtToObjectivesPerMin": 0.481005808832972,
              "visionScorePerMin": 0.7275124366463395,
              "totalDamageTakenPerMin": 0.45763948876625765,
              "totalMinionsKilledPerMin": 0.5076263592152495,
              "killsRatio": 0.5486610323284072,
              "deathsRatio": 0.508225840217252,
              "killAssistPerMin": 0.5047972119436263,
              "killsPerMin": 0.43590976830589445,
              "deathsPerMin": 0.48648132233492675,
              "assistsPerMin": 0.5320732225475051,
              "totalHealPerMin": 0.5031897961412106,
              "damageSelfMitigatedPerMin": 0.49377271493365904,
              "damageDealtToTurretsPerMin": 0.450574643278721,
              "timeCCingOthersPerMin": 0.46951336743824185,
              "neutralMinionsKilledPerMin": 0.4598152378585334,
              "totalTimeCrowdControlDealtPerMin": 0.4653981553009674,
              "visionWardsBoughtInGamePerMin": 0.797330864328279,
              "neutralMinionsKilledEnemyJunglePerMin": 0.46825566258890233,
              "wardsPlacedPerMin": 0.7788415876622959,
              "wardsKilledPerMin": 0.7257836543272291
            }
          },
          "gold": {
            "total": {
              "totalDamageDealtToChampionsPerMin": 0.5359958886101854,
              "damageDealtToObjectivesPerMin": 0.5175611912275759,
              "visionScorePerMin": 0.5488116721302512,
              "totalDamageTakenPerMin": 0.5469109145279111,
              "totalMinionsKilledPerMin": 0.5182364806741352,
              "killsRatio": 0.5110939039412435,
              "deathsRatio": 0.5001670584555309,
              "killAssistPerMin": 0.5202467576091006,
              "killsPerMin": 0.5098491473222191,
              "deathsPerMin": 0.524010311150205,
              "assistsPerMin": 0.5183483855363928,
              "totalHealPerMin": 0.5156755718020836,
              "damageSelfMitigatedPerMin": 0.5367619553223216,
              "damageDealtToTurretsPerMin": 0.49067066619381217,
              "timeCCingOthersPerMin": 0.4957165864142435,
              "neutralMinionsKilledPerMin": 0.5113900926095815,
              "totalTimeCrowdControlDealtPerMin": 0.4904749878956224,
              "visionWardsBoughtInGamePerMin": 0.5359578614367764,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5048696255672254,
              "wardsPlacedPerMin": 0.5239098797116974,
              "wardsKilledPerMin": 0.5681268519412493
            },
            "top": {
              "totalDamageDealtToChampionsPerMin": 0.5220016411504389,
              "damageDealtToObjectivesPerMin": 0.5057272531564979,
              "visionScorePerMin": 0.529789767450234,
              "totalDamageTakenPerMin": 0.5687750325605139,
              "totalMinionsKilledPerMin": 0.5474128349805111,
              "killsRatio": 0.5172489432154661,
              "deathsRatio": 0.5111701371231888,
              "killAssistPerMin": 0.5242646419956453,
              "killsPerMin": 0.5020392808745057,
              "deathsPerMin": 0.529354683513775,
              "assistsPerMin": 0.5335208621989147,
              "totalHealPerMin": 0.47962438421037895,
              "damageSelfMitigatedPerMin": 0.5582013372029061,
              "damageDealtToTurretsPerMin": 0.45954241437942717,
              "timeCCingOthersPerMin": 0.4930435186103821,
              "neutralMinionsKilledPerMin": 0.5455552205513778,
              "totalTimeCrowdControlDealtPerMin": 0.47336766500034533,
              "visionWardsBoughtInGamePerMin": 0.5269504216855316,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5092203285772774,
              "wardsPlacedPerMin": 0.5278424080399797,
              "wardsKilledPerMin": 0.5804590175744744
            },
            "mid": {
              "totalDamageDealtToChampionsPerMin": 0.544866353031137,
              "damageDealtToObjectivesPerMin": 0.5324245121664781,
              "visionScorePerMin": 0.5920188685574156,
              "totalDamageTakenPerMin": 0.5648182291905648,
              "totalMinionsKilledPerMin": 0.5484684774705567,
              "killsRatio": 0.49759257679144986,
              "deathsRatio": 0.5154568184538665,
              "killAssistPerMin": 0.5082969424523384,
              "killsPerMin": 0.5101942853475441,
              "deathsPerMin": 0.5342443749468905,
              "assistsPerMin": 0.5016268007847191,
              "totalHealPerMin": 0.48693941276205865,
              "damageSelfMitigatedPerMin": 0.544413491455587,
              "damageDealtToTurretsPerMin": 0.5256069551741281,
              "timeCCingOthersPerMin": 0.48106439562527603,
              "neutralMinionsKilledPerMin": 0.5195362112601838,
              "totalTimeCrowdControlDealtPerMin": 0.5014174635813916,
              "visionWardsBoughtInGamePerMin": 0.5400159087861411,
              "neutralMinionsKilledEnemyJunglePerMin": 0.49615423163786837,
              "wardsPlacedPerMin": 0.5456286338668971,
              "wardsKilledPerMin": 0.6205972967939509
            },
            "jungle": {
              "totalDamageDealtToChampionsPerMin": 0.5632114556011213,
              "damageDealtToObjectivesPerMin": 0.5457687761234415,
              "visionScorePerMin": 0.5981795922643398,
              "totalDamageTakenPerMin": 0.544112083324716,
              "totalMinionsKilledPerMin": 0.5742278841915696,
              "killsRatio": 0.5189204902201225,
              "deathsRatio": 0.4793473748019123,
              "killAssistPerMin": 0.529587076465107,
              "killsPerMin": 0.5345365725536426,
              "deathsPerMin": 0.5021514686672551,
              "assistsPerMin": 0.5114370172204116,
              "totalHealPerMin": 0.4982446688641278,
              "damageSelfMitigatedPerMin": 0.5509680463414283,
              "damageDealtToTurretsPerMin": 0.4993398274794123,
              "timeCCingOthersPerMin": 0.5328943853850736,
              "neutralMinionsKilledPerMin": 0.5607898079174946,
              "totalTimeCrowdControlDealtPerMin": 0.5039099939788766,
              "visionWardsBoughtInGamePerMin": 0.5503365823563345,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5159353011980228,
              "wardsPlacedPerMin": 0.5298637162235018,
              "wardsKilledPerMin": 0.5939363471200775
            },
            "bottom": {
              "totalDamageDealtToChampionsPerMin": 0.5367320108826833,
              "damageDealtToObjectivesPerMin": 0.5086856288260067,
              "visionScorePerMin": 0.5509940913734608,
              "totalDamageTakenPerMin": 0.569049468174565,
              "totalMinionsKilledPerMin": 0.5516619569754594,
              "killsRatio": 0.5042099131530048,
              "deathsRatio": 0.5137921651060281,
              "killAssistPerMin": 0.5158295943072972,
              "killsPerMin": 0.49668323760876554,
              "deathsPerMin": 0.5425606152759872,
              "assistsPerMin": 0.529351700553162,
              "totalHealPerMin": 0.5314915012827205,
              "damageSelfMitigatedPerMin": 0.5630970380505069,
              "damageDealtToTurretsPerMin": 0.47536224051684023,
              "timeCCingOthersPerMin": 0.4849570361425234,
              "neutralMinionsKilledPerMin": 0.5342997605193851,
              "totalTimeCrowdControlDealtPerMin": 0.4906129417409287,
              "visionWardsBoughtInGamePerMin": 0.5403440496665761,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5024004534743731,
              "wardsPlacedPerMin": 0.5394484407305027,
              "wardsKilledPerMin": 0.5588588254691037
            },
            "support": {
              "totalDamageDealtToChampionsPerMin": 0.5320178580596368,
              "damageDealtToObjectivesPerMin": 0.5031956528383861,
              "visionScorePerMin": 0.5837279919380973,
              "totalDamageTakenPerMin": 0.5517183380505892,
              "totalMinionsKilledPerMin": 0.4628813001226547,
              "killsRatio": 0.5203883754625998,
              "deathsRatio": 0.47991236524224296,
              "killAssistPerMin": 0.5256733612681234,
              "killsPerMin": 0.5071671958096271,
              "deathsPerMin": 0.5126271908071027,
              "assistsPerMin": 0.5258956624568584,
              "totalHealPerMin": 0.5688660042606939,
              "damageSelfMitigatedPerMin": 0.5016842307473042,
              "damageDealtToTurretsPerMin": 0.5021216871378394,
              "timeCCingOthersPerMin": 0.4705523061563163,
              "neutralMinionsKilledPerMin": 0.5002662106561077,
              "totalTimeCrowdControlDealtPerMin": 0.48042598920055213,
              "visionWardsBoughtInGamePerMin": 0.5460029310938602,
              "neutralMinionsKilledEnemyJunglePerMin": 0.48724948604709356,
              "wardsPlacedPerMin": 0.5530942630069845,
              "wardsKilledPerMin": 0.5485788114355279
            }
          },
          "iron": {
            "total": {
              "totalDamageDealtToChampionsPerMin": 0.4571916955413997,
              "damageDealtToObjectivesPerMin": 0.44838063768174474,
              "visionScorePerMin": 0.3520991708680892,
              "totalDamageTakenPerMin": 0.45785108180205614,
              "totalMinionsKilledPerMin": 0.43417100505604195,
              "killsRatio": 0.41968903939731494,
              "deathsRatio": 0.4997416290297173,
              "killAssistPerMin": 0.41226594696033386,
              "killsPerMin": 0.47743293392702224,
              "deathsPerMin": 0.48861062074052247,
              "assistsPerMin": 0.40349828699584145,
              "totalHealPerMin": 0.47413279331465297,
              "damageSelfMitigatedPerMin": 0.4718899392848143,
              "damageDealtToTurretsPerMin": 0.4972266054071636,
              "timeCCingOthersPerMin": 0.5005929044680878,
              "neutralMinionsKilledPerMin": 0.4727897194522203,
              "totalTimeCrowdControlDealtPerMin": 0.47960593883787295,
              "visionWardsBoughtInGamePerMin": 0.3239842515229615,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4968211227936083,
              "wardsPlacedPerMin": 0.4063420477018419,
              "wardsKilledPerMin": 0.31257668287057244
            },
            "top": {
              "totalDamageDealtToChampionsPerMin": 0.46606492983123904,
              "damageDealtToObjectivesPerMin": 0.47063265494681095,
              "visionScorePerMin": 0.3345934905876422,
              "totalDamageTakenPerMin": 0.4409689346322114,
              "totalMinionsKilledPerMin": 0.3085533583561255,
              "killsRatio": 0.4834198654193036,
              "deathsRatio": 0.48011607156924413,
              "killAssistPerMin": 0.4476840494510377,
              "killsPerMin": 0.4878562109614222,
              "deathsPerMin": 0.48302813305219355,
              "assistsPerMin": 0.43508426921005017,
              "totalHealPerMin": 0.5028093858830248,
              "damageSelfMitigatedPerMin": 0.4679692580210974,
              "damageDealtToTurretsPerMin": 0.5104111163966074,
              "timeCCingOthersPerMin": 0.49210527375566493,
              "neutralMinionsKilledPerMin": 0.47339714943279165,
              "totalTimeCrowdControlDealtPerMin": 0.45726596956388804,
              "visionWardsBoughtInGamePerMin": 0.352884507969466,
              "neutralMinionsKilledEnemyJunglePerMin": 0.4802021252464948,
              "wardsPlacedPerMin": 0.40711752434554016,
              "wardsKilledPerMin": 0.3337062959833492
            },
            "mid": {
              "totalDamageDealtToChampionsPerMin": 0.4631232806791444,
              "damageDealtToObjectivesPerMin": 0.45025806872346186,
              "visionScorePerMin": 0.3184194297919464,
              "totalDamageTakenPerMin": 0.45101388185689245,
              "totalMinionsKilledPerMin": 0.3001684855618414,
              "killsRatio": 0.4424155702809985,
              "deathsRatio": 0.47668337563110574,
              "killAssistPerMin": 0.43852786369367025,
              "killsPerMin": 0.48195665319391895,
              "deathsPerMin": 0.47570895305368227,
              "assistsPerMin": 0.4192188466825767,
              "totalHealPerMin": 0.4681598914504121,
              "damageSelfMitigatedPerMin": 0.4677933738636992,
              "damageDealtToTurretsPerMin": 0.4983393060164166,
              "timeCCingOthersPerMin": 0.5019023131461958,
              "neutralMinionsKilledPerMin": 0.42028195344958313,
              "totalTimeCrowdControlDealtPerMin": 0.4571658803143159,
              "visionWardsBoughtInGamePerMin": 0.33075316042956554,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5077545181908153,
              "wardsPlacedPerMin": 0.4167426149503162,
              "wardsKilledPerMin": 0.32241968702871093
            },
            "jungle": {
              "totalDamageDealtToChampionsPerMin": 0.4145360090446364,
              "damageDealtToObjectivesPerMin": 0.38208798182145576,
              "visionScorePerMin": 0.2894420429273338,
              "totalDamageTakenPerMin": 0.419527352740677,
              "totalMinionsKilledPerMin": 0.47729364911363403,
              "killsRatio": 0.3623424555238053,
              "deathsRatio": 0.5120393086284473,
              "killAssistPerMin": 0.3817543653553825,
              "killsPerMin": 0.45608056090652904,
              "deathsPerMin": 0.5037497463504682,
              "assistsPerMin": 0.3678868101082101,
              "totalHealPerMin": 0.4781565407722217,
              "damageSelfMitigatedPerMin": 0.44831861708988524,
              "damageDealtToTurretsPerMin": 0.491649539231216,
              "timeCCingOthersPerMin": 0.5205068109208154,
              "neutralMinionsKilledPerMin": 0.3372004672880554,
              "totalTimeCrowdControlDealtPerMin": 0.4993013336310976,
              "visionWardsBoughtInGamePerMin": 0.27595438323797594,
              "neutralMinionsKilledEnemyJunglePerMin": 0.47829347258884364,
              "wardsPlacedPerMin": 0.4442688926298644,
              "wardsKilledPerMin": 0.27332117612069573
            },
            "bottom": {
              "totalDamageDealtToChampionsPerMin": 0.43780916414796917,
              "damageDealtToObjectivesPerMin": 0.4278071306572349,
              "visionScorePerMin": 0.32546467476968566,
              "totalDamageTakenPerMin": 0.4406007050569209,
              "totalMinionsKilledPerMin": 0.26790923940032396,
              "killsRatio": 0.42717590765382496,
              "deathsRatio": 0.4970191012624783,
              "killAssistPerMin": 0.40733843738702374,
              "killsPerMin": 0.45889649505708,
              "deathsPerMin": 0.4712841190243645,
              "assistsPerMin": 0.3922332792954925,
              "totalHealPerMin": 0.47119093477804114,
              "damageSelfMitigatedPerMin": 0.4755922578914936,
              "damageDealtToTurretsPerMin": 0.47129669038515903,
              "timeCCingOthersPerMin": 0.5097266390072195,
              "neutralMinionsKilledPerMin": 0.46763395262841306,
              "totalTimeCrowdControlDealtPerMin": 0.47319154155688453,
              "visionWardsBoughtInGamePerMin": 0.3589133470001672,
              "neutralMinionsKilledEnemyJunglePerMin": 0.5440798856132893,
              "wardsPlacedPerMin": 0.3586640629966994,
              "wardsKilledPerMin": 0.29889708686399
            },
            "support": {
              "totalDamageDealtToChampionsPerMin": 0.49008061177067586,
              "damageDealtToObjectivesPerMin": 0.4976031670160996,
              "visionScorePerMin": 0.22560226088043484,
              "totalDamageTakenPerMin": 0.4833486221217766,
              "totalMinionsKilledPerMin": 0.5422566344466239,
              "killsRatio": 0.37151954154015165,
              "deathsRatio": 0.534784077539234,
              "killAssistPerMin": 0.384460102340091,
              "killsPerMin": 0.5045099256775974,
              "deathsPerMin": 0.5082100759051907,
              "assistsPerMin": 0.36879651762793,
              "totalHealPerMin": 0.4383312121727218,
              "damageSelfMitigatedPerMin": 0.48354007339322,
              "damageDealtToTurretsPerMin": 0.5376333861949868,
              "timeCCingOthersPerMin": 0.46953634900069346,
              "neutralMinionsKilledPerMin": 0.4873848066930264,
              "totalTimeCrowdControlDealtPerMin": 0.5123348464855875,
              "visionWardsBoughtInGamePerMin": 0.24430059625920064,
              "neutralMinionsKilledEnemyJunglePerMin": 0.48279713237159244,
              "wardsPlacedPerMin": 0.24481999168485202,
              "wardsKilledPerMin": 0.2617864472729401
            }
          }
        }
      },
      "radar": {
        "aggressiveness": 0.45649899054273085,
        "stability": 0.46422986811258116,
        "influence": 0.42943992455013963
      },
      "analysis": {
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
      "source": {
        "totalDamageDealtToChampionsPerMin": 579.301634556204,
        "damageDealtToObjectivesPerMin": 412.58436387044037,
        "visionScorePerMin": 0.6428878636725202,
        "totalDamageTakenPerMin": 736.5559669369034,
        "totalMinionsKilledPerMin": 5.178633077209469,
        "killsRatio": 0.4523326423897498,
        "deathsRatio": 0.21062380502141634,
        "killAssistPerMin": 0.40913313622361214,
        "killsPerMin": 0.18997888691962211,
        "deathsPerMin": 0.193254576031049,
        "assistsPerMin": 0.21915424930398997,
        "totalHealPerMin": 0.0,
        "damageSelfMitigatedPerMin": 519.6657410922164,
        "damageDealtToTurretsPerMin": 153.01379846087653,
        "timeCCingOthersPerMin": 0.6223712908852908,
        "neutralMinionsKilledPerMin": 1.146562854788723,
        "totalTimeCrowdControlDealtPerMin": 8.225308744902383,
        "visionWardsBoughtInGamePerMin": 0.08849294964828612,
        "neutralMinionsKilledEnemyJunglePerMin": 0.15889483804541743,
        "wardsPlacedPerMin": 0.2858455678565267,
        "wardsKilledPerMin": 0.08548998474086925
      }
      },
      getOtherData: [],
      mostChamp: "error",
      champTitle: "",
      champInfo: {},
      recommendChamps: [],
      champType: "",
      background: "아이오니아",
      lolbti: "",
      lolbtiChamp: "",
      korChampName: "",

      // setBackground: {
      //   getBackground: true,
      // },
      // backgroundImage: {
      //   backgroundImage: 'url(\'' + require('@/assets/images/test.png') + '\')',
      //   height: '15em',
      //   backgroundSize: '100%',
      //   opacity: '0.7',
      // }
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
    //     this.selectLolbtiChamp()
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
    // mostChamp: {
    //   deep: true,
    //   immediate: true,
    //   handler() {
    //     this.changeChampTitle()
    //     this.changeChampInfo()
    //     this.getRecommendChamps(this.mostChamp)
    //     this.changeChampType()
    //     this.changeBackground()

    //   }
    // }
  },
  methods: {
    setLolbodyData() {
      this.selectMostChamp()
      this.selectLolbti()
      this.selectLolbtiChamp()
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
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.diamond.total, "다이아"]
    },
    PlaData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.platinum.total, "플레티넘"]
    },
    GoldData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.gold.total, "골드"]
    },
    SilverData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.silver.total, "실버"]
    },
    BronzeData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.bronze.total, "브론즈"]
    },
    IronData() {
      this.getOtherData = [this.getLolbodyData.stastics.tierAnalysis.iron.total, "아이언"]
    },

    selectLolbtiChamp() {
      this.lolbtiChamp = champion.data[this.getLolbodyData.champList[0].name].tags[0]

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
    },
    selectSecondChamp() {
      this.mostChamp = this.getLolbodyData.champList[1]
      this.korChampName = champion.data[this.getLolbodyData.champList[1].name].name
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