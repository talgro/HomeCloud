/* eslint-disable */
<template>
  <div id="home-page">
    <input style="display: none" type="file" ref="fileInput" @change="fileSelected">
    <v-layout row class="mb-3">
      <v-flex xs1>
<!--    TODO: make it so the button does not stay clicked (fab button bug, consider switching design)    -->
        <v-btn @click="$refs.fileInput.click()" fab>
          <v-icon>add</v-icon>
        </v-btn>
      </v-flex>
<!--   TODO: discuss how to do search and if necessary at all   -->
      <v-flex xs4>
        <v-text-field prepend-icon="search" single-line clearable color="grey"></v-text-field>
      </v-flex>
    </v-layout>
<!--  TODO: for some weird reason the v-model="uploadValue" does not work  -->
    <v-progress-linear :indeterminate="true" :active="uploading"></v-progress-linear>
    <v-divider></v-divider>
    <v-breadcrumbs divider="/" :items="trail">
      <template v-slot:item="props">
        <div @click="breadcrumbsNavigateTo(props.item.pos)">{{ props.item.text }}</div>
      </template>
    </v-breadcrumbs>
    <v-divider></v-divider>
    <v-layout row wrap class="mt-3">
      <v-flex xs12 sm6 md4 lg3 v-for="element in curr_folder.content" v-bind:key="element.name">
        <item
          v-bind:element="element"
          v-on:FolderClicked="navigateTo($event)"
          v-on:FileClicked="downloadFile($event)"
          v-on:DeleteItem="deleteItem($event)"
          v-on:EditName="editName($event)">
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
      trail: [{text: 'root', disabled: false, pos: 0}],
      selectedFile: null,
      address: '',
      uploadValue: 0,
      uploading: false
    }
  },
  methods: {
    editName (event) {
      let url = this.address + this.trailToString()
      this.$http.put(url + event.name, event.newName, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (response) {
        this.updateCurrFolder(url)
      })
    },
    //  TODO: how to handle spaces being encoded as %20 in REST API
    uploadFile () {
      this.uploading = true
      this.uploadValue = 0
      let url = this.address + 'upload/' + this.trailToString()
      const fd = new FormData()
      fd.append('file', this.selectedFile, this.selectedFile.name)
      this.$http.post(url, fd,
        {progress (e) {
          if (e.lengthComputable) {
            this.uploadValue = e.loaded / e.total * 100
            console.log(this.uploadValue)
          }
        },
        headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (res) {
        this.selectedFile = null
        this.updateCurrFolder(this.address + this.trailToString())
        this.uploading = false
      })
    },
    fileSelected (event) {
      this.selectedFile = event.target.files[0]
      this.uploadFile()
    },
    breadcrumbsNavigateTo (pos) {
      this.trail = this.trail.slice(0, pos + 1)
      console.log(this.trail)
      let url = this.address + this.trailToString()
      this.updateCurrFolder(url)
    },
    trailToString () {
      let ret = ''
      for (let i = 0; i < this.trail.length; ++i) {
        ret += this.trail[i].text + '/'
      }
      return ret
    },
    navigateTo (event) {
      let url = this.address + this.trailToString() + event.name
      this.trail.push({text: event.name, disabled: false, pos: this.trail.length})
      this.updateCurrFolder(url)
    },
    downloadFile (event) {
      let url = this.address + 'download/' + this.trailToString() + event.name
      this.$http({
        method: 'get',
        url: url,
        headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie },
        responseType: 'arraybuffer',
        withCredentials: true})
        .then(function (response) {
          const url = window.URL.createObjectURL(new Blob([response.data]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', event.name)
          document.body.appendChild(link)
          link.click()
        })
    },
    deleteItem (event) {
      let url = this.address + this.trailToString()
      this.$http.delete(url + event.name, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function () {
        this.updateCurrFolder(url)
      })
    },
    updateCurrFolder (url) {
      // this.$http.get(url, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (data) {
      //   this.curr_folder = data.body
      // })
      this.$http.get('http://talgropper-c5dt.localhost.run/clients/getHomeServerAddress/123', { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (data) {
        console.log(data)
        // this.curr_folder = data.body
      })
      console.log('ends here')
    }
  },
  created () {
    this.address = this.$store.getters.getServerDomain
    // this.updateCurrFolder(this.address + 'root/')
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
