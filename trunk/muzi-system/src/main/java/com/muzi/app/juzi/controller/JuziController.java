package com.muzi.app.juzi.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muzi.app.juzi.service.JuziService;


/**
 * 句子data获取接口
 * @author Administrator
 *
 */
@RequestMapping("/app/juzi")
@RestController
public class JuziController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JuziService juziService;
	/**
	 * 分页获取数据
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/api/list")
	public List<Map> list(int pageNum){
		logger.info("当前分页数据....."+pageNum);
		List<Map> list = juziService.findPage(pageNum);
		return list;
	}
	
	
	
}
