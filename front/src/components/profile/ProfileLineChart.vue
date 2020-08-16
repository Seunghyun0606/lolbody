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
                        },
                        formatter: function(value) {
                            if(value == undefined)
                                return null;
                            const timestamp =  new Date(parseInt(value.substring(0, 13)));
                            const month = new Date(timestamp).getMonth() + 1 + '월 '
                            const day = new Date(timestamp).getDate() + '일 '
                            // let hour = new Date(timestamp).getHours() + '시'
                            return month + day // + hour
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
                    tickAmount: 5,
                    max: 10,
                    forceNiceScale: true,
                    labels: {
                        show : true,
                        formatter: (value) => { 
                            if(value >= 10 )
                                return '10+';
                            else
                                return value;
                        },
                    },
                },
                tooltip:{
                    custom: function({series, seriesIndex, dataPointIndex, w}) {
                        console.log(series)
                        console.log(w.globals.labels[dataPointIndex])
                        return '<div class="arrow_box">' +
                        '<span>' + series[seriesIndex][dataPointIndex] + '</span>' +
                        '</div>'
                    }
                    //x: {
                    //    formatter: (value, {series, seriesIndex, dataPointIndex}) => { 
                    //        console.log(series)
                    //        console.log(seriesIndex)
                    //        console.log(dataPointIndex)
                    //        console.log(value)
                    //        return value
                    //    },
                    //},
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