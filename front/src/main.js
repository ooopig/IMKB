import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from "axios";
import 'font-awesome/css/font-awesome.css';
import Meta from 'vue-meta';


import './js/ueditor/ueditor.config.js'
import './js/ueditor/ueditor.all.min.js'
import './js/ueditor/lang/zh-cn/zh-cn.js'
import './js/ueditor/ueditor.parse.min.js'

import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
import {initMenu} from "@/utils/menu";

//引入bmob
import Bmob from 'hydrogen-js-sdk'
//初始化
//rest key 0ebf8f1579a34d75979f66f473b31819
//1044ee2f21839bbdea534d54b96a4d1c
Bmob.initialize('c05ef2cc5739c911','292711')

// axios.defaults.baseURL="/"

// Vue.prototype.pythonAxios = axios.create({
//   baseURL:'/python'
// })

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(Meta);

Vue.prototype.postRequest = postRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  if (window.sessionStorage.getItem("tokenStr")) {
    initMenu(router, store);
    if (!window.sessionStorage.getItem("user")) {
      return getRequest('/java/user/info').then(resp => {
        if (resp) {
          //存入用户信息
          window.sessionStorage.setItem("user", JSON.stringify(resp));
          store.commit("initUser",resp);
          console.log("存入用户信息");
          next();
        }
      });
    }
    next();
  } else {
    next();
  }
})

new Vue({
  router,
  store,
  //定义metaInfo
  metaInfo(){
    return{
      meta:[
        {
          name:'referrer',
          content:'no-referrer'
        }
      ]
    }
  },
  render: h => h(App)
}).$mount('#app')
