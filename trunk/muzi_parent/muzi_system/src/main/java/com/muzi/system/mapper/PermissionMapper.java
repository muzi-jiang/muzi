package com.muzi.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muzi.system.entity.MenuTree;
import com.muzi.system.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */

public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询登录用户的菜单权限
     * @param username
     * @return
     */
    List<MenuTree> getUserMenu(String username);
}
