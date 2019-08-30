import Vue from 'vue'
import Vuex from 'vuex'
import VueResource from 'vue-resource'

Vue.use(Vuex)
Vue.use(VueResource)

export const store = new Vuex.Store({
  state: {
    backendURL: '',
    serverInfo: {
      serverName: '',
      serverAddress: ''
    },
    loggedIn: false,
    userInfo: {
      userId: ''
    },
    mostFrequent: [ { url: 'root/folder1/folder2/file1.txt' }, { url: 'root/folder1/folder2/file2.txt' }, { url: 'root/folder1/folder2/file3.txt' }, { url: '' }, { url: '' } ]
  },
  getters: {
    getBackendURL: state => {
      return state.backendURL
    },
    getUsername: state => {
      return state.userInfo.userId
    },
    getMostFrequent: state => {
      return state.mostFrequent
    },
    getLoggedIn: state => {
      return state.loggedIn
    },
    getCookie: state => {
      let cookies = document.cookie.split(';')
      for (let i = 0; i < cookies.length; ++i) {
        let cookie = cookies[i].split('=')
        if (cookie[0].includes('accessToken')) {
          return cookie[1]
        }
      }
    },
    getServerAddress: state => {
      return state.serverInfo.serverAddress
    },
    getServerName: state => {
      return state.serverInfo.serverName
    }
  },
  mutations: {
    setUserId: (state, newUserId) => {
      state.userInfo.userId = newUserId
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
    },
    setServerAddress: (state, value) => {
      state.serverInfo.serverAddress = value
    },
    setServerName: (state, newServerName) => {
      state.serverInfo.serverName = newServerName
    }
  }
})
