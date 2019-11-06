import Vue from 'vue'
import Router from 'vue-router'

import menubar from '../components/menubar'
import index from  './../components/index'
import sentence from '../components/sentence'
import like from '../components/like'
import myinfo from  './../components/myinfo'
import login from  './../components/login'


Vue.use(Router);

export default new Router({
  //更改为history模式
  mode:'history',
  routes: [

    //有底部菜单栏的组件
    {path:'/',component:menubar,
     children:[
       {path:"/",redirect:'/index'},
       {path:"/index",component:index},
       {path:"/text",component:sentence,meta:{keepAlive:true}},
       {path:"/like",component:like},
       {path:"/myinfo",component:myinfo,meta:{requireAuth:true}}   //requireAuth:true表示进入该路由需要进行验证
     ]
    },
    //登录页面
    {path:'/login',component:login}

  ],
  linkActiveClass:'mui-active'  //覆盖默认的高亮的类router-link-exact-active
})
