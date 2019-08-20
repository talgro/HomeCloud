<template>
  <div class="login-page">
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
                <v-text-field vmodel="registerForm.username" :rules="usernameRules" :counter="20" required label="Username"></v-text-field>
              </v-flex>
              <v-flex>
                <v-text-field  type="password" vmodel="registerForm.password" :rules="passwordRules" :counter="20" required label="Password"></v-text-field>
              </v-flex>
              <v-flex>
                <v-text-field vmodel="registerForm.email" :rules="usernameRules" required label="E-mail"></v-text-field>
              </v-flex>
              <v-flex>
                <div>
                  <v-btn>Register</v-btn>
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
      registerDialog: false
    }
  },
  methods: {
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
