package com.muzi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muzi.system.entity.MenuTree;
import com.muzi.system.entity.Permission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 查询登录用户的菜单权限
     * @param username
     * @return
     */
    List<MenuTree> getUserMenu(String username);
}
