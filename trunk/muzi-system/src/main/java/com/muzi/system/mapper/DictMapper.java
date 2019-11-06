package com.muzi.system.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper {

	
	String findCodeByTypeCode(String typeCode);
	
	
}
