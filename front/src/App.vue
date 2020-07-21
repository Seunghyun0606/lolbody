<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
    </v-app-bar>

    <v-content>
      <v-container fluid>
        <v-row justify="center" align="center" style="height:200px;">
          <v-col></v-col>
          <v-col>
            <v-text-field v-model="userId" label="test"></v-text-field>
            <v-btn @click="getApiData(userId)">버튼</v-btn>
            <p> {{ apiData.tier }}</p>

          </v-col>
          <v-col></v-col>
        </v-row>

        <v-row>
          <v-col cols=2 class="back-grey">
            <v-row>
              <RadarChart/>
            </v-row>
            다각형 + 유저성향 + 배지?

          </v-col>
          <v-col>
            <MultiSearch/>

          </v-col>
          <v-col cols=2 class="back-grey">
            여기에 무엇 배치?

          </v-col>
        </v-row>

        <v-row justify="center" align="center" style="height:200px;">
          <div class="back-grey">광고?</div>
        </v-row>

      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import MultiSearch from './components/MultiSearch';
import RadarChart from './components/RadarChart';

import { mapActions } from 'vuex'
import { mapState } from 'vuex'

export default {
  name: 'App',
  components: {
    MultiSearch,
    RadarChart,
  },

  data () {
    return {
      userId: "",
    }
  },
  computed: {
    ...mapState(['apiData'])

  },
  methods: {
    ...mapActions(['getApiData'])
    
  },
  created() {

  }
};
</script>

<style scoped>

.back-grey {
  background-color: grey;
}

</style>
