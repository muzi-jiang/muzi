package com.muzi.system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *   错误页面跳转
 * @author Administrator
 *
 */
@RequestMapping("/system/error")
@Controller
public class ErrorController {

	@RequestMapping("/status404")
	public ModelAndView status404(){
		return new ModelAndView("404");
	}
	
	@RequestMapping("/status500")
	public ModelAndView status500(){
		return new ModelAndView("500");
	}
	
	
	
	
	
}
