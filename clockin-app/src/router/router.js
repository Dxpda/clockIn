import VueRouter from "vue-router";
import login from "@/view/home/login";
import index from "@/view/home/index";
import home from "@/view/home/home";
import news from "@/view/home/news";
import history from "@/view/home/history";
import dada from "@/view/home/add";
import sms from "@/view/home/sms";


export default new VueRouter({
    routes:[
        {
            path:"/",
            name:"Login",
            component:login,
        },

        {
            path:"/a/b/c/d/e/f/g/h/i/j/k/q/w/e/r/t/y/u/i/o/p/a/s/d/f/g/h/j/k/l/z/x/c/v/b/n/m/da/xing/peng/register/sms",
            name:"Sms",
            component:sms
        },


        {
            path:"/index",
            name:"Index",
            component:index,
            meta:{
                title:"首页"
            },
            children:[{
                path:"home",
                name:"Home",
                component: home,
            },
            {
                path:"news",
                name:"News",
                component:news
            },
            {
                path:"history",
                name:"History",
                component:history,

            },
            {
                path:"add",
                name:"Add",
                component:dada,
                meta:{
                    title:"添加考勤"
                }
            }]
        }
    ]
})
