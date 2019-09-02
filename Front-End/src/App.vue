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
    //
  },
  methods: {
    createCookie (name, cookieName, expDays) {
      let cookies = document.cookie.split(';')
      for (let i = 0; i < cookies.length; ++i) {
        let cookie = cookies[i].split('=')
        if (cookie[0].includes(name)) {
          let d = new Date()
          d.setTime(d.getTime() + (expDays * 24 * 60 * 60 * 1000))
          this.$cookies.set(cookieName, cookie[1], '1y')
          return
        }
      }
    },
    initUserData () {
      // TODO: fill in full url to AWS
      console.log('before init: ', this.$store.getters.getCookie)
      this.$http.get(this.$store.getters.getBackendURL + '/clients/getMyHomeServerDetails', { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (response) {
        console.log('initData response: ', response)
        // this.$store.commit('setUserId', response.body.username)
        this.$store.commit('setUserId', 'tomgr')
        // this.$store.commit('setServerAddress', response.body.home_server_address)
        this.$store.commit('setServerAddress', 'https://danie-ko4x.localhost.run')
        this.$store.commit('setServerName', response.body.home_server_name)
        console.log(this.$store.getters.getUsername)
        console.log(this.$store.getters.getServerAddress)
        console.log(this.$store.getters.getServerName)
      }).catch(err => console.log('failed:', err))
    }
  },
  beforeCreate () {
    AmplifyEventBus.$on('authState', info => {
      if (info === 'signedIn') {
        // this.createCookie('accessToken', 'access_cookie', 365)
        this.$store.commit('setLoggedIn', true)
        this.initUserData()
        console.log('in $on: ', this.$store.getters.getCookie)
        this.$router.push('/home-page')
      }
      if (info === 'signedOut') {
        this.$store.commit('setLoggedIn', false)
        this.$router.push('/')
      }
    })
    Auth.currentAuthenticatedUser().then(user => {
      this.initUserData()
      this.$router.push('/home-page')
    }).catch(() => {
      this.$store.commit('setLoggedIn', false)
    })
    // console.log('after all: ', this.$store.getters.getCookie)
    // console.log(Auth.currentSession().then(res => {
    //   let accessToken = res.getAccessToken()
    //   let jwt = accessToken.getJwtToken()
    //   console.log('test jwt: ', jwt)
    // }))
    if (this.$store.getters.getLoggedIn === true) {
      this.initUserData()
    }
  }
}
</script>
