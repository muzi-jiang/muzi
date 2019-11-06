package com.muzi.app.login.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.muzi.app.dict.DictApp;
import com.muzi.app.juzi.entity.User;
import com.muzi.app.login.dao.LoginAppDao;

@Component
public class LoginAppDaoImpl implements LoginAppDao{


	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Override
	public User findUserById(String username) {
		//设置条件
		Query query = Query.query(Criteria.where("username").is(username)).skip(0).limit(1);
		User user = this.mongoTemplate.findOne(query, User.class,DictApp.ConnectionName.MUZI_USER.getValue());
		return user;
	}

}
