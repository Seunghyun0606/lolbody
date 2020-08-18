<template>
  <LineChart height="70%" :options="chartOptions" :series="series" />
</template>

<script>

import LineChart from "vue-apexcharts"

export default {
  name: "MultiSearchLineChart",
  components: {
      LineChart,
  },
  props: {
    lineChart: Object,
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
      for (var i in this.lineChart) {
        if ( i === 'TOP') {
          this.series[0].data[0] = this.lineChart[i] * 10
        } else if ( i === 'JUNGLE') {
          this.series[0].data[1] = this.lineChart[i] * 10
        } else if ( i === 'MID') {
          this.series[0].data[2] = this.lineChart[i] * 10

        } else if ( i === 'BOTTOM' | i === 'BOT') {
          this.series[0].data[3] = this.lineChart[i] * 10

        } else {
          this.series[0].data[4] = this.lineChart[i] * 10
        }
      } 
    },
  },
  created() {
    this.changeLaneData()
  }

}

</script> 

<style>

</style>