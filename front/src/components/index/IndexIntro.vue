<template>
  <v-container>
    <v-row
      class="text-center"
      justify="center">
      <v-col
        cols="12"
        md="4"
        v-for="n in 3"
        :key="n">
        <v-img
          :src="require('@/assets/logo.svg')"
          class="my-3"
          contain
          height="200"
        />
        설명{{n}}
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "IndexIntro",

}
</script>

<style>

</style>