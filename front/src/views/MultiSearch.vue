<template>

<v-container>
  <v-row justify='center' align='center' class="fs-search-bar">

    <!-- 멀티써치용 써치바 -->
    <MultiSearchBar/>

    <div class="multi" v-if="multiSearchDatas.length">
      <div class="grid">
        <div class="grid-header" v-for="(header, index) in multiHeader" v-bind:key="index">
          {{ header }}
        </div>
        <!-- 여기까지 6개는 header -->
      </div>


      <!-- 반복되어야할 그리드 (5인 멀티서치 정보) -->
      <div class="grid grid2 align-items-center" v-for="(multiSearchData, index) in multiSearchDatas" :key="index" @click="userProfile(multiSearchData.summonerName)">
        <div class="grid-body align-items-center">

          <!-- 랭크 -->
          <div class="grid-body-center-left">
            <img :src="multiUserDatas[index].tier !== null ? require(`@/assets/images/tier/${multiUserDatas[index].tier}.png`) : require(`@/assets/images/tier/unranked.png`)" alt="tier">
          </div>

          <!-- 유저네임, 티어, 승패-->
          <div class="grid-body-center-right">
            <div class="user-name">
              {{ multiSearchData.summonerName }}
            </div>
            <div>
              {{ multiUserDatas[index].tier }} {{ multiUserDatas[index].rank }}
            </div>
            <div>
              {{ multiSearchData.totalGame === 0 ? 0 : Math.round(((multiSearchData.wins*100)/multiSearchData.totalGame)) }}% ({{ multiSearchData.wins }}승 {{ multiSearchData.losses }}패)
            </div>
          </div>

          <!-- 가장 많이가는 라인 -->
          <div class="grid-body-bot-left">
            <img class="lane-width" :src="multiSearchData.mainLane !== 'NONE' ? require(`@/assets/images/position/${multiSearchData.mainLane}.png`) : require(`@/assets/images/error.png`)" alt="mainLane">
            <img class="lane-width" :src="multiSearchData.subLane !== 'NONE' ? require(`@/assets/images/position/${multiSearchData.subLane}.png`) : require(`@/assets/images/error.png`)" alt="subLane">
          </div>

          <!-- 배지 칩으로 넣어야함 // 현재 더미데이터, 나중에는 서버에서 받아와서 for문 돌려야함 -->
          <div class="grid-body-bot-right">
            <MultiSearchBadge :index="index"/>
          </div>
        </div>
        <!-- 레이더차트 컴포넌트 -->
        <div class="disabled">
          <MultiSearchRadarChart :index="index"/>
        </div>

        <!-- 라인 차트 컴포넌트-->
        <div style="width: 176px">
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

    </div>
  </v-row>
  <v-row class="justify-center">
    <v-col>
      <MultiLoading :loading="isMultiSearchLoading" :color="color" :size="size"></MultiLoading>

    </v-col>
  </v-row>
</v-container>
</template>

<script>
// 랭크 이미지 받아올때 require써야 build시에 web-pack이 똑바로 인지한다.
import MultiSearchLineChart from "@/components/multisearch/MultiSearchLineChart" 
import MultiSearchLatestChamp from "@/components/multisearch/MultiSearchLatestChamp"
import MultiSearchMostChamp from "@/components/multisearch/MultiSearchMostChamp"
import MultiSearchRadarChart from "@/components/multisearch/MultiSearchRadarChart"
import MultiSearchBadge from "@/components/multisearch/MultiSearchBadge"
import MultiSearchBar from "@/components/multisearch/MultiSearchBar"
import MultiLoading from '@/components/multisearch/MultiLoading.vue'


// import { mapActions } from "vuex"
import { mapState } from "vuex"
import { mapGetters } from  "vuex"

export default {
  name: "MultiSearch",
  components: {
    MultiSearchLineChart,
    MultiSearchLatestChamp,
    MultiSearchMostChamp,
    MultiSearchRadarChart,
    MultiSearchBadge,
    MultiSearchBar,
    MultiLoading,
  },
  data() {
    return {
      multiHeader: [ "소환사 정보", "레이더 차트", "포지션 통계", "최근 전적", "최근 챔피언", "모스트 챔피언" ],
      size: "50px",
      color: "grey",
    }
  },
  computed: {
    ...mapState([
      "multiSearchDatas",
      "multiUserDatas",
      "isMultiSearchLoading",
    ]),
    ...mapGetters([
    ]),
    
  },
  methods: {
    userProfile(userName) {
      this.$router.push('/Profile/'+userName)
    }
  },
  created() {
    this.$store.commit('toggleNavSearch', false)
    this.$store.dispatch('initMultiSearchData')
  }

}
    
</script>

<style scoped>

.fs-search-bar {
  width: 1000px;
  min-width: 1000px;
  margin: auto;
}

.user-name {
  font-weight: bold;
}

.lane-width {
  width: 40%;
}

.align-items-center {
  align-items: center;
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

.grid2:not(.disabled):hover {
  cursor: pointer;
  background-color: #f0f0f0;
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

  position: relative;
  top: -4px;
  text-align: left;
  padding-left: 15px
}
.grid-body-lane {
  grid-area: cd-l;
}


.grid-champ { 
  display: grid;
  grid-template-columns: repeat(5, 1fr);

}


</style>