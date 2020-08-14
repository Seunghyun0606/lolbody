<template>
  <DonutChart type="radialBar" height="100px" :options="chartOptions" :series="series"></DonutChart>

</template>

<script>
import DonutChart from 'vue-apexcharts'

// https://apexcharts.com/docs/chart-types/pie-donut/ 참고하세요

export default {
  name: 'ProfileDonutChart2',
  components: {
    DonutChart,
  },
  data() {
    return {
      series: [30, 50, 50, 100],

      chartOptions: {
        chart: {
          // height: 350,
          type: 'radialBar',
        },
        legend: {
          show: true,
        },
        labels: ['Apples', 'Oranges', 'Bananas', 'Berries'],
        plotOptions: {
          radialBar: {
            offsetX: 0,
            hollow: {
              marign: 0,
              size: "10%",
            }
            // track: {
            //   show: true,
            //   margin: 10,
            // },
            // dataLabels: {
            //   name: {
            //     fontSize: '22px',
            //   },
            //   value: {
            //     fontSize: '16px',
            //   },
            //   total: {
            //     show: true,
            //     label: 'Total',
            //     formatter: function () {
            //       // By default this function returns the average of all series. The below is just an example to show the use of custom formatter function
            //       return 249
            //     }
            //   }
            // }
          }
        },
      },
    }
  }

}
</script>

<style>

</style>