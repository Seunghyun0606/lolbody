<template>
    <!-- <div>
        <div class="d-inline-block mr-2" v-for="(badge, idx) in badgeSet" :key="idx+'_badge'">
            <div class="ma-1 pa-1 badge d-inline-block" :class="['num'+badge.tier]" >
                <span class="sharp"># </span>
                <span class="badge-content">{{badge.name}}</span>
                <span>{{badge.comment}}</span>
            </div>
            <span class="multi fs-11">X{{badge.cnt}}</span>
        </div>
    </div> -->
    <!--:tooltip="badge.comment" :style="teStyle" -->
    <div class="d-inline-block mr-2" >
        <div class="ma-1 pa-1 badge d-inline-block hidden-has-tooltip" :class="['num'+badge.tier, (badge.name=='민폐甲' || badge.name =='명타겟') ? 'badbagdge'+badge.tier : '']" @mouseenter="mousepoint" >
            <span class="sharp"># </span>
            <span class="badge-content">{{badge.name}}</span>
        </div>
        <span class="hidden-tooltip" :style="MXY">{{badge.comment}}</span>
        <span class="multi fs-11">X{{badge.cnt}}</span>
    </div>
</template>

<script>
export default {
  name: 'Profilebadge',
  props: ['badge'],
  data(){
      return {
          MX: 0,
          MY: 0,
          scrollY : 0
      }
  },
  created() {
      window.addEventListener("scroll", this.onScroll)
      this.onScroll();
  },
  destroyed(){
      window.removeEventListener("scroll", this.onScroll)
  },
  computed:{
    //   badgeSet(){
    //       return this.$store.getters.getBadgeSet
    //   },
      // tooltip_content(){
      //     return this.badge.description;
      // } 
        MXY(){
            return {'--MY': this.getMY +"px", '--MX': this.getMX+"px" }
        },
        getMX(){
            return parseInt(this.MX);
        },
        getMY(){
            return parseInt(this.MY) - this.scrollY;
        },
  },
  methods:{
        mousepoint(event){
            this.MX = event.pageX;
            this.MY = event.pageY;
        },
        onScroll(){
            this.scrollY = window.scrollY;
        }
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

.badge:hover {
    opacity: 0.7;
    cursor: pointer;
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

.badbagdge0{
    background-color: #670000;
}
.badbagdge1{
    background-color: #a10202;
}
.badbagdge2{
    background-color: #c07a7a;
}
.badbagdge0 .badge-content{
    color: white;
}
.badbagdge1 .badge-content{
    color: white;
}
.badbagdge2 .badge-content{
    color: white;
}
</style>