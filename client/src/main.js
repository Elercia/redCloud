import 'materialize-css'
import 'materialize-css/dist/css/materialize.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'


import Vue from 'vue'
import App from './App.vue'
import router from './router'
import {store} from './store/store'


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
