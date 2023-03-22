
import axios from "axios";
import Vue from "vue";
import { Toast } from 'vant';
import Cookies from "js-cookie";
import router from "@/router/router";
Vue.use(Toast);




//给url加上指定的地址,当发送请求前会先和当前路径进行拼接,
//所以发送请求的那边就可以省略这一句不用写了
// axios.defaults.baseURL = "http://127.0.0.1:8848/api"
axios.defaults.baseURL = "http://43.139.31.34:8848/api/"
// axios.defaults.baseURL = "http://115.236.153.177:45107/api"
//设置超时时间
axios.defaults.timeout = 7000



// axios请求拦截器
axios.interceptors.request.use(
config=>{

    config.headers = {
        "Accept-Language":"en-CN;q=1.0",
        "source_type":"apple-appstore",
        "version_code":"5.4.8",
        "push_device_type":"4",
        "Accept": "*/*",

    }

    //在这里面可以进行一些操作,如果不放行就会失败,从而无法访问
    if(Cookies.get("token")){
        config.headers['Authorization'] = 'Bearer '+Cookies.get("token")
        config.headers["user"] = Cookies.get("user")
    }
    config.headers = {...config.headers}//如果不这样写,那么在接口中设置的header都会被覆盖

    //放行
    return config
},error=>{

    Toast.fail(error);
    return Promise.reject(error)
})


// axios响应拦截器
axios.interceptors.response.use(
    request =>{
        let status = request.data.status
        request = request.data
        if(status === 401){
            Cookies.remove("token")
            sessionStorage.clear()
            if(router.history.current.path !== "/login"){
                router.push("/")
            }
            Toast.fail(request.msg);
        }else if(status === 404){
            Toast.fail(request.msg);
        }
        return request
    },
    //只要响应的状态码不是2 开头的都是失败的请求
    error =>{
        if(error.message === "timeout of 7000ms exceeded"){
            error.message = "当前网络差,请刷新重试"
        }
        Toast.fail(error);
        // console.log("响应拦截器失败的回调",error)
        // return Promise.reject(error)
        //直接在这处理失败的信息,这样发送请求时只要接收成功的,失败的统一在这处理
        return new Promise(()=>{})
    }
)

export default axios
