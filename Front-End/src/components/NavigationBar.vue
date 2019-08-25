<template>
  <div id="NavBar">
    <nav>
      <v-toolbar clipped-left app>
        <v-toolbar-title class="text-uppercase">
          <span class="font-weight-thin">Home</span><span class="font-weight-bold">Cloud</span>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items>
<!--          TODO: add a variable so this component can access it-->
          <amplify-sign-out v-if="true"></amplify-sign-out>
        </v-toolbar-items>
      </v-toolbar>
    </nav>
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
