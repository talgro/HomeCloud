/* eslint-disable */
<template>
  <div>
    <div
      v-if="this.$store.getters.getServerAddress === null"
      class="mt-5"
    >
      <center>
        <h1
          class="red--text"
        >
          Your account has not been connected to a server yet.
        </h1>
        <br>
        <h1
          class="red--text"
        >
          Set up a new server and connect your account to it or
        </h1>
        <br>
        <h1
          class="red--text"
        >
          add it in an already connected users' "MANAGE SERVER" section
        </h1>
        <br>
        <h1
          class="red--text"
        >
          and then log in again.
        </h1>
      </center>
    </div>
    <div
      v-else
      id="home-page"
    >
      <input
        style="display: none"
        type="file"
        ref="fileInput"
        @change="fileSelected"
      >
      <v-layout
        row
        class="mb-3"
      >
        <v-flex
          xs1
          class="ma-1"
        >
          <v-btn
            @click="newFolderDialog = true"
            fab
          >
            <v-icon>create_new_folder</v-icon>
          </v-btn>
        </v-flex>
        <v-flex
          xs1
          class="ma-1"
        >
          <!--    TODO: make it so the button does not stay clicked (fab button bug, consider switching design)    -->
          <v-btn
            @click="$refs.fileInput.click()"
            fab
          >
            <v-icon>cloud_upload</v-icon>
          </v-btn>
        </v-flex>
        <!--   TODO: discuss how to do search and if necessary at all   -->
        <v-flex
          class="ma-1"
          xs4>
          <v-text-field
            prepend-icon="search"
            single-line
            clearable
            color="grey"
          ></v-text-field>
        </v-flex>
      </v-layout>
      <!--  TODO: for some weird reason the v-model="uploadValue" does not work  -->
      <v-progress-linear
        :indeterminate="true"
        :active="uploading"
      ></v-progress-linear>
      <v-divider></v-divider>
      <v-breadcrumbs
        divider="/"
        :items="trail"
      >
        <template
          v-slot:item="props"
        >
          <div
            @click="breadcrumbsNavigateTo(props.item.pos)"
          >
            {{ props.item.text }}
          </div>
        </template>
      </v-breadcrumbs>
      <v-divider></v-divider>
      <v-layout
        row
        wrap
        class="mt-3"
      >
        <v-flex
          xs12
          sm6
          md4
          lg3
          v-for="element in curr_folder.content"
          :key="element.name"
        >
          <item
            :element="element"
            @FolderClicked="navigateTo($event)"
            @FileClicked="downloadFile($event)"
            @DeleteItem="deleteItem($event)"
            @EditName="editName($event)"
          ></item>
        </v-flex>
      </v-layout>
      <v-dialog v-model="newFolderDialog" width="400">
        <v-card>
          <v-card-title class="headline" primary-title>
            Enter a new folder name:
          </v-card-title>
          <v-divider></v-divider>
          <v-text-field v-model="newFolderName" label="Enter a new folder name" single-line></v-text-field>
          <v-card-actions>
            <v-btn flat @click="editName">
              Create
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn flat @click="editDialog = false">
              Cancel
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </div>
</template>

<script>
import NavDrawer from './NavigationDrawer'
import Item from './Item.vue'

export default {
  data () {
    return {
      curr_folder: '',
      trail: [{text: this.$store.getters.getUsername, disabled: false, pos: 0}],
      selectedFile: null,
      uploadValue: 0,
      uploading: false,
      newFolderDialog: false,
      newFolderName: ''
    }
  },
  methods: {
    createFolder () {
      // TODO: ask daniel for the right url
      let url = this.address + '/newFolder/' + this.trailToString()
      this.$http.post(url, this.newFolderName).then(function (response) {
        this.updateCurrFolder(url)
      })
    },
    editName (event) {
      let url = this.address + '/' + this.trailToString()
      this.$http.put(url + event.name, event.newName, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (response) {
        this.updateCurrFolder(url)
      })
    },
    //  TODO: how to handle spaces being encoded as %20 in REST API
    uploadFile () {
      this.uploading = true
      this.uploadValue = 0
      let url = this.address + '/upload/' + this.trailToString()
      const fd = new FormData()
      fd.append('file', this.selectedFile, this.selectedFile.name)
      this.$http.post(url, fd,
        {progress (e) {
          if (e.lengthComputable) {
            this.uploadValue = e.loaded / e.total * 100
            console.log(this.uploadValue)
          }
        },
        headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } })
        .then(function (res) {
          this.selectedFile = null
          this.updateCurrFolder(this.address + '/' + this.trailToString())
          this.uploading = false
        })
    },
    fileSelected (event) {
      this.selectedFile = event.target.files[0]
      this.uploadFile()
    },
    breadcrumbsNavigateTo (pos) {
      this.trail = this.trail.slice(0, pos + 1)
      console.log(this.trailToString())
      let url = this.address + '/' + this.trailToString()
      console.log(url)
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
      let url = this.address + '/' + this.trailToString() + event.name
      this.trail.push({text: event.name, disabled: false, pos: this.trail.length})
      this.updateCurrFolder(url)
    },
    downloadFile (event) {
      let url = this.address + '/' + 'download/' + this.trailToString() + event.name
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
          console.log('url?', url)
          link.click()
        })
    },
    deleteItem (event) {
      let url = this.address + '/' + this.trailToString()
      this.$http.delete(url + event.name, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function () {
        this.updateCurrFolder(url)
      })
    },
    updateCurrFolder (url) {
      // TODO: replace the address back to url
      this.$http.get(url, { headers: { Authorization: 'Bearer ' + this.$store.getters.getCookie } }).then(function (response) {
        console.log('response:', response)
        console.log('url:', url)
        this.curr_folder = response.body
      })
    }
  },
  computed: {
    address () {
      return this.$store.getters.getServerAddress
    },
    userId () {
      return this.$store.getters.getUsername
    }
  },
  created () {
    this.updateCurrFolder(this.$store.getters.getServerAddress + '/' + this.$store.getters.getUsername)
    // this.curr_folder = JSON.parse('{' +
    //       '    "numOfItems": 4,' +
    //       '    "totalSize": 55330266,' +
    //       '    "content": [' +
    //       '        {' +
    //       '            "name": "folder",' +
    //       '            "type": "folder",' +
    //       '            "size": 29081154,' +
    //       '            "lastModified": "21/07/2019 15:47:08",' +
    //       '            "createDate": "18/07/2019 17:37:22"' +
    //       '        },' +
    //       '        {' +
    //       '            "name": "folder2",' +
    //       '            "type": "folder",' +
    //       '            "size": 26248994,' +
    //       '            "lastModified": "21/07/2019 15:35:16",' +
    //       '            "createDate": "21/07/2019 12:06:19"' +
    //       '        },' +
    //       '        {' +
    //       '            "name": "test.txt",' +
    //       '            "type": "txt",' +
    //       '            "size": 59,' +
    //       '            "lastModified": "21/07/2019 19:24:33",' +
    //       '            "createDate": "21/07/2019 19:24:24"' +
    //       '        },' +
    //       '        {' +
    //       '            "name": "test2.txt",' +
    //       '            "type": "txt",' +
    //       '            "size": 59,' +
    //       '            "lastModified": "21/07/2019 19:24:33",' +
    //       '            "createDate": "21/07/2019 19:25:56"' +
    //       '        },' +
    //       '        {' +
    //             '            "name": "folder6",' +
    //             '            "type": "folder",' +
    //             '            "size": 29081154,' +
    //             '            "lastModified": "21/07/2019 15:47:08",' +
    //             '            "createDate": "18/07/2019 17:37:22"' +
    //             '        },' +
    //             '        {' +
    //             '            "name": "folder7",' +
    //             '            "type": "folder",' +
    //             '            "size": 26248994,' +
    //             '            "lastModified": "21/07/2019 15:35:16",' +
    //             '            "createDate": "21/07/2019 12:06:19"' +
    //             '        },' +
    //             '        {' +
    //             '            "name": "test6.txt",' +
    //             '            "type": "txt",' +
    //             '            "size": 59,' +
    //             '            "lastModified": "21/07/2019 19:24:33",' +
    //             '            "createDate": "21/07/2019 19:24:24"' +
    //             '        }' +
    //       '    ]' +
    //       '}')
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
