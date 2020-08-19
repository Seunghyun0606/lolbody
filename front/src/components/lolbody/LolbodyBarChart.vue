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
    }
  },
  props: {
    getMyData: Object,
    getOtherData: Array,
  },
  computed: {
    series() { 
      return [
        {
          name: '동 티어대비',
          data: []
        },
        {
          name: '나',
          data: []
        },
      ]
    },
    chartOptions() {
      return {
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
      }
    }
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
        console.log(this.getOtherData)

        this.other(this.getOtherData[0])
      },
    },
    series: {
      deep: true,
      immediate: true,
      handler() {
        console.log(this.series)
        this.check(this.series)
      }
    }
  },

  methods: {
    check(datas) {
      this.series = datas

    },
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

      for ( var data in datas ) {
        compList.push(Math.round(datas[data]*100))

      }
      this.series[0].data = compList
    },

  },
  
}

</script>

<style>

</style>