<template>
  <v-container class="full-height">
    <v-row class="align-center full-height">
      <!-- for 문 돌려서 3개 뽑으시면 됩니다. col에서 돌리세요 아래 3개는 디자인 예시입니다. -->
      <v-col>
        <v-row class="center">
          <v-col>
            <img class="icon mdi" :src="require(`@/assets/images/error.png`)" alt="temporarily">

          </v-col>
        </v-row>
        <v-row class="center">
          <v-col>
            3승 3패
          </v-col>
        </v-row>
      </v-col>


      <v-col>
        <v-row class="center">
          <v-col>
            <img class="icon mdi" :src="require(`@/assets/images/error.png`)" alt="temporarily">

          </v-col>
        </v-row>
        <v-row class="center">
          <v-col>
            3승 3패
          </v-col>
        </v-row>
      </v-col>
      <v-col>
        <v-row class="center">
          <v-col>
            <img class="icon mdi" :src="require(`@/assets/images/error.png`)" alt="temporarily">

          </v-col>
        </v-row>
        <v-row class="center">
          <v-col>
            3승 3패
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

.center {
  text-align: center;
}

.full-height {
  height: 100%;
}



</style>