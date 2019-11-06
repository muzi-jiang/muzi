package com.muzi.app.juzi.service;

import java.util.List;
import java.util.Map;

public interface JuziService {
		
	/**
	 * 分页查询句子信息 
	 * @param pageNum
	 * @return
	 */
	List<Map> findPage(int pageNum);
}
