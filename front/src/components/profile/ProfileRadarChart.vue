<template>

  <apexchart class='move-apexchart' type="radar" width="350" :options="chartOptions" :series="series.series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'
import { mapState } from 'vuex'

export default {
    name: 'ProfileRadarChart',
    components: {
        apexchart
    },
    computed: {
        ...mapState([
            'profileRadarChartOption',
        ]),
        
        chartOptions() {
            return {
                chart: {
                    toolbar: {
                        show: false,
                    },
                legend:{
                    position: 'top'
                },
                },
                xaxis: {
                    categories: ['공격력', '안정성', '영향력'],
                },
                yaxis: {
                    show: false,
                    tickAmount: 5,
                    min: 0,
                    max: 100,
                },
                legend: {
                    position: 'top',
                },
                markers: {
                    size: 3
                }
            };
        },
        series(){
            return {
                series: [{
                    name: this.profileRadarChartOption.series[0].name,
                    data: this.profileRadarChartOption.series[0].data
                },
                {
                    name: this.profileRadarChartOption.series[1].name,
                    data: this.profileRadarChartOption.series[1].data
                }],
            };
        }
    }
}
</script>

<style scoped>

.move-apexchart {
  position: relative;
  left: -70px;
}

</style>