<template>
  <div id="nav-bar">
    <v-navigation-drawer
      permanent
      app
      clipped>
      <h1 class="px-3 pt-2">Most Frequent:</h1>
      <v-list class="px-3">
        <template v-for="(item, index) in items">
          <v-list-tile
            :key="item.url"
          >
            <v-list-tile-content>
              <v-list-tile-title>{{ truncateBeginning(30, item.url) }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-divider
            v-if="index + 1 < items.length"
            :key="index"
          ></v-divider>
        </template>
      </v-list>
      <v-divider></v-divider>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: 'NavigationDrawer',
  data () {
    return {
      // TODO: use vuex to change this to true when in homepage and false if not
      onHomePage: false,
      // TODO: use vuex to update items accordingly
      items: [
        {
          url: 'http://danie.localhost.run/root/folder1'
        },
        {
          url: 'http://danie.localhost.run/root/folder3/folder50/file4'
        },
        {
          url: 'http://danie.localhost.run/root/folder1/daniel/tom.txt'
        },
        {
          url: 'http://danie.localhost.run/root/folder1/file65'
        },
        {
          url: 'http://danie.localhost.run/root/folder1/helloworld.java'
        }
      ]
    }
  },
  methods: {
    truncateBeginning (length, str) {
      if (str.length <= length) {
        return str
      } else {
        return '...' + str.substring(str.length - 1 - length, str.length)
      }
    }
  }
}
</script>

<style scoped>

</style>
