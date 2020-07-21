<template>
  <apexchart width="500" type="radar" :options="options" :series="series"></apexchart>
</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
  components: {
    apexchart
  },
  data() {
    return {
      options: {
        chart: {
          id: 'vuechart-example'
        },
        xaxis: {
          categories: ['공격력', '안정성', '영향력']
        },
        yaxis: {
          tickAmount: 5,
          min: 0,
          max: 100,
        }
      },
      series: [{
        name: '초반',
        data: [30, 40, 45]
      },
      {
        name: '후반',
        data: [50, 40, 60]
      }]
    }
  }
}
</script>

<style>

</style>