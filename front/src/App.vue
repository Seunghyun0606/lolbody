<template>
    <v-app style="background-color: #fafafa;">
        <v-app-bar app color="#33A39E" dense dark flat>
            <v-tabs align-with-title>
            <v-tab router-link to="/">Main</v-tab>
            <!-- <v-tab router-link to="/Profile">Profile</v-tab> -->
            <v-tab router-link to="/MultiSearch">Multi Search</v-tab>
            <v-tab router-link to="/Lolbody">Lolbody</v-tab>

            <!-- <v-tab router-link to="/Prac">Prac</v-tab> -->
            
            </v-tabs>
            <v-spacer></v-spacer>
            <NavSearchBar v-if="isIndex ? false : true"/>
        </v-app-bar>

        <v-main class='mt-16 pt-0'>
        
            <router-view :key="$route.fullPath"></router-view>

        </v-main>

  </v-app>
</template>

<script>
Kakao.init('7b7a42d9cf2509fe7d9c7db809ef3807')
console.log(Kakao.isInitialized())
import NavSearchBar from '@/components/index/NavSearchBar.vue'
import { mapState } from 'vuex'

export default {
  name: 'App',
  components: {
    NavSearchBar,
  },

  data () {
    return {
    }
  },

  computed: {
    ...mapState(['isIndex',])
  }
}
</script>

<style>

.v-avatar {
	border : rgb(200, 170, 110) 1px solid;
}
.clear {
    clear: both;
}

.fs-0{
  font-size: 0;
}
.fs-15{
	font-size: 0.938em;
}
.fs-14{
	font-size: 0.875em;
}
.fs-13{
	font-size: 0.813em;
}
.fs-12{
    font-size: 0.75em !important;
}
.fs-12px{
    font-size: 12px !important;
}
.fs-11{
    font-size: 0.688em !important;
}
.fs-10{
	font-size: 0.625em !important;
}
.fs-9{
	font-size: 0.563em !important;
}
.fs-8{
	font-size: 0.5em !important;
}

.fc_blue{
	color:rgb(1, 1, 211);
}
.fc_red{
	color:rgb(211, 1, 1);
}
.fc_blue{
    color:rgb(1, 1, 211);
}
.fc_red{
    color:rgb(211, 1, 1);
}

.bg_card {
	background-color: #fafafa !important;
}
.bg_fail {
    background-color: #ffe1e1 !important;
    border :#bea8a8 solid 1px !important;
}
.bg_win {
    background-color: #d1e5ff !important;
    border :#a3b2c7 solid 1px !important;
}
.bg_black{
    background-color: black;
}
.bg_gray {
    background-color: #c9c9c9 !important;
}
.bg_gray_op{
    background-color: rgba(133, 133, 133, 0.25);
}


.border-rounded{
    border-radius: 15px;
}
.vertical-align-bottom{
    vertical-align: bottom;
}
.vertical-align-center{
    vertical-align: center !important;
}


[tooltip]:hover {
	position: relative;
  cursor: pointer;

}

[tooltip]:after {
  content: attr(tooltip);

  transition: bottom .3s ease-in-out, opacity .3s ease-in-out;
	background-color: rgba(0, 0, 0, 0.8);
  word-wrap: break-word;
  opacity: 0;
	left: -9999px;

  position: absolute;
	bottom: 90%;
  
  padding: 10px;
  border-radius: 10px;
	color: #FFFFFF;
	font-size: 10px;
	z-index: 9999;
}

[tooltip]:hover:after {
  bottom: 110%;
	left: 0;
	opacity: 1;
}



</style>
