<template>
  <v-app>
    <app-header></app-header>
    <v-content class="blue lighten-2">
      <v-container class="my-5">
        <router-view></router-view>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import NavBar from './components/NavigationBar.vue'
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
    'app-header': NavBar
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
