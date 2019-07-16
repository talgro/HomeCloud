<template>
  <v-app>
    <v-btn dark>button</v-btn>
<!--    <div id="app">-->
    <v-content>
      <router-view></router-view>
    </v-content>
<!--    </div>-->
  </v-app>
</template>

<script>
import Header from './components/Header.vue'
import LoginPage from './components/LoginPage.vue'
import RegisterPage from './components/Register.vue'

export default {
  data () {
    return {
    }
  },
  components: {
    'login-page': LoginPage,
    'register-page': RegisterPage,
    'app-header': Header
  },
  name: 'App'
}
</script>

<!--<style>-->
<!--  #app {-->
<!--    font-family: 'Avenir', Helvetica, Arial, sans-serif;-->
<!--    -webkit-font-smoothing: antialiased;-->
<!--    -moz-osx-font-smoothing: grayscale;-->
<!--    text-align: center;-->
<!--    color: #2c3e50;-->
<!--    margin-top: 60px;-->
<!--  }-->
<!--</style>-->
