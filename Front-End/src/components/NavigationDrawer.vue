<template>
  <div id="nav-bar">
    <v-navigation-drawer
      permanent
      app
      clipped>
      <h1 class="px-3 pt-2">Most Frequent:</h1>
      <v-list class="px-3">
        <template v-for="(item, index) in mostFrequent">
          <v-list-tile
            :key="item.url"
          >
            <v-list-tile-content>
              <v-list-tile-title>{{ truncateBeginning(30, item.url) }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-divider
            v-if="index + 1 < mostFrequent"
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
      onHomePage: false
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
  },
  computed: {
    mostFrequent () {
      return this.$store.getters.getMostFrequent
    }
  },
  created () {
    console.log(this.$store.getters.getMostFrequent)
  }
}
</script>

<style scoped>

</style>
