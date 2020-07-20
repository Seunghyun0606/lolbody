<template>
  <div>
    <VueApexCharts width="365" hegith="200" type="radar" :options="chartOptions" :series="series"></VueApexCharts>
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
            data: [80, 50, 30],
          }
         ],
        
        chartOptions: {
          chart: {
            toolbar: {
              show: false,
            },
            width: '100%',
            height: '500px',
            offsetX: 0,
            parentHeightOffset: 0,
          },
          title: {
            text: 'Basic Radar Chart',
            align: 'center'
          },
          xaxis: {
            categories: ['Test1', 'Test2', 'Test3'],
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
