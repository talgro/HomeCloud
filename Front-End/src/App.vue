<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import TopPageSegment from './components/TopPageSegment.vue'
import LoginPage from './components/LoginPage.vue'

export default {
  data () {
    return {
      login: true,
      register: false,
      loggedIn: false
    }
  },
  components: {
    'top-page-segment': TopPageSegment,
    'login-page': LoginPage
  },
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
