/* eslint-disable */
<template>
  <p v-on:click="emitNode">{{ node.name }}</p>
</template>

<script>
export default {
  name: 'File',
  props: [
    'node'
  ],
  methods: {
    emitNode () {
      this.$emit('changeNode', this.node)
    }
  }
}
</script>

<style scoped>

</style>
