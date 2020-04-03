package com.muzi.communal.util;

import com.muzi.system.entity.Permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树形节点封装
 */
public class PermissionTreeUtils {

    public static List<Permission> getPermissionTree(List<Permission> list){
        Map<String,Object> map = new HashMap<>();
        //查询出所有父级节点
        List<Permission> permissionList = list.stream().filter(i -> i.getPermissionParentid() == 0).collect(Collectors.toList());
        if(permissionList != null && permissionList.size()>0){
            //查找二级菜单
            permissionList.forEach( item ->{
                List<Permission> twoList = list.stream().filter(i -> i.getPermissionParentid().equals(item.getId())).collect(Collectors.toList());
                item.setChildren(twoList);
            });
            return permissionList;
        }
        return null;
    }
}
