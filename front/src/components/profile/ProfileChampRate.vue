<template>
  <v-container class="full-height align-content-center">
    <!-- for 문 돌려서 3개 뽑으시면 됩니다. col에서 돌리세요 아래 3개는 디자인 예시입니다. -->
    <v-row class="full-height">
      <v-col class="align-self-center">

        <v-row class="justify-space-around align-center">
          <v-col cols=3>

            <img class="icon small d-flex" :src="require(`@/assets/images/error.png`)" alt="temporarily">

          </v-col>
          <v-col cols=4>
            <div>
              1승 1패
            </div>  
          </v-col>

        </v-row>
        
        <v-row class="justify-space-around align-center">
          <v-col cols=3>

            <img class="icon small d-flex" :src="require(`@/assets/images/error.png`)" alt="temporarily">

          </v-col>
          <v-col cols=4>
            <div>
              1승 1패
            </div>  
          </v-col>

        </v-row>
        <v-row class="justify-space-around align-center">
          <v-col cols=3>

            <img class="icon small d-flex" :src="require(`@/assets/images/error.png`)" alt="temporarily">

          </v-col>
          <v-col cols=4>
            <div>
              1승 1패
            </div>  
          </v-col>

        </v-row>
      </v-col>
    </v-row>



  </v-container>

</template>

<script>
export default {
  name: 'ProfileChampRate',
  data() {
    return {
      
    }
  }


}
</script>

<style scoped>

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
  height: 3rem;
}

.center {
  text-align: center;
}

.full-height {
  height: 100%;
}



</style>