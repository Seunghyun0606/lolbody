<template>

  <apexchart class='move-apexchart' type="radar" width="350" :options="profileRadarChartOption.options" :series="profileRadarChartOption.series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'
import { mapState } from 'vuex'

export default {
  name: 'ProfileRadarChart',
  components: {
    apexchart
  },
  computed: {
    ...mapState([
      'profileRadarChartOption',
    ]),

  },

}
</script>

<style scoped>

.move-apexchart {
  position: relative;
  left: -70px;
}

</style>