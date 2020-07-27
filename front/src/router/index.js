import Vue from 'vue'
import VueRouter from 'vue-router'

import Profile from "@/components/Profile.vue";
import Index from '../views/Index.vue'
import MultiSearch from '@/components/Multisearch.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
  },
  {
    path: '/Profile',
    name: 'proifle',
    component: Profile,
  },
  {
    path: '/MultiSerach',
    name: 'MultiSerach',
    component: MultiSearch,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
