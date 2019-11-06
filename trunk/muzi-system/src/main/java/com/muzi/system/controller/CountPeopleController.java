package com.muzi.system.controller;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 监听网站登录人数
 * @author Administrator
 *
 */
@RequestMapping("/system/people")
@Controller
public class CountPeopleController {

	@RequestMapping("/count")
	@ResponseBody
	public String count(HttpServletRequest request , HttpServletResponse response){
		Cookie cookie;
		try {
			// 把 sessionId 记录在浏览器中
			cookie = new Cookie("JSESSIONID", URLEncoder.encode(request.getSession().getId(),"utf-8"));
			cookie.setPath("/");
			 //设置 cookie 有效期为 2 天，设置长一点
			cookie.setMaxAge( 48*60 * 60);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer count = (Integer)request.getSession().getServletContext().getAttribute("count");
		return "当前在线人数："+count;
	}
}
