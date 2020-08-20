<template>

  <ColumnChart type="bar" height="200" :options="chartOptions" :series="series"></ColumnChart>

</template>

<script>
import ColumnChart from 'vue-apexcharts'
// import { mapGetters } from 'vuex'


export default {
  name: 'LolbodyColumnChart',
  components: {
    ColumnChart
  },
  props: ['radarData'],
  data() {
    return {        
      
    }
  },
  computed: {
        series(){
            var agg = [];
            var stab = [];
            var infl = [];

            for ( var data of this.radarData ) {
                for ( var value in data.radarReference ) {
                    if ( value === "aggressiveness" ) {
                        agg.unshift(Math.round(data.radarReference[value]*100))
                    }
                    else if ( value === "stability" ) {
                        stab.unshift(Math.round(data.radarReference[value]*100))
                    }
                    else {
                        infl.unshift(Math.round(data.radarReference[value]*100))
                    }
                }
            }

            return [{
                    name: '공격성',
                    data: agg
                },
                {
                    name: '안정성',
                    data: infl
                },
                {
                    name: '영향력',
                    data: stab
            }]
        },
        chartOptions(){
            let categories = [];
            let champs = [];

            for ( let data of this.radarData ) {
                categories.unshift(data.timestamp);
                champs.unshift(data.name)
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
                        horizontal: false,
                        columnWidth: '55%',
                        // endingShape: 'rounded'
                    },
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    show: true,
                    width: 2,
                    colors: ['transparent']
                },
                xaxis: {
                    categories: categories,
                    labels: {
                        showDuplicates: false,
                        style: {
                            fontSize: '10px',
                        },
                        formatter: function(value) {
                            if (value == undefined)
                                return null;
                            const timestamp =  new Date(parseInt(value));
                            const month = new Date(timestamp).getMonth() + 1 + '월 '
                            const day = new Date(timestamp).getDate() + '일 '
                            return month + day // + hour
                        }
                    },
                },
                fill: {
                    opacity: 1
                },
                tooltip: {
                    x: {
                        formatter: (value, {dataPointIndex}) => {
                            return '<img src="'+this.imageload('champion/' + champs[dataPointIndex]+'.png')+'" class="v-avatar" width="40px" >'
                        }
                    },
                    y: {
                        formatter: function (val) {
                            return val + " 점"
                        }
                    }
                }
            }
        }
  },
  methods: {
    imageload(URL){
      try{
          return require('@/assets/images/'+ URL);
      }catch{
          return require('@/assets/images/error.png');
      }
    }
  }

}
</script>

<style scoped>



</style>