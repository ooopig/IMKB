<template>
  <div class="background">
      <div class="login_container">
        <el-form
            :model="loginForm"
            :rules="rules"
            ref="loginform"
        >
          <h3 style="margin: 0 auto;text-align: center;margin-bottom: 10px;">系统登录</h3>
          <el-form-item prop="username">
            <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名" auto-complete="false">

            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" show-password auto-complete="false">

            </el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div class="xxx">
              <el-input  v-model="loginForm.code" placeholder="点击图片验证码" auto-complete="false" style="width: 250px;margin-right: 5px;">

              </el-input>
              <img :src="captchaUrl"  @click="updateCaptcha">
            </div>
          </el-form-item>

          <el-checkbox v-model="loginForm.checked" class="rember">记住我</el-checkbox>
          <br>
          <el-button type="primary" style="width: 100%" @click="submitForm">登录</el-button>
        </el-form>
        <div  @click="regist">
          <a href="javascript:;"  class="rrr" style="text-decoration: none">没有账号？点我进行注册</a>
        </div>
      </div>
  </div>
</template>

<script>
import {postRequest} from "../utils/api";

export default {
  name: "Login",
  data(){
    var validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'));
      } else {
        if (this.loginForm.username !== '') {

        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if(this.loginForm.password !== ''){

        }
      }
      callback();
    };
    var validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else {
        if(this.loginForm.code !== ''){

        }
      }
      callback();
    };
    return{
      //captchaUrl:"http://localhost:8081/captcha?time="+new Date(),
      captchaUrl:"/captcha?time="+new Date(),
      checked: false,
      loading:false,
      loginForm: {
        username: '',
        password: '',
        code: '',
      },
      rules:{
        username:[{validator:validateName,trigger:'blur'}],
        password: [{validator:validatePass,trigger:'blur'}],
        code:[{validator:validateCode,trigger:'blur'}]
      }
    }
  },

  methods:{
    updateCaptcha(){
      //this.captchaUrl = "http://localhost:8081/captcha?time="+new Date();
      this.captchaUrl = "/captcha?time="+new Date();
    },
    regist(){
      this.$router.replace("/register");
    },
    submitForm() {
      this.loading=true;
      this.$refs.loginform.validate((valid) => {
        if (valid) {
          //alert('submit!');
          this.loading=false;
          console.log(this.loginForm);
          postRequest('/login',this.loginForm)
              .then(response=>{
                //alert(response);
                if(response){

                  const tokenStr = response.obj.tokenHead+response.obj.token;
                  window.sessionStorage.setItem('tokenStr',tokenStr); // token存储到session中
                  //跳转首页,不可回退
                  console.log('登陆成功，跳转首页+'+ this.$route.query.redirect)
                  let path = this.$route.query.redirect;
                  this.$router.replace((path=='/' || path == undefined)?'/home':path);
                  //this.$router.replace('/home')
                }
              })
        } else {
          alert('error submit!!');
          return false;
        }
      });
    },

  },
  mounted() {
    let that = this
    document.onkeydown =function(e){
      e = window.event || e;
      if(that.$route.path=='/'&&(e.code=='Enter'||e.code=='enter')){//验证在登录界面和按得键是回车键enter
        that.submitForm();//登录函数
      }
    }
  }

}
</script>

<style scoped>
.background{
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
  margin: -8px;
  background-image: url("../images/bakg (2).png");
  background-size:cover;

}

.login_container{
  border-radius: 15px;
  background-clip: padding-box;
  width: 350px;
  margin: 180px auto;
  background: #f5f3f3;
  border: 1px solid #eaeaea;
  //box-shadow: 0 0 25px #cac6c6;
  padding: 15px 35px 15px 35px;
}
.xxx{
  display: flex;
  align-items: center;
}
.rember{
  text-align: left;
  margin: 0px 0px 15px 0px;
}
.rrr{
  font-size: smaller;
  color: gray;
  margin-top: 5px;
}
</style>
