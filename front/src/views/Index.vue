<template>
  <div class="index">
      <IndexLogo />
      <IndexSearchBar />
      <IndexIntro />
  </div>
</template>

<script>
// @ is an alias to /src
import IndexLogo from '@/components/index/IndexLogo.vue';
import IndexSearchBar from '@/components/index/IndexSearchBar.vue';
import IndexIntro from '@/components/index/IndexIntro.vue';

import { mapActions } from 'vuex'

export default {
  name: 'Index',

  components: {
    IndexLogo,
    IndexSearchBar,
    IndexIntro,
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
      userName: ''
    }
  }
};
</script>
