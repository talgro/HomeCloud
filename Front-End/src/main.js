// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuetify from 'vuetify'
import './plugins/vuetify'
import App from './App'
import VueResource from 'vue-resource'
import router from './router'
import Amplify, * as AmplifyModules from 'aws-amplify'
import { AmplifyPlugin } from 'aws-amplify-vue'
import awsmobile from './aws-exports'
import { store } from './store/store'

Amplify.configure(awsmobile)
Vue.use(AmplifyPlugin, AmplifyModules)

Vue.config.productionTip = false
Vue.use(VueResource)
Vue.use(Vuetify)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
