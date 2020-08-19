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
        data: []
      },
      {
        name: '나',
        data: []
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
  props: {
    getMyData: Object,
    getOtherData: Object,
  },
  watch: {
    getMyData: {
      deep: true,
      immediate: true,
      handler() {
        this.init(this.getMyData)
      }
      
    },
    getOtherData: {
      deep: true,
      immediate: true,
      handler() {
        this.other(this.getOtherData)
      },
    }
  },
  methods: {
    init(datas) {
      var myList = []
      var columnName = []

      for ( var data in datas ) {
        myList.push(Math.round(datas[data]*100))
        columnName.push(data)

      }
      this.series[1].data = myList
      this.chartOptions.xaxis.categories = columnName
    },
    other(datas) {
      var compList = []

      for ( var data in datas.mean ) {
        compList.push(Math.round(datas.mean[data]*100))

      }
      this.series[0].data = compList
    },

  },
  
}

</script>

<style>

</style>