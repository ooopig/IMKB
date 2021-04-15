<template>
  <div id="KG" class="container">
<!--    顶部栏-->
    <div class="top">
<!--      选择器-->
      <el-select clearable
                 v-model="inputLevel"
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
      <el-input
          size="small"
          clearable
          prefix-icon="el-icon-search"
          class="inline-input"
          v-model="inputName"
          placeholder="请输入关键词"
          style="margin-right: 5px;width: 200px"
      >

      </el-input>

      <el-button type="primary" size="small"  icon="el-icon-search" @click="search">搜索</el-button>

      <div style="margin-left: 50px;display: flex;flex-direction: column;justify-content: center;color:#111111"> 节点数量：
        {{currentNodesNumber}}
      </div>
      <div style="margin-left: 50px;display: flex;flex-direction: column;justify-content: center;color:#111111"> 关系数量：
        {{currentLinksNumber}}
      </div>
      <!--    导出节点信息-->
      <el-button size="small" type="success" @click="exportGraph"style="margin-left: 100px">
        <i class="fa fa-level-up" aria-hidden="true"></i>
        导出数据
      </el-button>
      <!--      刷新-->
      <div @click="refresh" style="margin-left: 100px; cursor: pointer;color: #409eff;display: flex;flex-direction: column;justify-content: center">
        <i class="fa fa-refresh">刷新</i>
      </div>

    </div>

    <div class="bottom">
<!--      图谱展示部分-->
      <svg id="svg" class="ggraph"></svg>
<!--      节点信息面板-->
      <div class="board">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>当前节点</span>
            <span style="margin-left: 10px;color: #409eff">{{test}}</span>
          </div>
          <div class="text item" v-show="board.haveCurrentNode">
            <el-image style="height: 150px;margin-bottom: 5px" v-show="board.currentNode.pic" :src="board.currentNode.pic"></el-image>
<!--折叠面板-->
            <el-collapse v-model="activeNames" @change="handleChange" accordion>
              <el-collapse-item title="基本信息" name="1" >
                <p style="text-align: left">名称：{{board.currentNode.name}}</p>
                <p style="text-align: left">分组：{{board.currentNode.group}}</p>
<!--                <p style="text-align: left">描述：{{board.currentNode.summary}}</p>-->
              </el-collapse-item>
              <el-collapse-item title="父节点" name="2"  >
                <div v-if="board.parents" style="text-align: left" v-for="(v,k) in board.parents" :key="k">
                  {{v.name}}
                </div>
                <div v-else>
                  无
                </div>
              </el-collapse-item>
              <el-collapse-item title="子节点" name="3" >
                <div v-if="board.children" style="text-align: left" v-for="(v,k) in board.children" :key="k">
                  {{v.name}}
                </div>
                <div v-else>
                  无
                </div>
              </el-collapse-item>
            </el-collapse>
            <el-button @click="getNodeInfo" type="text" style="width: 100%" >查看更多信息</el-button>
          </div>
        </el-card>

<!--        <el-card class="operatenode" shadow="hover" v-show="board.showOperate&board.haveCurrentNode">-->
        <el-card class="operatenode" shadow="hover" v-show="false">
          <div slot="header" class="clearfix">
            <span>操作节点</span>
          </div>
          <div>
            <div style="display: flex;justify-content: space-around">
              <el-button @click="modifyNode"> 编辑</el-button>
              <el-button @click="addNode"> 添加</el-button>
              <el-button @click="deletekgNode"> 删除</el-button>
            </div>
          </div>
        </el-card>
      </div>

    </div>

<!--    节点编辑dialog-->
    <el-dialog title="节点编辑" :visible.sync="dialogEditNode" width="500px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="属性编辑" name="first">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="id">
              <el-input v-model="form.id"></el-input>
            </el-form-item>
            <el-form-item label="group">
              <el-input v-model="form.group"></el-input>
            </el-form-item>
            <div style="display: flex;flex-direction: row;justify-content: center">
              <el-button @click="editNodeSave(1)" type="primary">保存</el-button>
              <el-button @click="editNodeReset(1)">重置</el-button>
            </div>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="添加图片" name="second">
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :on-remove="removeImg"
              :on-success="uploadImgSuccess"
              :file-list="fileList"
              list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-tab-pane>
        <el-tab-pane label="添加描述" name="third">
          <my-component-ue :value="ueprop.defaultMsg" :config="ueprop.config" ref="ue"></my-component-ue>
          <div style="display: flex;flex-direction: row;justify-content: center">
            <el-button @click="editNodeSave(1)" type="primary">保存</el-button>
            <el-button @click="editNodeReset(1)">重置</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>

    </el-dialog>

<!--    添加dialog-->
    <el-dialog title="添加新关系" :visible.sync="dialogAdd" width="500px">


    </el-dialog>
    <!--      节点详细信息抽屉-->
    <el-drawer
        :title="board.currentNode.name"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">

      <el-collapse v-model="drawActiveName" accordion style="padding: 5px">
        <el-collapse-item title="图片" name="1" style="justify-content: center">
          <div class="demo-image__preview" style="display: flex;flex-direction: row;justify-content: center">
            <el-image
                style="width: 200px; height: 130px"
                :src="board.currentNode.pic"
                :preview-src-list="srcList">
            </el-image>
          </div>
        </el-collapse-item>
        <el-collapse-item title="介绍" name="2">
          <p>
            {{board.currentNode.summary}}
          </p>
        </el-collapse-item>
        <el-collapse-item title="属性" name="3">
          <p v-for="(v,k) in board.currentNode.basic_info" :key="v">
            {{k}}：{{v}}
          </p>
        </el-collapse-item>
        <el-collapse-item title="创建时间" name="4">
          <p>{{board.currentNode.createTime}}</p>
        </el-collapse-item>
      </el-collapse>
    </el-drawer>

  </div>
</template>
<script>
import * as d3 from 'd3'
import $ from "jquery";
import MyComponentUe from "../../components/UE";

export default {
  name: "KbKG",
  components: {MyComponentUe},
  data(){
    return{
      drawer: false,
      direction: 'rtl',
      drawActiveName:'1',
      srcList:[],
      ueprop:{
        defaultMsg: '这里是UE测试',
        config: {
          initialFrameWidth: null,
          initialFrameHeight: 500
        }
      },
      nodeNames:[],
      timeout:null,
      dialogEditNode:false,
      dialogAdd:false,
      activeNames: ['1'],
      activeName: 'first',
      AddActiveName:'first',
      form:{

      },
      fileList:[],

      links:[],
      nodes:[],
      nodeText:[],
      linkText:[],
      simulation:null,
      dataGraph:{
        "nodes": [],
        "links": [],
      },
      colorList : [
        '#FE0302','#71d075','#1699c1','#0be09d',
         '#00CD00','#89D5FF','#FF7204','#019899',
        '#c46bc6', '#d72c2c','#1d3093',
      ],
      height:0,
      width:0,

      inputName:'',
      inputLevel:'',
      currentNodesNumber:1,
      currentLinksNumber:1,

      existNodeId: [],
      existRelationId:[],

      options:
          [
          {
        value: '1',
        label: '一级节点',
        color: '#71d075'
      }, {
        value: '2',
        label: '二级节点',
        color: '#1699c1'
      }, {
        value: '3',
        label: '三级节点',
        color: '#0be09d'
      }, {
        value: '4',
        label: '四级节点',
        color: '#00CD00'
      }, {
        value: '5',
        label: '五级节点',
        color: '#FF7204'
      }],
      value: '',
      test:'',
      board:{
        haveCurrentNode:false,
        showOperate:true,
        currentNode:{

        },
        children:[],
        parents:[],
      },
      drawNode:{},

    }
  },
  methods:{
    /**
     * 抽屉关闭函数
    */
    handleClose(done) {
      done()
    },

    /**
     * 富文本编辑器
     * */
    getUEContent() {
      let content = this.$refs.ue.getUEContent();
      console.log(content)
    },
    setText(){
      let content = this.$refs.ue.setText();
    },
    /**
     * 折叠面板处理函数
    */
    handleChange(val) {
      if(val === 1){

      }
      else if(val === 2){

      }
      else if(val === 3){

      }
    },
    /**
     * 节点修改
     */
    modifyNode(){
      let that = this
      that.dialogEditNode = true;
      that.form = that.board.currentNode
      //console.log("编辑节点",that.form)
    },

    removeImg(){

    },
    uploadImgSuccess(){

    },

    editNodeSave(id){
      let msg = ""
      if(id === 1){
        msg = '修改属性'
      }
      else if(id === 2) {
        msg='上传图片'
      }
      else if(id === 3) {
        msg = '添加描述'
      }
      else if(id === 4) {
        msg = '上传文件'
      }
      this.$message({
        showClose: true,
        message: msg
      });
    },
    editNodeReset(id){

      if(id === 1){
        this.form = this.board.currentNode
      }
      else if(id === 2) {

      }
      else if(id === 3) {

      }
      else if(id === 4) {

      }
    },

    /**
     * 删除节点
     * */
    deletekgNode(){
      let node = this.currentNode
      this.$confirm('此操作将永久删除该节点, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/manage/kb/delNode/'+node.id)
            .then(resp=>{
              if(resp){
                //刷新动画
              }
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    /**
     * 添加节点
    */
    addNode(){
      let that = this
      that.dialogAdd = true
    },

    /**
     * 初始化
     * @param data
     */
    initGraph(data){
      let that = this
      //console.log(that.colorList)
      // const links = data.links.map(d => Object.create(d));
      // const nodes = data.nodes.map(d => Object.create(d));

      d3.select('svg').selectAll('*').remove();

      that.width = $('#svg').width();
      that.height = $('#svg').height();

      const links = data.links
      const nodes = data.nodes

      that.currentNodesNumber = nodes.length;
      that.currentLinksNumber = links.length;


      that.simulation = d3.forceSimulation(nodes)
          .force("link", d3.forceLink(links).id(d => d.id).distance(150))
          .force("charge", d3.forceManyBody().strength(-100))
          .force('collide',d3.forceCollide().strength(-30))
          .force("center", d3.forceCenter(that.width / 2, that.height / 2));

      const svg = d3.select("svg")
          .attr("viewBox", [0, 0, that.width, that.height])
          // .attr('width',that.width)
          // .attr('height',that.height)
          // .attr('preserveAspectRatio', 'xMidYMidmeet')

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
          /**
           .append('textPath')
           .attr(
           'xlink:href',function (d){
                //console.log(d)
                return "#"+d.source+'_'+d.relation+"_"+d.target
              }
           //d=>"#"+d.source+'_'+d.relation+"_"+d.target
           )
           .attr('startOffset','50%')
           */
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
     * 单击节点事件,获取该节点的所有信息
     * @param d
     */
    singleClick(d){
      let that = this
      that.getRequest('/kg/graph/node/'+d.id)
          .then(node=>{
            that.board.haveCurrentNode=true
            let prop = JSON.parse(node['properties'])
            that.board.currentNode = {
              id:node.id,
              name:node.name,
              group:node.level,
              summary:prop.summary,
              pic:prop.pic,
              basic_info:prop.basic_info,
              best_practices:prop.best_practices,
              pics:[],
              files:[],
              createUser:node.createUser,
              createTime:node.createTime,
            }
            //console.log(that.board.currentNode)
          })
      that.getNodeChildren(d.id)
      that.getNodeParent(d.id)
    },


    /**
     * 获得节点的信息
     */
    // getNodeInfo(id){
    //   let that = this
    //   that.drawer = true
    //   that.getRequest('/kg/graph/node/'+id)
    //   .then(resp=>{
    //     console.log(resp)
    //     that.drawNode = {
    //       'id':resp['id'],
    //       'name':resp['name'],
    //       'properties':JSON.parse(resp['properties']),
    //     }
    //   })
    // },
    getNodeInfo(){
      this.drawer = true
    },

    /**
     * 获得一个节点的所有孩子
     *
    */
    getNodeChildren(id){
      let that = this
      that.getRequest('/kg/graph/getChildren/'+id)
          .then(resp=>{
              that.existNodeId = []
              for (let node in that.dataGraph.nodes) {
                that.existNodeId.push(that.dataGraph.nodes[node].id)
              }
            if(resp){
              that.board.children = resp
              for (let index in resp) {
                let temp_node = {
                      "id":resp[index].id,
                      "name":resp[index].name,
                      "group":resp[index].level,
                }

                if (that.existNodeId.includes(temp_node.id)){

                }
                else {
                  that.dataGraph.nodes.push(temp_node)
                  that.existNodeId.push(temp_node.id)
                }
              }
              //获得所有子关系
              that.getNodeRelations(id)
              //that.updateGraph(that.dataGraph)
            }
      })
    },

    /**
     * 获得一个节点的所有父亲
     *
     */
    getNodeParent(id){
      let that = this
      that.getRequest('/kg/graph/getParents/'+id)
          .then(resp=>{
            if(resp){
              that.board.parents = resp
              //console.log('孩子',that.board.children)
            }
          })
    },

    /**
     * 获得该节点的所有关系
     */
    getNodeRelations(id){
      let that = this
      that.getRequest('/kg/graph/getRelation/'+id)
          .then(resp=>{
            if(resp){
              that.existRelationId = []
              for (let k in that.dataGraph.links) {

                that.existRelationId.push(that.dataGraph.links[k].relationId)
              }
              //console.log(that.dataGraph.links)
              for (let index in resp) {
                //let existRelationId = []
                //console.log(existRelationId)
                let temp_link =
                    {
                      "source":resp[index].sourceId,
                      "relationId":resp[index].id,
                      "target":resp[index].targetId,
                      "value":resp[index].weight,
                      "relation":resp[index].name
                    }
                if (that.existRelationId.includes(temp_link.relationId)){

                }
                else {
                    if(that.existNodeId.includes(temp_link.source) && that.existNodeId.includes(temp_link.target)){
                      that.dataGraph.links.push(temp_link)
                      that.existRelationId.push(temp_link.relationId)
                    }else {
                      console.log('与之相关的节点不存在')
                  }
                }
              }
              //console.log(that.dataGraph.links)
              //that.clearNodeAndRelation(that.dataGraph);
              that.updateGraph(that.dataGraph)
            }
          })
    },

    /**
     * 处理节点文字换行
     * @param d3text
     * @param text
     */
    textBreaking(d3text, text) {
      const len = text.length
      if (len <= 3) {
        d3text.append('tspan')
            .attr('x', 0)
            .attr('y', -3)
            .text(text)
      } else {
        const topText = text.substring(0, 3)
        const midText = text.substring(3, 7)
        let botText = text.substring(7, len)
        let topY = -22
        let midY = 8
        let botY = 34
        if (len <= 9) {
          topY += 10
          midY += 10
        } else {
          botText = text.substring(7, 9) + '...'
        }
        d3text.text('')
        d3text.append('tspan')
            .attr('x', 0)
            .attr('y', topY)
            .text(function () {
              return topText
            })
        // d3text.append('tspan')
        //     .attr('x', 0)
        //     .attr('y', midY)
        //     .text(function () {
        //       return midText
        //     })
        // d3text.append('tspan')
        //     .attr('x', 0)
        //     .attr('y', botY - 7)
        //     .text(function () {
        //       return botText
        //     })
      }
    },
    /**
     * 动态刷新
     * @param data
     */
    updateGraph(data){
      let that = this
      //console.log(that.colorList)
      const nodes = data.nodes
      const links = data.links


      that.currentNodesNumber = nodes.length;
      that.currentLinksNumber = links.length;

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

    initNodeNames() {
      let that = this
      that.getRequest('/kg/graph/search/names')
      .then(resp=>{
        that.nodeNames = resp
        console.log('名字：',that.nodeNames)
      })

    },

    /**
     * 查找
     */
    search(){
      let that = this
      that.getRequest('/kg/graph/search?name='+that.inputName+'&level='+that.inputLevel)
      .then(resp=>{
        if(resp){
          let temp_nodes = []
          //console.log(resp)
          for (let index in resp) {

            temp_nodes.push(
                {
                  "id":resp[index].id,
                  "name":resp[index].name,
                  "group":resp[index].level
                }
            )
          }
          that.dataGraph.nodes = temp_nodes
          that.dataGraph.links = []
          that.initGraph(that.dataGraph)
        }
      })

    },

    /**
     * 刷新图谱
     */
    refresh(){
      // this.generateTestGraph();
      // this.initGraph(this.testGraph)
      this.board.haveCurrentNode = false
      this.test = ""
      this.initNode()
    },

    /**
     * 导出节点数据为csv
     *
     */
    exportGraph(){
      //要导出的json数据
      const jsonData = this.testGraph.nodes
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
      link.download =  "节点数据.csv";
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },

    /**
     * 获得原始节点数据
     */
    initNode(){
      let that = this
      that.getRequest('/kg/graph/initNode')
          .then(resp=>{
            if(resp){
              //that.nodes = resp
              let temp_nodes = []
              for (let index in resp) {
                temp_nodes.push(
                    {
                      "id":resp[index].id,
                      "name":resp[index].name,
                      "group":resp[index].level
                    }
                )
              }
              that.dataGraph.nodes = temp_nodes
              that.initLink()
            }
          })
    },

    /**
     * 获得原始关系数据
     */
    initLink(){
      let that = this
      that.getRequest('/kg/graph/getRelation/1')
          .then(resp=>{
            if(resp){
              let temp_links = []
              for (let index in resp) {
                temp_links.push(
                    {
                      "source":resp[index].sourceId,
                      "relationId":resp[index].id,
                      "target":resp[index].targetId,
                      "value":resp[index].weight,
                      "relation":resp[index].name
                    }
                )
              }
              //console.log(temp_nodes)
              that.dataGraph.links = temp_links
              //console.log(that.dataGraph.links)
              //初始化图谱
              that.initGraph(that.dataGraph)

            }
          })
    },

    isShowOperate(){
      if(this.$store.state.currentUser.roles[0].id === 901){
        this.board.showOperate = false
        //console.log(this.board.showOperate)
      }
      else{
        this.board.showOperate = true
      }
    }

  },

  mounted() {
    this.initNode()
    this.isShowOperate()
  },
  created() {
    // this.generateTestGraph();
    // this.initGraph(this.testGraph)
  }
}
</script>

<style>
.container{
  display: flex;
  flex-direction: column;
  height: 100%;
}
.top{
  padding: 3px;
  border-bottom: 1px solid #e3e3e3;
  display: flex;
  flex-direction: row;
}
.bottom{
  display: flex;
  flex-direction: row;
  height: 100%;
}
.ggraph{
  width: 80%;
}
.board{
  width: 20%;
  border-left: #e3e3e3 solid 2px;
  /*background: #e3e3e3;*/
}
.box-card{
  margin: 5px;
}
.operatenode{
  margin: 5px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
