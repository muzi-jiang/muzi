package com.muzi.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.muzi.system.entity.Role;

@Mapper
public interface RoleManagerMapper {
	
	/**
	 * 分页查询数据
	 * @param params
	 * @return
	 */
	List<Role> list(Map<String, Object> map);

	
	
	 /**
     * 总条数
     * @param map
     * @return
     */
    int count(Map<String, Object> map);
	
    
    
    /**
     * 通过角色名字查询数据
     * @param rolename
     * @return
     */
    Role findRoleByRoleName(String rolename);
    
    
    
    /**
     * 添加角色
     * @param role
     * @return
     */
    int save(Role role);
    
    
    
    /**
     * 新增角色-权限-中间表  数据
     * @param id
     * @param list
     * @return
     */
    int saveRoleAndPermission(@Param("id") int id,@Param("list") List<String> list);
    
    
    
    /**
     * 通过id查询角色信息
     * @param id
     * @return
     */
    Role findRoleById(int id);
    
    
    
    /**
     * 通过角色id查询所有的菜单id
     * @param roleId
     * @return
     */
    List<Integer> listMenuIdByRoleId(Integer roleId);
    
    
    /**
     * 修改角色信息表
     * @param role
     * @return
     */
    int update(Role role);
    
    
    
    /**
     * 删除角色-权限中间表
     * @param id
     */
    void removeByRoleId(int id);
    
    
    
    
    /**
     * 通过角色id删除角色
     * @param id
     */
    void removeRoleById(int id); 
    
    
    /**
     * 获取所有正常的角色
     * @return
     */
    List<Role> findAllNormal();
    
}
