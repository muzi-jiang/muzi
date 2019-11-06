package com.muzi.app.login.service;

import java.util.Map;

public interface LoginAppService {
	
	
	/**
	 * 用户登录验证信息
	 * @param username
	 * @param password
	 * @return
	 */
	Map<String, Object> login(String username,String password);
}
