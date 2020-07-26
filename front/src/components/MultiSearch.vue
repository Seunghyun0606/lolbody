<template>

<div class="multi">
  <div class="grid align-items-center">
    <div class="grid-header" v-for="(header, index) in multiHeader" v-bind:key="index">
      {{ header }}
    </div>
    <!-- 여기까지 6개는 header -->
  </div>

  <!-- 반복되어야할 그리드 (5인 멀티서치 정보) -->
  <div class="grid align-items-center">

    <div class="grid-body align-items-center">
    <!-- 하위 그리드 -->
      <div class="grid-body-top">
        이게 뱃지? 시즌 랭크?  
      </div>
      <div class="grid-body-center-left">
        랭크뱃지
      </div>
      <div class="grid-body-center-right">
        이름
      </div>
      <div class="grid-body-bot-left">
        랭크 등급
      </div>
      <div class="grid-body-bot-right">
        전적(승률)
      </div>
      <div class="grid-body-lane">
        주라인 이미지 2개 적당히 거리벌려서
      </div>
    </div>


    <!-- 레이더차트 컴포넌트 -->
    <div>
      레이더 차트
    </div>

    <!-- 라인 차트 컴포넌트-->
    <div>
      <MultiSearchLineChart/>
    </div>

    <!-- 최근전적 -->
    <div>
      11승 9패
      퍼센트
      2.56 평점

    </div>
    
    <!-- 최근 챔피언 컴포넌트화 시켜서 for문 돌리면됨. -->
    <div class="grid-champ align-items-center">
      <MultiSearchLatestChamp/>
    </div>

    <!-- 모스트 챔피언 -->
    <div>
      모스트 챔피언 데이터 받아올거임
    </div>


  </div>
</div>

</template>

<script>
import MultiSearchLineChart from "./MultiSearchLineChart"
import MultiSearchLatestChamp from "./MultiSearchLatestChamp"

export default {
    name: "MultiSearch",
    components: {
      MultiSearchLineChart,
      MultiSearchLatestChamp,
    },
    data() {
      return {
        multiHeader: [ "소환사 정보", "레이더 차트", "포지션 통계", "최근 전적", "최근 챔피언", "모스트 챔피언" ],
        }
        
    }
  }
    
</script>

<style scoped>

.align-items-center {
    align-items: center;
}

.multi {
  width: 90%;
}

.grid {
  display: grid;
  grid-template-columns: 20% 17.5% 17.5% 10% 25% 10%;

  border: 2px solid #f76707;
  border-radius: 5px;

  text-align: center;

}

.grid > .grid-header {
  border: 2px solid #ffa94d;
  border-radius: 5px;
  background-color: #ffd8a8;
  padding: 1em;
  color: #d9480f;
}



.grid-body {
  height: 300px;

  display: grid;
  grid-template-columns: repeat(10, 1fr);
  grid-template-areas:
    "cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t"
    "cd-c-l cd-c-l cd-c-l cd-c-l cd-c-r cd-c-r cd-c-r cd-c-r cd-c-r cd-c-r"
    "cd-b-l cd-b-l cd-b-l cd-b-l cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r"
    "cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l"
  ;

}

.grid-body-top {
  grid-area: cd-t;
}
.grid-body-center-left {
  grid-area: cd-c-l;
}
.grid-body-center-right {
  grid-area: cd-c-r;
}
.grid-body-bot-left {
  grid-area: cd-b-l;
}
.grid-body-bot-right {
  grid-area: cd-b-r;
}
.grid-body-lane {
  grid-area: cd-l;
}


.grid-champ {
  height: 300px;
  
  display: grid;
  grid-template-columns: repeat(5, 1fr);

}


</style>