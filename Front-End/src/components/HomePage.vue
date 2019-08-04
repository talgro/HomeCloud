/* eslint-disable */
<template>
  <div id="home-page">
    <v-layout row class="mb-3">
      <v-flex xs1>
        <v-btn fab>
          <v-icon>add</v-icon>
        </v-btn>
      </v-flex>
      <v-flex xs4>
        <v-text-field prepend-icon="search" single-line clearable color="grey"></v-text-field>
      </v-flex>
    </v-layout>
    <v-divider></v-divider>
    <v-breadcrumbs divider="/" :items="trail">
      <template v-slot:item="items">
        <div @click="breadcrumbsNavigateTo(items.item.text)">{{ items.item.text }}</div>
      </template>
    </v-breadcrumbs>
    <v-divider></v-divider>
    <v-layout row wrap class="mt-3">
      <v-flex xs12 sm6 md4 lg3 v-for="element in curr_folder.content" v-bind:key="element.name">
        <item
          v-bind:element="element"
          v-on:FolderClicked="navigateTo($event)"
          v-on:FileClicked="downloadFile($event)"
          v-on:DeleteItem="deleteItem($event)">
        </item>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
import NavDrawer from './NavigationDrawer'
import Item from './Item.vue'

export default {
  data () {
    return {
      curr_folder: '',
      trail: [],
      deleteDialog: false
    }
  },
  methods: {
    breadcrumbsNavigateTo (text) {
      let end = 0
      for (let i = 0; i < this.trail.length; ++i) {
        if (this.trail[i].text === text) {
          end = i
          break
        }
      }
      this.trail.splice(end + 1)
      this.navigateTo({ name: this.trail[this.trail.length].text })
    },
    trailToString () {
      let ret = ''
      for (let i = 0; i < this.trail.length; ++i) {
        ret += this.trail[i].text + '/'
      }
      return ret
    },
    navigateTo (event) {
      let url = 'https://danie.localhost.run/root/' + this.trail.join('/') + event.name
      this.trail.push({text: event.name, disabled: false})
      this.curr_folder = JSON.parse(this.$http.get(url))
    },
    downloadFile (event) {
      let url = 'https://danie.localhost.run/root/' + this.trail.join('/') + '/' + event.name
      this.curr_folder = JSON.parse(this.$http.get(url))
    },
    deleteItem (event) {
      let url = 'https://danie.localhost.run/root/' + this.trailToString()
      console.log(url + event.name)
      this.$http.delete(url + event.name)
      this.curr_folder = JSON.parse(this.$http.get(url))
    }
  },
  created () {
    // this.$http.get('https://danie.localhost.run//root').then(function (data) {
    //   // this.curr_folder = JSON.parse(data)
    //   console.log(data)
    // })
    this.curr_folder = JSON.parse('{' +
          '    "numOfItems": 4,' +
          '    "totalSize": 55330266,' +
          '    "content": [' +
          '        {' +
          '            "name": "folder",' +
          '            "type": "folder",' +
          '            "size": 29081154,' +
          '            "lastModified": "21/07/2019 15:47:08",' +
          '            "createDate": "18/07/2019 17:37:22"' +
          '        },' +
          '        {' +
          '            "name": "folder2",' +
          '            "type": "folder",' +
          '            "size": 26248994,' +
          '            "lastModified": "21/07/2019 15:35:16",' +
          '            "createDate": "21/07/2019 12:06:19"' +
          '        },' +
          '        {' +
          '            "name": "test.txt",' +
          '            "type": "txt",' +
          '            "size": 59,' +
          '            "lastModified": "21/07/2019 19:24:33",' +
          '            "createDate": "21/07/2019 19:24:24"' +
          '        },' +
          '        {' +
          '            "name": "test2.txt",' +
          '            "type": "txt",' +
          '            "size": 59,' +
          '            "lastModified": "21/07/2019 19:24:33",' +
          '            "createDate": "21/07/2019 19:25:56"' +
          '        },' +
          '        {' +
                '            "name": "folder6",' +
                '            "type": "folder",' +
                '            "size": 29081154,' +
                '            "lastModified": "21/07/2019 15:47:08",' +
                '            "createDate": "18/07/2019 17:37:22"' +
                '        },' +
                '        {' +
                '            "name": "folder7",' +
                '            "type": "folder",' +
                '            "size": 26248994,' +
                '            "lastModified": "21/07/2019 15:35:16",' +
                '            "createDate": "21/07/2019 12:06:19"' +
                '        },' +
                '        {' +
                '            "name": "test6.txt",' +
                '            "type": "txt",' +
                '            "size": 59,' +
                '            "lastModified": "21/07/2019 19:24:33",' +
                '            "createDate": "21/07/2019 19:24:24"' +
                '        }' +
          '    ]' +
          '}')
  },
  name: 'HomePage',
  components: {
    'item': Item,
    'nav-drawer': NavDrawer
  }
}
</script>

<style scoped>

</style>
