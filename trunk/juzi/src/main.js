// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/index'

Vue.config.productionTip = false;

//导入mui样式
import './lib/css/mui/css/mui.min.css';

import VueResource from 'vue-resource';
Vue.use(VueResource);

import axios from 'axios';
Vue.prototype.axios = axios;

//导入mint-ui组件
import 'mint-ui/lib/style.css'
import { Loadmore,Spinner,Header,Tabbar,TabItem   } from 'mint-ui';
Vue.component(Loadmore.name, Loadmore);
Vue.component(Spinner.name, Spinner);
Vue.component(Header.name, Header);
Vue.component(Tabbar.name, Tabbar);
Vue.component(TabItem.name, TabItem);


import { Button, Container } from "element-ui";
Vue.component(Button.name, Button);
Vue.component(Container.name, Container);


//导入时间格式化的插件
import moment from 'moment';
//定义全局的时间过滤器
Vue.filter('dateFormat',function (dataStr,pattern = "YYYY-MM-DD HH:mm:ss") {
  return moment(dataStr).format(pattern);
});


import global from './router/global';
//定义全局路由跳转拦截
router.beforeEach((to,from,next) => {
  if (to.matched.some(res => res.meta.requireAuth)) {   //表示需要登录
    //设置回调地址
    global.topath = to.path;
    global.frompath = from.path;
    let user = sessionStorage.getItem("app_user");
    if(null == user){
      console.log("该请求需要验证身份");
      next({path:'/login'});
    }else{
      next();
    }
  }
  next();
});


new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});













