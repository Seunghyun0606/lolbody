<template>
  <div>
    <div class="d-inline-block mr-2" v-for="(badge, idx) in badgeMap" :key="idx+'_badge'">
      <div class="ma-1 pa-1 badge d-inline-block" :class="['num'+badge.tier]">
          <span class="sharp"># </span>
          <span class="badge-content">{{badge.name}}</span>
      </div>
      <span class="multi fs-11">X{{badge.cnt}}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profilebadge',
  //props: ['badge'],
  computed:{
      badgeMap(){
          return this.$store.getters.getBadgeMap
      },
      // tooltip_content(){
      //     return this.badge.description;
      // } 
  }

}

</script>

<style>
.sharp{
    color: #8C8C8C;
    text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
    -moz-text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
    -webkit-text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
}
.multi{
    color: #2B353D;
}
/* .sharp{
    color: #8C8C8C;
    text-shadow: -1px 0 #2B353D, 0 1px #2B353D, 1px 0 #2B353D, 0 -1px #2B353D;
    -moz-text-shadow: -1px 0 #2B353D, 0 1px #2B353D, 1px 0 #2B353D, 0 -1px #2B353D;
    -webkit-text-shadow: -1px 0 #2B353D, 0 1px #2B353D, 1px 0 #2B353D, 0 -1px #2B353D;
} */
.badge-content{
    color: black;
}

.badge {
    border-radius: 4px;
    text-align: center;
}

.basic-badge:hover {
  background-color: rgb(155, 221, 247);
}

.num0{
    background-color: rgb(237, 197, 106);
}

.num1{
    background-color: rgb(172, 190, 196);
}

.num2{
    background-color: rgb(205, 175, 140);
}
</style>