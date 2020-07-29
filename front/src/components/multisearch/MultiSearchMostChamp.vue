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
  width: 3rem;
  border-radius: 70%;
}


</style>