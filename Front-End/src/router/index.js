/* eslint-disable */

import Vue from 'vue'
import Router from 'vue-router'
import LoginPage from '../components/LoginPage.vue'
import HomePage from '../components/HomePage.vue'
import ManageServerPage from "../components/ManageServerPage";

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/home-page',
      name: 'HomePage',
      meta: { requireAuth: true },
      component: HomePage
    },
    {
      path: '/manage',
      name: 'manageServer',
      meta: { requireAuth: true },
      component: ManageServerPage
    }
  ]
})

router.beforeResolve((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    let user
    Vue.prototype.$Amplify.Auth.currentAuthenticatedUser().then(data => {
      if (data && data.signInUserSession) {
        user = data
      }
      next()
    }).catch((e) => {
      next({
        path: '/'
      })
    })
  }
  next()
})

export default router
