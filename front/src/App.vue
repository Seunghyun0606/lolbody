<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
    </v-app-bar>

    <v-main>
      <MultiSearch/>
    </v-main>
  </v-app>
</template>

<script>
import MultiSearch from './components/MultiSearch';


export default {
  name: 'App',
  components: {
    MultiSearch,
  },

  data () {
    return {
    }
  },

};
</script>

<style scoped>

</style>
