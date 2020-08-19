<template>
<div style="height: 100%; width: 100%; padding: 50px;">
  <VueWordCloud
    :words="words"
    :color="([, weight]) => weight > 10 ? 'DeepPink' : weight > 5 ? 'Green' : weight > 3 ? 'RoyalBlue' : weight > 1 ? 'Black' : 'Grey'"
    font-family="Anton"
    :spacing="spacing"
  />

</div>
</template>

<script>
import VueWordCloud from 'vuewordcloud';
import champion from '@/assets/data/champion.json';


export default {
  name: 'LolbodyWordCloud',
  components: {
    VueWordCloud
  },
  data() {
    return {
      spacing: 0.5,
      words: [
      ]
    }
  },
  watch: {
    champList: {
      deep: true,
      // immediate: true,
      handler() {
        this.changeWords(this.champList)

      }
    }
  },
  methods: {
    changeWords(datas) {
      for ( var tempData of datas ) {
        var champWord = []
        champWord.push(champion.data[tempData.name].name)
        champWord.push(tempData.games)
        this.words.push(champWord)
      }
    }

  },

  props: {
    champList: Array
  },


}
</script>

<style>

</style>