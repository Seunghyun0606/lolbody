<template>

  <!-- 나중에 만들때 전체 넓이랑 높이 고정값 주고 퍼센트 값으로 높이 정하자.. -->
  <v-container class='test-con'>
    <v-row class='test-row'>
      <v-spacer></v-spacer>


      <!-- 좌측 구역 -->
      <v-col class='test-col' cols='5'>

        <v-row class='test-row test-height'>
          유저 프로필, 랭크, 아이디, 레벨, 챔피언 라인정보.
        </v-row>

        <v-row class='test-row test-height'>
          <div>
            우측 막대 그래프
            <br>
            분석 데이터 산출물 이용. 전체랑 최근 20게임 평균 비교
            <br>
            col 하나 먹이고 row로 3~4개로 나눠서하면될듯
          </div>          
        </v-row>

        <v-row class='test-row test-height'>
          <div>
            상단에 현재 나의 높은 숙련도 챔피언, 및 라인
            <br>
            하단에 성향에 맞는 추천 챔피언과 이유. 로 2구역
          </div>
        </v-row>
      </v-col>
      <v-spacer></v-spacer>


      <!-- 우측 구역 -->
      <v-col class='test-col' cols='6'>

        <v-row justify="center" align="center" class='test-row test-height2'>
          <div>
            LOl BTI 컴포넌트 ( 16가지 정도로 분류하면될듯, 나라랑 몇가지 타이틀? 정도)
            <br>
            좌측에는 데마시아, 룬테라의 학살자 등으로 타이틀 달고 간단한 챔피언 아이콘 같은거 넣어주고
            <br>
            간단한 설명으로 구획 2개로 나눠서하면될듯
          </div>
        </v-row>

        <v-row class='test-row test-height2'>
          <div>
            레이더 차트 컴포넌트
            <br>
            좌측에 레이더 차트 넣고 우측에는 레이더 차트 설명
            <br>
            구획 2개로 나눠서하면될듯
          </div>
        </v-row>

        <v-row class='test-row test-height2'>
          <div>
            레이더 차트의 변화 // 전체 // 현재 // 이전 막대그래프
          </div>

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
export default {
  name: 'Lolbody',
  data() {
    return {

    }
  },

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
  height: 20rem;
}
.test-height2 {
  height: 15rem;
}

</style>