<template>
  <LineChart type="bar" height="100" :options="chartOptions" :series="series" />
</template>

<script>

import LineChart from "vue-apexcharts"

export default {
  name: "MultiSearchLineChart",
  components: {
      LineChart,
  },
  props: {
    multiSearchData: Object,
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
              columnWidth: '40%',
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
    changeLaneData() {
      for (var i of this.multiSearchData.positionRates) {
        if ( i.lane === 'TOP') {
          this.series[0].data[0] = i.rate
        } else if ( i.lane === 'JUNGLE') {
          this.series[0].data[1] = i.rate
        } else if ( i.lane === 'MID') {
          this.series[0].data[2] = i.rate

        } else if ( i.lane === 'BOTTOM' | i.lane === 'BOT') {
          this.series[0].data[3] = i.rate

        } else {
          this.series[0].data[4] = i.rate
        }
      } 
    },
  },
  created() {
    this.changeLaneData()
  }

  // methods: {
  //   makeXaxis() {
  //     var test = document.getElementsByTagName("tspan")
  //     var test1 = document.getElementsByTagName("tspan")[0]
  //     var testImg = document.createElement('img')
  //     testImg.src = require(`@/assets/images/tier/GOLD.png`)
  //     test1.appendChild(testImg)
  //     console.log(testImg.src)
  //     console.log(test)
  //     console.log(test1)
  //     // this.chartOptions.xaxis.categories = ['top', 'jg', 'a', 'b', 'c']
  //   },
  // },
  // created () {
  //   this.makeXaxis()
  // }
}

</script> 

<style>

</style>