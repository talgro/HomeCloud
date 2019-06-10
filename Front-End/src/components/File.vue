<template>
  <div>
    <p v-on:click="elementClicked">{{ element.name }}</p>
    <button v-on:click="deleteElement">delete {{ element.name }}</button>
  </div>
</template>

<script>
export default {
  name: 'File',
  methods: {
    elementClicked () {
      this.$emit('elementClicked', this.element.name)
    },
    deleteElement () {
      this.node.deleteElement()
    }
  },
  props: [
    'element'
  ]
}
</script>

<style scoped>

</style>
