<template>
  <div id="item">
    <v-card class="ma-2 pa-1 unselectable" hover @dblclick="itemClicked">
      <v-container class="ma-0 pa-0">
        <v-layout justify-start align-start row>
          <v-flex xs1>
            <v-icon v-if="element.type === 'folder'" color="yellow darken-3" size="80">folder</v-icon>
            <v-icon v-else size="80" color="grey lighten-1">insert_drive_file</v-icon>
          </v-flex>
        </v-layout>
      </v-container>
      <v-card-title primary-title class="ma-1 pa-1">
        <div class="text-truncate">
          <span class="font-weight-bold">Name:</span>
          {{ this.element.name }}<br>
          <span class="font-weight-bold">Size:</span>
          {{ this.element.size }}
        </div>
      </v-card-title>
      <v-divider></v-divider>
      <v-card-actions class="px-1">
        <v-btn flat small @click="editDialog = true">
          <v-icon>edit</v-icon>
        </v-btn>
        <v-btn flat small @click="deleteDialog = true">
          <v-icon>delete</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
    <v-dialog v-model="deleteDialog" width="400">
      <v-card>
        <v-card-title class="headline" primary-title>
          Are you sure you want to delete this item?
        </v-card-title>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn flat @click="deleteItem">
            Yes
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn flat @click="deleteDialog = false">
            No
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="editDialog" width="400">
      <v-card>
        <v-card-title class="headline" primary-title>
          Enter a new name:
        </v-card-title>
        <v-divider></v-divider>
        <v-text-field v-model="newName" label="Enter a new name" single-line></v-text-field>
        <v-card-actions>
          <v-btn flat @click="editName">
            Apply
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn flat @click="editDialog = false">
            Cancel
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  name: 'File',
  data () {
    return {
      deleteDialog: false,
      editDialog: false,
      newName: ''
    }
  },
  methods: {
    itemClicked () {
      if (this.element.type === 'folder') {
        this.$emit('FolderClicked', { name: this.element.name })
      } else {
        this.$emit('FileClicked', { name: this.element.name })
      }
    },
    deleteItem () {
      this.$emit('DeleteItem', { name: this.element.name })
      this.deleteDialog = false
    },
    editName () {
      this.$emit('EditName', { name: this.element.name, newName: this.newName })
      this.editDialog = false
    }
  },
  props: [
    'element'
  ]
}
</script>

<style scoped>
.v-btn {
  min-width: 0;
}
.unselectable {
 -webkit-user-select:none;
 -khtml-user-select:none;
 -moz-user-select:none;
 -ms-user-select:none;
 -o-user-select:none;
 user-select:none;
}
</style>
