## IMKG:智能制造的知识工程应用
## 说明
本科毕设项目，为智能制造相关产业建立一个知识库管理系统。
为智能制造知识库的构建提供一个网络建设平台。

## 
### 1. 技术点-后端
- Spring Boot
- Mysql
- neo4j
- MybatisPlus
- Spring Security
- JWT
- Swagger
- RBAC权限管理

### 2. 需要解决的问题
- 文件上传、下载、在线浏览（文件服务器域名配置）
- 知识库的数据库该如何建立（节点的属性等）
- 有关知识库的页面设计

### 3. 数据库设计
我是用的是mysql数据库，建表文件就是imkg.sql, 数据库概念模型如下：
![image](https://user-images.githubusercontent.com/46115362/170702825-f6348651-b257-442e-aca2-cf62fa2f2a6b.png)
虽然项目中用到了neo4j，但是由于操作比较繁琐，后面就放弃了，我直接将系欸但和关系存入到了mysql中，利用mysql进行增删改查

### 4. 运行
运行前需要进入application.yaml配置文件，针对自己的环境修改一下配置，主要是数据库
然后直接运行项目即可
后端运行成功后，可以使用swagger打开网页（http:localhost:8081/doc.html） 查看所有的API接口，并且进行测试
