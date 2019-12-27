import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/menu/login')), 'login');
const menu = r => require.ensure([], () => r(require('@/menu/menu')), 'menu');

export default new Router({
  routes: [
    {
      path: '/',
      component: login
    },
    {
      path: '/menu',
      component: menu,
      name: '',
      // children: [{
      //   path: '',
      //   component: home,
      //   meta: [],
      // }]
    }
  ]
})
