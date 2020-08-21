<template>

  <apexchart class="move-apexchart" height="270" :options="options" :series="series"></apexchart>

</template>

<script>
import apexchart from 'vue-apexcharts'
// import { mapGetters } from 'vuex'

export default {
    name: 'LolbodyRadarChart',
    components: {
        apexchart
    },
    props: ['radarData'],
    computed: {
        series(){
            let changeSeriesData = [];
            for ( let value in this.radarData ) 
                changeSeriesData.push(Math.round(this.radarData[value]*100));
            
            return [{
                name: 'Match Grade',
                data: changeSeriesData
            }];
        },
        options() {
            return {
                chart: {
                    type: "radar",
                    toolbar: {
                        show: false,
                    },
                    height: 270,
                    offsetY: 0,
                    offsetX: 20
                },
                legend: {
                    show: false,
                },
                xaxis: {
                    categories: ['공격력', '안정성', '영향력']
                },
                yaxis: {
                    show: false,
                    tickAmount: 5,
                    min: 0,
                    max: 100,
                },
                markers: {
                    size: 3
                },
            }
            
        }
    },
}
</script>

<style scoped>

</style>