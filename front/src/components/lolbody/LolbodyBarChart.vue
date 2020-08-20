<template>
  <BarChart type="bar" height="450" :options="chartOptions" :series="series"></BarChart>
</template>

<script>
import BarChart from "vue-apexcharts"

export default {
  name: 'LolbodyBarChart',
  components: {
        BarChart,
  },
  props: ['barSeries', 'othersSeries'],
  data() {
    return {
    }
  },
    computed: {
        series() {
            let myList = [];
            for ( let data in this.barSeries ) {
                // totalHealPerMIn은 값이 없어서 뺍니다.
                if (data === 'totalHealPerMin') 
                    continue
                myList.push(Math.round(this.barSeries[data]*100))
            }

            let otherList = [];
            for ( let data in this.othersSeries[0] ) 
                otherList.push(Math.round(this.othersSeries[0][data]*100))
            
            return [{
                    name: this.othersSeries[1],
                    data: otherList
                },
                {
                    name: '나',
                    data: myList
                }]
        },
        chartOptions(){
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
                    dataLabels: {
                        position: 'top',
                    },
                }
                },
                dataLabels: {
                    enabled: false,
                    offsetX: 17,
                    style: {
                        fontSize: '7px',
                        colors: ['#000']
                    }
                },
                stroke: {
                    show: true,
                    width: 1,
                    colors: ['#fff']
                },
                xaxis: {
                    categories: [
                        '분 당 챔피언피해량',
                        '분 당 오브젝트피해량',
                        '분 당 시야 점수',
                        '분 당 받은 피해량',
                        '분 당 총 CS 킬',
                        'KDA 대비 킬 비율',
                        'KDA 대비 데스 비율',
                        '분 당 킬/어시스트',
                        '분 당 킬량',
                        '분 당 데스량',
                        '분 당 어시스트량',
                        '분 당 피해 흡수량',
                        '분 당 타워 피해량',
                        '분 당 속박가한 시간',
                        '분 당 CS량',
                        '분 당 군중제어 시간',
                        '분 당 와드 구입량',
                        '분 당 카정량',
                        '분 당 와드 설치량',
                        '분 당 와드 제거량',
                    ],
                }
            }
        }
    },
    methods: {
        

    },
  
}

</script>

<style>

</style>