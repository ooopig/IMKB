import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    routes:[],
    users:[],
    currentUser:JSON.parse(window.sessionStorage.getItem('user'))
  },
  mutations: {
    initRoutes(state,data){
      state.routes = data;
    },
    initUser(state,user){
      state.currentUser = user;
    }
  },
  actions: {
  },
  modules: {
  }
})
