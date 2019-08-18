<template>
  <div class="login-page">
    <v-container>
      <v-layout row justify-center>
        <v-flex xs6>
          <v-card hover class="ma-5 pa-5">
            <v-form v-model="form.isValid">
              <v-container>
                <v-layout column align-center>
                  <v-flex xs3>
                    <v-text-field v-model="form.username" label="Username"></v-text-field>
                  </v-flex>
                  <v-flex xs3>
                    <v-text-field v-model="form.password" label="Password"></v-text-field>
                  </v-flex>
                  <v-flex class="mb-0" xs3>
                    <v-btn class="mb-0" dark v-on:click="login()">Login</v-btn>
                  </v-flex>
                  <v-flex xs3 class="mt-0">
                    <v-btn class="mt-0" flat>Register</v-btn>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-form>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data () {
    return {
      form: {
        isValid: false,
        username: '',
        password: ''
      },
      msg: ''
    }
  },
  methods: {
    login () {
      if (this.form.username !== '' && this.form.password !== '' && this.authenticate()) {
        this.$router.push({ name: 'HomePage' })
      } else {
        this.msg = 'Incorrect username and/or password!'
      }
    },
    // TODO: change this function to whatever function validates username and password
    authenticate () {
      if (this.form.username === 'tom.gropper' && this.form.password === '12345') {
        return true
      }
    }
  }
}
</script>
