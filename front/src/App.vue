<template>
  <v-app>
    <v-app-bar app color="primary" dense dark flat>
      <template>
        <v-tabs align-with-title>
          <v-tab router-link to="/">Main</v-tab>
          <v-tab router-link to="/Profile">Profile</v-tab>
          <v-tab router-link to="/MultiSearch">Multi Search</v-tab>
        </v-tabs>
      </template>
    </v-app-bar>

    <v-main class='mt-16'>
      
      <router-view></router-view>

    </v-main>

  </v-app>
</template>

<script>

export default {
  name: 'App',
  components: {
  },

  data () {
    return {
    }
  },
}
</script>

<style scoped>

</style>
