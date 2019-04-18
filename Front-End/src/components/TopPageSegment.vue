<template>
  <div id="top-page-segment">
    <input type="text" id="username" v-model="input.username" placeholder="Username"/>
    <input type="text" id="password" v-model="input.password" placeholder="Password"/>
    <button type="button" v-on:click="login()">Login</button>
    <button type="button" v-on:click="register()">Register</button>
    <h1>{{ loggedIn }}</h1>
  </div>
</template>

<script>
export default {
  name: 'TopPageSegment',
  data () {
    return {
      loggedIn: false,
      input: {
        username: '',
        password: ''
      },
      msg: ''
    }
  },
  methods: {
    login () {
      if (this.input.username !== '' && this.input.password !== '' && this.validate()) {
        this.msg = 'Success!'
      } else {
        this.msg = 'Incorrect username and/or passsword!'
      }
    },
    // TODO: change this function to whatever function changes the page to the register page
    register () {

    },
    // TODO: change this function to whatever function validates username and password
    validate () {
      if (this.input.username === 'tom.gropper' && this.input.password === '12345') {
        return true
      }
    }
  }
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
