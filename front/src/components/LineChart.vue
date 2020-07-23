<template>
  <div>
    <VueApexCharts type="line" :options="chartOptions" :series="series"></VueApexCharts>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
export default {
    name: 'LineChart',
    components: {
          VueApexCharts
    },
    data() {
        return {
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
                text: 'Temperature'
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