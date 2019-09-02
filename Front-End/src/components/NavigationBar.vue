<template>
  <div
    id="NavBar">
    <v-toolbar
      clipped-left
      app
      >
      <v-toolbar-title
        class="text-uppercase">
        <span
          class="font-weight-thin">Home</span><span class="font-weight-bold">Cloud</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items
        v-if="this.$store.getters.getLoggedIn">
        <v-btn
          flat
          @click="$router.push({name: 'HomePage'})">Browse</v-btn>
        <v-btn
          flat
          @click="$router.push({name: 'manageServer'})">
          Manage server</v-btn>
        <amplify-sign-out
          v-if="this.$store.getters.getLoggedIn"></amplify-sign-out>
      </v-toolbar-items>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
