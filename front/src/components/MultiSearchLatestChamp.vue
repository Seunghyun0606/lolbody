<template>

<div class="champ-card">
  <div>
    <img class="card-champ-img" src="@/assets/images/champion/Aatrox.png" alt="champ-img">
    <div style="position: relative;">
      <img class="card-sub card-sub-left" src="@/assets/images/champion/Akali.png" alt="spell-left">
      <img class="card-sub card-sub-right" src="@/assets/images/champion/Ahri.png" alt="spell-right">
      <img class="card-sub card-sub-top" src="@/assets/images/champion/Annie.png" alt="lane">  
    </div>
  </div>

  <div class="champ-card-text">
    7 / 3 / 0
  </div>

</div>


</template>

<script>

export default {
    name: "MultiSearchLatestChamp",
    data() {
        return {

        }
    }

}
</script>

<style>

.champ-card {
  width: 120%;
  /* height: 40%; */
  background-color: rosybrown;
  border-radius: 30%;
  padding: 15px 10px 5px;
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