package com.muzi.app.juzi.dao;

import java.util.List;
import java.util.Map;

/**
 * juzi 获取muzi_juzi 集合中的dao层
 * @author Administrator
 *
 */

public interface JuziDao {

	
	/**
	 * 分页查询句子信息 
	 * @param pageNum
	 * @return
	 */
	List<Map> findPage(int pageNum);
}
