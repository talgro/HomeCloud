<template>
  <div id="nav-bar">
    <v-navigation-drawer
      permanent
      app
      clipped>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: 'NaviagationDrawer',
  data () {
    return {
      items: [
        {icon: 'add_circle_outline', title: 'New'}
      ]
    }
  }
}
</script>

<style scoped>

</style>
