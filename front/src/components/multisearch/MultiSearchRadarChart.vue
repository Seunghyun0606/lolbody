<template>

  <!-- <MultiRadarChart class='move-apexchart' type="radar" width="235" :options="options" :series="series"></MultiRadarChart> -->
  <apexchart class="move-apexchart" type="radar" width="235" :options="radarChart.options" :series="radarChart.series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
  name: 'MultiSearchRadarChart',
  props: {
    radarChart: Object
  },
  components: {
    apexchart,
  },
  // watch: {
  //   multiSearchRadarData: {
  //     deep: true,
  //     handler() {
  //       this.changeSeries()
  //     }
      
  //   }
  // },
  
  // methods: {
  //   changeSeries() {
  //     console.log(2, this.multiSearchRadarData)
  //     this.series[0].name = this.multiSearchRadarDatas[this.index].lane1.lane
  //     this.series[1].name = this.multiSearchRadarDatas[this.index].lane2.lane
  //     // console.log(3, this.series)
  //     this.series[0].data = []
  //     this.series[1].data = []
  //     for ( var obj in this.multiSearchRadarDatas[this.index].lane1 ) {
  //       if (obj === "lane") {
  //         continue
  //       }
  //       this.series[0].data.push((this.multiSearchRadarDatas[this.index].lane1[obj]*100).toFixed(0))
  //       // console.log(this.multiSearchRadarData.lane1[obj])
  //     }
  //     for ( var obj2 in this.multiSearchRadarDatas[this.index].lane2 ) {
  //       if (obj2 === "lane" ) {
  //         continue
  //       }
  //       this.series[1].data.push((this.multiSearchRadarDatas[this.index].lane2[obj2]*100).toFixed(0))
  //     }
  //   }
  // },
}
</script>

<style scoped>

.move-apexchart {
  position: relative;
  left: -45px;
  top: 30px;
}

</style>