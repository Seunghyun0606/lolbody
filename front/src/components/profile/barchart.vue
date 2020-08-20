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
    props: ['team', 'selfName', 'win'],
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
                result[0].data.push(Math.round(this.team[t].radar.aggressiveness*100));
                result[1].data.push(Math.round(this.team[t].radar.influence*100));
                result[2].data.push(Math.round(this.team[t].radar.stability*100));
            }
            return result;
        },
        chartOptions(){
            let result = {
                chart: {
                    type: 'bar',
                    toolbar: {
                        show: false,
                    }, 
                    animations: {
                        enabled: false,
                    }
                },
                annotations: {
                    yaxis: [],
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
                    categories: [0, 1, 2, 3, 4],
                    tickAmount: 5,
                    max: 100
                },
                legend:{
                    show: true
                },
                tooltip: {
                    x: {
                        show: false,
                    },
                }
            };
            for(let t in this.team){
                if(this.team[t].name == this.selfName){
                    if(t == 4){
                        result.annotations.yaxis.push({
                            y: parseInt(t)-1,
                            y2: parseInt(t),
                            fillColor: this.win ? '#0054FF' : '#F00000',
                            opacity: 0.1,
                            offsetY: 14,  
                            yAxisIndex: parseInt(t),
                        })
                    }else{
                        result.annotations.yaxis.push({
                            y: parseInt(t),
                            y2: parseInt(t)+1,
                            fillColor: this.win ? '#0054FF' : '#F00000',
                            opacity: 0.1,
                            offsetY: -17,  
                            yAxisIndex: parseInt(t),
                        })
                    }
                }
            }
            return result;
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