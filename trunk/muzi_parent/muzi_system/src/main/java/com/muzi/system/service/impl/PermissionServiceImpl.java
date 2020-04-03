package com.muzi.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzi.system.entity.MenuTree;
import com.muzi.system.entity.Permission;
import com.muzi.system.mapper.PermissionMapper;
import com.muzi.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<MenuTree> getUserMenu(String username) {
        return baseMapper.getUserMenu(username);
    }
}
