<template>
  <div class="home">

    <el-container style="height: 100vh;width: 100vw">
      <!--头部-->
      <el-header class="home-header" style="height: 8%">
        <!--        头部左侧标题-->
        <div class="title">
          智能制造知识工程系统
        </div>

        <!--        头部右侧用户头像、信息-->
        <el-dropdown class="userInfo" @command="commandHandler">
          <span class="el-dropdown-link">
            欢迎您，<p style="color: white;font-size: large">{{user.name}}</p>
            <i><img :src="user.userFace"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
            <el-dropdown-item command="setting">设置</el-dropdown-item>
            <el-dropdown-item command="logout">注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <!--      下部容器-->
      <el-container style="height: 92%">
        <!--      左侧菜单栏-->
        <el-aside width="200px" style="background-color: #e9f2f8">
          <el-menu unique-opened router>
            <el-submenu  v-for="(item,index) in getRoutes" :key="index" :index="index+''"
                         style="background-color: #d2e7f5;border-bottom: #e3e3e3 solid 2px">
              <template slot="title"><i :class="item.iconcls" style="color: #8ae38a;margin-right: 5px;"></i>
                <span style="font-size: larger;">{{ item.name }}</span>
              </template>
              <el-menu-item-group style="background-color: #e9f2f8;">
                <el-menu-item style="border-bottom: #e3e3e3 solid 1px" :index="children.path" v-for="(children,indexj) in item.children" :key="indexj">
                  {{children.name}}
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <!--        右侧页面展示-->
        <el-main style="padding: 0px">
<!--          面包屑-->
          <el-breadcrumb
              separator-class="el-icon-arrow-right"
              style="margin-bottom: 3px;padding: 2px;margin-top: 5px"
              v-if="this.$router.currentRoute.path!='/home'">

            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
<!--            <el-breadcrumb-item v-for="(item, index) in breadList" :key="index"-->
<!--              :to="item.path"-->
<!--            >-->
<!--              {{-->
<!--                item.name-->
<!--              }}-->
<!--            </el-breadcrumb-item>-->

          </el-breadcrumb>

          <div v-if="this.$router.currentRoute.path=='/home'">
            <div class="homeWelcome" >
              欢迎来到智能制造知识工程系统！
            </div>
            <el-carousel :interval="4000" type="card" height="400px"  style=" margin-top: 70px;">
              <el-carousel-item v-for="item in 6" :key="item">
                <h3 class="medium">{{ item }}</h3>
              </el-carousel-item>
            </el-carousel>
          </div>

          <router-view/>
        </el-main>

      </el-container>
    </el-container>

  </div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      user:{},
      breadList:[

      ]
    };
  },
  methods:{
    menuClick(index){
      this.$router.push(index);
    },
    commandHandler(cmd) {
      if (cmd == 'logout') {
        this.$confirm('此操作将注销登录，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //注销
          this.postRequest("/java/logout");
          //清除用户信息
          window.sessionStorage.removeItem("user");
          window.sessionStorage.removeItem("tokenStr");
          //清空菜单
          this.$store.commit('initRoutes',[]);
          //跳转登录页
          this.$router.replace("/")
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
      }
      else if(cmd == 'userinfo'){
        this.$router.push('/userinfo');
      }
    },
    menu(){
      console.log(this.$router)
    },
    /**
     * @description 获取路由数组
     * @params val 路由参数
     * @author tw
     */
    getBreadList(val) {
      // 过滤路由matched对象
      if (val.matched) {
        let matched = val.matched.filter(item => item.path && item.path!='/home');
        // 拿到过滤好的路由v-for遍历出来
        //console.log('matched',matched)
        this.breadList = matched;
      }
    }
  },
  computed:{
    getRoutes(){
      let routes = this.$store.state.routes
      return routes;
    },
    getUser(){
      return this.$store.state.currentUser;
    },

  },
  mounted() {
    //console.log(this.$store.state.currentUser.userFace);
    this.user = this.$store.state.currentUser;
  },
  watch:{
    $route(val){
      this.getBreadList(val);
    }
  }
}
</script>

<style scoped>
.home-header{
  background: #409eff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;
}
.home-header .title{
  font-size: 30px;
  font-family: 华文楷体;
  color: white;
}
.home-header .userInfo{
  cursor: pointer;
}
.el-dropdown-link img{
  width: 48px;
  height: 48px;
  border-radius: 50%;
  margin-left: 20px;

}
.el-dropdown-link {
  display: flex;
  align-items: center;
  text-align: center;
}
.homeWelcome{
  text-align: center;
  font-size: 30px;
  font-family: 华文行楷;
  color: #409eff;
  padding-top: 50px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
