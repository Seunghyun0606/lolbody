<template>
  <div>
    <VueApexCharts type="line" :options="chartOptions" :series="series"></VueApexCharts>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
export default {
    name: 'ProfileLineChart',
    components: {
          VueApexCharts
    },
    data() {
        return {
          items:{
              summonerName:"재료페인",
              queueType:"RANKED_SOLO_5x5",
              tier:"PLATINUM",
              rank:"III",
              leaguePoints:32,
              wins:66,
              losses:53,
              hotStreak:false,
              veteran:false,
              freshBlood:false,
              inactive:false
          },
          series: [
            {
              name: "High - 2013",
              data: [10, 9, 8, 7, 6, 11, 15]
            }
          ],
          chartOptions: {
            chart: {
              height: 100,
              type: 'line',
              toolbar: {
                show: false
              },
              // annotations:{
              //   yaxis:[{
              //     y:
              //   }]
              // }
            },
            colors: ['#77B6EA', '#545454'],
            grid: {
              borderColor: '#e7e7e7'
            },
            markers: {
              size: 7,
              shape: "circle",
              radius: 2,
            },
            xaxis: {
              categories: [
                'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'
              ],
              title: {
                text: 'Month'
              }
            },
            yaxis: {
              title: {
                text: 'LP'
              },
              tickAmount: 15,
              min: 5,
              max: 20
            }
          },
        }
    },
    methods:{
      yData(){
        
      }
    }
}
</script>

<style>
</style>