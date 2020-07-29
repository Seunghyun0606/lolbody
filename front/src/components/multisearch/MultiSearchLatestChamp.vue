<template>

<div class="champ-card" :class="[ this.recentGame.win ? 'win' : 'fail' ]" >
  <div class="champ">
    <div class="lane">
      <img class="card-sub" :src="laneImage" alt="lane">  
    </div>

    <img class="card-champ-img" :src="require(`@/assets/images/champion/${recentGame.champName}.png`)" alt="card-champ-img">

    <div class="spell">
      <img class="card-sub card-sub-left" src="@/assets/images/champion/Akali.png" alt="spell-left">
      <img class="card-sub card-sub-right" src="@/assets/images/champion/Ahri.png" alt="spell-right">
    </div>
    <!-- <div class="champ-card-text">
      K / D / A
    </div> -->
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
        return require(`@/assets/images/position/${lane.charAt(0) + lane.slice(1).toLowerCase()}.png`)
      },
    },

}
</script>

<style scoped>

.champ-card {
  width: 90%;
  height: 70px;
  border-radius: 30%;
  padding: 10px 4px 4px 4px;
}

.champ {
  position: relative;
}

.lane {
  position: absolute;
  top: -10px;
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
  font-size: 9px;
}
  
.card-sub {
  width: 40%;
  border-radius: 70%;
  border: 0.01px solid white;
}

.card-sub-left {
  float: left;
}

.card-sub-right {
  float: right;
}

.fail {
  background-color: #ffebeb;

}

.win {
  background-color: #e5f4ff;

}
</style>