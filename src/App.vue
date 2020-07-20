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
          <div class="back-grey">광고?</div>
        </v-row>

        <v-row>
          <v-col cols=2 class="back-grey">
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

export default {
  name: 'App',

  components: {
    MultiSearch,
  },

  data: () => ({
    //
  }),
};
</script>

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
