<template>

<div class="champ-card" :class="[ this.recentGame.win ? 'bg_win' : 'bg_fail' ]" >
  <div class="champ">
    <div class="lane">
      <img class="card-sub" :src="laneImage" alt="lane">  
    </div>

    <img class="card-champ-img" :src="require(`@/assets/images/champion/${recentGame.champName}.png`)" alt="card-champ-img">

    <div class="spell">
      <img class="card-sub card-sub-left" :src="require(`@/assets/images/spell/${recentGame.spell1Id}.png`)" alt="spell-left">
      <img class="card-sub card-sub-right" :src="require(`@/assets/images/spell/${recentGame.spell2Id}.png`)" alt="spell-right">
    </div>
    <div class="champ-card-text">
      {{ recentGame.kda.kills }} / <span>{{ recentGame.kda.deaths }}</span> / {{ recentGame.kda.assists }}
    </div>
  </div>


</div>


</template>

<script>
import { mapState } from 'vuex'

export default {
    name: "MultiSearchLatestChamp",
    props: {
      recentGame: Object,

    },
    data() {
        return {
        }
    },
    computed: {
      ...mapState([
        "multiSearchData",

      ]),
      laneImage () {
        var lane = this.recentGame.lane
        return require(`@/assets/images/position/${lane}.png`)
      },
    },

}
</script>

<style scoped>

.champ-card {
  width: 90%;
  height: 70px;
  border-radius: 15%;
  padding: 10px 4px 4px 4px;
}

.champ {
  position: relative;
}

.lane {
  position: absolute;
  top: -11px;
}

.spell {
  position: absolute;
  top: 25px;
  bottom: 0px;
  padding: 0 1px 0;
}

.card-champ-img {
  width: 80%;
  border-radius: 70%;
}

.champ-card-text {
  padding-top: 5px;
  font-size: 9px;
  font-weight: 900;
}

.champ-card-text > span {
  color: red;
}
  
.card-sub {
  width: 40%;
  border-radius: 70%;
  border: 0.01px solid #f0ffffb5;
  background-color: #f0ffffb5;
}

.card-sub-left {
  float: left;
}

.card-sub-right {
  float: right;
}

.bg_fail {
  background-color: #ffe1e1 ;
}

.bg_win {
  background-color: #d1e5ff ;
}
</style>