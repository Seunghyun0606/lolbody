<template>
  <div>
    <div class="d-inline-block">
      <v-card class="ma-1 bg_card float-left" width="320.5px" height="160px" outlined>

        <ProfileWinRate>

          <ProfileDonutChart/>

        </ProfileWinRate>

      </v-card>
    </div>

    <div class="d-inline-block">
      <v-card class="ma-1 bg_card float-left" width="320.5px" height="160px" outlined>
        <ProfileWinRate>

          <ProfileDonutChart2/>

        </ProfileWinRate>



      </v-card>
    </div>
    <div class="d-inline-block">
      <v-card class="ma-1 bg_card float-right" width="320.5px" height="160px" outlined>

        <ProfileChampRate/>

      </v-card>
    </div>
  </div>


</template>

<script>

import ProfileChampRate from "@/components/profile/ProfileChampRate"
import ProfileWinRate from "@/components/profile/ProfileWinRate"

import ProfileDonutChart from "@/components/profile/ProfileDonutChart"
import ProfileDonutChart2 from "@/components/profile/ProfileDonutChart2"


export default {
  name: "Prac",
  components: {
    ProfileChampRate,
    ProfileWinRate,
    ProfileDonutChart,
    ProfileDonutChart2,
  },

  data() {
    return {

    }
  }

}
</script>

<style>

</style>