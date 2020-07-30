<template>

  <v-text-field

    label="Solo"
    placeholder="Placeholder"
    solo
    v-model="userName"
    @keyup.enter="getMultiSearchDatas(userName), getUserDatas(userName)"
  ></v-text-field>
</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'


export default {
  name: "MultiSearchBar",
  data() {
    return {
      userName: '',
    }
  },
  copmuted: {
    ...mapState([])
  },
  methods: {
    ...mapActions([
      "getMultiSearchDatas",
      "getUserDatas",
    ]),
  },
}
</script>

<style scoped>

</style>