import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export const store = new Vuex.Store({
  state: {
    // TODO: add option to get the serverDomain from AWS
    serverDomain: '',
    loggedIn: false,
    userInfo: {
      username: ''
    },
    mostFrequent: [ { url: 'root/folder1/folder2/file1.txt' }, { url: '' }, { url: '' }, { url: '' }, { url: '' } ]
  },
  getters: {
    getUsername: state => {
      return state.userInfo.username
    },
    getMostFrequent: state => {
      return state.mostFrequent
    },
    getLoggedIn: state => {
      return state.loggedIn
    }
  },
  mutations: {
    setUsername: (state, newUsername) => {
      state.userInfo.username = newUsername
    },
    pushFrequent: (state, url) => {
      let found = false
      for (let i = 0; i < state.mostFrequent.length; ++i) {
        if (state.mostFrequent[i].url === url) {
          found = true
          break
        }
      }
      if (!found) {
        for (let i = 0; i < state.mostFrequent.length - 1; ++i) {
          state.mostFrequent[i + 1] = state.mostFrequent[i]
        }
        state.mostFrequent[0] = { url: url }
      }
    },
    setLoggedIn: (state, value) => {
      state.loggedIn = value
    }
  },
  actions: {
    //
  }
})
