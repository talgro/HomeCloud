<template>
    <div id="home-page">
      <folder v-for="element in contents" v-bind:element="element"></folder>
    </div>
</template>

<script>

import Header from './Header.vue'
import Folder from './FolderElement.vue'

export default {
  data () {
    return {
      contents: []
    }
  },
  created () {
    // TODO: change this link to get the contents of the server
    // this.$http.get('http://jsonplaceholder.typicode.com/posts').then(function (data) {
    //   this.contents = data.body
    // })
    // TODO: delete after we can get json from the server
    this.contents = [
      {
        name: 'folder1',
        contents: [
          {
            name: 'file2'
          },
          {
            name: 'folder2',
            contents: [
              {
                name: 'file3'
              },
              {
                name: 'file4'
              }
            ]
          }
        ]
      },
      {
        name: 'file1'
      }
    ]
  },
  name: 'HomePage',
  components: {
    'app-header': Header,
    'folder': Folder
  }
}
</script>

<style scoped>

</style>
