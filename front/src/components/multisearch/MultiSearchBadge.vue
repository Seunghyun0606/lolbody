<template>  
    <span :class="[ win ? 'win-badge' : 'fail-badge' ]">{{ badge }}</span>
</template>

<script>
export default {
  name: "MultiSearchBadge",
  props: {
    badges: Object,
  },
  data() {
    return {
      win: true,
    }
  },
  computed: {
    badge() {
      if ( this.badges.contWins > 0) {
        return this.badges.contWins + "연승중"
      }
      else if ( this.badges.contFails > 0 ) {
        return this.badges.contFails + "연패중"
      }
      return ""
    },
  },
  methods: {
    isWin() {
      if ( this.badges.contWins > 0 ) {
        this.win = true
      }
      else if ( this.badges.contFails > 0 ) {
        this.win = false
      }
    }
  },
  created() {
    this.isWin()
  }

  
}
</script>

<style>


.win-badge {
  margin-right: 5px;
  padding: 2px 5px 2px;
  border: 1px solid #e5f4ff;
  background-color: #e5f4ff;
  color: blue;
  border-radius: 10%;
  width: 20px;
  font-size: 10px;
  font-weight: 700;
}
.fail-badge {
  margin-right: 5px;
  padding: 2px 5px 2px;
  border: 1px solid #ffe5e5;
  background-color: #ffe5e5;
  color: red;
  border-radius: 10%;
  width: 20px;
  font-size: 10px;
  font-weight: 700;
}

</style>