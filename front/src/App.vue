<template>
  <v-app>
    <v-content>
      <Profile />
    </v-content>
  </v-app>
</template>

<script>
import Profile from "./components/Profile.vue";
export default {
  name: 'App',
  components: {
    Profile,
  },

  data: () => ({
    //
  }),
};
</script>
