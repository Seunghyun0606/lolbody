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
  props: ['champList'],
  data() {
    return {
        spacing: 3,
    }
  },
  computed:{
      words(){
            let result = [];
            for ( var tempData of this.champList ) {
                var champWord = []
                champWord.push(champion.data[tempData.name].name)
                champWord.push(tempData.games)
                result.push(champWord)
            }
            return result;
      }
  },

}
</script>

<style>

</style>