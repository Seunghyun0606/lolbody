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

import { mapActions } from 'vuex'
import { mapState } from 'vuex'

export default {
  name: 'App',
  components: {
    MultiSearch,
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
