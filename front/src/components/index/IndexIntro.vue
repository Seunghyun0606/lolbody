<template>
  <v-container>
    <v-row
      class="text-center"
      justify="center">
      <v-col
        cols="12"
        md="4"
        >
        <GraphPractice/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import GraphPractice from '@/components/GraphPractice.vue'

export default {
  name: "IndexIntro",
  components: {
    GraphPractice,
  }

}
</script>

<style>

</style>