<template>

<div class="multi">
  <div class="grid">
    <div class="grid-header" v-for="(header, index) in multiHeader" v-bind:key="index">
      {{ header }}
    </div>
    <!-- 여기까지 6개는 header -->
  </div>


  <!-- 반복되어야할 그리드 (5인 멀티서치 정보) -->
  <div class="grid align-items-center" v-for="(multiSearchData, index) in multiSearchDatas" :key="index">

    <div class="grid-body align-items-center">
    <!-- 하위 그리드 -->
      <div class="grid-body-top">
        여기 뱃지? 시즌 랭크?  
      </div>
      <!-- 랭크 -->
      <div class="grid-body-center-left">
        <img :src="require(`@/assets/images/tier/Emblem_${userDatas[index].tier.charAt(0) + userDatas[index].tier.slice(1).toLowerCase()}.png`)" alt="tier">
      </div>
      <!-- 유저네임 -->
      <div class="grid-body-center-right">
        {{ multiSearchData.summonerName }}
      </div>
      <!-- 티어 -->
      <div class="grid-body-bot-left">
        {{ userDatas[index].tier }} {{ userDatas[index].rank }}
      </div>
      <!-- 승패 -->
      <div class="grid-body-bot-right">
        {{ multiSearchData.wins }} / {{ multiSearchData.losses }}
      </div>
      <!-- 주라인 ( 나중에 갈아끼워야함 ) -->
      <div class="grid-body-lane">
        {{ multiSearchData.lane }} 이미지로 갈아야함
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
      {{ multiSearchData.recentMatchResults.wins }}승 {{ multiSearchData.recentMatchResults.fails }}패
      <br>
      {{ multiSearchData.recentMatchResults.rate }}%
      <br>
      평점 필요.

    </div>
    
    <!-- 최근 챔피언 컴포넌트화 시켜서 for문 돌리면됨. -->
    <div class="grid-champ align-items-center">
      <MultiSearchLatestChamp v-for="(recentGame, index) in multiSearchData.recentGames" :recentGame="recentGame" :key="index"/>
    </div>

    <!-- 모스트 챔피언 -->
    <div>
      <MultiSearchMostChamp v-for="(mostChamp, index) in multiSearchData.mostChamp" :mostChamp="mostChamp" :key="index"/>
    </div>
  </div>

  <input v-model="userName" style="border: 1px solid black;" type="text" @keyup.enter="getMultiSearchDatas(userName), getUserDatas(userName)">
</div>

</template>

<script>
// 랭크 이미지 받아올때 require써야 build시에 web-pack이 똑바로 인지한다.
import MultiSearchLineChart from "./MultiSearchLineChart"
import MultiSearchLatestChamp from "./MultiSearchLatestChamp"
import MultiSearchMostChamp from "./MultiSearchMostChamp"

import { mapActions } from "vuex"
import { mapState } from "vuex"
import { mapGetters } from  "vuex"

export default {
    name: "MultiSearch",
    components: {
      MultiSearchLineChart,
      MultiSearchLatestChamp,
      MultiSearchMostChamp,
    },
    data() {
      return {
        multiHeader: [ "소환사 정보", "레이더 차트", "포지션 통계", "최근 전적", "최근 챔피언", "모스트 챔피언" ],
        userName: "",
      }
    },
    computed: {
      ...mapState([
        "multiSearchDatas",
        "userDatas",
      ]),
      ...mapGetters([
      ]),

    },
    methods: {
      ...mapActions([
        "getMultiSearchDatas",
        "getUserDatas",
      ])
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
  height: 100%;

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
.grid-body-center-left > img {
  width: 100%;
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