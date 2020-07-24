<template>
  <div class="text-center">
    <v-img
          :src="require('@/assets/lolbody-logo.png')"
          class="my-5"
          contain
          height="150"
        />
    <GraphPractice/>
  </div>
</template>

<script>
import GraphPractice from '@/components/GraphPractice.vue'

export default {
  name: "IndexLogo",
  components: {
    GraphPractice,
  }  
}
</script>

<style>

</style>