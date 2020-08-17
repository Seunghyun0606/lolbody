<template>
  <v-container class="full-height align-content-center">
    <v-row class="full-height">
      <v-col class="align-self-center mt-3">

        <!-- row에서 for 문 돌려서 3개 챔피언 뽑으시고, 챔피언 고유값이나 for문의 index값으로 hover 이벤트를 구분해주세요 아래 3개는 디자인 예시입니다. -->
        <v-row class="justify-space-around" v-for="(val, idx) in champs" :key="val.name + idx">
          <v-col cols=3 @mouseenter="mouseOn(idx)" @mouseleave="mouseOn(idx)">
            <img :src="imageload('champion/'+val.name+'.png')" style="position: absolute; z-index: 2" :class="[{ dis: init[idx] }, 'icon', 'small', 'd-flex']" alt="temporarily">
            <ProfileChampWinRateChart :win="val.win" :lose="val.lose"/>
          </v-col>
          <v-col cols=4 class="mt-2">
            <div>
              {{ val.win }}승 {{ val.lose }}패
            </div>  
          </v-col>
        </v-row>





        <!-- 살짝 문제가 있어서 꼼수 쓴 부분입니다. 무시하시면됩니다. -->
        <v-row class="justify-space-around" >
          <v-col cols=3 @mouseenter="mouseOn()" @mouseleave="mouseOn()">
            <!-- <img style="position: absolute; z-index: 5" :class="[{ dis: init1 }, 'icon', 'small', 'd-flex']" :src="require(`@/assets/images/error.png`)" alt="temporarily"> -->
            <!-- <ProfileChampWinRateChart/> -->
            <div :class="[{ dis: init1 }]"></div>
          </v-col>
          <!-- <v-col cols=4 class="mt-2">
            <div>
              1승 1패
            </div>  
          </v-col> -->
        </v-row>

 
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import ProfileChampWinRateChart from "@/components/profile/ProfileChampWinRateChart"

export default {
  name: 'ProfileChampRate',
  components: {
    ProfileChampWinRateChart,
  },
  data() {
    return {
        init: [false, false, false],
      init1: false,
      mouseOver: {
        display: "none !important;",
      }

      
    }
  },
  computed:{
      champs(){
          return this.$store.getters.getProfileChampRate;
      }
  },
  // computed: {
  //   init2() {
  //     return this.init[0]
  //   }
  // },
  // watch: {
  //   init: {
  //     deep: true,
  //     handler: function() {
  //       console.log('a')
  //     }
  //   }
  // },
  methods: {
    mouseOn(val) {
    //  console.log(val)

    //  console.log(this.init[val])
      this.init[val] = !this.init[val]
    //  console.log(this.init[val])

      this.init1 = !this.init1

    },
    imageload(URL){
        try{
            return require('@/assets/images/'+ URL);
        }catch{
            return require('@/assets/images/error.png');
        }
    },
  },


}
</script>

<style scoped>

.dis {
  display: none !important;
}


.container {
  margin: 0;
  padding: 0;
}

.row {
  margin: 0;
  padding: 0;
}

.col {
  margin: 0;
  padding: 0;
}


.icon {
  border-radius: 70%;
  height: 5em;
  border: 0.5px solid white;
}

.big {
  height: 5rem;
}

.mdi {
  height: 4rem;
}
.small {
  height: 2.5rem;
}

.center {
  text-align: center;
}

.full-height {
  height: 100%;
}



</style>