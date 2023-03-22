import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

//引入路由器
import VueRouter from "vue-router";
import router from "./router/router.js";

Vue.use(VueRouter)

import 'vant/lib/index.css';
import "@/plugins/vant.js"
import {
    Toast,Dialog,Notify
} from 'vant';


Vue.prototype.$toast = Toast
Vue.prototype.$dialog = Dialog
Vue.prototype.$notity = Notify
Vue.prototype.$baseApi = "http://43.139.31.34:8848/api/file/"
// Vue.prototype.$baseApi = "http://127.0.0.1:8848/api/file/"
// Vue.prototype.$baseApi = "http://115.236.153.177:45107/api/file/"

new Vue({
    render: h => h(App),
    router
}).$mount('#app')
