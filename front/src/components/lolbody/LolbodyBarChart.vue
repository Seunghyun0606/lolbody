<template>
  <BarChart type="bar" height="450" :options="chartOptions" :series="series"></BarChart>
</template>

<script>
import BarChart from "vue-apexcharts"

export default {
  name: 'LolbodyBarChart',
  components: {
    BarChart,
  },
  data() {
    return {
      series: [
      {
        name: '동 티어대비',
        data: [44, 55, 41, 64, 22, 43, 21]
      },
      {
        name: '현재 기준',
        data: [53, 32, 33, 52, 13, 44, 32]
      },
      {
        name: '이전 기준',
        data: [23, 42, 12, 44, 88, 34, 32]
      },
      ],
      chartOptions: {
        chart: {
          type: 'bar',
          toolbar: {
            show: false,
          },
        },
        plotOptions: {
          bar: {
            horizontal: true,
            dataLabels: {
              position: 'top',
            },
          }
        },
        dataLabels: {
          enabled: true,
          offsetX: -6,
          style: {
            fontSize: '12px',
            colors: ['#fff']
          }
        },
        stroke: {
          show: true,
          width: 1,
          colors: ['#fff']
        },
        xaxis: {
          categories: ['딜량', '회복량', '2003', 2004, 2005, 2006, 2007],
        },
      },
    }
  },
  
}

</script>

<style>

</style>