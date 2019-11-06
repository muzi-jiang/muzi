package com.muzi.app.login.dao;


import com.muzi.app.juzi.entity.User;

/**
 * 获取登录信息
 * @author Administrator
 *
 */
public interface LoginAppDao {

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	User findUserById(String username);
	
}
