import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import store from './store'
import axios from 'axios'
// import ApexCharts from 'apexcharts'
// import VueApexCharts from 'vue-apexcharts'

Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  store,
  axios,
  // ApexCharts,
  // VueApexCharts,
  render: h => h(App)
}).$mount('#app')
