<template>
  <div>
    <apexchart height="150" type="line" :options="chartOptions" :series="series.series"></apexchart>
  </div>
</template>

<script>
import apexchart from 'vue-apexcharts'
import { mapState } from 'vuex'


export default {
    name: 'ProfileLineChart',
    components: {
        apexchart
    },
    computed: {
        ...mapState([
            'profileLineChartOption',
        ]),
        chartOptions() {
            return {
                chart: {
                    height: 150,
                    type: 'line',
                    zoom: {
                        enabled: false,
                    },
                    toolbar: {
                        show: false
                    },
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
                    labels: {
                        showDuplicates: false,
                        style: {
                            fontSize: '10px',
                        }
                    },
                    tickAmount: 3,
                    categories: this.profileLineChartOption.chartOptions.xaxis.categories,
                    type: 'category',
                },
                yaxis: {
                    title: {
                        text: this.profileLineChartOption.series[0].name
                    },
                }
            };
        },
        series() {
            return {
                series: [{
                    name: this.profileLineChartOption.series[0].name,
                    data: this.profileLineChartOption.series[0].data
                }],
            };
        },
    },
}
</script>

<style>
</style>