import fetch from '@/config/fetch'

/**
 * 登陆
 */

export const login = data => fetch('/base01/system/manager/login/userLogin', data, 'POST');



/**
 * 
 * @param {获取句子列表数据} data 
 */
export const getJuziList = data => fetch('/base01/system/juzi/list', data, 'GET');