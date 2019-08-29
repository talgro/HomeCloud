<template>
    <div>
      <v-layout row>
        <v-flex
          xs12 md6
        >
          <v-text-field
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
          >
            <v-icon>add</v-icon>
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
      // TODO: get from AWS the list of users
      users: [{ email: 'test@test.com' },
        { email: 'test2@test.com' },
        { email: 'test3@test.com' },
        { email: 'test4@test.com' }]
    }
  },
  methods: {
    // TODO: call function that sends a delete request to AWS on selected user
  },
  created () {
    // TODO: call function to get users from AWS
  }
}
</script>

<style scoped>

</style>
