<template>
  <v-app>
    <nav-drawer></nav-drawer>
    <nav-bar></nav-bar>
    <v-content class="ma-3">
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import NavBar from './components/NavigationBar'
import NavDrawer from './components/NavigationDrawer'
import LoginPage from './components/LoginPage.vue'
import RegisterPage from './components/RegisterPage.vue'

export default {
  name: 'App',
  components: {
    'login-page': LoginPage,
    'register-page': RegisterPage,
    'nav-bar': NavBar,
    'nav-drawer': NavDrawer
  },
  data () {
    return {
      //
    }
  }
}
</script>
