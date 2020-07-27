<template>
  <div class="index">
      <IndexLogo />
      <ProfileGameHistory/>
      <IndexSearchBar />
      <IndexIntro />
  </div>
</template>

<script>
// @ is an alias to /src
import IndexLogo from '@/components/index/IndexLogo.vue';
import IndexSearchBar from '@/components/index/IndexSearchBar.vue';
import IndexIntro from '@/components/index/IndexIntro.vue';
import ProfileGameHistory from '@/components/ProfileGameHistory'

export default {
  name: 'Index',

  components: {
    IndexLogo,
    IndexSearchBar,
    IndexIntro,
    ProfileGameHistory,
  },

  data: () => ({
    //
  }),
};
</script>
