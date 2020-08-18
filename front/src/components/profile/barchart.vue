<template>
<div id="chart">
    <apexchart type="bar" width="300px" height="250px" :options="chartOptions" :series="series"></apexchart>
</div>
</template>

<script>
import apexchart from 'vue-apexcharts'

export default {
    name: 'barChart',
    components: {
        apexchart
    },
    props: ['team'],
    computed:{
        series(){
            let result = [{
                    name: '공격력', 
                    data: []
                }, 
                {
                    name: '안정성', 
                    data: []
                }, 
                {
                    name: '영향력', 
                    data: []
                }];
            for(let t in this.team){
                result[0].data.push(Math.round(this.team[t].radar.aggressiveness*100)/100);
                result[1].data.push(Math.round(this.team[t].radar.influence*100)/100);
                result[2].data.push(Math.round(this.team[t].radar.stability*100)/100);
            }
            return result;
        },
        chartOptions(){
            let result = [];
            for(let t in this.team){
                result.push(this.team[t].champ);
            }
            return {
                chart: {
                    type: 'bar',
                    toolbar: {
                        show: false,
                    },
                },
                plotOptions: {
                    bar: {
                        horizontal: true,
                        columnWidth: '70%',
                        barHeight: '80%',
                        dataLabels: {
                            position: 'top',
                        }
                    }
                },
                dataLabels: {
                    enabled: true,
                    offsetX: 20,
                    offsetY: 1,
                    style: {
                        fontSize: '9px',
                        colors: ['#000']
                    }
                },
                stroke: {
                    show: true,
                    width: 1,
                    colors: ['#fff']
                },
                xaxis: {
                    type: 'category',
                    categories: [0, 0, 0, 0, 0],
                    
                },
                //yaxis:{
                //    labels: {
                //        formatter: function(val) {
                //            return val;
                //        }
                //    }
                //},
                legend:{
                    show: true
                },
                tooltip: {
                    x: {
                        show: false,
                    },
                }
            }
        }
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