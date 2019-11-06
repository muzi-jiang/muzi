package com.muzi.app.juzi.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Component;

import com.muzi.app.dict.DictApp;
import com.muzi.app.juzi.dao.JuziDao;

@Component
public class JuziDaoImpl  implements JuziDao{

	

	@Autowired
	private MongoTemplate mongoTemplate;
	 
	 
	@Override
	public List<Map> findPage(int pageNum) {
		@SuppressWarnings("deprecation")
		Aggregation aggregation = Aggregation.newAggregation(
				/**
				 * 从表名称  主表关联字段  从表关联字段  查询结果集
				 */
				Aggregation.lookup(DictApp.ConnectionName.MUZI_USER.getValue(), "user_id", "_id", "user"),
				Aggregation.skip((pageNum-1)*DictApp.PAGE_SIZE),
				Aggregation.limit(DictApp.PAGE_SIZE)
		);
		List<Map> results = mongoTemplate.aggregate(aggregation,DictApp.ConnectionName.MUZI_JUZI.getValue(), Map.class).getMappedResults();
		return results;
	}
}
