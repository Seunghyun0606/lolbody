<template>
  <v-container class='index-intro'>
    <v-row>        

        <IndexIntroText/>

      <v-col cols=6>
        <GraphPractice/>

      </v-col>

    </v-row>
  </v-container>
</template>

<script>
import GraphPractice from '@/components/index/GraphPractice.vue'
import IndexIntroText from '@/components/index/IndexIntroText'

export default {
  name: "IndexIntro",
  components: {
    GraphPractice,
    IndexIntroText,
  },
  data() {
    return {

    }
  }

}
</script>

<style scoped>

.index-intro {
  width: 690px !important;
  padding: 12px 0 0;
}

</style>