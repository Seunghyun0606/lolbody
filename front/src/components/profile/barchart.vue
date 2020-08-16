<template>
<div id="chart">
    <apexchart type="bar" width="200px" height="430px" :options="chartOptions" :series="series"></apexchart>
</div>
</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
    name: 'barChart',
    components: {
        apexchart
    },
    data() {
        return {
            series: [
                {
                    name: '공격력', 
                    data: [44, 55, 41, 64, 22]
                }, 
                {
                    name: '안정성', 
                    data: [53, 32, 33, 52, 13]
                }, 
                {
                    name: '영향력', 
                    data: [53, 32, 33, 52, 13]
                }
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
                    width: 0,
                    colors: ['#fff']
                },
                xaxis: {
                    categories: [2001, 2002, 2003, 2004, 2005],
                },
                legend:{
                    show: false
                }
            },
        }
    }
    //computed: {
    //    //...mapState([
    //    //    'profileRadarChartOption',
    //    //]),
    //    chartOptions() {
    //        return {
    //            chart: {
    //                type: 'bar',
    //                height: 430
    //            },
    //            plotOptions: {
    //                bar: {
    //                    horizontal: true,
    //                    dataLabels: {
    //                        position: 'top',
    //                    },
    //                }
    //            },
    //            dataLabels: {
    //                enabled: true,
    //                offsetX: -6,
    //                style: {
    //                    fontSize: '12px',
    //                    colors: ['#fff']
    //                }
    //            },
    //            stroke: {
    //                show: true,
    //                width: 1,
    //                colors: ['#fff']
    //            },
    //            xaxis: {
    //                categories: [2001, 2002, 2003, 2004, 2005, 2006, 2007],
    //            },
    //        };
    //    },
    //    series(){
    //        return {
    //            series: [
    //                {
    //                    name: '공격력', 
    //                    data: [44, 55, 41, 64, 22, 43, 21]
    //                }, 
    //                {
    //                    name: '안정성', 
    //                    data: [53, 32, 33, 52, 13, 44, 32]
    //                }, 
    //                {
    //                    name: '영향력', 
    //                    data: [53, 32, 33, 52, 13, 44, 32]
    //                }
    //            ],
    //        };
    //    }
    //}
}
</script>

<style>

</style>