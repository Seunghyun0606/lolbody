<template>

  <apexchart class="move-apexchart" type="radar" width="350" :options="options" :series="series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
  name: 'LolbodyRadarChart',
  components: {
    apexchart
  },
  data() {
    return {
      options: {
        chart: {
          id: 'vuechart-example',
          toolbar: {
            show: false,
          },
        },
        legend: {
          show: false,
        },
        xaxis: {
          categories: ['공격력', '안정성', '영향력']
        },
        yaxis: {
          show: false,
          tickAmount: 5,
          min: 0,
          max: 100,
        },
        markers: {
          size: 3
        }
      },
      series: [{
        name: '초반',
        data: [10, 60, 90]
      },
      {
        name: '후반',
        data: [50, 40, 60]
      }]
    }
  },
  methods: {
  },
  computed: {
  },
}
</script>

<style scoped>

.move-apexchart {
  position: relative;
  left: -65px;
  top: 30px;
}

</style>