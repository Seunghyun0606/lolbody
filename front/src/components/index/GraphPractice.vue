<template>

  <apexchart class='move-apexchart' type="radar" width="500" :options="options" :series="series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
  components: {
    apexchart
  },
  data() {
    return {
      test: [1, 2, 3],
      indexGraphSeries: {
        idx: 0,
        pre: [
          [10, 50, 45],
          [65, 30, 80],
          [10, 25, 30],
        ],
        post: [
          [50, 90, 10],
          [40, 30, 10],
          [40, 75, 60],
        ]
      },
      options: {
        chart: {
          id: 'vuechart-example',
          toolbar: {
            show: false,
          },
          // height: '100%'
        },
        legend: {
          show: false,
        },
        xaxis: {
          categories: ['공격력', '안정성', '영향력']
        },
        yaxis: {
          tickAmount: 5,
          min: 0,
          max: 100,
        }
      },
      series: [{
        name: 'MID',
        data: [30, 60, 50]
      },
      {
        name: 'BOTTOM',
        data: [50, 40, 60]
      }]
    }
  },
  methods: {
    changeIndexGraphSeries() {
      if (this.indexGraphSeries.idx >= 2) {
        this.indexGraphSeries.idx = 0
      } else {
        this.indexGraphSeries.idx += 1
      }
      this.series = [{
        data: this.indexGraphSeries.pre[this.indexGraphSeries.idx]
        },
        {
          data: this.indexGraphSeries.post[this.indexGraphSeries.idx]
      }]
    }
  },
  computed: {
    onlyGraph() {
      return document.querySelectorAll('g > g')[3]
    },
    onlyGraph2() {
      return document.getElementsByClassName('apexcharts-radar-series')[0]
    }
  },
  created() {
    setInterval(() => {
      this.changeIndexGraphSeries();
    }, 1500)
  },
  mounted() {
  }
}
</script>

<style scoped>

.move-apexchart {
  position: relative;
  left: -50px;
}

</style>