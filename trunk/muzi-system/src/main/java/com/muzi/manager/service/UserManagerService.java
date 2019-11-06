package com.muzi.manager.service;

import java.util.Map;

import com.muzi.manager.util.R;
import com.muzi.system.entity.Role;
import com.muzi.system.entity.User;
import com.muzi.system.util.PageUtils;
import com.muzi.system.util.Tree;

public interface UserManagerService {
	
	/**
	 * 页面数据查询
	 * @param params
	 * @return
	 */
	PageUtils list(Map<String, Object> params);

	
	
	/**
	 *  获取树形节点角色
	 * @return
	 */
	Tree<Role> getTree();
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	R saveUser(User user);
	
	
	/**
	 * 查询用户
	 * @param id
	 * @return
	 */
	User findUserById(int id);


	/**
	 * 根据用户id获取角色的树形权限
	 * @param roleId
	 * @return
	 */
	Tree<Role> getEditTree(int id);


	/**
	 * 保存修改用用戶
	 * @param user
	 * @return
	 */
	R editUser(User user);


	/**
	 * 物理删除用户
	 * @param user
	 * @return
	 */
	R deleteUser(int id);


	/**
	 *  密码重置
	 * @param id
	 * @return
	 */
	R resetPwd(String id);
	
	
	
	
	
	
}
