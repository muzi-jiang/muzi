package com.muzi.manager.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.muzi.manager.util.R;
import com.muzi.system.entity.Permission;

public interface MenuManagerService {

	
	/**
     * 页面数据
     * @param params
     * @return
     */
    List<Permission> list(Map<String, Object> params);
	
	
    /**
     * 添加
     * @param id
     * @return
     */
    ModelAndView toAdd(int id);
    
    
    
    
    /**
     * 保存菜单
     * @param permission
     * @return
     */
    R save(Permission permission);
    
    
    
    /**
     * 转到修改界面
     * @param id
     * @return
     */
    ModelAndView toEdit(int id);
    
    
    
    /**
     * 保存修改菜单
     * @param permission
     * @return
     */
    R update(Permission permission);
    
    
    
    
    /**
     * 物理删除菜单
     * @param id
     * @return
     */
    R remove(int id);
    
    
    
    
    
    
}
