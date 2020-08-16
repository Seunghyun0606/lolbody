<template >
  <DonutChart type="donut" height="70rem" :options="chartOptions" :series="series"></DonutChart>


</template>

<script>
import DonutChart from 'vue-apexcharts'

// https://apexcharts.com/docs/chart-types/pie-donut/ 참고하세요

export default {
  name: 'ProfileChampWinRateChart',
  components: {
    DonutChart,
  },
  props: ['win', 'lose'],
    computed:{
        series(){
            return [this.win, this.lose]
        },
    },
  data() {
    return {
      
      chartOptions: {

        colors: ['#1A73E8', '#B32824'], // 호버했을때 데이터 레이블 색깔
        chart: {
          offsetY: -3,
          offsetX: -5,
          type: 'donut',
        },
        dataLabels: {
          enabled: false
        },
        fill: {
          colors: ['#1A73E8', '#B32824']  // 차트 색깔
        },
        legend: {
          show: false,
        },
        labels: ["승리", "패배"],
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: "100%"
            },
          }
        }]
      },
    }
  },
}
</script>

<style>

</style>

