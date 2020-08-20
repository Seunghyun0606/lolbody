<template>
    <apexchart type="donut" width="90" height="90" :options="chartOptions" :series="series"></apexchart>
</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
    name: 'ProfilePie',
    components: {
        apexchart
    },
    props: ['KDA'],
    computed:{
        series(){
            if(this.KDA == null)
                return [0, 0, 0];
            return this.KDA;
        },
        chartOptions() {
            return{
                chart: {
                    type: 'donut',
                    toolbar: {
                        show: false,
                    }, 
                    animations: {
                        enabled: false,
                    },
                    offsetY: -8
                },
                plotOptions: {
                    pie: {
                        donut: {
                            size: '60%',
                            labels: {
                                show: true,
                                name: {
                                    show: false
                                },
                                total: {
                                    showAlways: true,
                                    show: true,
                                    fontSize: '10px',
                                    formatter: function () {
                                        return 'KDA';
                                    }
                                    //formatter: function (w) {
                                    //    return w.globals.series[0]+'/'+w.globals.series[1]+'/'+w.globals.series[2]
                                    //}
                                },
                                value: {
                                    show: true,
                                    fontSize: '12px',
                                    offsetY: 5,
                                    fontWeight: 600,
                                }
                            }
                        }
                    }
                },
                labels: ["킬", "데스", "어시스트"],
                colors: ['#6799FF', '#D30101', '#4374D9'],
                dataLabels: {
                    enabled: false,
                    formatter: function (val, {seriesIndex, w}) {
                        return w.globals.series[seriesIndex];
                    }
                },
                legend:{
                    show: false
                }
            }
        },
    }
}
</script>

<style>

</style>