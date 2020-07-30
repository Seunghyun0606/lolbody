<template>
  <v-container class="index">
    <v-row class="index-row" justify="center" align="center">
      <IndexLogo />
      <IndexSearchBar />
      <IndexIntro />

    </v-row>
  </v-container>
</template>

<script>
// @ is an alias to /src
import IndexLogo from '@/components/index/IndexLogo.vue';
import IndexSearchBar from '@/components/index/IndexSearchBar.vue';
import IndexIntro from '@/components/index/IndexIntro.vue';

import { mapActions } from 'vuex'
import { mapState } from 'vuex'

export default {
  name: 'Index',

  components: {
    IndexLogo,
    IndexSearchBar,
    IndexIntro,
  },
  computed: {
    ...mapState(['isIndex'])
  },

  methods: {
    ...mapActions(["getProfileDatas"]),

    test(){
        this.getProfileDatas(this.userName);
        this.$router.push('/Profile');
    }
  },

  data() {
    return {
      userName: '',
    }
  },
  created() {
    this.$store.commit('toggleNavSearch', true)
  }
};
</script>

<style>

.index > .index-row {
  width: 1000px;
  margin: auto;
  
}

</style>