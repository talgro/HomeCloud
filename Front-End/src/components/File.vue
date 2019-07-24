<template>
  <div id="file">
<!--    <v-flex xs6 sm4 md3>-->
      <v-card class="pl-2 ma-2">
        <v-layout align-center row>
          <v-flex xs3>
            <v-icon
              x-large>
              folder
            </v-icon>
          </v-flex>
          <v-layout column>
            <div>
              <span class="font-weight-bold">Name: </span>
              <span>{{ this.element.name }}</span>
            </div>
            <div>
              <span class="font-weight-bold">Type:</span>
              <span>{{ this.element.type }}</span>
            </div>
            <div>
              <span class="font-weight-bold">Size: </span>
              <span>{{ this.element.size }}</span>
            </div>
          </v-layout>
        </v-layout>
      </v-card>
<!--    </v-flex>-->
<!--    <v-layout>-->
<!--      <v-flex xs6 sm4 md3>-->
<!--        <v-card>-->
<!--          <v-layout align-center row>-->
<!--            <v-flex>-->
<!--              <v-icon-->
<!--                x-large>-->
<!--                folder-->
<!--              </v-icon>-->
<!--            </v-flex>-->
<!--            <v-layout column>-->
<!--              <span>{{ this.element.name }}</span>-->
<!--              <span>Type: {{ this.element.type }}</span>-->
<!--              <span>Size: {{ this.element.size }}</span>-->
<!--            </v-layout>-->
<!--          </v-layout>-->
<!--        </v-card>-->
<!--      </v-flex>-->
<!--    </v-layout>-->
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
