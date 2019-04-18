<template>
  <div id="login-page">
    <input type="text" id="username" v-model="input.username" placeholder="Username"/>
    <input type="text" id="password" v-model="input.password" placeholder="Password"/>
    <button type="button" v-on:click="login()">Login</button>
    <button type="button" v-on:click="register()">Register</button>
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
      if (this.input.username !== '' && this.input.password !== '' && this.validate()) {
        this.msg = 'Success!'
        this.$router.push({ name: 'top-page-segment' })
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

<style scoped>

</style>
