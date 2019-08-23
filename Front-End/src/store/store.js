import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    userInfo: {
      username: ''
    },
    JWT: ''
  },
  getters: {
    getJWT: state => {
      return state.JWT
    },
    getUsername: state => {
      return state.userInfo.username
    }
  },
  mutations: {
    setUsername: (state, newUsername) => {
      state.userInfo.username = newUsername
    },
    setJWT: (state, newJWT) => {
      state.JWT = newJWT
    }
  },
  actions: {
    updateJWT: ({ commit }) => {
      // TODO: add tals' endpoint to get jwt from
      let newJWT = ''
      commit('setJWT')
    }
  }
})
