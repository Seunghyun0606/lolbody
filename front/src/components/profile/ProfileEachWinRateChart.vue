<template>
  <DonutChart type="radialBar" height="100%" :options="chartOptions" :series="series"></DonutChart>

</template>

<script>
import DonutChart from 'vue-apexcharts'


export default {
  name: 'ProfileEachWinRateChart',
  components: {
    DonutChart,
  },
  computed: {
      series(){
          return this.$store.getters.getProfileEachWinRateChart.data
      },
      chartOptions() {
          return {
        tooltip: {
          enabled: true,  // 호버시 툴팁입니다.
          theme: "dark",
        },
        // colors: ['#000'], // 여기에 컬러 넣으세요
        chart: {
          type: 'radialBar',
        },
        legend: {
          show: true,
          offsetX: -15,
          offsetY: 9,
        },
        labels: this.$store.getters.getProfileEachWinRateChart.labels,

        plotOptions: {
          radialBar: {
            offsetX: 0,
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