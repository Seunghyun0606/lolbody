<template>
  <div>
    <apexchart height="150" type="line" :options="chartOptions" :series="series"></apexchart>
  </div>
</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
    name: 'ProfileLineChart',
    components: {
        apexchart
    },
    props: ['category', 'yaxis', 'series'],
    computed: {
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
                            const timestamp =  new Date(parseInt(value));
                            const month = new Date(timestamp).getMonth() + 1 + '월 '
                            const day = new Date(timestamp).getDate() + '일 '
                            return month + day // + hour
                        }
                    },
                    tickAmount: 3,
                    categories: this.category,
                    type: 'category',
                    tooltip: {
                        formatter: function(val, {w}) {
                            return w.globals.categoryLabels[val-1]
                        }
                    }
                },
                yaxis: this.yaxis,
                tooltip:{
                    //custom: function({series, seriesIndex, dataPointIndex, w}) {
                    //    return '<div class="arrow_box">' +
                    //    '<span>' + series[seriesIndex][dataPointIndex] + '</span>' +
                    //    '</div>'
                    //}
                    x: {
                        formatter: (value, {w}) => {
                            return '<img src="'+this.imageload('champion/' + w.globals.seriesNames[0][value-1]+'.png')+'" class="v-avatar" width="40px" >'
                        }
                    },
                    y:{
                        title: {
                            formatter: () => {
                                return this.yaxis.title.text
                            },
                        },
                    }
                }
            };
        },
    },
    methods:{
        imageload(URL){
            try{
                return require('@/assets/images/'+ URL);
            }catch{
                return require('@/assets/images/error.png');
            }
        },
    }
}
</script>

<style>
</style>