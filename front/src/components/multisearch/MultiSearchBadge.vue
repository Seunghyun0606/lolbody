<template>  
    <span :class="[ win ? 'win-badge' : 'fail-badge' , { dis: disp } ]">{{ badge }}</span>
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
      disp: true,
    }
  },
  computed: {
    badge() {
      if ( this.badges.contWins > 1) {
        // this.disp = true
        return this.badges.contWins + "연승중"
      }
      else if ( this.badges.contFails > 1 ) {
        // this.disp = true
        return this.badges.contFails + "연패중"
      }
      return ""
    },
  },
  methods: {
    isWin() {
      if ( this.badges.contWins > 1 ) {
        this.win = true
        this.disp = false
      }
      else if ( this.badges.contFails > 1 ) {
        this.win = false
        this.disp = false
      }
    }
  },
  created() {
    this.isWin()
  }

  
}
</script>

<style>

.dis {
  display: none !important;
}


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