<template>
  <v-container class="full-height">

    <v-row class="justify-space-around full-height">

      <v-col cols=5 class="full-height">
        <v-row style="height: 75%;">
          <v-col class="center align-self-end">

            <slot></slot>

          </v-col>
        </v-row>

        <v-row class="justify-center" style="height: 25%;">
          10전 5승 5패
        </v-row>
      </v-col>

      <v-col cols=5 class="center align-self-center">
        <v-row>
          솔랭: 3승 3패
        </v-row>
        <v-row>
          자랭: 1승 1패
        </v-row>
        <v-row>
          일반: 1승 1패
        </v-row>
        <v-row>
          칼바람: 1승 1패
        </v-row>
      </v-col>

    </v-row>



  </v-container>  

</template>

<script>

// import ProfileDonutChart from "@/components/profile/ProfileDonutChart"
// import ProfileDonutChart2 from "@/components/profile/ProfileDonutChart2"

export default {
  name: 'ProfileWinRate',
  components: {
    // ProfileDonutChart,
    // ProfileDonutChart2,
  
  },

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
  height: 5em;
}

.small {
  height: 2em;
}

.center {
  text-align: center;
}

.full-height {
  height: 100%;
}

</style>