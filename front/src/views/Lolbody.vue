<template>

  <!-- 나중에 만들때 전체 넓이랑 높이 고정값 주고 퍼센트 값으로 높이 정하자.. -->
  <v-container class='test-con'>
    <v-row class='test-row justify-space-around'>

      <!-- 좌측 구역 -->
      <v-col class='test-col' cols='5'>

        <!-- 유저 프로필, 랭크, 아이디, 레벨, 챔피언 라인정보. -->
        <v-row class='test-row justify-space-around align-content-space-around' style="height: 10em;">
          <!-- 랭크 -->
          <v-col cols="3" class='test-col'>
            <v-row class='test-row'>
              <v-col class="center">
                <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <!-- <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily"> -->
            </v-row>
            <!-- 많이가는 라인 -->
            <v-row class='test-row'>
              <v-col :tooltip="tooltip_content" class="center">
                <img class="icon small" :src="require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <v-col :tooltip="tooltip_content" class="center">
                <img class="icon small" :src="require(`@/assets/images/error.png`)" alt="temporarily">
              </v-col>
              <!-- <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.mainLane}.png`)" alt="mainLane">
              <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.subLane}.png`)" alt="subLane"> -->
            </v-row>
          </v-col>

          <!-- 유저 텍스트정보 -->
          <v-col cols="8" class='test-col'>
            <v-row class='test-row'>
              <v-col>
                <v-row class='test-row'>
                  유저 이름
                  <!-- {{ multiSearchData.summonerName }} -->
                </v-row>
                <v-row>
                  계급
                  <!-- {{ userDatas[index].tier }} {{ userDatas[index].rank }} -->
                </v-row>
                <v-row>
                  승패
                  <!-- {{ Math.round(((multiSearchData.wins*100)/multiSearchData.totalGame)) }}% ({{ multiSearchData.wins }}승 {{ multiSearchData.losses }}패) -->
                </v-row>
              </v-col>
            </v-row>
            <v-row class='test-row'>
              이쯤에 배지.
              <!-- <MultiSearchBedge :index="index"/> -->
            </v-row>
          </v-col>
        </v-row>


        <!-- 분석 데이터 산출물 이용. 전체랑 최근 20게임 평균 비교 col 하나 먹이고 row로 3~4개로 나눠서하면될듯 -->
        <v-row class='test-row test-height'>
          <v-col>
            <!-- 우측 막대 그래프 분석 데이터 산출물 이용. 전체랑 최근 20게임 평균 비교 col 하나 먹이고 row로 3~4개로 나눠서하면될듯 -->
            <!-- 동티어 동일챔프 기준? -->
            <LolbodyBarChart/>
          </v-col>
        
        </v-row>


        <!-- 유저 챔피언 숙련도 부분 -->
        <v-row class='test-row test-height'>
          <v-col>
            <!-- 라인 선택해서 볼수있게. -->
            <v-row>
              <v-col class="border-box center">
                라인1
              </v-col>
              
              <v-col class="border-box center">
                라인2
              </v-col>
            </v-row>

            <v-row class='align-content-center' style="height: 90%;">
              <v-col >
                
                <!-- 선택한 라인에서 높은 숙련도 챔피언 정보 -->
                <v-row class="justify-space-around">
                  <v-col class="center" :tooltip="tooltip_content" cols="4">
                    <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily">
                  </v-col>

                  <v-col cols="7">
                    이런 챔피언을 주로하시는군요
                  </v-col>
                </v-row>

                <!-- 추천 챔피언 정보 -->
                <v-row class="justify-space-around">
                  <!-- for 문 돌려서 추천하는 챔피언 몇개 나오게한다. col에서 돌려야함 -->
                  <v-col cols="4">
                    <v-row class="justify-space-around">
                      <!-- 여기서 for문 돌릴 예정 -->
                      <v-col :tooltip="tooltip_content" class="center">
                        <img class="icon small" :src="require(`@/assets/images/error.png`)" alt="temporarily">
                      </v-col>
                      <v-col :tooltip="tooltip_content" class="center">
                        <img class="icon small" :src="require(`@/assets/images/error.png`)" alt="temporarily">
                      </v-col>
                      <v-col :tooltip="tooltip_content" class="center">
                        <img class="icon small" :src="require(`@/assets/images/error.png`)" alt="temporarily">
                      </v-col>
                    </v-row>
                  </v-col>

                  <v-col cols="7">
                    이런 챔피언을 추천합니다.
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


      <!-- 우측 구역 -->
      <v-col class='test-col' cols='6'>

        <!-- 롤비티아이 구역 -->
        <v-row class='test-row test-height2 align-content-center'>
          <v-col>
            <v-row>
              <v-col cols='4' class="center" >
                <img class="icon big" :src="require(`@/assets/images/error.png`)" alt="temporarily">

              </v-col>
              <v-col>
                데마시아의 용맹한 전사
                <br>
                (데마시아 // 용맹한 // fighter 느낌)
                <br>
                당신은 데마시아의 용맹한 어쩌구저쩌구입니다. ~~ 하면서 text를 짜야함.
              </v-col>

            </v-row>
          </v-col>



          <!-- <div>
            LOl BTI 컴포넌트 ( 16가지 정도로 분류하면될듯, 나라랑 몇가지 타이틀? 정도)
            <br>
            좌측에는 데마시아, 룬테라의 학살자 등으로 타이틀 달고 간단한 챔피언 아이콘 같은거 넣어주고
            <br>
            간단한 설명으로 구획 2개로 나눠서하면될듯
          </div> -->
        </v-row>

        <!-- 레이더 차트 구역 -->
        <v-row class='test-row test-height2'>
          <!-- <div>
            레이더 차트 컴포넌트
            좌측에 레이더 차트 넣고 우측에는 레이더 차트 설명
            구획 2개로 나눠서하면될듯
          </div> -->
          <v-col cols="7" style="position: relative;">
            <LolbodyRadarChart />
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

        <!-- 레이더차트 변화, Column 차트 구역 -->
        <v-row class='test-row test-height2'>
          <!-- <div>
            레이더 차트의 변화 // 전체 // 현재 // 이전 막대그래프
          </div> -->
          <v-col>

            <LolbodyColumnChart/>
          </v-col>

        </v-row>

        <!-- 워드 클라우드? -->
        <v-row class='test-row test-height2'>
          <!-- <div>
            워드 클라우드 형태로 뱃지 획득? 표현
          </div> -->
          <LolbodyWordCloud/>

        </v-row>
      </v-col>
    </v-row>
  </v-container>
  
</template>

<script>
import { mapState } from 'vuex'
import LolbodyBarChart from '@/components/lolbody/LolbodyBarChart'
import LolbodyColumnChart from '@/components/lolbody/LolbodyColumnChart'
import LolbodyRadarChart from '@/components/lolbody/LolbodyRadarChart'
import LolbodyWordCloud from '@/components/lolbody/LolbodyWordCloud'
// import MultiSearchBedge from '@/components/multisearch/MultiSearchBedge'

export default {
  name: 'Lolbody',
  components: {
    LolbodyBarChart,
    LolbodyColumnChart,
    LolbodyRadarChart,
    LolbodyWordCloud,
    // MultiSearchBedge,
  },
  data() {
    return {
      tooltip_content: 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
    }
  },
  computed: {
    ...mapState(['profileDatas'])
  }

}
</script>

<style scoped>

* {
  padding: 0;
  margin: 0;
}

.test-con {
  border: 2px solid black;
  padding: 0;
}
.test-row {
  border: 2px solid red;
  padding: 0;
  margin: 0;
}
.test-col {
  border: 2px solid blue;
  padding: 0;
}

.test-height {
  height: 20em;
}
.test-height2 {
  height: 15em;
}

.icon {
  border-radius: 70%;
  height: 5em
}

.big {
  height: 5em;
}

.small {
  height: 2em;
}

.border-box {
  border: 1.5px solid black;
  background-color: skyblue;
  transition: 0.5s;
}

.border-box:hover {
  background-color: rgb(255, 145, 0);
  cursor: pointer;
}

.center {
  text-align: center;
}


</style>