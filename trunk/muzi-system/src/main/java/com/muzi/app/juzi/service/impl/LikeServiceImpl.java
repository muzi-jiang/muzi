package com.muzi.app.juzi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muzi.app.juzi.beam.Book;
import com.muzi.app.juzi.service.LikeService;
import com.muzi.app.util.InterfaceUtils;

import net.sf.json.JSONObject;

@Service
@Transactional
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	private Book book;
	
	/**
	 * 聚合数据接口调用
	 */
	@Override
	public String list() {
		/**
		 * 获取参数
		 */
		String key = book.getKey();
		String url = book.getUrl();
		/**
		 * 接口调用
		 */
		String interface1 = InterfaceUtils.getInterface("key="+key, url);
		JSONObject fromObject = JSONObject.fromObject(interface1);
		Object object = fromObject.get("result");
		return object+"";
	}

}
