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
  data() {
    return {
      // series: [
      //   {
      //     name: '동 티어대비',
      //     data: []
      //   },
      //   {
      //     name: '나',
      //     data: []
      //   },
      // ],
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
            'totalDamageDealtToChampionsPerMin',
            'damageDealtToObjectivesPerMin',
            'visionScorePerMin',
            'totalDamageTakenPerMin',
            'totalMinionsKilledPerMin',
            'killsRatio',
            'deathsRatio',
            'killAssistPerMin',
            'killsPerMin',
            'deathsPerMin',
            'assistsPerMin',
            'totalHealPerMin',
            'damageSelfMitigatedPerMin',
            'damageDealtToTurretsPerMin',
            'timeCCingOthersPerMin',
            'neutralMinionsKilledPerMin',
            'totalTimeCrowdControlDealtPerMin',
            'visionWardsBoughtInGamePerMin',
            'neutralMinionsKilledEnemyJunglePerMin',
            'wardsPlacedPerMin',
            'wardsKilledPerMin',
          ],
        }
      },
    }
  },
  computed: {
      series: {
        get() {
          return [
              {
                name: '동 티어대비',
                data: []
              },
              {
                name: '나',
                data: []
              },
            ]
        },
        set(){
          
        }
      },
      // chartOptions() {
      //   return {
      //     chart: {
      //       type: 'bar',
      //       toolbar: {
      //         show: false,
      //       },
      //     },
      //     plotOptions: {
      //       bar: {
      //         horizontal: true,
      //         dataLabels: {
      //           position: 'top',
      //         },
      //       }
      //     },
      //     dataLabels: {
      //       enabled: false,
      //       offsetX: 17,
      //       style: {
      //         fontSize: '7px',
      //         colors: ['#000']
      //       }
      //     },
      //     stroke: {
      //       show: true,
      //       width: 1,
      //       colors: ['#fff']
      //     },
      //     xaxis: {
      //       categories: [
      //         'totalDamageDealtToChampionsPerMin',
      //         'damageDealtToObjectivesPerMin',
      //         'visionScorePerMin',
      //         'totalDamageTakenPerMin',
      //         'totalMinionsKilledPerMin',
      //         'killsRatio',
      //         'deathsRatio',
      //         'killAssistPerMin',
      //         'killsPerMin',
      //         'deathsPerMin',
      //         'assistsPerMin',
      //         'totalHealPerMin',
      //         'damageSelfMitigatedPerMin',
      //         'damageDealtToTurretsPerMin',
      //         'timeCCingOthersPerMin',
      //         'neutralMinionsKilledPerMin',
      //         'totalTimeCrowdControlDealtPerMin',
      //         'visionWardsBoughtInGamePerMin',
      //         'neutralMinionsKilledEnemyJunglePerMin',
      //         'wardsPlacedPerMin',
      //         'wardsKilledPerMin',
      //       ],
      //     },
      //   }
      // },
  },
  props: {
    getMyData: Object,
    getOtherData: [Array, Object]
    // barSeries: Array,
  },
  watch: {
    getMyData: {
      deep: true,
      immediate: true,
      handler() {
        this.init(this.getMyData)
      }
      
    },
    getOtherData: {
      deep: true,
      immediate: true,
      handler() {

        this.other(this.getOtherData[0])
      },
    },
    series: {
      deep: true,
      immediate: true,
      handler() {
        this.check(this.series)
      }
    }
  },

  methods: {
    check(datas) {
      this.series = datas

    },
    init(datas) {
      var myList = []
      var columnName = []

      for ( var data in datas ) {
        myList.push(Math.round(datas[data]*100))
        columnName.push(data)

      }
      this.series[1].data = myList
      this.chartOptions.xaxis.categories = columnName
    },
    other(datas) {
      var compList = []

      for ( var data in datas ) {
        compList.push(Math.round(datas[data]*100))

      }
      this.series[0].data = compList
    },

  },
  
}

</script>

<style>

</style>