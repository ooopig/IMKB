<template>
  <div>
    <el-tabs v-model="editableTabsValue" type="card"  @tab-remove="removeTab">
      <el-tab-pane
          label="搜索"
          name="0"
      >
        <div class="search">
          <el-input
              clearable
              size="small"
              prefix-icon="el-icon-search"
              class="inline-input"
              v-model="inputName"
              placeholder="请输入关键词"
              style="margin-right: 5px;width: 200px"
          >
          </el-input>
          <el-button type="primary" size="small"  icon="el-icon-search" @click="search">搜索</el-button>
        </div>
        <div v-if="searchResults" style="padding: 5px;">
          <el-card v-for="node in searchResults" class="node-item" shadow="hover">
            <div style="display: flex;flex-direction: row">

              <el-image class="img" v-if="node.pic" :src="node.pic"></el-image>
              <el-image class="img" v-else  :src="require('../../static/no_pic.jpg')"></el-image>
              <div @click="specific(node.id)" style="display: flex;flex-direction: column;justify-content: space-between; width: 90%;padding-left: 10px;">
                <div>
                  <div style="font-size: larger;font-weight: bold ">{{node.name}}</div>
                  <div class="item-summary">{{node.properties.summary}}</div>
                </div>
                <div  class="item-bottom">
                  <span style="color: #ff5151">贡献者：</span>
                  <span>{{node.createUser}}</span>
                  <span style="margin-left: 20px; ">时间：</span>
                  <span>{{node.createTime}}</span>
                </div>
              </div>
            </div>

          </el-card>
        </div>
      </el-tab-pane>

      <el-tab-pane
          v-for="(item, index) in editableTabs"
          closable
          :key="item.name"
          :label="item.title"
          :name="item.name">

        <el-card shadow="always" style="margin: 10px">
          <el-form label-width="80px">
            <el-form-item label="标题" >
              <el-input v-model="item.nodeTab.name"></el-input>
            </el-form-item>
            <el-form-item label="基本介绍" :disabled="item.nodeTab.disabled">
              <el-input
                  type="textarea"
                  autosize
                  placeholder="请输入内容"
                  v-model="item.nodeTab.properties.summary">
              </el-input>
            </el-form-item>
            <el-form-item label="描述图片" >
              <div style="display: flex;flex-direction: row">
                <div v-for="fit in item.nodeTab.pics">
                  <el-image
                      style="width: 100px; height: 100px;margin-left: 5px"
                      :src="fit.url"
                      fit="cover"></el-image>
                </div>
              </div>
            </el-form-item>
            <el-form-item

                :label="'相关文件'">
              <div v-for="file in item.nodeTab.files">
                <el-link type="primary" :href="file.url" target="_blank">{{ file.name }}</el-link>
              </div>
            </el-form-item>

            <el-form-item
                :label="'最佳实践'">
              <div v-for="file in item.nodeTab.properties.best_practices">
                <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入内容"
                    v-model="file.value">
                </el-input>
              </div>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
name: "KbSearch",
  data() {
    return {
      inputName:'',
      editableTabsValue: '0',
      editableTabsId:[],
      editableTabs:
      [

      ],
      searchResults:[],
    }
  },
  methods: {
    specific(id){
      let that = this
      if(that.editableTabsId.includes(id)){
        return
      }
      let newTabName = id + '';
      that.editableTabsId.push(id)
      let node = {}
      for(let i = 0;i<that.searchResults.length;i++){
        if(that.searchResults[i].id == id){
          node = that.searchResults[i]
          break;
        }
      }
      this.editableTabs.push({
        title: node.name,
        name: newTabName,
        nodeTab:node,
      });
      that.editableTabsValue = newTabName;
    },
    removeTab(targetName) {
      this.editableTabsId.splice(this.editableTabsId.indexOf(targetName),1)
      let tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
        });
      }
      this.editableTabsValue = activeName;
      this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    },

    search(){
      let that = this
      if(that.inputName == ''){
        this.$message.error('请输入查询关键词');
      }
      that.getRequest('/java/kg/graph/search2?name='+that.inputName)
          .then(resp=>{
            if(resp){
              this.$message({
                message: '共有' + resp.length + '条查询结果',
                type: 'success'
              });
              let results = []
              //console.log(resp)
              resp.forEach(node=>{
                let result = {
                  id:node.id,
                  name:node.name,
                  properties:JSON.parse(node['properties']),
                  pic:JSON.parse(node['properties']).pic,
                  pics:[],
                  files:[],
                  createUser:node.createUser,
                  createTime:node.createTime,
                  disabled:true
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
                    })
              })
              that.searchResults = results
              //console.log(results)
            }
          })
    }
  },
  mounted() {

  },
  watch:{
    $route(to,from){

    }
  }
}
</script>

<style scoped>
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
