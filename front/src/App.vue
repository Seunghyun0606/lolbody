<template>
  <v-app>
    <v-app-bar app color="primary" dense dark flat>
      <v-tabs align-with-title>
        <v-tab router-link to="/">Main</v-tab>
        <v-tab router-link to="/Profile">Profile</v-tab>
        <v-tab router-link to="/MultiSearch">Multi Search</v-tab>
      </v-tabs>
      <v-spacer></v-spacer>
      <NavSearchBar/>
    </v-app-bar>

    <v-main class='mt-16 pt-0'>
      
      <router-view></router-view>

    </v-main>

  </v-app>
</template>

<script>
import NavSearchBar from '@/components/index/NavSearchBar.vue'

export default {
  name: 'App',
  components: {
    NavSearchBar,
  },

  data () {
    return {
    }
  },
}
</script>

<style scoped>

</style>
