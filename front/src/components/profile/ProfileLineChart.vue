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
                labels: [],
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
                    title: {
                        // text: '최근 10게임 KDA'
                    }
                },
                yaxis: {
                    title: {
                        text: this.profileLineChartOption.series[0].name
                    },
                // tickAmount: 5,
                // min: 0,
                // max: 10
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