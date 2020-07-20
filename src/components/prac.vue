<template>
  <div>
    <apexcharts width="500" height="350" type="bar" :options="chartOptions" :series="series"></apexcharts>
    <button @click="changeWidth">CHANGE WIDTH</button>
  </div>
</template>

<script>
import VueApexCharts from "vue-apexcharts";

export default {
  name: "prac",
  components: {
    apexcharts: VueApexCharts
  },
  data: function() {
    return {
      width: 500,
      chartOptions: {
        chart: {
          id: "vuechart-example"
        },
        xaxis: {
          categories: [1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998]
        }
      },
      series: [
        {
          name: "series-1",
          data: [30, 40, 45, 50, 49, 60, 70, 91]
        }
      ]
    };
  },
  methods: {
    changeWidth: function() {
      this.width = '300'
    }
  }
};
</script>