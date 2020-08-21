<template>
    <v-app class="bg_main">
        <v-app-bar app color="#33A39E" dense dark flat>
            <v-tabs align-with-title>
            <v-tab router-link to="/">Main</v-tab>
            <!-- <v-tab router-link to="/Profile">Profile</v-tab> -->
            <v-tab router-link to="/MultiSearch">Multi Search</v-tab>
            <!-- <v-tab router-link to="/Lolbody">Lolbody</v-tab> -->

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
  },
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
.bg_main{
    background-color: #fafafa !important;
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

.index-explanation{
  color: gray;
  /* text-align: right; */
}

[tooltip]:hover {
    position: relative;
    cursor: pointer;
}

[tooltip]:after {
    background-color: rgba(0, 0, 0, 0.8);
    word-wrap: break-word;
    opacity: 0;
    padding: 10px;
    border-radius: 10px;
    color: #FFFFFF;
    font-size: 10px;
    
    content: attr(tooltip);
    position: fixed;
    z-index: -1;
    width: auto;
}

[tooltip]:hover:after {
    top: var(--MY) !important;
    left: var(--MX) !important;

    opacity: 1;
    z-index: 9999;
    width: 100px;
    transform: translateX(-50%) translateY(-50%) !important;
}

.hidden-has-tooltip:hover + .hidden-tooltip{
    opacity: 1 !important;
    z-index: 9999 !important;
    transform: translateX(-50%) translateY(-130%);
}
.hidden-tooltip {
    position: fixed;
    top: var(--MY);
    left: var(--MX);
    color: #fff;
    width: 150px;
    padding: 5px 5px;
    border-radius: 10px;
    background-color: rgb(0, 0, 0);
    opacity: 0;
    z-index: -1;
}
/* [tooltip]:hover {
	position: absolute;
    cursor: pointer;
}

[tooltip]:after {
	-webkit-transition: bottom .3s ease-in-out, opacity .3s ease-in-out;
	-moz-transition: bottom .3s ease-in-out, opacity .3s ease-in-out;
	transition: bottom .3s ease-in-out, opacity .3s ease-in-out;

	background-color: rgba(0, 0, 0, 0.8);

  -webkit-box-shadow: 0px 0px 3px 1px rgba(50, 50, 50, 0.4);
	-moz-box-shadow: 0px 0px 3px 1px rgba(50, 50, 50, 0.4);
	box-shadow: 0px 0px 3px 1px rgba(50, 50, 50, 0.4);
	
  -webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	
  color: #FFFFFF;
	font-size: 12px;
	margin-bottom: 10px;
	padding: 7px 12px;
	position: absolute;
	width: auto;
	min-width: 50px;
	max-width: 300px;
	word-wrap: break-word;

	z-index: 9999;

	opacity: 0;
	left: -9999px;
  top: 90%;
	
	content: attr(data-tooltip-text);
}

[tooltip]:hover:after {
	top: 0%;
	left: -30px;
	opacity: 1;
} */

</style>
