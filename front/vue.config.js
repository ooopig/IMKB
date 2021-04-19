/**
 * 配置vue.config.js文件后需要重启vue服务
 * @type {{}}
 */


let proxyObj={}

//设置代理对象
proxyObj['/java']={
    //websocket
    ws:false,
    target:'http://localhost:8081',
    changeOrigin:true,
    //不重写请求地址
    pathRewrite:{
        '^/java':'/'
    }
}

proxyObj['/python']={
    //websocket
    ws:false,
    target:'http://127.0.0.1:5000',
    changeOrigin:true,
    //不重写请求地址
    pathRewrite:{
        '^/python':'/'
    }
}

module.exports={
    devServer:{
        host:'localhost',
        port:8080,
        proxy:proxyObj,
    }
}
