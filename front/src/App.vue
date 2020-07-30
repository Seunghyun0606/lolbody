<template>
  <v-app>
    <v-app-bar app color="primary" dense dark flat>
      <v-tabs align-with-title>
        <v-tab router-link to="/">Main</v-tab>
        <!-- <v-tab router-link to="/Profile">Profile</v-tab> -->
        <v-tab router-link to="/MultiSearch">Multi Search</v-tab>
      </v-tabs>
      <v-spacer></v-spacer>
      <NavSearchBar/>
    </v-app-bar>

    <v-main class='mt-16 pt-0'>
      
      <router-view></router-view>

    </v-main>

  </v-app>
</template>

<script>
import NavSearchBar from '@/components/index/NavSearchBar.vue'

export default {
  name: 'App',
  components: {
    NavSearchBar,
  },

  data () {
    return {
    }
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
	font-size: 0.625em;
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
}
.bg_win {
    background-color: #d1e5ff !important;
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
    vertical-align: center;
}
</style>
