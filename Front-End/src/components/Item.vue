<template>
  <div id="item">
    <v-card class="ma-2 pa-1" hover>
      <v-container class="ma-0 pa-0" @dblclick="itemClicked">
        <v-layout justify-start align-start row>
          <v-flex xs1>
            <v-icon size="80">folder</v-icon>
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
        <v-btn flat small>
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
          <v-btn flat @click="deleteItem()">
            Yes
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn flat @click="deleteDialog = false">
            No
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
      deleteDialog: false
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
    }
  },
  props: [
    'element'
  ]
}
</script>

<style scoped>
.v-btn {
  min-width: 0px;
}
</style>
