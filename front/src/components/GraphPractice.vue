<template>
  <apexchart width="500" type="radar" :options="options" :series="series"></apexchart>
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
          [10, 20, 30],
          [20, 10, 30],
          [40, 50, 70],
        ],
        post: [
          [20, 10, 60],
          [40, 80, 50],
          [30, 20, 10],
        ]
      },
      options: {
        chart: {
          id: 'vuechart-example'
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
        name: '초반',
        data: [30, 60, 50]
      },
      {
        name: '후반',
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
  created() {
    setInterval(() => {
      this.changeIndexGraphSeries();
    }, 3000)
  },
}
</script>

<style>

</style>