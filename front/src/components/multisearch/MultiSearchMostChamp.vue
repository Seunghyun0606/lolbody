<template>
  <img class="card-champ-img" :src="require(`@/assets/images/champion/${mostChamp}.png`)" alt="champ-img">
</template>

<script>
export default {
  name: "MultiSearchMostChamp",
  props: {
    mostChamp: String,
  },
  data() {
    return {

    }
  },


}
</script>

<style scoped>

.card-champ-img {
  width: 27.5%;
  border-radius: 70%;
  margin: 0 5px 5px 0;
}


</style>