import Vue from 'vue'
import Router from 'vue-router'
import RegisterPage from '../components/Register.vue'
import LoginPage from '../components/LoginPage.vue'
import HomePage from '../components/HomePage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: {
        name: 'LoginPage'
      }
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/register',
      name: 'RegisterPage',
      component: RegisterPage
    },
    {
      path: '/home-page',
      name: 'HomePage',
      component: HomePage
    }
  ]
})
