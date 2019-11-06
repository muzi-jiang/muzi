package com.muzi.app.juzi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muzi.app.juzi.dao.JuziDao;
import com.muzi.app.juzi.service.JuziService;

@Service
@Transactional
public class JuziServiceImpl implements JuziService{

	@Autowired
	private JuziDao juziDao;
	
	@Override
	public List<Map> findPage(int pageNum) {
		return juziDao.findPage(pageNum);
	}
	
}
