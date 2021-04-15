<template>
  <div>
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="节点管理" name="first">
<!--          顶部搜索栏-->
          <div style=" display: flex;flex-direction: row;justify-content: space-between; padding: 2px;border-bottom: #e3e3e3 solid 1px">
            <div >
              <span style="margin-right: 3px;">
                搜索节点:
              </span>
              <el-select
                  v-model="nodePara.level"
                  size="small"
                  style="width: 150px"
                  clearable
                  @clear="initNodes"
                  placeholder="请选择节点等级">
                <el-option
                    v-for="item in levelOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
              <el-input clearable
                        v-model="nodePara.name"
                        @clear="initNodes"
                        size="small"
                        style="width: 200px;margin-right: 10px;"
                        prefix-icon="el-icon-search"
                        placeholder="请输入节点名">
              </el-input>
<!--              <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>-->

              <span style="margin-right: 3px;margin-left: 3px;">
                搜索创建人:
              </span>
              <el-input clearable
                        v-model="nodePara.createUser"
                        @clear="initNodes"
                        size="small"
                        style="width: 200px;margin-right: 10px;"
                        prefix-icon="el-icon-search"
                        placeholder="请输入创建人姓名">
              </el-input>
              <el-button size="small" type="primary" icon="el-icon-search" @click="searchNodes">搜索</el-button>


            </div>

<!--            <div style="margin-right: 5px">-->
<!--              <el-button size="small" type="success" @click="exportNodes">-->
<!--                <i class="fa fa-level-up" aria-hidden="true"></i>-->
<!--                导出数据-->
<!--              </el-button>-->
<!--            </div>-->
          </div>
<!--          节点信息展示-->
          <div style="width: 100%;">
            <el-table
                :data="nodes"
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
                  width="80"
                  fixed
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="name"
                  label="名称"
                  fixed
                  width="180"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="level"
                  label="等级"
                  width="80"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="createUser"
                  label="创建人"
                  width="150"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="createTime"
                  label="创建时间"
                  width="200"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="modifyUser"
                  label="修改人"
                  width="150"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="modifyTime"
                  label="修改时间"
                  width="200"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="status"
                  label="节点状态"
                  width="80"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="operate"
                  label="操作"
                  fixed="right"
                  width="150"
                  align="center">
                <template slot-scope="scope">
                  <el-button size="mini" @click="editNode(scope.row)">编辑</el-button>
                  <el-button size="mini" type="danger" @click="deleteNode(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 5px; display: flex;justify-content: center;">
              <el-pagination
                  background
                  layout="total, prev, pager, next"
                  :total="totalNodes"
                  @current-change="nodeChangePage"
              >
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="关系管理" name="second">
<!--          顶部搜索栏-->
          <div style=" display: flex;flex-direction: row;justify-content: space-between; padding: 2px;border-bottom: #e3e3e3 solid 1px">
            <div >
              <el-input clearable
                        v-model="relationPara.name"
                        @clear="initRelations"
                        size="small"
                        style="width: 200px;margin-right: 10px;"
                        prefix-icon="el-icon-search"
                        placeholder="请输入关系名">
              </el-input>
              <el-button size="small" type="primary" icon="el-icon-search" @click="searchRelations">搜索</el-button>
            </div>
          </div>
          <!--          关系信息展示-->
          <div style="width: 100%;">
            <el-table
                :data="relations"
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
                  label="名称"
                  fixed
                  width="200"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="weight"
                  label="权重"
                  width="100"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="createUserName"
                  label="创建人"
                  width="150"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="createTime"
                  label="创建时间"
                  width="200"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="modifyUserName"
                  label="修改人"
                  width="150"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="modifyTime"
                  label="修改时间"
                  width="200"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="operate"
                  label="操作"
                  fixed="right"
                  width="150"
                  align="center">
                <template slot-scope="scope">
                  <el-button size="mini" @click="editRelation(scope.row)">编辑</el-button>
                  <el-button size="mini" type="danger" @click="deleteRelation(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 5px; display: flex;justify-content: center;">
              <el-pagination
                  background
                  layout="total, prev, pager, next"
                  :total="totalRelations"
                  @current-change="nodeChangePage"
              >
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="文件管理" name="third">
          <!--          顶部搜索栏-->
          <div style=" display: flex;flex-direction: row;justify-content: space-between; padding: 2px;border-bottom: #e3e3e3 solid 1px">
            <div >
              <el-input clearable
                        v-model="filePara.name"
                        @clear="initFiles"
                        size="small"
                        style="width: 200px;margin-right: 10px;"
                        prefix-icon="el-icon-search"
                        placeholder="请输入文件名">
              </el-input>
              <el-input clearable
                        v-model="filePara.createUserName"
                        @clear="initFiles"
                        size="small"
                        style="width: 200px;margin-right: 10px;"
                        prefix-icon="el-icon-search"
                        placeholder="请输入文件创建者姓名">
              </el-input>
              <el-input clearable
                        v-model="filePara.nodeName"
                        @clear="initFiles"
                        size="small"
                        style="width: 200px;margin-right: 10px;"
                        prefix-icon="el-icon-search"
                        placeholder="请输入知识描述">
              </el-input>
              <el-button size="small" type="primary" icon="el-icon-search" @click="searchFiles">搜索</el-button>
            </div>
          </div>
          <!--          关系信息展示-->
          <div style="width: 100%;">
            <el-table
                :data="files"
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
                  label="名称"
                  fixed
                  width="400"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="type"
                  label="类型"
                  width="100"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="createUserName"
                  label="创建人"
                  width="150"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="createTime"
                  label="创建时间"
                  width="200"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="nodeName"
                  label="对应节点"
                  width="150"
                  align="center">
              </el-table-column>
              <el-table-column
                  prop="operate"
                  label="操作"
                  fixed="right"
                  width="150"
                  align="center">
                <template slot-scope="scope">

                  <el-link :underline="false" :href="scope.row.url" target="_blank">查看</el-link>

                  <el-button size="mini" type="danger"  @click="deletefile(scope.row) " style="margin-left: 10px">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 5px; display: flex;justify-content: center;">
              <el-pagination
                  background
                  layout="total, prev, pager, next"
                  :total="totalFiles"
                  @current-change="fileChangePage"
              >
              </el-pagination>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
<!--    修改节点信息dialog-->
    <el-dialog
        title="编辑节点信息"
        :visible.sync="nodeDialogVisible"
        width="30%">
      <div>
        <table>
          <tr>
            <td>节点名称</td>
            <td><el-input v-model="node.name"></el-input></td>
          </tr>
          <tr>
            <td>节点等级</td>
            <td>
              <el-select
                  v-model="node.level"
                  size="small"
                  placeholder="请选择节点等级">
                <el-option
                    v-for="item in levelOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </td>
          </tr>
          <tr>
            <td>节点状态</td>
            <td>
              <el-switch
                  v-model="nodeStatus"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
              </el-switch>
              <span v-if="nodeStatus">
                启用
              </span>
              <span v-if="!nodeStatus">
                禁用
              </span>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
      <el-button @click="cancleUpdateNode">取 消</el-button>
      <el-button type="primary" @click="sureUpdateNode">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ManageKb",
  data() {
    return {
      activeName: 'first',
      totalNodes:0,
      totalRelations:0,
      totalFiles:0,
      nodes:[],
      relations:[],
      files:[],
      nodePara:{
        currentPage:1,
        size:8,
        name:'',
        createUser:'',
        level:'',
      },
      relationPara:{
        currentPage:1,
        size:8,
        name:'',
      },
      filePara:{
        currentPage:1,
        size:8,
        nodeName:'',
        createUserName:'',
        name:''
      },
      levelOptions:
          [
            {
              value: '1',
              label: '一级节点'
            },
            {
              value: '2',
              label: '二级节点'
            },
            {
              value: '3',
              label: '三级节点'
            },
            {
              value: '4',
              label: '四级节点'
            },
            {
              value: '5',
              label: '五级节点'
            }
          ],
      node:{},
      nodeStatus:true,
      nodeDialogVisible:false,
      relationDialogVisible:false,
      fileDialogVisible:false,
    };
  },
  methods: {
    searchFiles(){
      this.initFiles()
    },
    // lookFile(file){
    //
    // },
    deletefile(file){

    },
    handleClick() {
      let that = this
      if(that.activeName == 'first'){
        //console.log(0)
        this.initNodes()
      }else if(that.activeName == 'second'){
        //console.log(1)
        this.initRelations()
      }else if(that.activeName == 'third'){
       // console.log(2)
        this.initFiles()
      }
    },
    editNode(node){
      let that = this
      that.node = node
      that.nodeDialogVisible = true
      //console.log(typeof node.status)
      if(node.status === 0){
        that.nodeStatus = false
      }else{
        that.nodeStatus = true
      }

    },
    editRelation(relation){

    },
    deleteNode(node){
      this.$confirm('此操作将永久删除该节点, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/manage/kb/delNode/'+node.id)
            .then(resp=>{
              if(resp){
                this.initNodes()
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
      console.log(node);
    },
    deleteRelation(relation){
      this.$confirm('此操作将永久删除该关系, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/manage/kb/delNode/'+node.id)
            .then(resp=>{
              if(resp){
                this.initNodes()
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
      console.log(node);
    },
    initNodes(){
      let that = this
      this.getRequest('/manage/kb/getNodes?currentPage='+that.nodePara.currentPage+
          '&size='+that.nodePara.size+
          '&name='+that.nodePara.name+
          '&level='+that.nodePara.level+
          '&createUser='+that.nodePara.createUser
      ).then(resp=>{
        if(resp){
          that.totalNodes = resp.total
          that.nodes = resp.data
          //this.$set(this,users,resp.data)
        }
      })
    },
    initRelations(){
      let that = this
      this.getRequest('/manage/kb/getRelations?currentPage='+that.nodePara.currentPage+
          '&size='+that.nodePara.size+
          '&name='+that.nodePara.name
      ).then(resp=>{
        console.log(resp)
        if(resp){
          that.relations = resp.data
          that.totalRelations = resp.total
          //this.$set(this,users,resp.data)
        }
      })
    },
    initFiles(){
      let that = this
      this.getRequest('/manage/kb/getFiles?currentPage='+that.filePara.currentPage+
          '&size='+that.filePara.size+
          '&name='+that.nodePara.name+
          '&createUserName='+that.filePara.createUserName+
          '&nodeName='+that.filePara.nodeName
      ).then(resp=>{
        console.log(resp)
        if(resp){
          that.files = resp.data
          that.totalFiles = resp.total
          //this.$set(this,users,resp.data)
        }
      })
    },
    cancleUpdateNode(){
      this.nodeDialogVisible = false
    },
    sureUpdateNode(){
      let that = this
      that.nodeDialogVisible = false
      if(this.nodeStatus == false){
        this.node.status = 0
      }else{
        this.node.status = 1
      }
      that.node.modifyUser = that.$store.state.currentUser.id
      that.getRequest('/kg/graph/node/'+that.node.id)
          .then(resp=>{
            if(resp){
              that.node.createUser = resp.createUser
              console.log(that.node)
              that.putRequest('/manage/kb/updateNode/',that.node)
                  .then(resp=>{
                    if(resp){
                      that.initNodes()
                    }
                  })
            }

          })


    },
    nodeChangePage(currentPage){
      this.nodePara.currentPage = currentPage
      this.initNodes()
    },
    fileChangePage(currentPage){
      this.filePara.currentPage = currentPage
      this.initNodes()
    },
    searchNodes(){
      this.initNodes()
    },
    searchRelations(){
      this.initRelations()
    },
    exportNodes(){

    },
  },
  mounted() {
      this.initNodes();
  }
}
</script>

<style scoped>

</style>
