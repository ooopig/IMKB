<template>
  <div style="padding: 5px">
    <el-steps :active="active" finish-status="success"  align-center>
      <el-step title="知识审核"></el-step>
      <el-step title="知识抽取"></el-step>
      <el-step title="知识推理"></el-step>
    </el-steps>

    <el-card v-if="active==0" shadow="always" style="margin: 10px" title="baidshyvb">
      <el-form label-width="80px">
        <el-form-item label="标题" >
          <el-input v-model="node.name"></el-input>
        </el-form-item>
        <el-form-item label="基本介绍" :disabled="node.disabled">
          <el-input
              type="textarea"
              autosize
              placeholder="请输入内容"
              v-model="node.summary">
          </el-input>
        </el-form-item>
        <el-form-item label="基本属性" >
          <div>
            <div v-for="(k,v) in node.basic_info">
              {{v}}：{{k}}
            </div>
          </div>
        </el-form-item>
        <el-form-item label="描述图片" >
          <div style="display: flex;flex-direction: row">
            <div v-for="fit in node.pics">
              <el-image
                  style="width: 100px; height: 100px;margin-left: 5px"
                  :src="fit.url"
                  fit="cover"></el-image>
            </div>
          </div>
        </el-form-item>

        <el-form-item
            :label="'相关文件'">
          <div v-for="file in node.files">
            <el-link type="primary" :href="file.url" target="_blank">{{ file.name }}</el-link>
          </div>
        </el-form-item>

        <el-form-item
            :label="'最佳实践'">
          <div v-for="file in node.best_practices">
            <el-input
                type="textarea"
                autosize
                placeholder="请输入内容"
                v-model="file.value">
            </el-input>
          </div>
        </el-form-item>
        <el-form-item>
          <div style="display: flex;flex-direction: row;justify-content: space-around">
            <el-button @click="cancle" type="danger">不通过</el-button>
            <el-button @click="next" type="success">下一步</el-button>
          </div>

        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "KnowledgeExtract",
  data(){
    return{
      node:{},
      active: 0
    }
  },
  methods:{
    getNode(id){
      let that = this
      that.getRequest('/kg/graph/node/'+id)
          .then(node=>{
            let aa = JSON.parse(node['properties'])
            let result = {
              id:node.id,
              name:node.name,
              summary:aa.summary,
              basic_info:aa.basic_info,
              best_practices:aa.best_practices,
              pic:JSON.parse(node['properties']).pic,
              pics:[],
              files:[],
              createUser:node.createUser,
              createTime:node.createTime,
            }
            that.getRequest('/file/get/'+node.id)
                .then(resp=>{
                  if(resp){
                    for(let item of resp){
                      if(item.type == 'img'){
                        result.pics.push(item)
                      }else{
                        result.files.push(item)
                      }
                    }
                    that.node = result
                    console.log(result)
                  }
                })
          })

    },
    next() {
      if (this.active++ > 2) this.active = 0;
    },
    cancle(){
      let that = this
      that.getRequest('/expert/kb/notAdoptNode/'+that.node.id)
          .then(resp=>{
            if(resp){
              that.$router.replace({path:'/expert/evaluate'});
            }
          })
    }
  },
  mounted() {
    let id = this.$route.query.id
    this.getNode(id)
  }

}
</script>

<style scoped>

</style>
