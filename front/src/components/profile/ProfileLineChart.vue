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
        'radarChartDatas',
      ])
    },
    // methods: {
      // change() {
      //   let lane1 = radarChartDatas.lane1;
      //   let lane2 = radarChartDatas.lane2;

      //   this. lane1 
      // }
    // },
    data() {
        return {

          series: [
            {
              name: "Lane1",
              data: [1, 2, 3, 4, 5, 6.3, 7.5] 
            },
            {
              name: "Lane2",
              data: [3, 4, 5, 6, 5, 6.3, 7.5] 
            }
          ],
          chartOptions: {
            chart: {
              height: 150,
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
              size: 5,
              shape: "circle",
              radius: 1,
            },
            xaxis: {
              categories: [
                'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'
              ],
              title: {
                text: '최근 20게임'
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
    methods:{
      changeData(){
        
      }
    },
    mounted() {

    }
}
</script>

<style>
</style>