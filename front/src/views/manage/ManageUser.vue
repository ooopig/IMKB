<template>
  <div>
<!--    顶部搜索栏-->
    <div style="display: flex;flex-direction: row;justify-content: space-between; padding: 2px;border-bottom: #e3e3e3 solid 1px">
      <div >
        <el-select
            v-model="para.role"
            size="small"
            style="width: 150px"
            clearable
            @clear="initUsers"
            placeholder="请选择用户角色">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
        <el-input clearable
                  v-model="para.name"
                  @clear="initUsers"
                  size="small"
                  style="width: 200px;margin-right: 10px;"
                  prefix-icon="el-icon-search"
                  placeholder="请输入用户名">
        </el-input>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
<!--        <el-button size="small" type="primary">-->
<!--          <i class="fa fa-angle-double-down" aria-hidden="true"></i>-->
<!--          高级搜索-->
<!--        </el-button>-->
      </div>
      <div style="margin-right: 5px">
<!--        <el-button size="small" type="success">-->
<!--          <i class="fa fa-level-up" aria-hidden="true"></i>-->
<!--          导入数据-->
<!--        </el-button>-->
        <el-button size="small" type="success" @click="exportUsers">
          <i class="fa fa-level-up" aria-hidden="true"></i>
          导出数据
        </el-button>
      </div>
    </div>
    <!--    信息展示栏-->
    <div style="width: 100%;">
      <el-table
      :data="users"
      stripe
      border
      style="width: 100%">
        <el-table-column
            type="selection"
            width="60">
        </el-table-column>
        <el-table-column
            prop="id"
            label="编号"
            width="100"
            fixed
            align="center">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            fixed
            width="150"
            align="center">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话"
            width="150"
            align="center">
        </el-table-column>
        <el-table-column
            prop="telephone"
            label="手机"
            width="150"
            align="center">
        </el-table-column>
        <el-table-column
            prop="role"
            label="角色"
            width="150"
            align="center">
        </el-table-column>
        <el-table-column
            prop="contribution"
            label="贡献"
            width="150"
            align="center">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址"
            width="250"
            align="center">
        </el-table-column>
        <el-table-column
            prop="operate"
            label="操作"
            fixed="right"
            width="150"
            align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="editUser(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteUser(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 5px; display: flex;justify-content: center;">
        <el-pagination
            background
            layout="total, prev, pager, next"
            :total="totalUser"
            @current-change="changePage"
        >
        </el-pagination>
      </div>

    </div>
<!--    修改用户信息dialog-->
    <el-dialog
        title="编辑用户信息"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <table>
          <tr>
            <td>用户名称</td>
            <td><el-input v-model="user.name"></el-input></td>
          </tr>
          <tr>
            <td>电话号码</td>
            <td><el-input v-model="user.telephone"></el-input></td>
          </tr>
          <tr>
            <td>手机号码</td>
            <td><el-input v-model="user.phone"></el-input></td>
          </tr>
          <tr>
            <td>用户地址</td>
            <td><el-input v-model="user.address"></el-input></td>
          </tr>
          <tr>
            <td>用户角色</td>
            <td>
              <el-select
                  v-model="user.role"
                  size="small"
                  placeholder="请选择用户角色">
                <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
      <el-button @click="cancleUpdateInfo">取 消</el-button>
      <el-button type="primary" @click="sureUpdateInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: "ManageUser",
  data(){
    return{
      users:[],
      totalUser:0,
      para:{
        currentPage:1,
        size:10,
        name:'',
        role:'',
      },
      options:
          [
          {
        value: '普通用户',
        label: '普通用户'
      }, {
        value: '知识贡献者',
        label: '知识贡献者'
      }, {
        value: '知识专家',
        label: '知识专家'
      }, {
        value: '系统管理员',
        label: '系统管理员'
      }
      ],
      options2: [
        {
          value: 901,
          label: '普通用户'
        }, {
          value: 902,
          label: '知识贡献者'
        }, {
          value: 903,
          label: '知识专家'
        }, {
          value: 900,
          label: '系统管理员'
        }
      ],
      dialogVisible:false,
      user:{},
    }
  },
  methods:{
    initUsers(){
      let that = this
      this.getRequest('/manage/user/getUsers?currentPage='+that.para.currentPage+
          '&size='+that.para.size+
          '&name='+that.para.name+
          '&role='+that.para.role+
          '&contribution='+that.para.contribution
      ).then(resp=>{
            if(resp){
              that.users = resp.data
              that.totalUser = resp.total
              //this.$set(this,users,resp.data)
            }
      })
    },
    search(){
      this.initUsers();
    },
    changePage(currentPage){
      this.para.currentPage = currentPage
      this.initUsers()
    },
    prevPage(){
      this.para.currentPage--
      this.initUsers()
      //console.log(this.para)
      // this.getRequest('/manage/user/getUsers',this.para).then(resp=>{
      //   if(resp){
      //     this.users = resp.data
      //     this.totalUser = resp.total
      //   }
      // })

    },
    nextPage(){
      this.para.currentPage++
      this.initUsers()
      //console.log(this.para)
      // this.getRequest('/manage/user/getUsers',this.para).then(resp=>{
      //   if(resp){
      //     this.users = resp.data
      //     console.log(resp.data);
      //     this.totalUser = resp.total
      //   }
      // })
    },
    editUser(user){
      let that = this
      that.dialogVisible = true
      that.user = user
      console.log(user);
    },
    deleteUser(user){
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/manage/user/delUser/'+user.id)
            .then(resp=>{
              if(resp){
                this.initUsers()
                // this.$message({
                //   message: '删除成功',
                //   type: 'success'
                // });
              }
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


      console.log(user);
    },
    cancleUpdateInfo(){
      this.dialogVisible = false
    },
    sureUpdateInfo(){
      console.log(this.user)
      let that = this
      this.putRequest("/manage/user/update/",this.user)
      .then(resp=>{
        if(resp){
          this.initUsers()
        }
      })

      this.dialogVisible = false
    },
    exportUsers(){
      this.getRequest("/manage/user/getAllUsers").then(resp=>{
        if(resp){
          //要导出的json数据
          const jsonData = resp.obj
          //console.log(jsonData)
          //列标题，逗号隔开，每一个逗号就是隔开一个单元格
          //let str = `姓名,电话,邮箱\n`;
          let str='';
          for(let k in jsonData[0]){
              str+=`${k},`;

          }
          str+='\n';
          //增加\t为了不让表格显示科学计数法或者其他格式
          for(let i = 0 ; i < jsonData.length ; i++ ){
            for(let item in jsonData[i]){
              str+=`${jsonData[i][item] + '\t'},`;
            }
            str+='\n';
          }
          //encodeURIComponent解决中文乱码
          let uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
          //通过创建a标签实现
          let link = document.createElement("a");
          link.href = uri;
          //对下载的文件命名
          link.download =  "用户数据.csv";
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        }
      })



    },
  },
  mounted() {
    this.initUsers();
  }
}
</script>

<style scoped>

</style>
