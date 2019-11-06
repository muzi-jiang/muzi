package com.muzi.manager.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.manager.service.RoleManagerService;
import com.muzi.manager.service.UserManagerService;
import com.muzi.manager.util.R;
import com.muzi.system.entity.Role;
import com.muzi.system.entity.User;
import com.muzi.system.util.PageUtils;
import com.muzi.system.util.Tree;

/**
 * 用户管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/backstage/manager/user")
public class UserManagerController {

	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserManagerService userManagerService;
	
	@Autowired
	private RoleManagerService roleManagerService;
	
	/**
	 * 主页控制
	 * @return
	 */
	@RequestMapping("/toUser")
    public ModelAndView toRole(){
        ModelAndView mav = new ModelAndView("manager/user/manager");
        return mav;
    }
	
	/**
	 * 页面用户数据查询
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params){
       return userManagerService.list(params);
    }
	
	
	/**
	 * 添加用户
	 * @return
	 */
	@RequestMapping("/toAddUser")
    public ModelAndView toAddUser(){
		logger.info(" 添加用户........");
        ModelAndView mav = new ModelAndView("manager/user/add");
        return mav;
    }
	
	
	/**
	 * 获取树形节点角色
	 * @return
	 */
	@RequestMapping("/tree")
    @ResponseBody
    public Tree<Role> tree(){
        Tree<Role>  tree = userManagerService.getTree();
        return tree;
    }
	
	
	/**
	 * 保存用户
	 * @return
	 */
	@RequestMapping("/insertUser")
	@ResponseBody
    public R insertUser(User user){
		logger.info(" 保存用户........");
		System.out.println(user);
		return userManagerService.saveUser(user);
	}
	
	
	
	/**
	 * 编辑用户
	 * @return
	 */
	@RequestMapping("/toEdit")
    public ModelAndView toEdit(int id){
		logger.info(" 编辑用户........");
        ModelAndView mav = new ModelAndView("manager/user/edit");
      //查询管理员
        User user = userManagerService.findUserById(id);
        mav.addObject("user",user);
        return mav;
    }
	
	
	 /**
     * 根据用户id获取角色的树形权限
     * @param roleId
     * @return
     */
    @RequestMapping("/editTree")
    @ResponseBody
    public Tree<Role> editTree(Integer userId) {
    	logger.info("获取树形节点........");
    	Tree<Role> tree = userManagerService.getEditTree(userId);
    	return tree;
    }	
	
	
	
    
    
    /**
	 * 修改用戶保存
	 * @return
	 */
	@RequestMapping("/editUser")
	@ResponseBody
    public R editUser(User user){
		logger.info("修改用戶保存........");
		return userManagerService.editUser(user);
	}
    
    
    
    
	/**
	 * 物理删除用户
	 * @return
	 */
	@RequestMapping("/deleteUser")
	@ResponseBody
    public R deleteUser(int id){
		logger.info("物理删除用户........");
		return userManagerService.deleteUser(id);
	}
    
    
    
	/**
	 * 密码重置
	 * @return
	 */
	@RequestMapping("/resetPwd")
	@ResponseBody
    public R resetPwd(String id){
		logger.info(" 密码重置........");
		return userManagerService.resetPwd(id);
	}
    
	
}
