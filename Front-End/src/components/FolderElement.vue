<template>
    <div id="folder">
      <p>{{ element.name }}</p>
    </div>
</template>

<script>
export default {
  name: 'FolderElement',
  data () {
    return {
      contents: []
    }
  },
  props: [
    'element'
  ]
}
</script>

<style scoped>

</style>
