/* eslint-disable */
<template>
  <div class="login-page">
    <amplify-authenticator></amplify-authenticator>
    <amplify-confirm-sign-up></amplify-confirm-sign-up>
    <v-container>
      <v-layout row justify-center>
        <v-flex xs6>
          <v-card hover class="ma-5 pa-5">
            <v-form v-model="loginForm.isValid">
              <v-container>
                <v-layout column align-center>
                  <v-flex xs3>
                    <v-text-field v-model="loginForm.username" label="Username"></v-text-field>
                  </v-flex>
                  <v-flex xs3>
                    <v-text-field v-model="loginForm.password" label="Password" type="password"></v-text-field>
                  </v-flex>
                  <v-flex class="mb-0" xs3>
                    <v-btn class="mb-0" dark v-on:click="login()">Login</v-btn>
                  </v-flex>
                  <v-flex xs3 class="mt-0">
                    <v-btn @click="registerDialog = true" class="mt-0" flat>Register</v-btn>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-form>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
    <v-dialog v-model="registerDialog" width="400">
      <v-card>
        <v-card-title class="headline" primary-title>
          Register
        </v-card-title>
        <v-form>
          <v-container>
            <v-layout column align-center>
              <v-flex>
<!--       TODO: add :rules="usernameRules"         -->
                <v-text-field vmodel="registerForm.username"  :counter="20" required label="Username"></v-text-field>
              </v-flex>
<!--       TODO: add :rules="passwordRules"         -->
              <v-flex>
                <v-text-field  type="password" vmodel="registerForm.password" :counter="20" required label="Password"></v-text-field>
              </v-flex>
<!--       TODO: add :rules="emailRules"         -->
              <v-flex>
                <v-text-field vmodel="registerForm.email" required label="E-mail"></v-text-field>
              </v-flex>
              <v-flex>
                <div>
                  <v-btn @click="register">Register</v-btn>
                  <v-btn @click="registerDialog = false" flat>Cancel</v-btn>
                </div>
              </v-flex>
            </v-layout>
          </v-container>
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { Auth } from 'aws-amplify'
export default {
  name: 'LoginPage',
  data () {
    return {
      loginForm: {
        isValid: false,
        username: '',
        password: ''
      },
      registerForm: {
        isValid: false,
        username: '',
        password: '',
        email: ''
      },
      msg: '',
      registerDialog: false,
      signedIn: false
    }
  },
  methods: {
    register () {
      Auth.signUp({
        username: this.registerForm.email,
        password: this.registerForm.password,
        attributes: {
          email: this.registerForm.email // optional
        },
        validationData: [] // optional
      })
        .then(data => console.log(data))
        .catch(err => console.log(err))
    },
    login () {
      if (this.loginForm.username !== '' && this.loginForm.password !== '' && this.authenticate()) {
        this.$router.push({ name: 'HomePage' })
      } else {
        this.msg = 'Incorrect username and/or password!'
      }
    },
    // TODO: change this function to whatever function validates username and password
    authenticate () {
      if (this.loginForm.username === 'tom.gropper' && this.loginForm.password === '12345') {
        return true
      }
    }
  }
}
</script>
