<template>

<div class="champ-card" :class="[ this.recentGame.win ? 'win' : 'fail' ]" >
  <div>
    <img class="card-champ-img" :src="require(`@/assets/images/champion/${recentGame.champName}.png`)" alt="champ-img">
    <div style="position: relative;">
      <img class="card-sub card-sub-left" src="@/assets/images/champion/Akali.png" alt="spell-left">
      <img class="card-sub card-sub-right" src="@/assets/images/champion/Ahri.png" alt="spell-right">
      <img class="card-sub card-sub-top" src="@/assets/images/champion/Annie.png" alt="lane">  
    </div>
  </div>

  <div class="champ-card-text">
    K / D / A
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
    },

}
</script>

<style>

.champ-card {
  width: 120%;
  border-radius: 30%;
  padding: 15px 10px 5px;
}
.fail {
  background-color: rosybrown;

}

.win {
  background-color: cadetblue;

}

.champ-card-text {
  font-size: 12px;
  margin-top: 5px;
}

.card-champ-img {
  width: 3rem;
  border-radius: 70%;
}

.card-sub {
  position: absolute;
  width: 33%;
  border-radius: 70%;
  border: 0.01px solid white;
}

.card-sub-left {
  top: -22px;
  left: 0px;
}

.card-sub-right {
  top: -22px;
  left: 42px;

}

.card-sub-top {
  top: -65px;
  left: 22px;

}

</style>