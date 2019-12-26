import fetch from '@/config/fetch'

/**
 * 登录
 * @param {} data 
 */
export const login = data => fetch('/base01/system/manager/login/userLogin', data, 'POST');