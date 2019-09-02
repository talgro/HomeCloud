/* eslint-disable */
<template>
  <div class="login-page, text-align: center">
    <center>
      <amplify-authenticator
        :auth-config="auth.authConfig"
        class="mt-5"
      ></amplify-authenticator>
    </center>
<!--    <v-container>-->
<!--      <v-layout row justify-center>-->
<!--        <v-flex xs6>-->
<!--          <v-card hover class="ma-5 pa-5">-->
<!--            <v-form v-model="loginForm.isValid">-->
<!--              <v-container>-->
<!--                <v-layout column align-center>-->
<!--                  <v-flex xs3>-->
<!--                    <v-text-field v-model="loginForm.firstName" required label="First Name"></v-text-field>-->
<!--                  </v-flex>-->
<!--                  <v-flex xs3>-->
<!--                    <v-text-field v-model="loginForm.lastName" required label="Last Name"></v-text-field>-->
<!--                  </v-flex>-->
<!--                  <v-flex xs3>-->
<!--                    <v-text-field type="password" v-model="loginForm.password" required label="Password"></v-text-field>-->
<!--                  </v-flex>-->
<!--                  <v-flex class="mb-0" xs3>-->
<!--                    <v-btn class="mb-0" dark @click="login">Login</v-btn>-->
<!--                  </v-flex>-->
<!--                  <v-flex xs3 class="mt-0">-->
<!--                    <v-btn @click="registerDialog = true" class="mt-0" flat>Register</v-btn>-->
<!--                  </v-flex>-->
<!--                </v-layout>-->
<!--              </v-container>-->
<!--            </v-form>-->
<!--          </v-card>-->
<!--        </v-flex>-->
<!--      </v-layout>-->
<!--    </v-container>-->
<!--    <v-dialog v-model="registerDialog" width="400">-->
<!--      <v-card>-->
<!--        <v-card-title class="headline" primary-title>-->
<!--          Register-->
<!--        </v-card-title>-->
<!--        <v-form>-->
<!--          <v-container>-->
<!--            <v-layout column align-center>-->
<!--              <v-flex>-->
<!--                &lt;!&ndash;       TODO: add :rules="usernameRules"         &ndash;&gt;-->
<!--                <v-text-field v-model="registerForm.email" :counter="20" required label="Email"></v-text-field>-->
<!--              </v-flex>-->
<!--              <v-flex>-->
<!--                &lt;!&ndash;       TODO: add :rules="usernameRules"         &ndash;&gt;-->
<!--                <v-text-field v-model="registerForm.firstName" :counter="20" required label="First Name"></v-text-field>-->
<!--              </v-flex>-->
<!--              <v-flex>-->
<!--                &lt;!&ndash;       TODO: add :rules="usernameRules"         &ndash;&gt;-->
<!--                <v-text-field v-model="registerForm.lastName" :counter="20" required label="Last Name"></v-text-field>-->
<!--              </v-flex>-->
<!--              &lt;!&ndash;       TODO: add :rules="passwordRules"         &ndash;&gt;-->
<!--              <v-flex>-->
<!--                <v-text-field type="password" v-model="registerForm.password" :counter="20" required label="Password"></v-text-field>-->
<!--              </v-flex>-->
<!--              &lt;!&ndash;       TODO: add :rules="emailRules"         &ndash;&gt;-->
<!--              <v-flex>-->
<!--                <div>-->
<!--                  <v-btn @click="register">Register</v-btn>-->
<!--                  <v-btn @click="registerDialog = false" flat>Cancel</v-btn>-->
<!--                </div>-->
<!--              </v-flex>-->
<!--            </v-layout>-->
<!--          </v-container>-->
<!--        </v-form>-->
<!--      </v-card>-->
<!--    </v-dialog>-->
  </div>
</template>

<script>
/* eslint-disable */
import { Auth } from 'aws-amplify'

export default {
  name: 'LoginPage',
  data() {
    return {
      loginForm: {
        isValid: false,
        firstName: '',
        lastName: '',
        password: ''
      },
      registerForm: {
        isValid: false,
        firstName: '',
        lastName: '',
        password: '',
        email: ''
      },
      msg: '',
      auth: {
        signedIn: false,
        authConfig: {
          usernameAttributes: 'E-mail',
          signUpConfig: {
            hideAllDefaults: true,
            signUpFields: [
              {
                label: 'E-mail',
                key: 'email',
                required: true,
                displayOrder: 1,
                type: 'string'
              },
              {
                label: 'First Name',
                key: 'given_name',
                required: true,
                displayOrder: 2,
                type: 'string'
              },
              {
                label: 'Last Name',
                key: 'family_name',
                required: true,
                displayOrder: 3,
                type: 'string'
              },
              {
                label: 'Password',
                key: 'password',
                required: true,
                displayOrder: 4,
                type: 'password'
              }
            ]
          }
        }
      },
      registerDialog: false,
    }
  },
  methods: {
  //   register() {
  //     Auth.signUp({
  //       username: this.registerForm.firstName + this.registerForm.lastName,
  //       password: this.registerForm.password,
  //       attributes: {
  //         // user_id: this.registerForm.email,
  //         given_name: this.registerForm.firstName,
  //         family_name: this.registerForm.lastName,
  //         email: this.registerForm.email // optional
  //       },
  //       validationData: [] // optional
  //     })
  //       .then(data => console.log("successful registration data", data))
  //       .catch(err => console.log("failed registration data", err))
  //   },
  //   login() {
  //     if (this.loginForm.email !== '' && this.loginForm.password !== '' && this.loginForm.password !== '') {
  //       Auth.signIn({
  //         username: this.loginForm.firstName + this.loginForm.lastName,
  //         password: this.loginForm.password,
  //       })
  //         .then(data => {
  //           console.log("successful login data", data);
  //           this.$router.push({name: 'HomePage'});
  //         })
  //         .catch(err => console.log("failed login data", err))
  //     } else {
  //       this.msg = 'Incorrect firstName and/or password!'
  //     }
  //   },
  }
}
</script>
