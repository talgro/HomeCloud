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
  methods: {
    createCookie (name, expDays) {
      let cookies = document.cookie.split(';')
      for (let i = 0; i < cookies.length; ++i) {
        let cookie = cookies[i].split('=')
        if (cookie[0].includes(name)) {
          let d = new Date()
          d.setTime(d.getTime() + (expDays * 24 * 60 * 60 * 1000))
          document.cookie = name + '=' + cookie[1] + ';' + 'expires=' + d.toUTCString() + ';' + 'path=/'
          return
        }
      }
    }
  },
  beforeCreate () {
    AmplifyEventBus.$on('authState', info => {
      if (info === 'signedIn') {
        this.createCookie('idToken', 365)
        this.signedIn = true
        this.$router.push('/home-page')
      }
      if (info === 'signedOut') {
        this.$router.push('/')
        this.signedIn = false
      }
    })
    Auth.currentAuthenticatedUser().then(user => {
      this.signedIn = true
      this.$router.push('/home-page')
    }).catch(() => {
      this.signedIn = false
    })
  }
}
</script>
