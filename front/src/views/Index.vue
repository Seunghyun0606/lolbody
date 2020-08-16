<template>
  <v-container class="index">
    <v-row class="index-row" justify="center" align="center">
      <IndexLogo />
      <IndexSearchBar />
      <IndexIntro />

    </v-row>
  </v-container>
</template>

<script>
// @ is an alias to /src
import IndexLogo from '@/components/index/IndexLogo.vue';
import IndexSearchBar from '@/components/index/IndexSearchBar.vue';
import IndexIntro from '@/components/index/IndexIntro.vue';

import { mapActions } from 'vuex'
import { mapState } from 'vuex'

export default {
  name: 'Index',

  components: {
    IndexLogo,
    IndexSearchBar,
    IndexIntro,
  },
  computed: {
    ...mapState(['isIndex'])
  },

  methods: {
    ...mapActions(["getProfileDatas"]),

    test(){
        this.getProfileDatas(this.userName);
        this.$router.push('/Profile');
    }
  },

  data() {
    return {
      userName: '',
    }
  },
  created() {
    this.$store.commit('toggleNavSearch', true)
  }
};


  // data() {
  //   return {
  //     test: [1, 2, 3],
  //     indexGraphSeries: {
  //       idx: 0,
  //       pre: [
  //         [60, 50, 45],
  //         [45, 70, 80],
  //         [80, 45, 60],
  //       ],
  //       post: [
  //         [50, 90, 70],
  //         [40, 60, 50],
  //         [80, 75, 40],
  //       ]
  //     },
  //     options: {
  //       chart: {
  //         id: 'vuechart-example',
  //         toolbar: {
  //           show: false,
  //         },
  //       },
  //       legend: {
  //         show: false,
  //       },
  //       xaxis: {
  //         categories: ['공격력', '안정성', '영향력']
  //       },
  //       yaxis: {
  //         show: false,
  //         tickAmount: 5,
  //         min: 0,
  //         max: 100,
  //       },
  //       markers: {
  //         size: 3
  //       }
  //     },
  //     series: [{
  //       name: '초반',
  //       data: [10, 60, 90]
  //     },
  //     {
  //       name: '후반',
  //       data: [50, 40, 60]
  //     }]
  //   }
  // },
  // methods: {
  //   changeIndexGraphSeries() {
  //     if (this.indexGraphSeries.idx >= 2) {
  //       this.indexGraphSeries.idx = 0
  //     } else {
  //       this.indexGraphSeries.idx += 1
  //     }
  //     this.series = [{
  //       data: this.indexGraphSeries.pre[this.indexGraphSeries.idx]
  //       },
  //       {
  //         data: this.indexGraphSeries.post[this.indexGraphSeries.idx]
  //     }]
  //   }
  // },
  // computed: {
  //   onlyGraph() {
  //     return document.querySelectorAll('g > g')[3]
  //   },
  //   onlyGraph2() {
  //     return document.getElementsByClassName('apexcharts-radar-series')[0]
  //   }
  // },
  // created() {
    // setInterval(() => {
    //   this.changeIndexGraphSeries();
    // }, 3000)
  // },
</script>

<style>

.index > .index-row {
  width: 1000px;
  margin: auto;
  
}

</style>