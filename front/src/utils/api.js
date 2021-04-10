import axios from "axios";
import {Message} from 'element-ui'
import router from "../router";

//请求拦截器
/**
 * 这个请求拦截器的功能是为我每一次请求去判断是否有token，如果token存在则在请求头加上这个token。
 * 后台会判断这个token是否过期。
 */
axios.interceptors.request.use(config=>{
    //将token放在请求头中，判断是否已经登录
    const token = window.sessionStorage.getItem('tokenStr');
    if(token){
        config.headers['Authorization'] = token;
    }
    return config;
},error => {
    //alert("请求拦截器拦截");
    console.log(error);
})

//响应拦截器,对于所有的响应进行统一的处理
axios.interceptors.response.use(success=>{
    //业务逻辑错误
    if(success.status && success.status == 200){
        if(success.data.code==500 || success.data.code==401 || success.data.code==403){
            Message.error({message:success.data.message});
            return;
        }
        if (success.data.message){
            Message.success({message:success.data.message});
        }
        return success.data;  //返回响应结果
    }
},error => {
    if(error.response.code==504 || error.response.code ==404){
        Message.error({message:'土豆服务器被吃啦o( ╯□╰ )o'});
    }else if (error.response.code == 403){
        Message.error({message:'权限不足'});
    }else if(error.message.code == 403){
        Message.warning({message:'请登录'});
        router.replace('/');
    }else{
        if(error.response.data.message){
            Message.error({message:error.response.data.message});
        }else{
            Message.error({message:'未知错误'});
        }
    }
    return;
})

let base=''

export const postRequest=(url,params)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:params
    })
}

export const getRequest=(url,params)=>{
    return axios({
        method:'get',
        url:`${base}${url}`,
        data:params
    })
}

export const putRequest=(url,params)=>{
    return axios({
        method:'put',
        url:`${base}${url}`,
        data:params
    })
}

export const deleteRequest=(url,params)=>{
    return axios({
        method:'delete',
        url:`${base}${url}`,
        data:params
    })
}
