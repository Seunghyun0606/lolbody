<template>

  <!-- 나중에 만들때 전체 넓이랑 높이 고정값 주고 퍼센트 값으로 높이 정하자.. -->
  <v-container class='test-con'>
    <v-row class='test-row'>
      <v-spacer></v-spacer>


      <!-- 좌측 구역 -->
      <v-col class='test-col' cols='5'>

        <!-- 유저 프로필, 랭크, 아이디, 레벨, 챔피언 라인정보. -->
        <v-row class='test-row' style="height: 10em;">
          <!-- 랭크 -->
          <div class="grid-body-center-left">
            <!-- <img :src="require(`@/assets/images/tier/${userDatas[index].tier}.png`)" alt="tier"> -->
          </div>

          <!-- 유저네임, 티어, 승패-->
          <div class="grid-body-center-right">
            <div class="user-name">
              <!-- {{ multiSearchData.summonerName }} -->
            </div>
            <div>
              <!-- {{ userDatas[index].tier }} {{ userDatas[index].rank }} -->
            </div>
            <div>
              <!-- {{ Math.round(((multiSearchData.wins*100)/multiSearchData.totalGame)) }}% ({{ multiSearchData.wins }}승 {{ multiSearchData.losses }}패) -->
            </div>
          </div>

          <!-- 가장 많이가는 라인 -->
          <div class="grid-body-bot-left">
            <!-- <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.mainLane}.png`)" alt="mainLane">
            <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.subLane}.png`)" alt="subLane"> -->
          </div>

          <!-- 배지 칩으로 넣어야함 // 현재 더미데이터, 나중에는 서버에서 받아와서 for문 돌려야함 -->
          <div class="grid-body-bot-right">
            <!-- <MultiSearchBedge :index="index"/> -->
          </div>
        </v-row>


        <!-- 우측 막대 그래프 분석 데이터 산출물 이용. 전체랑 최근 20게임 평균 비교 col 하나 먹이고 row로 3~4개로 나눠서하면될듯 -->
        <v-row class='test-row test-height'>
          <v-col>
            우측 막대 그래프 분석 데이터 산출물 이용. 전체랑 최근 20게임 평균 비교 col 하나 먹이고 row로 3~4개로 나눠서하면될듯
            <LolbodyBarChart/>
          </v-col>
        
        </v-row>

        <v-row class='test-row test-height'>
          <div>
            상단에 현재 나의 높은 숙련도 챔피언, 및 라인 --> 챔피언 mastery api에서 가져오면됨 puid? or encrypted usernmae
            <br>
            하단에 성향에 맞는 추천 챔피언과 이유. 로 2구역
          </div>
        </v-row>
      </v-col>
      <v-spacer></v-spacer>


      <!-- 우측 구역 -->
      <v-col class='test-col' cols='6'>

        <!-- 롤비티아이 구역 -->
        <v-row justify="center" align="center" class='test-row test-height2'>
          <div>
            LOl BTI 컴포넌트 ( 16가지 정도로 분류하면될듯, 나라랑 몇가지 타이틀? 정도)
            <br>
            좌측에는 데마시아, 룬테라의 학살자 등으로 타이틀 달고 간단한 챔피언 아이콘 같은거 넣어주고
            <br>
            간단한 설명으로 구획 2개로 나눠서하면될듯
          </div>
        </v-row>

        <!-- 레이더 차트 구역 -->
        <v-row class='test-row test-height2'>
          <div>
            레이더 차트 컴포넌트
            좌측에 레이더 차트 넣고 우측에는 레이더 차트 설명
            구획 2개로 나눠서하면될듯
          </div>
          <LolbodyRadarChart />

        </v-row>

        <!-- 레이더차트 변화, Column 차트 구역 -->
        <v-row class='test-row test-height2'>
          <div>
            레이더 차트의 변화 // 전체 // 현재 // 이전 막대그래프
          </div>
          <LolbodyColumnChart/>

        </v-row>

        <v-row class='test-row test-height2'>
          <div>
            워드 클라우드 형태로 뱃지 획득? 표현
          </div>
        </v-row>


      </v-col>

      <v-spacer></v-spacer>

    </v-row>
  </v-container>
  
</template>

<script>
import { mapState } from 'vuex'
import LolbodyBarChart from '@/components/lolbody/LolbodyBarChart'
import LolbodyColumnChart from '@/components/lolbody/LolbodyColumnChart'
import LolbodyRadarChart from '@/components/lolbody/LolbodyRadarChart'
// import MultiSearchBedge from '@/components/multisearch/MultiSearchBedge'

export default {
  name: 'Lolbody',
  components: {
    LolbodyBarChart,
    LolbodyColumnChart,
    LolbodyRadarChart,
    // MultiSearchBedge,
  },
  data() {
    return {
    }
  },
  computed: {
    ...mapState(['profileDatas'])
  }

}
</script>

<style scoped>

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

</style>