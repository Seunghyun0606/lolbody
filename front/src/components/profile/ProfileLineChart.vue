<template>
  <div>
    <VueApexCharts height="150" type="line" :options="chartOptions" :series="series"></VueApexCharts>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
import { mapState } from 'vuex'


export default {
    name: 'ProfileLineChart',
    components: {
          VueApexCharts
    },
    computed: {
      ...mapState([
        'matchDatas',
      ])
    },
    created() {
      this.change()
    },
    methods: {
      // 일단 짜고 나중에 리팩토링하자.
      // Line Chart 값넣기.
      change() {
        for ( var matchData of this.matchDatas ) {
          if ( matchData.myTeam === 'blueTeam' ) {

            this.series[0].data.unshift(matchData.blueTeam.teammate[matchData.myIndex].kda.toFixed(2))
          }
          else {
            this.series[0].data.unshift(matchData.redTeam.teammate[matchData.myIndex].kda.toFixed(2))
            
          }
          this.chartOptions.xaxis.categories.unshift(this.calcDate(matchData.timestamp))
          
          
        }
      },
      calcDate(timestamp) {
        let month = new Date(timestamp).getMonth() + 1 + '월 '
        let day = new Date(timestamp).getDate() + '일 '
        // let hour = new Date(timestamp).getHours() + '시'
        
        return month + day // + hour
      },
    },
    data() {
        return {

          series: [
            {
              name: "KDA",
              data: [] 
            },
            // {
            //   name: "Lane2",
            //   data: [3, 4, 5, 6, 5, 6.3, 7.5] 
            // }
          ],
          chartOptions: {
            chart: {
              height: 150,
              type: 'line',
              zoom: {
                enabled: false,
              },
              toolbar: {
                show: false
              },
            },
            colors: ['#77B6EA', '#545454'],
            grid: {
              borderColor: '#e7e7e7'
            },
            markers: {
              size: 5,
              shape: "circle",
              radius: 1,
            },
            labels: [3, 2, 3, 4, 5, 6, 7, 8, 9, 10],
            xaxis: {
              labels: {
                showDuplicates: false,
                style: {
                  fontSize: '10px',
                }
              },
              tickAmount: 3,
              categories: [],
              type: 'category',
              title: {
                // text: '최근 10게임 KDA'
              }
            },
            yaxis: {
              title: {
                text: 'KDA'
              },
              // tickAmount: 5,
              // min: 0,
              // max: 10
            }
          },
        }
    },

}
</script>

<style>
</style>