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
  data() {
    return {        
      series: [{
        name: '공격성',
        data: [44, 55, 57, 56, 61, 58, 63, 60, 66]
      }, {
        name: '안정성',
        data: [76, 85, 101, 98, 87, 105, 91, 114, 94]
      }, {
        name: '영향력',
        data: [35, 41, 36, 26, 45, 48, 52, 53, 41]
      }],
      chartOptions: {
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
          categories: [],
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

        // yaxis: {
        //   title: {
        //     text: '$ (thousands)'
        //   }
        // },
        fill: {
          opacity: 1
        },
        tooltip: {
          x: {
            formatter: (value, {w}) => {
              // console.log(w)
              // console.log(value)  
              
              // return '<img src="'+this.imageload('champion/Orianna.png')+'" class="v-avatar" width="40px" >'
              // // return w
              return '<img src="'+this.imageload('champion/' + w.globals.seriesNames[0][value-1]+'.png')+'" class="v-avatar" width="40px" >'
            }
          },
          y: {
            formatter: function (val) {
              return val + " 점"
            }
          }
        }
        
        
      },
    }
  },
  watch: {
    radarData: {
      deep: true,
      immediate: true,
      handler() {
        this.changeData(this.radarData)
      }

    }
  },
  computed: {
    // ...mapGetters(['getLolbodyData'])
  },
  props: {
    radarData: Array,
  },
  methods: {
    imageload(URL){
      try{
          return require('@/assets/images/'+ URL);
      }catch{
          return require('@/assets/images/error.png');
      }
    },
    changeData(datas) {
      var agg = []
      var stab = []
      var infl = []
      var categories = []

      for ( var data of datas ) {
        for ( var value in data.radarReference ) {
          if ( value === "aggressiveness" ) {
            agg.push(Math.round(data.radarReference[value]*100))
          }
          else if ( value === "stability" ) {
            stab.push(Math.round(data.radarReference[value]*100))
          }
          else {
            infl.push(Math.round(data.radarReference[value]*100))
          }
        }
        categories.push(data.timestamp)
      }
      this.series[0].data = agg.reverse()
      this.series[1].data = infl.reverse()
      this.series[2].data = stab.reverse()
      this.chartOptions.xaxis.categories = categories.reverse()

    }
  }

}
</script>

<style scoped>



</style>