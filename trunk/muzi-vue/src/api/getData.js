//国际化处理
import Vue from 'vue';
import VueI18n from 'vue-i18n';
Vue.use(VueI18n);
import { messages } from './../components/common/i18n';
const i18n = new VueI18n({
    locale: 'zh',
    messages
});
export const vueI18n = new Vue({ i18n });


import fetch from '@/config/fetch'

/**
 * 登陆
 */
export const login = data => fetch('/system/manager/login/userLogin', data, 'POST');

/**
 * 
 * @param {获取登录用户的菜单权限信息} data 
 */
export const getUserMenu = () => fetch('/system/manager/permission/getUserMenu');


/**
 * @param {获取所有菜单列表} data  
 */
export const getPermission = () => fetch('/system/manager/permission/list');

/**
 * @param {保存菜单信息} data 
 */
export const savePermission = data => fetch('/system/manager/permission/savePermiss', data ,'POST');

/**
 * 
 * @param {删除菜单} data 
 */
export const delPermission = data => fetch('/system/manager/permission/delPermission', data);




/**
 * @param {获取所有角色列表} data  
 */
export const getRole = () => fetch('/system/manager/role/list');



/**
 * @param {保存角色} data 
 */
export const saveRole = data => fetch('/system/manager/role/saveRole', data ,'POST');


/**
 * 
 * @param {删除角色} data 
 */
export const delRole = data => fetch('/system/manager/role/delRole', data);




/**
 * @param {获取所有用户} data  
 */
export const getUser = data => fetch('/system/manager/user/list', data);


/**
 * @param {保存当前用户} data 
 */
export const saveUser = data => fetch('/system/manager/user/saveUser', data ,'POST');


/**
 * 
 * @param {删除用户} data 
 */
export const delUser = data => fetch('/system/manager/user/delUser', data);






/**
 * 
 * @param {获取句子列表数据} data 
 */
export const getJuziList = data => fetch('/system/juzi/findPages', data );