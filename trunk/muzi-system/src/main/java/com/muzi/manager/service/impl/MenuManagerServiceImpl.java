package com.muzi.manager.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.manager.mapper.MenuManagerMapper;
import com.muzi.manager.service.MenuManagerService;
import com.muzi.manager.util.R;
import com.muzi.system.dict.DictEnum;
import com.muzi.system.entity.Permission;
import com.muzi.system.entity.User;

@Service
@Transactional
public class MenuManagerServiceImpl implements MenuManagerService{

	
	@Autowired
    private MenuManagerMapper menuManagerMapper; 
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public List<Permission> list(Map<String, Object> params) {
		return menuManagerMapper.list(params);
	}
		
	@Override
	public ModelAndView toAdd(int id) {
		 ModelAndView mav = new ModelAndView("manager/menu/add");
		 mav.addObject("type",id);
		 List<Permission> parentMenu = menuManagerMapper.findParentMenu();
		 mav.addObject("parentMenu",parentMenu);
		 if(0!=id){
			 Permission permission = menuManagerMapper.findOne(id);
			 mav.addObject("permission",permission);
		 }
		return mav;
	}

	@Override
	public R save(Permission permission) {
		if(null != permission){
			if(null==permission.getPermissionParentid()){
				permission.setPermissionParentid(0);
			}
			
			int countNum = menuManagerMapper.findMenuisExisByName(permission.getPermissionName());
			if(countNum>0){
				return R.error(DictEnum.SystemMsg.MENUNAMEIS_EXIST.getValue());
			}
			User user = (User)request.getSession().getAttribute("user");
			permission.setCreateUserId(user.getId());
			permission.setCreateUserName(user.getName());
			int save = menuManagerMapper.save(permission);
			if(save>0){
				 return R.ok(DictEnum.SystemMsg.ADDSUCCESS.getValue());
			}else{
				 return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
			}
		}else{
			 return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
		}
		
	
	}

	@Override
	public ModelAndView toEdit(int id) {
		ModelAndView mav = new ModelAndView("manager/menu/edit");
		//查询当前数据
		Permission permission = menuManagerMapper.findOne(id);
		mav.addObject("permission",permission);
		//查询所有父级菜单
		List<Permission> parentMenu = menuManagerMapper.findParentMenu();
		mav.addObject("parentMenu",parentMenu);
		if(0 != permission.getPermissionParentid()){   //去查询当前上一级菜单名称
			Permission thisParentMenu = menuManagerMapper.findOne(permission.getPermissionParentid());
			mav.addObject("thisParentMenu",thisParentMenu);
		}
		return mav;
	}

	@Override
	public R update(Permission permission) {
		if(null != permission){
			if(null==permission.getPermissionParentid()){
				permission.setPermissionParentid(0);
			}
			
			Permission findMenuByName = menuManagerMapper.findMenuByName(permission.getPermissionName());
			if(null != findMenuByName){
				if(findMenuByName.getId() != permission.getId()){
					return R.error(DictEnum.SystemMsg.MENUNAMEIS_EXIST.getValue());
				}
			}
			User user = (User)request.getSession().getAttribute("user");
			
			permission.setUpdateUserId(user.getId());
			permission.setUpdatedUserName(user.getName());
			int update = menuManagerMapper.update(permission);
			if(update>0){
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
		int remove = menuManagerMapper.remove(id);
		if(remove > 0){
			return R.ok(DictEnum.SystemMsg.DELETESUCCESS.getValue());
		}else{
			return R.error(DictEnum.SystemMsg.NETWORKCONNECTIONEXCEPTION.getValue());
		}
	}

}
