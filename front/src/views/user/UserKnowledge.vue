<template>
  <div>
    <div v-if="userNodes">
      <el-card v-for="node in userNodes" class="node-item" shadow="hover">
        <div style="display: flex;flex-direction: row">
          <el-image class="img" v-if="node.pics.length==0"  :src="require('../../static/no_pic.jpg')"></el-image>
          <el-image class="img" v-else-if="node.pics.length>0" :src="node.pics[0]"></el-image>
          <div @click="specific(node.id)" style="display: flex;flex-direction: column;justify-content: space-between; width: 90%;padding-left: 10px;">
            <div>
              <div style="font-size: larger;font-weight: bold ">{{node.name}}</div>
              <div class="item-summary">{{node.properties.summary}}</div>
            </div>
            <div  class="item-bottom">
              <span >状态：</span>
              <span v-if="node.status"  style="color: #67c23a">已通过审核</span>
              <span v-else style="color: #ff5151">正在审核</span>
              <span style="margin-left: 20px; ">时间：</span>
              <span>{{node.createTime}}</span>
            </div>
          </div>
        </div>

      </el-card>
    </div>
    <div v-else class="homeWelcome">
      你还没有贡献任何知识
    </div>
  </div>
</template>

<script>
export default {
  name: "UserKnowledge",
  data(){
    return{
      userNodes:[],
    }

  },
  methods:{
    specific(id){

    },
    initUserNodes(){
      let that = this
      let id = that.$store.state.currentUser.id
      that.getRequest('/java/kg/graph/search3?id='+id)
      .then(resp=>{
        //console.log(resp)
        let results = []
        resp.forEach(node=>{
          let result = {
            name:node.name,
            id:node.id,
            createTime:node.createTime,
            status:node.status,
            properties:JSON.parse(node.properties),
            pics:[],
            files:[],
          }
          if(result.properties.pic == undefined){

          }else {
            result.pics.push(result.properties.pic)
          }
          that.getRequest('/java/file/get/'+node.id)
          .then(resp=>{
            if(resp){
              for(let item of resp){
                if(item.type == 'img'){
                  result.pics.push(item)
                }else{
                  result.files.push(item)
                }
              }
              results.push(result)
            }
            console.log(results)
            that.userNodes = results
          })
        })

      })
    }
  },
  mounted() {
    this.initUserNodes()
  }
}
</script>

<style scoped>
.homeWelcome{
  text-align: center;
  font-size: 30px;
  font-family: 华文行楷;
  color: #409eff;
  padding-top: 50px;
}
.search{
  padding: 3px;
  border-bottom: 1px solid #e3e3e3;
  display: flex;
  flex-direction: row;
}
.without{
  text-align: center;
  font-size: 30px;
  font-family: 华文行楷;
  color: #409eff;
  padding-top: 50px;
}
.node-item{
  height: 130px;
  /*width: 100%;*/
  padding: 5px;
  cursor: pointer;
  /*border-bottom: 1px solid #e3e3e3 ;*/
  margin-bottom: 2px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.img{
  width: 10%;
  height: 110px;
  border: 2px solid #e7e6e6;
}
.item-summary {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box; /*下面三条缺一不可 可以生成 '...' 主要针对谷歌浏览器 */
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  margin-top: 3px;
}
.item-bottom{
  display: flex;
  flex-direction: row;
  font-size: smaller;
}
.dd{
  display: flex;
  flex-direction: row;
  justify-content: center;
}
</style>
