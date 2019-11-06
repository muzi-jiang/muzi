package com.muzi.manager.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muzi.manager.mapper.RoleManagerMapper;
import com.muzi.manager.mapper.UserManagerMapper;
import com.muzi.manager.service.UserManagerService;
import com.muzi.manager.util.R;
import com.muzi.system.dict.DictEnum;
import com.muzi.system.entity.Role;
import com.muzi.system.entity.User;
import com.muzi.system.mapper.DictMapper;
import com.muzi.system.util.BuildTree;
import com.muzi.system.util.PageUtils;
import com.muzi.system.util.Query;
import com.muzi.system.util.Tree;

@Service
@Transactional
public class UserManagerServiceImpl implements UserManagerService{
	
	@Autowired
	private UserManagerMapper userManagerMapper;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private DictMapper dictMapper;
	
	@Autowired
	private RoleManagerMapper roleManagerMapper;
	
	@Override
	public PageUtils list(Map<String, Object> params) {
		Query query = new Query(params);
		List<User> smsList = userManagerMapper.list(query);
		int total = userManagerMapper.count(query);
		PageUtils pageUtil = new PageUtils(smsList, total);
		return pageUtil;
	}

	@Override
	public R saveUser(User user) {
		if(null != user){
			//判断用户名是否存在
			User findUser = userManagerMapper.findUserByUserName(user.getUserName());
			if(null != findUser){
				return R.error(DictEnum.SystemMsg.ACCOUNT_EXIST.getValue());
			}
			User u = (User)request.getSession().getAttribute("user");
			
			
			//设置用户的初始密码
			String code = dictMapper.findCodeByTypeCode(DictEnum.DictTypeCode.PASSWORD_INIT.getValue());
			String passWord = new Md5Hash(code,user.getUserName()).toHex();
			
			user.setPassWord(passWord);
	
			user.setCreateUserId(u.getId());
			user.setCreateUserName(u.getName());
			int num = userManagerMapper.save(user);
			if(num <=0){
				//return R.ok(DictEnum.SystemMsg.ADDSUCCESS.getValue());
				return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
			}
			//添加用户-角色表
			List<String> roleIds = user.getRoleIds();
			roleIds.remove("-1");
			userManagerMapper.saveUserRoleMiddle(user.getId(), roleIds);
			return R.ok(DictEnum.SystemMsg.ADDSUCCESS.getValue());
		}
		return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
	}

	@Override
	public Tree<Role> getTree() {
		List<Tree<Role>> trees = new ArrayList<Tree<Role>>();
		List<Role> roles = roleManagerMapper.findAllNormal();
		roles.forEach(role ->{
			 Tree<Role> tree = new Tree<Role>();
			 tree.setId(role.getId()+"");
			 tree.setParentId("0");
			 tree.setText(role.getRolename());
			 trees.add(tree);
		});
		Tree<Role> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public User findUserById(int id) {
		return userManagerMapper.findUserById(id);
	}

	@Override
	public Tree<Role> getEditTree(int id) {
		List<Role> roles = roleManagerMapper.findAllNormal();
		List<Role> roleDOs =  roles;
		
		List<Integer> roleIds = userManagerMapper.findRoleIdByUserId(id);
		List<Tree<Role>> trees = new ArrayList<Tree<Role>>();
		roleDOs.forEach(sysRoleDO ->{
			Tree<Role> tree = new Tree<Role>();
			tree.setId(sysRoleDO.getId()+"");
			tree.setText(sysRoleDO.getRolename());
			Map<String, Object> state = new HashMap<>(16);
			Integer roleId = sysRoleDO.getId();
			if (roleIds.contains(roleId)) {
				state.put("selected", true);
			}else{
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		});
		Tree<Role> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public R editUser(User user) {
		if(null != user){
			//查詢用戶是否存在
//			User findUser = userManagerMapper.findUserByUserName(user.getUserName());
//			if(null != findUser){
//				if(findUser.getId() != user.getId()){
//					return R.error(DictEnum.SystemMsg.ACCOUNT_EXIST.getValue());
//				}
//			}
			//修改用戶
			User u = (User)request.getSession().getAttribute("user");
			user.setUpdateUserId(u.getId());
			user.setUpdatedUserName(u.getName());
			int num = userManagerMapper.edit(user);
			if(num <=0){
				//return R.ok(DictEnum.SystemMsg.ADDSUCCESS.getValue());
				return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
			}
			
			//通過用戶id刪除角色中間表
			userManagerMapper.deleteUserRoleMiddle(user.getId());
			
			//添加用户-角色表
			List<String> roleIds = user.getRoleIds();
			roleIds.remove("-1");
			userManagerMapper.saveUserRoleMiddle(user.getId(), roleIds);
			return R.ok(DictEnum.SystemMsg.UPDATESUCCESS.getValue());
		}
		return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
	}

	@Override
	public R deleteUser(int id) {
		int num = userManagerMapper.deleteUser(id);
		if(num>0){
			return R.ok(DictEnum.SystemMsg.DELETESUCCESS.getValue());
		}
		return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
	}

	@Override
	public R resetPwd(String id) {
		User user = userManagerMapper.findUserById(Integer.parseInt(id));
		//重新加密
		String code = dictMapper.findCodeByTypeCode(DictEnum.DictTypeCode.PASSWORD_INIT.getValue());
		String passWord = new Md5Hash(code,user.getUserName()).toHex();
		int num = userManagerMapper.resetPwd(id,passWord);
		if(num>0){
			return R.ok(DictEnum.SystemMsg.PASSWORDRESET.getValue());
		}
		return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
	}

}
