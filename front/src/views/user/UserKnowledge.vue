<template>
  <div>
    <dvi v-if="userNodes">
      <el-card v-for="node in userNodes" class="node-item" shadow="hover">
        <div style="display: flex;flex-direction: row">
          <el-image class="img" v-if="node.pics.length==0"  :src="require('../../static/no_pic.jpg')">
          </el-image>
          <el-image class="img" v-if="node.pics.length>0" :src="node.pics[0].url"></el-image>
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
    </dvi>
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
    test(){
      let aa  = {
        "summary":"[印刷（Printing，Graphic Arts，也用使用Graphic Communications即图形传播的）是将文字、图画、照片、防伪等原稿经制版、施墨、加压等工序，使油墨转移到纸张、纺织品、塑料品、皮革、PVC、PC等材料表面上，批量复制原稿内容的技术。印刷是把经审核批准的印刷版，通过印刷机械及专用油墨转印到承印物的过程。",
        "specific_info":"None",
        "basic_info":{"中文名": "印刷",
          "外文名": "Printing",
          "适用领域": "纺织品、包装、技术性、广告行业",
          "印刷原理": "物理性印刷和化学性印刷",
          "行业特点": "传统行业",
          "印刷城地址": "浙江省、温州市、龙港市、印刷城"},
        "pic":"https://bkimg.cdn.bcebos.com/pic/5fdf8db1cb134954196b4d6b564e9258d1094a43?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg"
      }
      let bb = {
        "summary":"印刷（Printing，GraphicArts，也用使用GraphicCommunications即图形传播的）是将文字、图画、照片、防伪等原稿经制版、施墨、加压等工序，使油墨转移到纸张、纺织品、塑料品、皮革、PVC、PC等材料表面上，批量复制原稿内容的技术。印刷是把经审核批准的印刷版，通过印刷机械及专用油墨转印到承印物的过程。[1]",
        "specific_info":"",
        "basic_info":{
          "中文名":"印刷",
          "印刷原理":"物理性印刷和化学性印刷",
          "行业特点":"传统行业",
          "外文名":"Printing",
          "印刷城地址":"浙江省、温州市、龙港市、印刷城",
          "适用领域":"纺织品、包装、技术性、广告行业"
        },
        "best_practices":[],
        "pic":"https://bkimg.cdn.bcebos.com/pic/5fdf8db1cb134954196b4d6b564e9258d1094a43?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg"}

    },

    initUserNodes(){
      let that = this
      let id = that.$store.state.currentUser.id
      that.getRequest('/kg/graph/search3?id='+id)
      .then(resp=>{
        console.log(resp)
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
              results.push(result)
            }
          })
        })
        console.log(results)
        that.userNodes = results
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
