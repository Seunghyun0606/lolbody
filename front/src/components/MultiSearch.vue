<template>
  <v-data-table
    :headers="headers"
    :items="multiserach_infos"
    :items-per-page="5"
    class="elevation-1"
    hide-default-footer
  ></v-data-table>
</template>

<script>
import { mapState } from 'vuex'


export default {
  name: "MultiSearch",
  computed: {
    ...mapState(['headers', 'multiserach_infos'])
  },
  data () {
    return {
    }
  },
}
</script>