<template>

  <!-- <MultiRadarChart class='move-apexchart' type="radar" width="235" :options="options" :series="series"></MultiRadarChart> -->
  <apexchart class="move-apexchart" type="radar" width="235" :options="options" :series="series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
  name: 'MultiSearchRadarChart',
  props: {
    multiSearchRadarData: Object
  },
  components: {
    apexchart,
  },
  watch: {
    multiSearchRadarData: {
      deep: true,
      handler() {
        this.changeSeries()
      }
      
    }
  },

  methods: {
    changeSeries() {
      // console.log(2, this.multiSearchRadarData)
      this.series[0].name = this.multiSearchRadarData.lane1.lane
      this.series[1].name = this.multiSearchRadarData.lane2.lane
      // console.log(3, this.series)
      this.series[0].data = []
      this.series[1].data = []
      for ( var obj in this.multiSearchRadarData.lane1 ) {
        if (obj === "lane") {
          continue
        }
        this.series[0].data.push((this.multiSearchRadarData.lane1[obj]*100).toFixed(0))
        // console.log(this.multiSearchRadarData.lane1[obj])
      }
      for ( var obj2 in this.multiSearchRadarData.lane2 ) {
        if (obj2 === "lane" ) {
          continue
        }
        this.series[1].data.push((this.multiSearchRadarData.lane2[obj2]*100).toFixed(0))
      }
    }
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
        name: '초반',
        data: [40, 40, 40]
      },
      {
        name: '후반',
        data: [90, 80, 80]
      }]
    }
  },
}
</script>

<style scoped>

.move-apexchart {
  position: relative;
  left: -45px;
  top: 30px;
}

</style>