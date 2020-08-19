<template>
  <DonutChart type="donut" height="85%" :options="chartOptions" :series="series"></DonutChart>


</template>

<script>
import DonutChart from 'vue-apexcharts'
// https://apexcharts.com/docs/chart-types/pie-donut/ 참고하세요

export default {
  name: 'ProfileTotalWinRateChart',
  components: {
    DonutChart,
  },
  computed: {
      ProfileTotalWinRateChart(){
          return this.$store.state.ProfileTotalWinRateChart;
      },
      series() {
          return [this.ProfileTotalWinRateChart.win, this.ProfileTotalWinRateChart.lose]
      },
      chartOptions(){
        return {
            colors: ['#1A73E8', '#B32824'], // 호버했을때 데이터 레이블 색깔
                chart: {
                    offsetY: 10,
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
            plotOptions: {
              pie: {
                donut: {
                  labels: {
                    show: true,
                    name: {
                      show: true,
                      fontSize: '20px',
                      fontWeight: 600,
                      offsetY: 0,
                      formatter: function (val) {
                        return val
                      }  // 호버시 승리, 패배 이름
                    },
                    value: {
                      show: true,
                      fontSize: '16px',
                      fontWeight: 400,
                      offsetY: 4,
                      formatter: function (val) {
                        return val
                      }  // 호버 했을때, 승, 패 값
                    },
                    total: {
                      show: true,
                      label: "총 게임 수",
                      fontSize: "14px",
                      fontWeight: 600,
                      color: 'black',
                    }
                  },
                }
              }
            },
            responsive: [{
              breakpoint: 480,
              options: {
                chart: {
                  width: "100%"
                },
                // legend: {
                //   position: 'bottom'
                // }
              }
            }]
      } //return
    }//chartoption
  },
}
</script>

<style>

</style>

