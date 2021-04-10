/**
 * 配置vue.config.js文件后需要重启vue服务
 * @type {{}}
 */


let proxyObj={}

//设置代理对象
proxyObj['/']={
    //websocket
    ws:false,
    target:'http://localhost:8081',
    changeOrigin:true,
    //不重写请求地址
    pathRewrite:{
        '^/':'/'
    }
}


module.exports={
    devServer:{
        host:'localhost',
        port:8080,
        proxy:proxyObj,
    }
}
