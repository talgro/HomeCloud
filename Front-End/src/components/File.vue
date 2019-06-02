<template>
<!--  <div>-->
    <p v-on:click="navigate">{{ node.name }}</p>
<!--    <button v-on:click="this.node.deleteFile()">[del]</button>-->
<!--  </div>-->
</template>

<script>
export default {
  name: 'File',
  methods: {
    navigate () {
      this.$emit('changeNode', this.node)
    }
  },
  props: [
    'node'
  ]
}
</script>

<style scoped>

</style>
