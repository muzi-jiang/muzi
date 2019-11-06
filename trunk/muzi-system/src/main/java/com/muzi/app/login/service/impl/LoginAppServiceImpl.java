package com.muzi.app.login.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muzi.app.juzi.entity.User;
import com.muzi.app.login.dao.LoginAppDao;
import com.muzi.app.login.service.LoginAppService;
import com.muzi.app.login.util.MD5APPUtil;

@Service
@Transactional
public class LoginAppServiceImpl implements LoginAppService{

	@Autowired
	private LoginAppDao loginDao;
	
	@Override
	public Map<String, Object> login(String username, String password) {
		//查询
		Map<String, Object> map = new HashMap<>();
		User user = loginDao.findUserById(username);
		if(null == user){
			map.put("code", 0);
			map.put("msg", "用户名不存在");
		}else{
			//密码比较
			boolean flag = MD5APPUtil.verify(password, user.getPassword());
			if(flag){
				map.put("code", 1);
				map.put("msg", "登录成功");
				map.put("user", user);
			}else{
				//密码错误
				map.put("code", 0);
				map.put("msg", "密码错误");
			}
		}
		return map;
	}

}
