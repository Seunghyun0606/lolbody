<template>

  <apexchart class="move-apexchart" width="350" :options="options" :series="series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'
// import { mapGetters } from 'vuex'

export default {
  name: 'LolbodyRadarChart',
  components: {
    apexchart
  },
  data() {
    return {
      options: {
        chart: {
          type: "radar",
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
        name: 'Match Grade',
        data: [10, 60, 90]
      },]
    }
  },
  watch: {
    radarData: {
      deep: true,
      immediate: true,
      handler() {
        this.changeSeries(this.radarData)
      }

    },
  },
  props: {
    radarData: Object
  },
  methods: {
    changeSeries(datas) {
      var changeSeriesData = []
      for ( var value in datas ) {
        changeSeriesData.push(Math.round(datas[value]*100))
      }
      this.series[0].data = changeSeriesData
    }

  },
  computed: {
    // ...mapGetters(['getLolbodyData'])
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