<template>
<div style="height: 100%; width: 100%; padding: 50px;">
  <VueWordCloud
    :words="words"
    :color="([, weight]) => weight > 15 ? 'DeepPink' : weight > 10 ? 'RoyalBlue' : weight > 5 ? 'Red' : 'Black'"
    font-family="Anton"
    :spacing="spacing"
  />

</div>
</template>

<script>
import VueWordCloud from 'vuewordcloud';

export default {
  name: 'LolbodyWordCloud',
  components: {
    VueWordCloud
  },
  data() {
    return {
      spacing: 0.5,
      words: [
        ['아리', 19],
        ['야스오', 4],
        ['미스포츈', 10],
        ['아펠리오스', 5],
        ['요네',11],
        ['알리스타', 16],
        ['칼리스타', 13],
        ['아칼리', 9],
      ]
    }
  }


}
</script>

<style>

</style>