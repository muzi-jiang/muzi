import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/components/page/login/login')), 'login');
const menu = r => require.ensure([], () => r(require('@/components/common/Home')), 'menu');
const dashboard = r => require.ensure([], () => r(require('@/components/page/Dashboard')), 'dashboard');


const juzi = r => require.ensure([], () => r(require('@/components/page/juzi/juzi')), 'juzi');

//系统管理
const permission = r => require.ensure([], () => r(require('@/components/page/system/permission')), 'permission');
const role = r => require.ensure([], () => r(require('@/components/page/system/role')), 'role');
const user = r => require.ensure([], () => r(require('@/components/page/system/user')), 'user');

//路由定义
const router = new Router({mode:'history',routes: []});
export default router;

/**
 * 动态加载路由
 */
import axios from 'axios'
getRouter();
function  getRouter(){
  axios.get("/api/system/manager/permission/getRouterAll").then ( res => {
    var array = res.data;
    var obj = array[0];
    obj.component = eval(obj.component);

    var childrens = obj.children;
    for(var i = 0;i<childrens.length; i++){
      var childrenObj = childrens[i];
      childrenObj.component = eval(childrenObj.component)
    }

    //添加其他系统组件
    array.splice(0,0,{path: '/',redirect: '/dashboard',meta:{requireAuth:true,title:"根目录"}});
    array.splice(0,0,{path: '/login',component: login,meta:{title:"登录界面"}});
      
    router.addRoutes(array);
  },err =>{
      console.log("动态路由加载失败...")
  });  
}


