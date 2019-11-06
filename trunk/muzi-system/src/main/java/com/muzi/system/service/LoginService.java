package com.muzi.system.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户认证服务层
 * @author Administrator
 *
 */
public interface LoginService {
		
	
	String userLogin(String userName,String passWord,Model model);
	
	
	ModelAndView toIndex();
	
	
}
