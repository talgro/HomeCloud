import Vue from 'vue'
import Router from 'vue-router'
import LoginPage from '../components/LoginPage.vue'
import TopPageSegment from '../components/TopPageSegment.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: {
        name: 'login-page'
      }
    },
    {
      path: '/login',
      name: 'login-page',
      component: LoginPage
    },
    {
      path: '/top-page-segment',
      name: 'top-page-segment',
      component: TopPageSegment
    }
  ]
})
