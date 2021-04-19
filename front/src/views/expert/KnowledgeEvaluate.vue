<template>
  <div>
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
<!--            <el-table-column-->
<!--                prop="createUser"-->
<!--                label="创建人"-->
<!--                width="150"-->
<!--                align="center">-->
<!--            </el-table-column>-->
<!--            <el-table-column-->
<!--                prop="createTime"-->
<!--                label="创建时间"-->
<!--                width="200"-->
<!--                align="center">-->
<!--            </el-table-column>-->
            <el-table-column
              prop="summary"
              align="center"
              label="概述">
            </el-table-column>
            <el-table-column
                prop="operate"
                label="操作"
                fixed="right"
                width="200"
                align="center">
              <template slot-scope="scope" >
                <el-button size="mini" @click="toExtract(scope.row)">查看</el-button>
                <el-button size="mini" type="danger" @click="butongguo(scope.row)">不通过</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin-top: 5px; display: flex;justify-content: center;">
            <el-pagination
                background
                layout="total, prev, pager, next"
                :total="totalNodes"
                @current-change="changePage"
            >
            </el-pagination>
          </div>
        </div>
  </div>
</template>

<script>
export default {
  name: "KnowledgeEvaluate",
  data(){
    return{
      totalNodes:0,
      nodes:[],
      nodePara:{
        currentPage:1,
        size:10,
      },
    }
  },
  methods:{
    initnodes(){
      let that = this
      that.getRequest('/java/expert/kb/getNodes?currentPage='+that.nodePara.currentPage+'&size='+that.nodePara.size)
      .then(resp=>{
        if(resp){
          that.totalNodes = resp.total
          let results = []
          //console.log(resp)
          resp.data.forEach(node=> {
            let result = node
            result.summary = JSON.parse(node.properties).summary
            results.push(result)
          })
          that.nodes = results
        }
      })
    },
    changePage(currentPage){
      this.nodePara.currentPage = currentPage
      this.initnodes()
    },
    toExtract(node){
      console.log(node)
      this.$router.push({path:'/expert/extract',query:{id:node.id}})
    },
    butongguo(node){
      let that = this
      delete node.summary
      //console.log(node)
      that.getRequest('/java/expert/kb/notAdoptNode/'+node.id)
      .then(resp=>{
        if(resp){
          that.initnodes()
        }
      })
    }
  },
  mounted() {
    this.initnodes()
  }

}
</script>

<style scoped>

</style>
