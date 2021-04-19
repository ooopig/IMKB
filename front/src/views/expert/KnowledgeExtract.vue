<template>
  <div style="padding: 5px">
    <el-steps :active="active" finish-status="success"  align-center>
      <el-step title="知识审核"></el-step>
      <el-step title="知识抽取"></el-step>
      <el-step title="知识推理"></el-step>
    </el-steps>

    <el-card v-if="active==0" shadow="always" style="margin: 10px" >
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
            <el-button @click="cancle" type="danger" :disabled="disable">不通过</el-button>
            <el-button @click="next" type="success" :disabled="disable">下一步</el-button>
          </div>

        </el-form-item>
      </el-form>
    </el-card>

    <div v-if="active==1">
    <el-card  shadow="always" style="margin:10px">
      <span>文本原文</span>
      <el-input
      type="textarea"
      autosize
      v-model="allText">
      </el-input>
      <div style="display: flex;flex-direction: row;justify-content: center">
        <el-button style="margin-top: 10px" size="small" @click="analyse">分析</el-button>
      </div>
    </el-card>
    <el-card v-if="showAnalyse" shadow="always" style="margin:10px">
      <div style="margin-bottom: 10px">
        <el-radio-group
            @change="radioChange"
            v-model="radio">
          <el-radio :label="1">分词</el-radio>
          <el-radio :label="2">词性标注</el-radio>
          <el-radio :label="3">命名实体</el-radio>
          <el-radio :label="4">关键词</el-radio>
        </el-radio-group>
      </div>
        <el-input v-if="radio==1 || radio == 2 ||radio == 3"
          autosize
          v-model="analyseText"
          type="textarea">
        </el-input>
        <div v-if="radio==4">
          <el-card>
            <div style="display: flex;flex-direction: row;justify-content: space-around">
              <div>
                <span style="margin-bottom: 10px; display: flex;flex-direction: row;justify-content: center">自定义关键词</span>
                <el-input
                autosize
                type="textarea"
                style="width: 400px"
                v-model="selfKey"
                placeholder="每行为一个关键词"
                >
                </el-input>
                <div style="margin-top: 20px; display: flex;flex-direction: row;justify-content: center">
                  <el-button type="primary" @click="addKey" size="small">确定</el-button>
                </div>
              </div>
              <div>
              <el-transfer
                  :titles="['备选关键词', '选定关键词']"
                  v-model="value"
                  :data="keyText">
              </el-transfer>
              </div>
            </div>
          </el-card>
        </div>


    </el-card>
      <div style="display: flex;flex-direction: row;justify-content: space-around">
        <el-button @click="preStep" type="danger">上一步</el-button>
        <el-button @click="next" type="success">下一步</el-button>
      </div>
    </div>

    <el-card v-if="active==2" style="margin: 10px;">
      <div style="display: flex;flex-direction: row">


<!--      图谱部分-->
      <div style="height: 500px;width: 750px;background-color: #f6f6f6">
        <svg id="svg1" style="height: 500px;width: 750px"></svg>
      </div>
<!--      面板部分-->
      <el-card style="height: 500px;width: 490px;margin-left: 10px">
        <!--        名称-->
        <div style="display: flex;flex-direction: row;">
          <span style="width: 80px;margin-top: 5px">名&nbsp &nbsp称：</span>
          <el-input
              style="width: 200px"
              size="small"
              disabled
              v-model="node.name">

          </el-input>
        </div>
<!--        节点等级-->
        <div style="display: flex;flex-direction: row;margin-top: 5px">
          <span style="width: 80px;margin-top: 5px">节点等级：</span>
          <el-select clearable
                     v-model="node.level"
                     @change="(val)=>selectLevel(val)"
                     style="width: 150px"
                     size="small"
                     placeholder="请选择节点等级">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
              <span v-bind:style="{'color':item.color }">{{ item.label }}</span>
            </el-option>
          </el-select>
        </div>
<!--父节点-->
        <div>
          <span style="width: 80px;margin-top: 5px">父节点：</span>
          <div style="display: flex;flex-direction: row;margin-top: 5px" v-for="item in parents">

            <div>
  <!--            远程搜索-->
              <el-select
                  size="small"
                  style="width: 40%"
                  v-model="item.parentName"
                  filterable
                  @change="(val)=>selectParent(val,item)"
                  remote
                  reserve-keyword
                  placeholder="请输入关键词"
                  :remote-method="remoteMethod"
                  :loading="item.parentLoading">
                <el-option
                    v-for="item in parentOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
              <el-input
                  size="small"
                  @blur="refreshGraph"
                  placeholder="关系名称"
                  v-model="item.parentRelationName"
                  style="width: 40%;margin-left: 5px"></el-input>
              <el-button style="width: 15%;margin-left: 10px" size="small" @click.prevent="removeParent(item)" type="danger" plain>删除</el-button>
            </div>
          </div>
          <el-button style="margin-left: 10px;margin-top: 5px" size="small" @click="addParent" type="primary" plain>新增</el-button>
        </div>

        <!--子节点-->
        <div>
          <span style="width: 80px;margin-top: 5px">子节点：</span>
          <div style="display: flex;flex-direction: row;margin-top: 5px" v-for="item in children">

            <div>
              <!--            远程搜索-->
              <el-select
                  size="small"
                  style="width: 40%"
                  v-model="item.childName"
                  filterable
                  @change="(val)=>selectChild(val,item)"
                  remote
                  reserve-keyword
                  placeholder="请输入关键词"
                  :remote-method="remoteMethod2"
                  :loading="item.childLoading">
                <el-option
                    v-for="item in childrenOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
              <el-input
                  size="small"
                  @blur="refreshGraph"
                  placeholder="关系名称"
                  v-model="item.childRelationName"
                  style="width: 40%;margin-left: 5px"></el-input>
              <el-button style="width: 15%;margin-left: 10px" size="small" @click.prevent="removeChild(item)" type="danger" plain>删除</el-button>
            </div>
          </div>
          <el-button style="margin-left: 10px;margin-top: 5px" size="small" @click="addChild" type="primary" plain>新增</el-button>
        </div>
      </el-card>
      </div>
      <div style="display: flex;flex-direction: row;justify-content: space-around;margin-top: 10px">
        <el-button @click="preStep" type="danger">上一步</el-button>
        <el-button @click="endExtract" type="success" >完成</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import * as d3 from "d3";

export default {
  name: "KnowledgeExtract",
  data(){
    return{
      node:{},
      existNodesName:[],

      nodeLevel0:[],
      nodeLevel1:[],
      nodeLevel2:[],
      nodeLevel3:[],
      nodeLevel4:[],
      nodeLevel5:[],
      remoteParent:[],
      parentOptions:[],
      remoteChildren:[],
      childrenOptions:[],
      parents:[],
      children:[],
      disable:false,
      active: 0,
      allText:'',
      analyseText:'',
      cutText:'',
      keyText:[],
      nerText:'暂无',
      posText:'暂无',
      radio:1,
      showAnalyse:false,
      data: [],
      value: [],
      selfKey:'',
      links:[],
      nodes:[],
      nodeText:[],
      linkText:[],
      simulation:null,
      dataGraph:{
        "nodes": [

        ],
        "links": [],
      },
      colorList : [
        '#FE0302','#71d075','#1699c1','#0be09d',
        '#00CD00','#89D5FF','#FF7204','#019899',
        '#c46bc6', '#d72c2c','#1d3093',
      ],
      height:500,
      width:750,

      options:
          [
            {
              value: 1,
              label: '一级节点',
              color: '#1699c1'
            },
             {
            value: 2,
            label: '二级节点',
            color: '#1699c1'
          }, {
            value: 3,
            label: '三级节点',
            color: '#0be09d'
          }, {
            value: 4,
            label: '四级节点',
            color: '#00CD00'
          }, {
            value: 5,
            label: '五级节点',
            color: '#FF7204'
          }],
      oldLevel:5,
    }
  },
  methods:{
    removeParent(item) {
      let that = this
      let index = this.parents.indexOf(item)
      //console.log(item)
      if (index !== -1) {
        this.parents.splice(index, 1)

        //更新图谱
        //console.log(item)
        //console.log(that.dataGraph.links)
        let temp = []
        let linkCount = 0
        for(let item1 of that.dataGraph.links){
          if(item1.sourceName == item.parentName ){
            linkCount++
            if(item1.relation == item.parentRelationName){

            }
            else {
              temp.push(item1)
            }
          }else {
            temp.push(item1)
          }
        }
        that.dataGraph.links = temp

        let tempNode = []
        //console.log(that.dataGraph.nodes)
        that.existNodesName = []
        //console.log(linkCount)
        if(linkCount==1){
          //删除节点
          for(let item2 of that.dataGraph.nodes){
            if(item2.name == item.parentName){
            }
            else {
              tempNode.push(item2)
              that.existNodesName.push(item2.name)
            }
          }
          that.dataGraph.nodes = tempNode
        }
        console.log(that.dataGraph.nodes)
        that.initSmallGraph(that.dataGraph)
      }
    },
    addParent() {
      this.parents.push(
          {
            key:Date.now(),
            parentLoading: false,
            parentRelationName:'',
            parentName:'',
          }
      )
    },
    selectParent(value,item){

    },
    removeChild(item) {
      let that = this
      let index = this.children.indexOf(item)
      if (index !== -1) {
        this.children.splice(index, 1)
        //更新图谱
        //console.log(item)
        console.log(that.dataGraph.links)
        let temp = []
        let linkCount = 0
        for(let item1 of that.dataGraph.links){
          if(item1.targetName == item.childName ){
            linkCount++
            if(item1.relation == item.childRelationName){

            }
            else {
              temp.push(item1)
            }
          }else {
            temp.push(item1)
          }
        }
        that.dataGraph.links = temp
        let tempNode = []
        that.existNodesName = []
        if(linkCount==1){
          //删除节点
          for(let item2 of that.dataGraph.nodes){
            if(item2.name == item.childName){
            }
            else {
              tempNode.push(item2)
              that.existNodesName.push(item2.name)
            }
          }
          that.dataGraph.nodes = tempNode
        }

        that.initSmallGraph(that.dataGraph)
      }
    },
    addChild() {
      this.children.push(
          {
            key:Date.now(),
            childLoading: false,
            childRelationName:'',
            childName:'',
          }
      )
    },
    selectChild(value,item){

    },
    /**
     *面板切换节点等级
    */
    selectLevel(value){
      if(value == this.oldLevel){

      }
      else {
        this.children = []
        this.parents = []
        this.oldLevel = value
        this.dataGraph.nodes = [
          {
            key:2,
            id:this.node.id,
            name:this.node.name,
            group:this.node.level,
          }
        ]
        this.dataGraph.links = []
        this.initSmallGraph(this.dataGraph)
        this.existNodesName = []
        this.existNodesName.push(this.node.name)
      }
    },
    remoteMethod(query) {
      let that = this
      if (query !== '') {
        this.loading = true;
        if(this.node.level === 1 ){
          setTimeout(() => {
            this.loading = false;
            for(let item of this.nodeLevel0){
              if(item.value.indexOf(query)>-1)
              this.parentOptions.push(item)
            }
          }, 200);
        }
        else if(this.node.level === 2 ){
          setTimeout(() => {
            this.loading = false;
            this.parentOptions = this.nodeLevel1.filter(item => {
              return item.value
                  .indexOf(query) > -1;
            });
          }, 200);
        }
        else if(this.node.level === 3 ){
          setTimeout(() => {
            this.loading = false;
            this.parentOptions = this.nodeLevel2.filter(item => {
              return item.value
                  .indexOf(query) > -1;
            });
          }, 200);
        }
        else if(this.node.level === 4 ){
          setTimeout(() => {
            this.loading = false;
            this.parentOptions = this.nodeLevel3.filter(item => {
              return item.value
                  .indexOf(query) > -1;
            });
          }, 200);
        }
        else if(this.node.level === 5 ){
          setTimeout(() => {
            this.loading = false;
            this.parentOptions = this.nodeLevel4.filter(item => {
              return item.value
                  .indexOf(query) > -1;
            });
          }, 200);
        }

      } else {
        this.options = [];
      }
    },
    remoteMethod2(query) {
      let that = this
      if (query !== '') {
        this.loading = true;
        if(this.node.level === 1 ){
          //console.log('节点等级为1',query)
          setTimeout(() => {
            this.loading = false;
            for(let item of this.nodeLevel2){
              //console.log(item)
              if(item.value.indexOf(query)>-1)
                this.childrenOptions.push(item)
            }
          }, 200);
        }
        else if(this.node.level === 2 ){
          setTimeout(() => {
            this.loading = false;
            this.childrenOptions = this.nodeLevel3.filter(item => {
              return item.value
                  .indexOf(query) > -1;
            });
          }, 200);
        }
        else if(this.node.level === 3 ){
          setTimeout(() => {
            this.loading = false;
            this.childrenOptions = this.nodeLevel4.filter(item => {
              return item.value
                  .indexOf(query) > -1;
            });
          }, 200);
        }
        else if(this.node.level === 4 ){
          setTimeout(() => {
            this.loading = false;
            this.childrenOptions = this.nodeLevel5.filter(item => {
              return item.value
                  .indexOf(query) > -1;
            });
          }, 200);
        }
      } else {
        this.options = [];
      }
    },
    refreshGraph(){
      let that = this

      for(let item of this.parents){
        that.getRequest('/java/kg/graph/search4?name='+item.parentName)
            .then(resp=>{
              if(resp){
                //父节点不存在，加入图谱节点
                if(that.existNodesName.indexOf(item.parentName)<0){
                  //如果不存在，根据节点名字请求后端，将该节点填入图谱节点中
                  that.dataGraph.nodes.push({
                    key:item.key,
                    id:resp.id,
                    name:resp.name,
                    group:resp.level,
                  })
                  that.existNodesName.push(item.parentName)
                }
                //更新关系
                let flag = true
                for (let item2 of that.dataGraph.links){
                  //存在
                  if(item2.sourceName == resp.name){
                    item2.relation = item.parentRelationName
                    flag = false
                  }
                }
                if(flag){
                  let temp = {
                    "source":resp.id,
                    sourceName:resp.name,
                    "target":that.node.id,
                    targetName:that.node.name,
                    "value":1,
                    "relation":item.parentRelationName
                  }
                  that.dataGraph.links.push(temp)
                }

                that.updateSmallGraph(that.dataGraph)
              }

            })
      }
      for(let item of this.children){
        that.getRequest('/java/kg/graph/search4?name='+item.childName)
            .then(resp=>{
              if(resp){
                //父节点不存在，加入图谱节点
                if(that.existNodesName.indexOf(item.childName)<0){
                  //如果不存在，根据节点名字请求后端，将该节点填入图谱节点中
                  that.dataGraph.nodes.push({
                    key:item.key,
                    id:resp.id,
                    name:resp.name,
                    group:resp.level,
                  })
                  that.existNodesName.push(item.childName)
                }
                //更新关系
                let flag = true
                for (let item2 of that.dataGraph.links){
                  //存在
                  if(item2.targetName == resp.name){
                    item2.relation = item.childRelationName
                    flag = false
                  }
                }
                if(flag){
                  let temp = {
                    "source":that.node.id,
                    sourceName:that.node.name,
                    "target":resp.id,
                    targetName:resp.name,
                    "value":1,
                    "relation":item.childRelationName
                  }
                  that.dataGraph.links.push(temp)
                }
                that.updateSmallGraph(that.dataGraph)
              }

            })
      }

      //this.updateSmallGraph(this.dataGraph)
    },
    addKey(){
      let keys = this.selfKey.split('\n')
      console.log('自定义关键词',keys)
      let l = this.keyText.length+1
      for(let item of keys){
        this.keyText.push({
          lable:l,
          key:item,
        })
        l = l+1
      }
      this.selfKey = ""
    },
    getNode(id){
      let that = this
      that.getRequest('/java/kg/graph/node/'+id)
          .then(node=>{
            let aa = JSON.parse(node['properties'])
            let result = {
              id:node.id,
              name:node.name,
              summary:aa.summary,
              basic_info:aa.basic_info,
              best_practices:aa.best_practices,
              pic:JSON.parse(node['properties']).pic,
              level:5,
              pics:[],
              files:[],
              createUser:node.createUser,
              createTime:node.createTime,
            }
            that.dataGraph.nodes.push({
              key:2,
              id:result.id,
              name:result.name,
              group:result.level,
            })
            that.existNodesName.push(result.name)
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
                    that.node = result
                    that.allText = result.summary
                    for(let item of result.best_practices){
                      that.allText+='\n'
                      that.allText+=item.value
                    }
                    //console.log(result)
                  }
                })
          })

    },
    next() {
      let that = this
      if (this.active++ > 2) this.active = 0;
      if(that.active == 2){
        setTimeout(()=>{
          that.initSmallGraph(this.dataGraph)
        },100)

      }
    },
    preStep(){
      this.active--
    },
    cancle(){
      let that = this
      that.getRequest('/java/expert/kb/notAdoptNode/'+that.node.id)
          .then(resp=>{
            if(resp){
              that.$router.replace({path:'/expert/evaluate'});
            }
          })
    },
    radioChange(lable){
      //console.log(lable)
      let that = this
      if(lable === 1){
        if(this.cutText != ''){
          this.analyseText = this.cutText
        }
        else{
          that.postRequest('/python/cut',{data:that.allText})
              .then(resp=>{
                if(resp){
                  that.cutText = resp
                  that.analyseText = that.cutText
                }
              })
        }
      }else if(lable === 2){
        that.analyseText = this.posText
        // if(this.posText != ''){
        //   this.analyseText = this.posText
        // }
        // else{
        //   that.postRequest('/python/pos',{data:that.allText})
        //       .then(resp=>{
        //         if(resp){
        //           that.posText = resp
        //           that.analyseText = that.posText
        //         }
        //       })
        // }
      }else if(lable === 3){
        this.analyseText = this.nerText
        // if(this.nerText != ''){
        //   this.analyseText = this.nerText
        // }
        // else{
        //   that.postRequest('/python/ner',{data:that.allText})
        //       .then(resp=>{
        //         if(resp){
        //           that.nerText = resp
        //           that.analyseText = that.nerText
        //         }
        //       })
        // }
      }else if(lable === 4){
        that.postRequest('/python/key',{data:that.allText})
            .then(resp=>{
              if(resp){
                console.log(resp)
                that.keyText = resp
              }
            })
      }

    },
    analyse(){
      this.showAnalyse = true
      if(this.cutText != ''){
        this.analyseText = this.cutText
      }
      let that = this
      that.postRequest('/python/cut',{data:that.allText})
      .then(resp=>{
        if(resp){
          that.cutText = resp
          that.analyseText = that.cutText
        }
      })
    },
    endExtract(){
      //console.log(this.dataGraph.links)
      let that = this
      //console.log(that.node)
      that.getRequest('/java/expert/kb/adoptNode/'+that.node.id+'/'+that.node.level)
      .then(resp=>{

      })
      for(let item of that.dataGraph.links){
        let para = {
          "createTime": "",
            "createUser": that.$store.state.currentUser.id,
            "id": 0,
            "modifyTime": "",
            "modifyUser": 0,
            "name": item.relation,
            "properties": "",
            "sourceId": item.source.id,
            "targetId": item.target.id,
            "weight": 1
        }
        that.postRequest('/java/manage/kb/addRelation',para)
            .then(resp=>{
              if(resp){
                that.$message.success('完成!')
              }
            })
      }

    },

    /**
     * 初始化
     * @param data
     */
    initSmallGraph(data){
      let that = this

      //d3.select('svg1').selectAll('*').remove();
      d3.selectAll("svg > *").remove();

      // that.widthSmall = $('#svg').width();
      // that.heightSmall = $('#svg').height();

      const links = data.links
      const nodes = data.nodes


      that.simulation = d3.forceSimulation(nodes)
          .force("link", d3.forceLink(links).id(d => d.id).distance(150))
          .force("charge", d3.forceManyBody().strength(-100))
          .force('collide',d3.forceCollide().strength(-30))
          .force("center", d3.forceCenter(that.width / 2, that.height / 2));

      const svg = d3.select("svg")
          .attr("viewBox", [0, 0, that.width, that.height])


      //箭头
      const positiveMarker = svg
          .append('marker')
          .attr('id', 'positiveMarker')
          .attr('markerUnits', 'strokeWidth')
          .attr('markerUnits', 'userSpaceOnUse')
          .attr('markerWidth', '12') //
          .attr('markerHeight', '12')
          .attr('viewBox', '0 -5 10 10')// 坐标系的区域
          .attr('refX', '35')
          .attr('refY', '0')
          .attr('orient', 'auto')// 绘制方向，可设定为：auto（自动确认方向）和 角度值
          .append('path')
          .attr('d','M 0 -5 L 10 0 L 0 5')
          .attr('fill','#e5e5e5')

      const g = svg.append('g')
          .on('dblclick',function (d){
            console.log("画布双击"+d)
            event.preventDefault()
          })
      //缩放
      svg.call(d3.zoom().on('zoom',function (){
        g.attr('transform',d3.event.transform)
      }))

      //连线
      that.links = g.append("g")
          .attr("stroke", "#999")
          .attr("stroke-opacity", 0.6)
          .attr('marker-end','url(#positiveMarker') // 箭头
          .selectAll("path")
          .data(links)
          .join("path")
          //.attr("stroke-width", d => Math.sqrt(d.value));
          .attr("stroke-width", 1)
          .attr('class','link')
          .attr('id',function (d){
            if(d.source instanceof Object){
              return d.source.id+'_'+d.relation+'_'+d.target.id
            }
            else{
              return d.source+'_'+d.relation+'_'+d.target
            }
          })
      //连线文字
      that.linkText = g.append('g')
          .selectAll('text')
          .data(links)
          .join('text')
          .style('fill','#999')
          .style('font-size', 8)
          //.style('font-weight','bold')
          .style('text-anchor','middle')
          .style('cursor', 'pointer')
          //.attr('fill-opacity', 0)
          .attr('class', 'linktext')
          .text(function (d) {
            //console.log(d.relation);
            return d.relation
          })
      //节点
      that.nodes = g.append("g")
          .attr("stroke", "#fff")
          .attr("stroke-width", 1.5)
          .selectAll("circle")
          .data(nodes)
          .join("circle")
          .style('cursor', 'pointer')
          .attr("r", 30)
          .attr("fill",function (d){
            return that.colorList[d.group]
          })
          .on('click',that.singleClick)
          .on('mouseenter',function (d){
            //console.log('鼠标移入'+d)
            d3.select(this).style('stroke-width',6)
          })
          .on('dblclick',function (d){
            //console.log("节点双击"+d)
            event.preventDefault()
          })
          .on('mouseleave', function () {
            //console.log('鼠标移出')
            d3.select(this).style('stroke-width', 2)
          })
          .call(that.drag(that.simulation));

      //鼠标移动到节点时显示的文字
      that.nodes.append("title")
          .text(d => d.name);

      //节点文字
      that.nodeText = g.append('g')
          .selectAll('text')
          .data(nodes)
          .join('text')
          .attr('class','nodetext')
          .attr('font-family','宋体')
          .attr('font-size',10)
          .attr('text-anchor','middle')
          .attr('fill','#ffffff')
          .style('cursor', 'pointer')
          .text(function (d){
            return d.name
          })
          .attr('dy','0.2em')
          // .attr('y',-20)
          // .attr('dy',10)

          // .attr('x',function ({id}) {
          //   return that.textBreaking(d3.select(this),id)
          // })

          .on('click',that.singleClick)
          .call(that.drag(that.simulation));

      //仿真
      that.simulation.on("tick", () => {
        that.links
            .attr('d',d=>'M '+d.source.x+' '+d.source.y+'L '+d.target.x+' '+d.target.y)

        that.nodes
            .attr("cx", d => d.x)
            .attr("cy", d => d.y);

        that.nodeText
            .attr("x", d => d.x)
            .attr("y", d => d.y);

        // that.linkText
        //     .attr("x", d => d.x)
        //     .attr("y", d => d.y);
        that.linkText
            .attr('x', function (d) { return (d.source.x + d.target.x) / 2 })
            .attr('y', function (d) { return (d.source.y + d.target.y) / 2 })

      });

    },
    /**
     * 拖拽
     * @param simulation
     * @returns {*}
     */
    drag (simulation) {
      function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
      }

      function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
      }

      function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0);
        d.fx = d3.event.x;
        d.fy = d3.event.y;
        // d.fx = null;
        // d.fy = null;
      }

      return d3.drag()
          .on("start", dragstarted)
          .on("drag", dragged)
          .on("end", dragended);
    },

    /**
     * 动态刷新
     * @param data
     */
    updateSmallGraph(data){
      let that = this
      //console.log(that.colorList)
      const nodes = data.nodes
      const links = data.links


      that.nodes = that.nodes
          .data(nodes)
          .enter()
          .append("circle")
          .attr("stroke", "#fff")
          .attr("stroke-width", 1.5)
          .attr("r",30)
          .style('cursor', 'pointer')
          .attr("fill",function (d){
            return that.colorList[d.group]
          })
          .merge(that.nodes)
          .on('click',that.singleClick)
          .on('mouseenter',function (d){
            //console.log('鼠标移入'+d)
            d3.select(this).style('stroke-width',6)
          })
          .on('dblclick',function (d){
            //console.log("节点双击"+d)
            event.preventDefault()
          })
          .on('mouseleave', function () {
            //console.log('鼠标移出')
            d3.select(this).style('stroke-width', 2)
          })
          .call(that.drag(that.simulation));

      d3.selectAll('title').remove();

      that.nodes.append("title")
          .text(d => d.name);

      that.nodeText = that.nodeText
          .data(nodes)
          .enter()
          .append('text')
          .merge(that.nodeText)
          .attr('class','nodetext')
          .attr('font-family','宋体')
          .attr('font-size',10)
          .attr('text-anchor','middle')
          .attr('dy','0.2em')
          .attr('fill','#ffffff')
          .style('cursor', 'pointer')
          .text(function (d){
            return d.name
          })
          // .attr('y',-20)
          // .attr('dy',10)
          // .attr('x',function (d) {
          //   return that.textBreaking(d3.select(this),d.id)
          // })
          .on('click',that.singleClick)
          .call(that.drag(that.simulation));

      that.links = that.links
          .data(links)
          .enter()
          .append('path')
          .attr("stroke", "#999")
          .attr("stroke-opacity", 0.6)
          //.attr("stroke-width", d => Math.sqrt(d.value));
          .attr("stroke-width", 1)
          .merge(that.links)
          .attr('class','link')
          .attr('marker-end','url(#positiveMarker') // 箭头
          //.attr('id',d=>d.source+'_'+d.relation+'_'+d.target)
          .attr('id',function (d){
            if(d.source instanceof Object){
              return d.source.id+'_'+d.relation+'_'+d.target.id
            }
            else{
              return d.source+'_'+d.relation+'_'+d.target
            }
          })

      that.linkText =  that.linkText
          .data(links)
          .enter()
          .append('text')
          .style('fill','#999')
          .style('font-size', 8)
          .style('text-anchor','middle')
          .style('cursor', 'pointer')
          //.style('text-weight','bold')
          //.attr('fill-opacity', 0)
          .attr('class', 'linktext')
          .merge(that.linkText)
          .text(function (d) {
            return d.relation
          })

      that.simulation.nodes(nodes);
      that.simulation.force('link').links(links);
      that.simulation.alpha(0.8).restart();
    },

    /**
     * 获得所有的节点并且按照等级进行存放
     */
    initNodeLevel(){
      let that = this
      that.getRequest('/java/kg/graph/allNode')
      .then(resp=>{
        for(let item of resp){
          item.lable = item.id
          item.value = item.name
          if(item.level === 0){
            that.nodeLevel0.push(item)
          }
          else if(item.level === 1){
            that.nodeLevel1.push(item)
          }
          else if(item.level === 2){
            that.nodeLevel2.push(item)
          }
          else if(item.level === 3){
            that.nodeLevel3.push(item)
          }
          else if(item.level === 4){
            that.nodeLevel4.push(item)
          }
          else if(item.level === 5){
            that.nodeLevel5.push(item)
          }
        }
        //console.log(that.nodeLevel1)
      })
    },

  },
  mounted() {
    let id = this.$route.query.id
    if(id){
      this.getNode(id)
      this.initNodeLevel()
    }
    else {
      this.disable = true
    }
  }

}
</script>

<style scoped>

</style>
