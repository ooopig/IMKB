import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from "@/views/Login";
import Register from "@/views/Register";
import UserInfo from "@/views/user/UserInfo";
import KG from "@/views/kb/KbKG";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    meta:{
      //
      title: '智能制造知识工程系统'
    },
    component: Login,
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta:{
      //
      title: '智能制造知识工程系统'
    },
    children:[
      {
        path:'/userinfo',
        name:'个人中心',
        meta:{
          //
          title: '智能制造知识工程系统'
        },
        component:UserInfo,
      }
    ],
  },
  {
    path: '/register',
    name: 'Register',
    meta:{
      //
      title: '智能制造知识工程系统'
    },
    component: Register,
  },
  // {
  //   path:'/*', // 注意，这里不是嵌套路由了，这是为了设置404页面，一定要放在最后面，这样当服务器找不到页面的时候就会全部跳转到404
  //   name:'four04',
  //   component: () => import('../views/error/four04.vue')
  // }
]

const router = new VueRouter({
  routes
})

/**
 * 解决router.addRoutes动态添加路由，不把原有的路由删除的问题
 * @param params
 */
// router.$addRoutes = (params) => {
//   router.matcher = new Router({mode: 'history'}).matcher;
//   router.addRoutes(params)
// }

// 在 router/index.js 中 将 addRouter 方法封装一下

// router.$addRoutes = (params) => {
//   router.matcher = new VueRouter({
//     mode: 'history',
//     routes: router.options.routes
//   }).matcher
//   router.addRoutes(params)
// }


// const createRouter = () => new VueRouter({
//   mode: 'history',
//
//   routes: routes
//
// })

// const router = createRouter()
//
// export function resetRouter () {
//   const newRouter = createRouter()
//
//   router.matcher = newRouter.matcher // the relevant part
//
// }

export default router


