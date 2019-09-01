<template>
    <div>
      <v-layout row>
        <v-flex
          xs12 md6
        >
          <v-text-field
            v-model="this.newUser"
            label="Enter a user to add:"
            placeholder="example@example.com"
          ></v-text-field>
        </v-flex>
        <v-flex
          xs2
        >
          <v-btn
            fab
            small
            @click="addUser"
          >
            <v-icon>person_add</v-icon>
          </v-btn>
        </v-flex>
      </v-layout>
      <v-card>
        <v-list
          class="pa-3"
        >
          <template
            v-for="(user, index) in users"
          >
            <v-list-tile
              :key="index"
            >
              <v-list-tile-content>
                <v-list-tile-title>{{ user.email }}</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-btn
                  icon
                  @click="deleteUser(user.email)"
                >
                  <v-icon>clear</v-icon>
                </v-btn>
              </v-list-tile-action>
            </v-list-tile>
            <v-divider
              v-if="index + 1 < users.length"
              :key="index"
            ></v-divider>
          </template>
        </v-list>
      </v-card>
    </div>
</template>

<script>
export default {
  name: 'manageServerPage',
  data () {
    return {
      newUser: '',
      // TODO: get from AWS the list of users
      users: [{ email: 'test@test.com' },
        { email: 'test2@test.com' },
        { email: 'test3@test.com' },
        { email: 'test4@test.com' }]
    }
  },
  methods: {
    addUser () {
      // TODO: add AWS endpoint
      this.$http.post(this.$store.getters.getBackendURL, this.newUser, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } })
        .then(function (response) {
          if (response.body === true) {
            // TODO add daniel endpoint
            this.$http.post(this.$store.getters.getServerAddress, this.newUser, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } })
              .then(function (response) {
                this.getUsers()
              })
          }
        })
    },
    // TODO: add AWS endpoint
    deleteUser (userId) {
      this.$http.delete(this.$store.getters.getBackendURL, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function () {
        this.getUsers()
      })
    },
    getUsers () {
      // TODO: add aws endpoint
      this.$http.get(this.$store.getters.getBackendURL, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (response) {
        this.users = response.body.users
      })
    }
  },
  created () {
    // TODO: call function to get users from AWS
    this.getUsers()
  }
}
</script>

<style scoped>

</style>
