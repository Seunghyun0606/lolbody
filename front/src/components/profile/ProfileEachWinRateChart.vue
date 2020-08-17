<template>
  <DonutChart class="ml-2" type="radialBar" height="100%" :options="chartOptions" :series="series"></DonutChart>

</template>

<script>
import DonutChart from 'vue-apexcharts'


export default {
  name: 'ProfileEachWinRateChart',
  components: {
    DonutChart,
  },
  computed: {
      eachdata(){
          return this.$store.getters.getProfileEachWinRateChart
      },
      series(){
          return this.$store.getters.getProfileEachWinRateChart.data
      },
      chartOptions() {
          return {
        tooltip: {
            enabled: true,  // 호버시 툴팁입니다.
            theme: "dark",
            y:{
                formatter: function() {
                    return ''
                },
                title: {
                    formatter: (seriesName, { series, seriesIndex}) => {
                        return seriesName + '<br>' + series[seriesIndex] + '% (' + this.eachdata.win[seriesIndex]+'승'+this.eachdata.lose[seriesIndex]+'패)'
                    },
                },
            },
        },
        // colors: ['#000'], // 여기에 컬러 넣으세요
        chart: {
          type: 'radialBar',
        },
        legend: {
          show: true,
          offsetX: -26,
          offsetY: 14,
        },
        labels: this.$store.getters.getProfileEachWinRateChart.labels,
        plotOptions: {
          radialBar: {
            offsetX: 6,
            hollow: { // 차트 내부 공간 간격입니다.
              marign: 0,
              size: "5%",
            },
            track: {  // 차트 간의 간격
              show: true,
              margin: 3,
            },
            dataLabels: {
              show: false,
            }
          }
        }
      }
    }//chartoption
  }
}
</script>

<style>
</style>