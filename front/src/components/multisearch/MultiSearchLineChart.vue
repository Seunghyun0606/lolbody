<template>
  <LineChart type="bar" height="130" :options="chartOptions" :series="series" />
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
            data: [44, 55, 66, 77, 88] // 들어오는 퍼센트 데이터 넣어줘야함.
          },
        ],
        
        chartOptions: {
          chart: {
            type: 'bar',
            toolbar: {
              show: false,
            // 위에 툴바 없애기
          },              
          },
          plotOptions: {
              bar: {
              horizontal: false,
              columnWidth: '55%',
              },
          },
          dataLabels: {
              enabled: false
          },
          xaxis: {
              categories: ['Top', 'Jg', 'Mid', 'Adc', 'Sup'],
              labels: {
                style: {
                  fontSize: '10px',
                  fontWeight: 100,
                }
              }
          },
          yaxis: {
            labels: {
              show: false,
            }

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
  methods: {
    makeXaxis() {
      var test = document.getElementsByTagName("tspan")
      var test1 = document.getElementsByTagName("tspan")[0]
      var testImg = document.createElement('img')
      testImg.src = require(`@/assets/images/tier/GOLD.png`)
      test1.appendChild(testImg)
      console.log(testImg.src)
      console.log(test)
      console.log(test1)
      // this.chartOptions.xaxis.categories = ['top', 'jg', 'a', 'b', 'c']
    },
  },
  created () {
    this.makeXaxis()
  }
}

</script> 

<style>

</style>