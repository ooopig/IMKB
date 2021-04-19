<template>
  <div>
    <el-card class="box-card" style="width: 400px">
      <div slot="header" class="clearfix">
        <span>{{ user.name }}</span>
      </div>
      <div>
        <div style="display: flex;justify-content: center;margin-bottom: 10px">
          <input type="file"
                 ref="inputFile"
                 style="display: none"
                 @change="uploadHeader($event)"
                 accept="image/png,image/gif,image/jpg,image/jpeg">
          <img :src="user.userFace" alt="" title="点击修改头像" @click="openFile" style="height: 100px;width: 100px;border-radius: 50%">
        </div>
        <div>电话号码：<el-tag>{{user.telephone}}</el-tag></div>
        <div>手机号码：<el-tag>{{user.phone}}</el-tag></div>
        <div>居住地址：<el-tag>{{user.address}}</el-tag></div>
        <div>知识贡献：<el-tag>{{user.contribution}}</el-tag></div>
        <div>
          用户角色：
          <el-tag type="success" v-for="(r,index) in user.roles" :key="index" >
            {{r.name_zh}}
          </el-tag>
        </div>
        <div style="display: flex;justify-content: space-around;margin-top: 10px">
          <el-button @click="showDialogInfo">修改信息</el-button>
          <el-button type="danger" @click="showUpdatePassword">修改密码</el-button>
        </div>
      </div>
    </el-card>

    <el-dialog
        title="编辑用户信息"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <table>
          <tr>
            <td>用户名称</td>
            <td><el-input v-model="newUser.name"></el-input></td>
          </tr>
          <tr>
            <td>电话号码</td>
            <td><el-input v-model="newUser.telephone"></el-input></td>
          </tr>
          <tr>
            <td>手机号码</td>
            <td><el-input v-model="newUser.phone"></el-input></td>
          </tr>
          <tr>
            <td>用户地址</td>
            <td><el-input v-model="newUser.address"></el-input></td>
          </tr>
        </table>
      </div>

      <span slot="footer" class="dialog-footer">
      <el-button @click="cancleUpdateInfo">取 消</el-button>
      <el-button type="primary" @click="SureUpdateInfo">确 定</el-button>
    </span>
    </el-dialog>

    <el-dialog
        title="编辑用户信息"
        :visible.sync="passwordDialogVisible"
        width="30%">
      <div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="旧密码" prop="oldPass" >
            <el-input type="password" show-password v-model="ruleForm.oldPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPass" >
            <el-input type="password" show-password v-model="ruleForm.newPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" show-password v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

    </el-dialog>
  </div>
</template>

<script>
import {putRequest} from "@/utils/api";

export default {
  name: "UserInfo",

  data(){
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.newPass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return{
      user: {},
      newUser:{},
      dialogVisible:false,
      passwordDialogVisible:false,
      headers:{
        Authorization:window.sessionStorage.getItem('tokenStr'),
      },

      ruleForm: {
        oldPass:"",
        newPass:'',
        checkPass:'',
      },
      rules: {
        oldPass:[
          {validator: validatePass, trigger: 'blur'}
        ],
        newPass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],

      }
    }
  },
  methods:{
    openFile(){
      this.$refs.inputFile.click();
    },
    uploadHeader(event){
      let that = this
      let pic = event.target.files;//target事件
      let file
      for(let item of pic){
        file = Bmob.File(item.name, item);
      }
      file.save().then(res => {
        console.log('头像上传成功');
        console.log(res);
        let imgUrl = res[0].url
        that.newUser.userFace = imgUrl
        that.SureUpdateInfo()
      })
    },
    initUser(){
      console.log('inituser')
      this.getRequest('/java/user/info')
          .then(resp=>{
            if(resp){
              console.log('拿到用户信息'+resp);
              this.user = resp;
              this.newUser= Object.assign({},this.user);
              window.sessionStorage.setItem('user',JSON.stringify(resp));
              this.$store.commit("initUser",resp);
            }
          })
    },
    showDialogInfo(){
      this.dialogVisible = true;
    },
    SureUpdateInfo(){
      putRequest('/java/user/update',this.newUser)
          .then(resp=>{
            if(resp){
              this.dialogVisible = false;
              this.initUser();
            }
          })
    },
    cancleUpdateInfo(){
      this.newUser = Object.assign({},this.user);
      this.dialogVisible = false;
    },
    showUpdatePassword(){
      this.passwordDialogVisible = true;
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.userId = this.user.id;
          this.ruleForm.pass = this.ruleForm.newPass;
          putRequest('/java/user/pass',this.ruleForm)
              .then(resp=>{
                if(resp){
                  //更新成功后，退出登录
                  this.postRequest('/java/logout');
                  window.sessionStorage.removeItem('user');
                  window.sessionStorage.removeItem('tokenStr');
                  this.$store.commit("initRoutes",[]);
                  this.$router.replace('/');
                }
              })
        } else {
          console.log('更新密码失败！');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  mounted() {
    this.initUser();
  }
}
</script>

<style scoped>

</style>
