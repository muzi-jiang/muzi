package com.muzi.system.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.muzi.system.entity.Permission;
import com.muzi.system.entity.User;

@Mapper
public interface LoginMapper {

	
	User findUserByUserName(String username);
	
	
	List<Permission> findParentPermissionByUserId(int id);
	
	
	List<Permission> findPermissionByParentId(@Param("id")String id);
}
