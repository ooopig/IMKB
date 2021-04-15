<template>
  <div>
    <el-tabs type="border-card">
      <el-tab-pane label="知识添加">
        <el-form ref="form" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="标题" placeholder="请输入标题" prop="name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="基本介绍">
            <el-input
                type="textarea"
                autosize
                placeholder="请输入内容"
                v-model="form.summary">
            </el-input>
          </el-form-item>
          <el-form-item label="描述图片">
            <el-upload
                action="#"
                ref="uploadImg"
                drag
                accept="image/jpeg,image/png,image/jpg"
                multiple
                :file-list="picList"
                :http-request="uploadPic"
                :before-upload="onBeforeUploadImage"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </el-form-item>
          <el-form-item
              :label="'相关文件'">
            <div style="display: flex;flex-direction: row" v-for="file in files">
              <el-input
                  type="textarea"
                  autosize
                  placeholder="请输入文件大致描述"
                  v-model="file.name">
              </el-input>
              <el-button style="margin-left: 10px" size="small" @click.prevent="removeFile(file)" type="danger" plain>删除</el-button>
            </div>
            <el-upload
                action="#"
                :limit="1"
                :http-request="uploadFile"
                :on-exceed="handleExceed"
                :file-list="fileList">
              <el-button size="small" type="primary">选择文件</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item style="display: flex;flex-direction: row;justify-content: center">
            <el-button  size="small" @click="addFile" type="primary" plain>新增文件</el-button>
          </el-form-item>
            <el-form-item
            :label="'最佳实践'">
            <div style="display: flex;flex-direction: row"
                 v-for="(bestPractice, index) in form.bestPractices"
                 :key="bestPractice.key">
              <el-input
                  type="textarea"
                  autosize
                  placeholder="请输入内容"
                  v-model="bestPractice.value">
              </el-input>
              <el-button style="margin-left: 10px" size="small" @click.prevent="removePractice(bestPractice)" type="danger" plain>删除</el-button>
              <el-button style="margin-left: 10px" size="small" @click="addPractice" type="primary" plain>新增</el-button>
            </div>
          </el-form-item>
          <el-form-item style="margin-top: 50px;display: flex;flex-direction: row;justify-content: center">
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="cancle">取消</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
<!--      <el-tab-pane label="知识修改">修改</el-tab-pane>-->
    </el-tabs>
  </div>
</template>

<script>
import Bmob from "hydrogen-js-sdk";

export default {
  name: "KbImport",
  data() {
    return {
      uid:0,
      form: {
        id:'',
        name: '',
        createUser: '',
        summary:'',
        bestPractices:[
            {value:'',key:'123'}
        ],
      },
      rules: {
        name: [
          {required: true, message: '请输入标题', trigger: 'blur'},
        ],
      },
      picList:[],
      fileList:[],
      files:[
        {name:'',path:'',uuid:'123'}
      ],
      logo:"",
      needUploadImages:[],
      needUploadFiles:[],
    }
  },
  methods: {
    removePractice(item) {
      let index = this.form.bestPractices.indexOf(item)
      if (index !== -1) {
        this.form.bestPractices.splice(index, 1)
      }
    },
    addPractice() {
      this.form.bestPractices.push({
        value: '',
        key: Date.now()
      })
    },
    removeFile(item) {
      let index = this.files.indexOf(item)
      if (index !== -1) {
        this.files.splice(index, 1)
      }
    },
    addFile() {
      let index = this.files.length-1
      let temp = this.files[index]
      console.log(temp)
      if(temp.name === '' || temp.uuid === ''){
        this.$message.error(`文件描述或者文件不能为空`)
      }
      else {
        this.files.push({
          name: '',
          path:'',
          uuid:''
        })
      }

    },
    onSubmit() {
      let that = this
      let flag = false
      if(that.form.name == ''){
        this.$message.error("请输入标题")
        return
      }
      //提交表单创建节点
      let param = {
        createTime: "",
        createUser: that.uid,
        level: 0,
        modifyTime: "",
        modifyUser: 0,
        name: that.form.name,
        properties: "",
        status: 0
      }

      //上传图片到bmob
      //console.log('上传',param.file)
      let fileimg
      let uploadedNode
      let imgsToDatabase
      if(that.needUploadImages.length>0){
        console.log('有图片')
        for(let item of that.needUploadImages){
          fileimg = Bmob.File(item.name,item)
        }
        fileimg.save().then(resp=>{
          console.log('保存图片完成',resp);
          imgsToDatabase = resp
          let properties = {
            summary:that.form.summary,
            basic_info:'',
            pic:resp[0].url,
            best_practices:that.form.bestPractices
          }
          param.properties = JSON.stringify(properties)
          //保存该节点
          that.postRequest('/kg/graph/addNode',param)
          .then(resp01=>{
              if(resp01){
                flag = true
                uploadedNode = resp01.obj
                console.log('保存节点完成',uploadedNode)
                //图片数据写入数据库
                for(let i = 0;i<that.needUploadImages.length;i++){
                  let para = {
                    createTime: "",
                    createUser: that.uid,
                    downTimes: 0,
                    name: that.needUploadImages[i].name,
                    nodeId: uploadedNode.id,
                    type: "img",
                    url: imgsToDatabase[i].url
                  }
                  that.postRequest('/file/upload',para)
                  .then(resp02=>{
                    if(resp02){
                      console.log('图片写入数据库完成')
                      flag = true
                    }
                  })
                }
                console.log('文件长度',that.needUploadFiles.length)
                //上传文件
                if(that.needUploadFiles.length>0){
                  let file
                  for(let item of that.needUploadFiles){
                    file = Bmob.File(item.name+'',item)
                    //console.log(item)
                  }
                  file.save().then(resp03=>{
                    console.log('文件传bmob完成',resp03)
                    let filesToDatabase = resp03
                    //文件数据写入数据库
                    for(let i = 0;i<that.needUploadFiles.length;i++){
                      let para = {
                        createTime: "",
                        createUser: that.uid,
                        downTimes: 0,
                        description:that.files[i].name,
                        name: that.needUploadFiles[i].name,
                        nodeId: uploadedNode.id,
                        type: "file",
                        url: filesToDatabase[i].url
                      }
                      that.postRequest('/file/upload',para)
                          .then(resp03=>{
                            if(resp03){
                              console.log('文件写入数据库完成')
                              flag = true
                            }
                          })
                    }
                  })
                }
              }
          })
        })
        //this.clear()
      }
      //没有描述图片
      else {
        let properties = {
          summary:that.form.summary,
          basic_info:'',
          pic:'',
          best_practices:that.form.bestPractices
        }
        param.properties = JSON.stringify(properties)
        //保存该节点
        that.postRequest('/kg/graph/addNode',param)
            .then(resp01=>{
              if(resp01){
                flag = true
                uploadedNode = resp01.obj
                //console.log(uploadedNode)
                //上传文件
                if(that.needUploadFiles.length>0){
                  let file
                  for(let item of that.needUploadFiles){
                    file = Bmob.File(item.name+'',item)
                    //console.log(item)
                  }
                  file.save().then(resp03=>{
                    //console.log('文件传bmob',resp03)
                    let filesToDatabase = resp03
                    //文件数据写入数据库
                    for(let i = 0;i<that.needUploadFiles.length;i++){
                      let para = {
                        createTime: "",
                        createUser: that.uid,
                        downTimes: 0,
                        description:that.files[i].name,
                        name: that.needUploadFiles[i].name,
                        nodeId: uploadedNode.id,
                        type: "file",
                        url: filesToDatabase[i].url
                      }
                      that.postRequest('/file/upload',para)
                          .then(resp02=>{
                            if(resp02){
                              flag = true
                            }
                          })
                    }
                  })
                }

              }
            })
        //this.clear()
      }

    },
    cancle(){
      this.clear()
    },
    clear(){
      this.form = {
        id:'',
            name: '',
            createUser: '',
            summary:'',
            bestPractices:[
          {value:'',key:'123'}
        ],
      }
      this.files = [
        {name:'',path:'',uuid:'123'}
      ]
      this.needUploadFiles = []
      this.needUploadImages = []
      this.picList = []
      this.fileList = []
    },
    onBeforeUploadImage(file) {
      const isIMAGE = file.type === 'image/jpeg' || 'image/jpg' || 'image/png'
      const isLt1M = file.size / 1024 / 1024 < 50
      if (!isIMAGE) {
        this.$message.error('上传文件只能是图片格式!')
      }
      if (!isLt1M) {
        this.$message.error('上传文件大小不能超过50MB!')
      }
      return isIMAGE && isLt1M
    },
    uploadPic(param){
      this.needUploadImages.push(param.file)
    },
    uploadFile(param){
      this.needUploadFiles.push(param.file)
      let index = this.files.length-1
      this.files[index].uuid = param.file.uid
    },
    handleRemove(file, fileList) {
      for(let i = 0;i<this.needUploadImages.length;i++){
        if(this.needUploadImages[i].name == file.name){
          this.needUploadImages.splice(i,1)
          break;
        }
      }
    },
    handlePreview(file) {
      //console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件`);
    },
  },
  mounted() {
    this.uid = this.$store.state.currentUser.id
  }
}
</script>

<style scoped>

</style>
