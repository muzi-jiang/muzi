package com.muzi.app.juzi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muzi.app.juzi.service.LikeService;


/**
 * 菜单栏3数据调用
 * @author Administrator
 *
 */
@RequestMapping("/app/like")
@RestController
public class LikeController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LikeService likeService;
	
	
	@RequestMapping("/api/list")
	public String list(){
		logger.info("获取图书数据......");
		return likeService.list();
	}
	
}
