package com.muzi.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.muzi.system.entity.Permission;

@Mapper
public interface MenuManagerMapper {

	/**
     * 页面数据
     * @param params
     * @return
     */
    List<Permission> list(Map<String, Object> params);
    
    
    /**
     * 查询所有的父级菜单
     * @return
     */
    List<Permission> findParentMenu();
    
    
    /**
     * 查询一条数据
     * @param id
     * @return
     */
    Permission findOne(int id);
    
    
    
    /**
     * 判断菜单名是否存在
     * @param menuName
     * @return
     */
    int findMenuisExisByName(String menuName);
    
    
    /**
     * 保存菜单数据
     * @param permission
     * @return
     */
    int save(Permission permission);
    
    
    /**
     * 通过菜单名查询菜单信息
     * @param name
     * @return
     */
    Permission findMenuByName(String name);
    
    
    
    /**
     * 修改菜单
     * @param permission
     * @return
     */
    int update(Permission permission);
    
    
    /**
     * 通过id删除菜单并删除当下所有的子菜单
     * @param id
     * @return
     */
    int remove(int id);
    
    
    
    
    
    
}
