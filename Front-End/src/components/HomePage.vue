/* eslint-disable */
<template>
  <div id="home-page">
    <h1 class="text-xs-center">THIS IS THE HOMEPAGE</h1>
    <v-layout row wrap>
      <v-flex xs6 sm4 md3 v-for="element in curr_folder.contents" v-bind:key="element.name">
        <file
          v-bind:element="element"
          v-on:elementClicked="navigateTo($event)">
        </file>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
import Header from './NavigationBar.vue'
import File from './File.vue'

export default {
  data () {
    return {
      curr_folder: []
    }
  },
  methods: {
    navigateTo (elementName) {
      // daniel is sending me a string and not a json object
      this.curr_folder = this.$http.get(elementName) // TODO: need to complete the url to get the new folder contents
    }
  },
  created () {
    // TODO: replace custom json to json from server
    // this.curr_folder = {
    //   name: 'root',
    //   type: 'Folder',
    //   size: 123,
    //   last_modified: '10/06/2019',
    //   created: '10/06/2019',
    //   children: [
    //     {
    //       name: 'folder1',
    //       type: 'Folder',
    //       size: 732,
    //       last_modified: '12/08/2019',
    //       created: '11/06/2019',
    //       children: [
    //         {
    //           name: 'file2',
    //           type: 'txt',
    //           size: 4725,
    //           last_modified: '25/10/2019',
    //           created: '11/09/2019'
    //         }
    //       ]
    //     },
    //     {
    //       name: 'file1',
    //       type: 'txt',
    //       size: 4828,
    //       last_modified: '20/05/2018',
    //       created: '05/04/2017'
    //     }
    //   ]
    // }
    // TODO: uncomment when there is a connection established with the homeserver
    // this.curr_folder = JSON.parse(
    // {
    //  contents: [
    //    {
    //      name: 'test file daniel.txt',
    //      type:'txt',
    //      size: 2.3991314E11
    //    }
    //  ]
    // }
    // )
    // this.$http.get('http://danie.localhost.run/root/test').then(function (response) {
    //   console.log(response)
    //   this.curr_folder = JSON.parse(response)
    //
    //   console.log(this.curr_folder)
    // })
    this.curr_folder = JSON.parse(
      '{"contents": [{"name": "test file daniel.txt","type": "txt","size": 2.3991314E11}' +
      ', {"name": "test file daniel1.txt","type": "txt","size": 2.3991314E11}' +
      ', {"name": "test file daniel2.txt","type": "txt","size": 2.3991314E11}' +
      ', {"name": "test file daniel3.txt","type": "txt","size": 2.3991314E11}' +
      ', {"name": "test file daniel4.txt","type": "txt","size": 2.3991314E11}' +
      ']}'
    )
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
