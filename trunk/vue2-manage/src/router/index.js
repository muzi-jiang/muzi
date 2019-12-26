import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

//const login = r => require.ensure([], () => r(require('@/menu/login')), 'login');
const login = r => require.ensure([], () => r(require('@/components/HelloWorld')), 'login');

export default new Router({
  routes: [
    {
      path: '/',
      component: login
    }
  ]
})
