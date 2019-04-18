import Vue from 'vue'
import Router from 'vue-router'
import NotLoggedInPage from '../components/NotLoggedInPage.vue'
import RegisterPage from '../components/Register.vue'
import LoggedInPage from '../components/LoggedInPage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: {
        name: 'NotLoggedInPage'
      }
    },
    {
      path: '/',
      name: 'NotLoggedInPage',
      component: NotLoggedInPage
    },
    {
      path: '/Register',
      name: 'RegisterPage',
      component: RegisterPage
    },
    {
      // TODO: make it so the username will be in the URL
      path: '/user-name',
      name: 'logged-in-page',
      component: LoggedInPage
    }
  ]
})
