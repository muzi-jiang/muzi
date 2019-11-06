package com.muzi.manager.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.manager.service.RoleManagerService;
import com.muzi.manager.util.R;
import com.muzi.system.entity.Permission;
import com.muzi.system.entity.Role;
import com.muzi.system.util.PageUtils;
import com.muzi.system.util.Tree;


/**
 * 角色控制
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/backstage/manager/role")
public class RoleManagerController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RoleManagerService roleManagerService;
	
	
	/**
	 * 跳转角色控制主页
	 * @return
	 */
	@RequestMapping("/toRole")
    public ModelAndView toRole(){
        ModelAndView mav = new ModelAndView("manager/role/manager");
        return mav;
    }
	
	
	/**
	 * 页面角色数据查询
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params){
       return roleManagerService.list(params);
    }
	
	
	/**
	 * 转到添加界面
	 * @return
	 */
	@RequestMapping("/toaddRole")
    public ModelAndView toadd(){
        ModelAndView mav = new ModelAndView("manager/role/add");
        return mav;
    }
	
	
	/**
	 * 获取树形节点菜单
	 * @return
	 */
	@RequestMapping("/tree")
    @ResponseBody
    public Tree<Permission> tree(){
        Tree<Permission>  tree = roleManagerService.getTree();
        return tree;
    }
	
	
	/**
	 * 保存新增角色
	 * @param rolename
	 * @param remarks
	 * @param menuIds
	 * @return
	 */
	@RequestMapping("/save")
    @ResponseBody
    public R save(String rolename,String remarks,String[] menuIds){
		return roleManagerService.save(rolename, remarks, menuIds);
	}
	
	
	
	/**
     * 转到角色修改界面
     * @return
     */
    @RequestMapping("/toEditRole")
    public ModelAndView toEditRole(int id){
    	ModelAndView mav = roleManagerService.toEditRole(id);
        return mav;
    }
    
    
    
    /**
     * 根据角色id获取角色的树形权限
     * @param roleId
     * @return
     */
    @RequestMapping("/editTree")
    @ResponseBody
    public Tree<Permission> editTree(Integer roleId) {
    	logger.info("获取树形节点........");
    	Tree<Permission> tree = roleManagerService.getEditTree(roleId);
    	return tree;
    }
    
    
    /**
     * 修改保存角色信息
     * @param role
     * @return
     */
    @PostMapping("/update")
    @ResponseBody()
    public R update(Role role) {
    	return roleManagerService.update(role);
    }
    
    
    /**
     * 物理删除角色
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody()
    public R remove(int id){
    	return roleManagerService.remove(id);
    }
    
    
    
    
    
	
}
