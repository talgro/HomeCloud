/* eslint-disable */
<template>
    <div id="home-page">
      <h1>THIS IS THE HOMEPAGE</h1>
      <h1>{{ curr_folder.name }}</h1>
      <file
        v-for="element in curr_folder.children"
        v-bind:element="element" v-bind:key="element.name"
        v-on:elementClicked="navigateTo($event)">
      </file>
    </div>
</template>

<script>
import Header from './Header.vue'
import File from './File.vue'

export default {
  data () {
    return {
      curr_folder: {}
    }
  },
  methods: {
    navigateTo (elementName) {
      this.curr_folder = this.$http.get(elementName) // TODO: need to complete the url to get the new folder contents
    }
  },
  created () {
    // TODO: replace custom json to json from server
    this.curr_folder = {
      name: 'root',
      type: 'Folder',
      size: 123,
      last_modified: '10/06/2019',
      created: '10/06/2019',
      children: [
        {
          name: 'folder1',
          type: 'Folder',
          size: 732,
          last_modified: '12/08/2019',
          created: '11/06/2019',
          children: [
            {
              name: 'file2',
              type: 'txt',
              size: 4725,
              last_modified: '25/10/2019',
              created: '11/09/2019'
            }
          ]
        },
        {
          name: 'file1',
          type: 'txt',
          size: 4828,
          last_modified: '20/05/2018',
          created: '05/04/2017'
        }
      ]
    }
    /* //TODO: uncomment when there is a connection established with the homeserver
    this.curr_folder = this.$http.get('/root')
    */
  },
  name: 'HomePage',
  components: {
    'app-header': Header,
    'file': File
  }
}
</script>

<style scoped>

</style>
