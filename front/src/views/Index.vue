<template>
  <div class="index">
      <IndexLogo />
      <ProfileGameHistory/>
      <IndexSearchBar />
      <IndexIntro />
      <input v-model="userName" style="border: 1px solid black;" type="text" @keyup.enter="test(userName)">
  </div>
</template>

<script>
// @ is an alias to /src
import IndexLogo from '@/components/index/IndexLogo.vue';
import IndexSearchBar from '@/components/index/IndexSearchBar.vue';
import IndexIntro from '@/components/index/IndexIntro.vue';
import ProfileGameHistory from '@/components/profile/ProfileGameHistory'

import { mapActions } from 'vuex'

export default {
  name: 'Index',

  components: {
    IndexLogo,
    IndexSearchBar,
    IndexIntro,
    ProfileGameHistory,
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
