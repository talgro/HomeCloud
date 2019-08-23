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
import { AmplifyEventBus } from 'aws-amplify-vue'
import { Auth } from 'aws-amplify'

export default {
  name: 'App',
  components: {
    'login-page': LoginPage,
    'nav-bar': NavBar,
    'nav-drawer': NavDrawer
  },
  data () {
    return {
      signedIn: false
    }
  },
  beforeCreate () {
    console.log('hello?')
    AmplifyEventBus.$on('')
    AmplifyEventBus.$on('authState', info => {
      if (info === 'signedIn') {
        console.log('logged in')
        this.signedIn = true
        this.$router.push('/home-page')
      }
      if (info === 'signedOut') {
        console.log('not logged in')
        this.$router.push('/')
        this.signedIn = false
      }
      console.log('not either')
    })
    Auth.currentAuthenticatedUser().then(user => {
      this.signedIn = true
      this.$router.push('/')
    }).catch(() => {
      this.signedIn = false
      this.$router.push('/home-page')
    })
  }
}
</script>
