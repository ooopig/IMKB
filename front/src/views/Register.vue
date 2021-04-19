<template>
  <div class="register">
    <el-card class="register_container">
      <div slot="header" style="display: flex;justify-content: center">
        <span style="font-size: larger">欢迎您注册智能制造知识库系统</span>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="telephone">
          <el-input v-model="ruleForm.telephone"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" >
          <el-input type="password" show-password v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" show-password v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="ruleForm.role" placeholder="普通用户" disabled>
            <el-option label="管理员" value="shanghai"></el-option>
            <el-option label="知识专家" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" v-model="ruleForm.address" placeholder="xxxxxx"></el-input>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
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
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        id:'1000',
        name: '',
        phone:'',
        telephone:'',
        username:'',
        password:'',
        checkPass:'',
        address:'',
        role:'',
        enabled:true,
        userFace:'http://files.bigpigpeiqi.cn/2021/03/27/7aa4eb744023feb5805341ffde728373.jpg',
        remark:'',
        contribution:0
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入用户', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { min: 11, max: 11, message: '请输入11位手机号', trigger: 'blur' }
        ],
        password: [{validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //alert('submit!');
          this.postRequest('/java/register',this.ruleForm)
              .then(resp=>{
                if(resp){
                  //更新成功后，返回登录界面
                  window.sessionStorage.removeItem('user');
                  window.sessionStorage.removeItem('tokenStr');
                  this.$store.commit("initRoutes",[]);
                  this.$router.replace('/');
                }
              })
        } else {
          console.log('注册失败');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.register{
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
  margin: -8px;
  background-image: url("../images/bakg (2).png");
  background-size:cover;
  display: flex;
  align-items: center;
}
.register_container{
  border-radius: 15px;
  background-clip: padding-box;
  width: 500px;
  margin: 0px auto;
  background: #f5f3f3;
  border: 1px solid #eaeaea;
//box-shadow: 0 0 25px #cac6c6;
  padding: 15px 35px 15px 35px;
}
</style>
