import Vue from 'vue'
import VueRouter from 'vue-router'

import Profile from "@/views/Profile.vue";
import Index from '@/views/Index.vue'
import MultiSearch from '@/views/MultiSearch.vue'
import Lolbody from '@/views/Lolbody.vue'

// import Prac from '@/views/Prac.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
  },
  {
    path: '/Profile/:userName',
    name: 'proifle',
    component: Profile,
  },
  {
    path: '/MultiSearch',
    name: 'MultiSearch',
    component: MultiSearch,
  },
  {
    path: '/Lolbody/:userName',
    name: 'Lolbody',
    component: Lolbody,
  },
  // {
  //   path: '/Prac',
  //   name: 'Prac',
  //   component: Prac,
  // }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
