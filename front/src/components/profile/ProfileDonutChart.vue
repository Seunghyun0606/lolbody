<template v-slot:header>
  <DonutChart type="donut" width="300px" :options="chartOptions" :series="series"></DonutChart>


</template>

<script>
import DonutChart from 'vue-apexcharts'

// https://apexcharts.com/docs/chart-types/pie-donut/ 참고하세요

export default {
  name: 'ProfileDonutChart',
  components: {
    DonutChart,
  },
  data() {
    return {
      series: [5, 5],
      chartOptions: {
        chart: {
          type: 'donut',
        },
        dataLabels: {
          enabled: false
        },
        fill: {
          type: 'gradient',
        },
        legend: {
          show: true,
          // formatter: function(val, opts) {
          //   return val + " - " + opts.w.globals.series[opts.seriesIndex]
          // }
        },
        labels: ["전적", "전적"],
        plotOptions: {
          pie: {
            donut: {
              labels: {
                show: true,
                name: {
                  show: true,
                  fontSize: '20px',
                  fontFamily: 'Helvetica, Arial, sans-serif',
                  fontWeight: 600,
                  color: undefined,
                  offsetY: 0,
                  formatter: function (val) {
                    return val
                  }
                },
                value: {
                  show: true,
                  fontSize: '16px',
                  fontFamily: 'Helvetica, Arial, sans-serif',
                  fontWeight: 400,
                  color: undefined,
                  offsetY: 4,
                  formatter: function (val) {
                    return val
                  }
                },
                total: {
                  show: true,
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
      },
    }
  }

}
</script>

<style>

</style>