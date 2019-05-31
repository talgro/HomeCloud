/* eslint-disable */
<template>
    <div id="home-page">
      <h1 v-on:click="test">THIS IS THE HOMEPAGE</h1>
      <h1>{{ curr_folder.name }}</h1>
      <file
        v-for="node in curr_folder.children"
        v-bind:node="node" v-bind:key="node.name"
        v-on:changeNode="curr_folder = $event">
      </file>
    </div>
</template>

<script>
import Header from './Header.vue'
import TreeNode from '../Classes/TreeNode.js'
import Tree from '../Classes/Tree.js'
import File from './File.vue'

export default {
  data () {
    return {
      curr_folder: {},
      files_tree: {}
    }
  },
  methods: {
    test: function (event) {
      console.log('test22')
    }
  },
  created () {
    // TODO: replace custom json to json from server.
    this.files_tree = new Tree()
    let file2 = new TreeNode('file2', [])
    let folder2 = new TreeNode('folder2', [file2])
    let folder3 = new TreeNode('folder3', [])
    let folder1 = new TreeNode('folder1', [folder2, folder3])
    let file1 = new TreeNode('file1', [])
    this.files_tree.root.addChild(folder1)
    this.files_tree.root.addChild(file1)

    /* this.files_tree = new Tree(
      {
        name: 'root',
        children: [
          {
            name: 'folder1',
            children: [
              {
                name: 'folder2',
                children: [
                  {
                    name: 'file2',
                    children: []
                  }
                ]
              },
              {
                name: 'folder3',
                children: []
              }
            ]
          },
          {
            name: 'file1',
            children: []
          }
        ]
      }
    ) */
    this.curr_folder = this.files_tree.root
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
