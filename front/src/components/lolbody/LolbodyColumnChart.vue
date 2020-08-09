<template>

  <ColumnChart type="bar" height="200" :options="chartOptions" :series="series"></ColumnChart>

</template>

<script>
import ColumnChart from 'vue-apexcharts'

export default {
  name: 'LolbodyColumnChart',
  components: {
    ColumnChart
  },
  data() {
    return {        
      series: [{
        name: '공격성',
        data: [44, 55, 57, 56, 61, 58, 63, 60, 66]
      }, {
        name: '안정성',
        data: [76, 85, 101, 98, 87, 105, 91, 114, 94]
      }, {
        name: '영향력',
        data: [35, 41, 36, 26, 45, 48, 52, 53, 41]
      }],
      chartOptions: {
        chart: {
          type: 'bar',
          toolbar: {
            show: false,
          },
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%',
            // endingShape: 'rounded'
          },
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: ['7/7', '7/8', '7/9', '7/10', '8/1', '8/3', '8/4', '8/10', '8/11'],
        },
        // yaxis: {
        //   title: {
        //     text: '$ (thousands)'
        //   }
        // },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + " 점"
            }
          }
        }
        
        
      },
    }
  }
}
</script>

<style scoped>



</style>