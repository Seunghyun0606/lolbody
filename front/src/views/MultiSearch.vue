<template>

<v-container>
  <v-row justify='center' align='center'>
    <div class="multi">

      <div class="grid">
        <div class="grid-header" v-for="(header, index) in multiHeader" v-bind:key="index">
          {{ header }}
        </div>
        <!-- 여기까지 6개는 header -->
      </div>


      <!-- 반복되어야할 그리드 (5인 멀티서치 정보) -->
      <div class="grid grid2 align-items-center" v-for="(multiSearchData, index) in multiSearchDatas" :key="index">

        <div class="grid-body align-items-center">

          <!-- 랭크 -->
          <div class="grid-body-center-left">
            <img :src="require(`@/assets/images/tier/${userDatas[index].tier}.png`)" alt="tier">
          </div>

          <!-- 유저네임, 티어, 승패-->
          <div class="grid-body-center-right">
            <div class="user-name">
              {{ multiSearchData.summonerName }}
            </div>
            <div>
              {{ userDatas[index].tier }} {{ userDatas[index].rank }}
            </div>
            <div>
              {{ Math.round(((multiSearchData.wins*100)/multiSearchData.totalGame)) }}% ({{ multiSearchData.wins }}승 {{ multiSearchData.losses }}패)
            </div>


          </div>

          <!-- 가장 많이가는 라인 -->
          <div class="grid-body-bot-left">
            <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.mainLane}.png`)" alt="mainLane">
            <img class="lane-width" :src="require(`@/assets/images/position/${multiSearchData.subLane}.png`)" alt="subLane">
          </div>

          <div class="grid-body-bot-right">
          </div>

          <!-- 배지 칩으로 넣어야함 -->
          <div class="grid-body-lane">
          </div>
        </div>

        <!-- 레이더차트 컴포넌트 -->
        <div>
          <MultiSearchRadarChart/>
        </div>

        <!-- 라인 차트 컴포넌트-->
        <div>
          <MultiSearchLineChart :multiSearchData="multiSearchData"/>
        </div>

        <!-- 최근전적 -->
        <div style="font-size: 10px;">
          {{ multiSearchData.recentMatchResults.wins }}승 {{ multiSearchData.recentMatchResults.fails }}패
          <br>
          {{ multiSearchData.recentMatchResults.rate }}%
          <br>
          {{ multiSearchData.recentMatchKda }}

        </div>
        
        <!-- 최근 챔피언 컴포넌트화 시켜서 for문 돌리면됨. -->
        <div class="grid-champ align-items-center">
          <MultiSearchLatestChamp v-for="(recentGame, index) in multiSearchData.recentGames" :recentGame="recentGame" :key="index"/>
        </div>

        <!-- 모스트 챔피언 -->
        <div style="margin-left: 5px; margin-right: 5px">
          <MultiSearchMostChamp v-for="(mostChamp, index) in multiSearchData.mostChamp" :mostChamp="mostChamp" :key="index"/>
        </div>
      </div>

      <input v-model="userName" style="border: 1px solid black;" type="text" @keyup.enter="getMultiSearchDatas(userName), getUserDatas(userName)">
    </div>
  </v-row>
</v-container>
</template>

<script>
// 랭크 이미지 받아올때 require써야 build시에 web-pack이 똑바로 인지한다.
import MultiSearchLineChart from "@/components/multisearch/MultiSearchLineChart" 
import MultiSearchLatestChamp from "@/components/multisearch/MultiSearchLatestChamp"
import MultiSearchMostChamp from "@/components/multisearch/MultiSearchMostChamp"
import MultiSearchRadarChart from "@/components/multisearch/MultiSearchRadarChart"

import { mapActions } from "vuex"
import { mapState } from "vuex"
import { mapGetters } from  "vuex"

export default {
    name: "MultiSearch",
    components: {
      MultiSearchLineChart,
      MultiSearchLatestChamp,
      MultiSearchMostChamp,
      MultiSearchRadarChart,
    },
    data() {
      return {
        multiHeader: [ "소환사 정보", "레이더 차트", "포지션 통계", "최근 전적", "최근 챔피언", "모스트 챔피언" ],
        userName: "",
        test: 0.0123,
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
      ]),

    }
  }
    
</script>

<style scoped>


.user-name {
  font-weight: bold;
}

.lane-width {
  width: 40%;
}

.align-items-center {
  align-items: center;
}

.multi {
  width: 1000px;
}

.grid {
  display: grid;
  grid-template-columns: 20% 15% 17.5% 6% 29% 12.5%;

  border: 1px solid #f76707;

  text-align: center;

}

.grid2 {
  grid-template-rows: 90px;
}

.grid > .grid-header {
  background-color: #ffd8a8;
  height: 100%;

  font-size: 12px;

  color: #d9480f;
}



.grid-body {

  display: grid;
  height: 90%;
  grid-template-columns: repeat(10, 1fr);
  grid-template-areas:
    /* "cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t   cd-t" */
    "cd-c-l cd-c-l cd-c-l cd-c-r cd-c-r cd-c-r cd-c-r cd-c-r cd-c-r cd-c-r"
    "cd-b-l cd-b-l cd-b-l cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r"
    "cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l   cd-l"
    /* "cd-b-l cd-b-l cd-b-l cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r cd-b-r" */
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
  padding-left: 10px;
}

.grid-body-center-right {
  grid-area: cd-c-r;
  font-size: 12px;
  text-align: left;
  padding-left: 15px;
}

.grid-body-bot-left {
  grid-area: cd-b-l;
  padding-left: 10px;
}
.grid-body-bot-right {
  grid-area: cd-b-r;
}
.grid-body-lane {
  grid-area: cd-l;
}


.grid-champ { 
  display: grid;
  grid-template-columns: repeat(5, 1fr);

}


</style>