<template>
  <div>
    <p v-on:click="navigate">{{ node.name }}</p>
    <button v-on:click="deleteFile">delete {{ node.name }}</button>
  </div>
</template>

<script>
export default {
  name: 'File',
  methods: {
    navigate () {
      this.$emit('changeNode', this.node)
    },
    deleteFile () {
      this.node.deleteFile()
    }
  },
  props: [
    'node'
  ]
}
</script>

<style scoped>

</style>
