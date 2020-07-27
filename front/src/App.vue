<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
    </v-app-bar>

    <v-main>
      <router-view></router-view>
      <MultiSearch/>
      <Profile/>
    </v-main>

  </v-app>
</template>

<script>
import MultiSearch from './components/MultiSearch';
import Profile from "./components/Profile.vue";


export default {
  name: 'App',
  components: {
    MultiSearch,
    Profile,
  },

  data () {
    return {
    }
  },
}
</script>

<style scoped>

</style>
