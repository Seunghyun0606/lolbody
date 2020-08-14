<template>
  <div>
    <div class="d-inline-block">
      <v-card class="ma-1 bg_card float-left" width="200px" height="160px" outlined>

        <ProfileTotalWinRateChart/>
      </v-card>
    </div>

    <div class="d-inline-block">
      <v-card class="ma-1 bg_card float-left" width="240px" height="160px" outlined>

          <ProfileEachWinRateChart/>




      </v-card>
    </div>
    <div class="d-inline-block">
      <v-card class="ma-1 bg_card float-right" width="201px" height="160px" outlined>

        <ProfileChampRate/>

      </v-card>
    </div>
  </div>


</template>

<script>

import ProfileChampRate from "@/components/profile/ProfileChampRate"
// import ProfileWinRate from "@/components/profile/ProfileWinRate"

import ProfileEachWinRateChart from "@/components/profile/ProfileEachWinRateChart"
import ProfileTotalWinRateChart from "@/components/profile/ProfileTotalWinRateChart"


export default {
  name: "Prac",
  components: {
    ProfileChampRate,
    // ProfileWinRate,
    ProfileEachWinRateChart,
    ProfileTotalWinRateChart,
  },

  data() {
    return {

    }
  }

}
</script>

<style>

</style>