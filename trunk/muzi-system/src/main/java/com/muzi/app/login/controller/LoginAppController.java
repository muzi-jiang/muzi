package com.muzi.app.login.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muzi.app.login.service.LoginAppService;

/**
 * 移动端 用户登录
 * @author Administrator
 *
 */
@RequestMapping("/app/login")
@RestController
public class LoginAppController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LoginAppService loginService;
	
	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/api/userLogin")
	public Map<String, Object> login(String username,String password){
		logger.info("信息验证......");
		return loginService.login(username, password);
	}
}
