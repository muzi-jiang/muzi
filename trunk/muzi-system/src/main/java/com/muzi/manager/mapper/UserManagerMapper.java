package com.muzi.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.muzi.system.entity.User;

@Mapper
public interface UserManagerMapper {
	
	
	/**
	 * 分页查询数据
	 * @param params
	 * @return
	 */
	List<User> list(Map<String, Object> map);
	
	
	
	 /**
     * 总条数
     * @param map
     * @return
     */
    int count(Map<String, Object> map);



    /**
     * 通过用户名查询数据
     * @param userName
     * @return
     */
	User findUserByUserName(String userName);



	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	int save(User user);
	
	
	
	
	
	 /**
     * 新增用户-角色-中间表  数据
     * @param id
     * @param list
     * @return
     */
    int saveUserRoleMiddle(@Param("id") int id,@Param("list") List<String> list);
	
	
	
    /**
	 * 查询用户
	 * @param id
	 * @return
	 */
	User findUserById(int id);



	/**
	 * 通过用户id查询所有角色id
	 * @param id
	 * @return
	 */
	List<Integer> findRoleIdByUserId(int id);


	/**
	 * 修改用戶
	 * @param user
	 */
	int edit(User user);


	/**
	 * 通過用戶id刪除角色中間表
	 * @param id
	 */
	void deleteUserRoleMiddle(Integer id);


	/**
	 * 物理删除用户
	 * @param id
	 * @return
	 */
	int deleteUser(int id);


	/**
	 * 用户密码重置
	 * @param id
	 * @param passWord
	 */
	int resetPwd(@Param("id")String id,@Param("passWord") String passWord);
	
    
    

}
