<template>
  <LineChart type="bar" height="250" :options="chartOptions" :series="series" />
</template>

<script>

import LineChart from "vue-apexcharts"

export default {
    name: "MultiSearchLineChart",
    components: {
        LineChart,
    },
    data() {
      return {        
        series: [
          {
            name: 'Lane',
            data: [44, 55, 66, 77, 88]
          },
        ],
        
        chartOptions: {
          chart: {
            type: 'bar',
            height: 350,
            toolbar: {
              show: false,
            // 위에 툴바 없애기
          },              
          },
          plotOptions: {
              bar: {
              horizontal: false,
              columnWidth: '55%',
              endingShape: 'rounded'
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
              categories: ['Top', 'Jungle', 'Mid', 'ADC', 'Sup'],
          },
          yaxis: {
              title: {
              text: 'Lane'
              }
          },
          fill: {
              opacity: 1
          },
          tooltip: {
              y: {
              formatter: function (val) {
                  return val + "%"
                  }
                }
            }
        },
      }
  },     
}

</script>

<style>

</style>