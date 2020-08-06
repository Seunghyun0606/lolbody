<template>
  <div>
    <VueApexCharts height="150" type="line" :options="profileLineChartOption.chartOptions" :series="profileLineChartOption.series"></VueApexCharts>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
import { mapState } from 'vuex'


export default {
    name: 'ProfileLineChart',
    components: {
          VueApexCharts
    },
    computed: {
      ...mapState([
        'profileLineChartOption',
      ])
    },

}
</script>

<style>
</style>