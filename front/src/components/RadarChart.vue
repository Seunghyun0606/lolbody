<template>
  <div>
    <VueApexCharts style="background-color: red;" width="500" type="radar" :options="chartOptions" :series="series"></VueApexCharts>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'

export default {
    name: 'RadarChart',
    components: {
      VueApexCharts,

    },
    data () {
        return {
        series: [{
            name: 'Test 1',
            data: [80, 50, 30, 40, 100],
          },
          {
            name: 'Test 2',
            data: [40, 60, 40, 100, 10],
          }],
        
        chartOptions: {
          chart: {
            // background: 'black',
            toolbar: {
              show: false,
            },
            // width: '100%',
            // height: '500px',
            // offsetX: 0,
            // parentHeightOffset: 0,
          },
          // title: {
          //   text: 'Basic Radar Chart'
          //   align: 'center'
          // },
          xaxis: {
            categories: ['Test1', 'Test2', 'Test3', 'Test4', 'Test5'],
            labels: {
              show: true,
            }
          },
        },
      }
    }
  }
</script>

<style>

</style>
