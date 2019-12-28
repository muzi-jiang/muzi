import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/menu/login')), 'login');
const menu = r => require.ensure([], () => r(require('@/menu/menu')), 'menu');
const user = r => require.ensure([], () => r(require('@/page/system/user')), 'user');
const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/menu',
      meta:{requireAuth:true}
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/menu',
      component: menu,
      name: 'menu',
      meta:{requireAuth:true},
      children: [{
        path: '/user',
        component: user,
        meta:{requireAuth:true}
      },]
    }
  ]
});

export default router;


router.beforeEach((to,from,next) =>{
  if(to.meta.requireAuth){
    if(sessionStorage.getItem("user") != null && sessionStorage.getItem("user") != undefined){
      next();
    }else{
      next({path:"/login"})
    }
  }else{
    if(sessionStorage.getItem("user") != null && sessionStorage.getItem("user") != undefined){
      next({path:"/menu"});
    }else{
      next()
    }
  }
});
