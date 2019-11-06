package com.muzi.manager.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.muzi.manager.util.R;
import com.muzi.system.entity.Permission;
import com.muzi.system.entity.Role;
import com.muzi.system.util.PageUtils;
import com.muzi.system.util.Tree;

public interface RoleManagerService {
	
	/**
	 * 页面数据查询
	 * @param params
	 * @return
	 */
	PageUtils list(Map<String, Object> params);
	
	
	/**
     * 查询树形资源
     * @return
     */
    Tree<Permission> getTree();
    
    
    
    
    /**
     * 保存新增角色
     * @param rolename
     * @param remarks
     * @param menuIds
     * @return
     */
    R save(String rolename,String remarks,String[] menuIds);
    
    
    /**
     * 修改角色界面
     * @param id
     * @return
     */
    ModelAndView toEditRole(int id);
    
    
    
    
    
    /**
     * 根据角色id获取角色的树形权限
     * @param id
     * @return
     */
    Tree<Permission> getEditTree(int id);
    
    
    
    /**
     * 修改保存角色
     * @param role
     * @return
     */
    R update(Role role);
    
    
    
    
    /**
     * 删除角色
     * @param id
     * @return
     */
    R remove(int id);
    
    
    /**
     * 获取所有正常的角色
     * @return
     */
    List<Role> findAllNormal();
    
    
    
    
    
}
