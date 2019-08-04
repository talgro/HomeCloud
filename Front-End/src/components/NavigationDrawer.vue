<template>
  <div id="nav-bar">
    <v-navigation-drawer
      permanent
      app
      clipped>
      <v-list>
        <v-list-tile>
          <v-btn>New</v-btn>
        </v-list-tile>
        <v-list-tile
          v-for="item in items"
          :key="item.title">
          <v-list-tile-action>
            <v-icon class="white--text">{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content class="ml-3">
            <v-list-tile-title class="white--text">{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
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
