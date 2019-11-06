package com.muzi.manager.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.manager.mapper.MenuManagerMapper;
import com.muzi.manager.mapper.RoleManagerMapper;
import com.muzi.manager.service.RoleManagerService;
import com.muzi.manager.util.R;
import com.muzi.system.dict.DictEnum;
import com.muzi.system.entity.Permission;
import com.muzi.system.entity.Role;
import com.muzi.system.entity.User;
import com.muzi.system.util.BuildTree;
import com.muzi.system.util.PageUtils;
import com.muzi.system.util.Query;
import com.muzi.system.util.Tree;

@Service
@Transactional
public class RoleManagerServiceImpl implements RoleManagerService{

	@Autowired
	private RoleManagerMapper roleManagerMapper;

	@Autowired
	private MenuManagerMapper menuManagerMapper;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public PageUtils list(Map<String, Object> params) {
		Query query = new Query(params);
		List<Role> smsList = roleManagerMapper.list(query);
		int total = roleManagerMapper.count(query);
		PageUtils pageUtil = new PageUtils(smsList, total);
		return pageUtil;
	}

	@Override
	public Tree<Permission> getTree() {
		List<Tree<Permission>> trees = new ArrayList<Tree<Permission>>();
		List<Permission> permissions = menuManagerMapper.list(new HashMap<>(16));
		permissions.forEach(permission ->{
			 Tree<Permission> tree = new Tree<Permission>();
			 tree.setId(permission.getId()+"");
			 tree.setParentId(permission.getPermissionParentid()+"");
			 tree.setText(permission.getPermissionName());
			 trees.add(tree);
		});
		Tree<Permission> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public R save(String rolename, String remarks, String[] menuIds) {
		//查询是否具有相同名字的角色名称
		Role role = roleManagerMapper.findRoleByRoleName(rolename);
		if(null !=role){
			 return R.error(DictEnum.SystemMsg.ROLENAMEIS_EXIST.getValue());
		}
		List<String> strings = Arrays.asList(menuIds);
		List<String> arrList = new ArrayList<String>(strings);
		arrList.remove("-1");
		//1.保存角色信息
		Role r = new Role();
		r.setRolename(rolename);
		r.setRemarks(remarks);
		User user = (User)request.getSession().getAttribute("user");
		r.setCreateUserId(user.getId());
		r.setCreateUserName(user.getName());
		int save = roleManagerMapper.save(r);
		if(save <=0){
			return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
		}
		
		//新增角色-权限-中间表  数据
		int saveRoleAndPermission = roleManagerMapper.saveRoleAndPermission(r.getId(), arrList);
		if(saveRoleAndPermission<=0){
			 return R.error(DictEnum.SystemMsg.ROLENAMEIS_EXIST.getValue());
		}
		return R.ok(DictEnum.SystemMsg.ADDSUCCESS.getValue());
	}

	@Override
	public ModelAndView toEditRole(int id) {
		ModelAndView mav = new ModelAndView("manager/role/edit");
		//通过id查询角色名称
		Role role = roleManagerMapper.findRoleById(id);
		System.out.println(role);
		mav.addObject("role",role);
		return mav;
	}

	@Override
	public Tree<Permission> getEditTree(int id) {
		List<Permission> menus = menuManagerMapper.list(new HashMap<>(16));
		List<Permission> menuDOs =  menus;
		
		List<Integer> menuIds = roleManagerMapper.listMenuIdByRoleId(id);
		List<Integer> temp = menuIds;
		menus.forEach( menu ->{
			if (temp.contains(menu.getPermissionParentid())) {
				menuIds.remove(menu.getPermissionParentid());
			}
		});
		List<Tree<Permission>> trees = new ArrayList<Tree<Permission>>();
		menuDOs.forEach( sysMenuDO ->{
			Tree<Permission> tree = new Tree<Permission>();
			tree.setId(sysMenuDO.getId()+"");
			tree.setParentId(sysMenuDO.getPermissionParentid()+"");
			tree.setText(sysMenuDO.getPermissionName());
			Map<String, Object> state = new HashMap<>(16);
			Integer menuId = sysMenuDO.getId();
			if (menuIds.contains(menuId)) {
				state.put("selected", true);
			} else {
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		});
		Tree<Permission> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public R update(Role role) {
		if(null != role){
			Role isRole = roleManagerMapper.findRoleByRoleName(role.getRolename());
			if(null != isRole){
				if(isRole.getId() != role.getId()){
					 return R.error(DictEnum.SystemMsg.ROLENAMEIS_EXIST.getValue());
				}
			}
			User user = (User)request.getSession().getAttribute("user");
			role.setUpdateUserId(user.getId());
			role.setUpdatedUserName(user.getName());
			//执行修改
			int r = roleManagerMapper.update(role);
			List<String> menuIds = role.getMenuIds();
			//通过角色id删除该角色拥有的所有菜单权限  角色-菜单-中间表
			roleManagerMapper.removeByRoleId(role.getId());
			//添加角色-菜单中间表   
			//删除顶级菜单id  "-1"
			menuIds.remove("-1");
			int saveRoleAndPermission = roleManagerMapper.saveRoleAndPermission(role.getId(),menuIds);
			if(saveRoleAndPermission>0){
				return R.ok(DictEnum.SystemMsg.UPDATESUCCESS.getValue());
			}else{
				return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
			}
		}else{
			return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
		}
	}

	@Override
	public R remove(int id) {
		/**删除角色信息 */
		roleManagerMapper.removeRoleById(id);
		/**删除角色菜单中间表 */
		roleManagerMapper.removeByRoleId(id);
		return R.ok(DictEnum.SystemMsg.DELETESUCCESS.getValue());
	}

	@Override
	public List<Role> findAllNormal() {
		return roleManagerMapper.findAllNormal();
	}
	
}
