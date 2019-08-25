<template>
  <div class="login-page">
    <input type="text" id="username" v-model="input.username" placeholder="Username"/>
    <input type="text" id="password" v-model="input.password" placeholder="Password"/>
    <v-btn dark v-on:click="login()">Login</v-btn>
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data () {
    return {
      input: {
        username: '',
        password: ''
      },
      msg: ''
    }
  },
  methods: {
    login () {
      if (this.input.username !== '' && this.input.password !== '' && this.authenticate()) {
        this.$router.push({ name: 'HomePage' })
      } else {
        this.msg = 'Incorrect username and/or password!'
      }
    },
    // TODO: change this function to whatever function validates username and password
    authenticate () {
      if (this.input.username === 'tom.gropper' && this.input.password === '12345') {
        return true
      }
    }
  }
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
