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

export default {
  name: 'Index',

  components: {
    IndexLogo,
    IndexSearchBar,
    IndexIntro,
  },

  data: () => ({
    //
  }),
};
</script>
