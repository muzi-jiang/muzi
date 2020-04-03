package com.muzi.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muzi.communal.util.PermissionTreeUtils;
import com.muzi.communal.util.ReturnMessage;
import com.muzi.system.entity.Permission;
import com.muzi.system.entity.Role;
import com.muzi.system.entity.RolePermission;
import com.muzi.system.entity.User;
import com.muzi.system.service.PermissionService;
import com.muzi.system.service.RolePermissionService;
import com.muzi.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
@Controller
@RequestMapping("/system/manager/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> getList(){
        Map<String,Object> dataMap = new HashMap<>();

        //查询树形菜单节点
        List<Permission> permissions = permissionService.list(new QueryWrapper<Permission>().eq("status", 1));
        List<Permission> permissionTree = PermissionTreeUtils.getPermissionTree(permissions);
        dataMap.put("permissions",permissionTree);

        //查询素有角色权限对应关系
        List<RolePermission> list = rolePermissionService.list();

        //查询所有的菜单权限
        List<Map<String, Object>> maps = roleService.listMaps();
        maps.forEach( map ->{
            List<Integer> rolePermission = list.stream().filter(item -> map.get("id").equals(item.getRoleId()))
                                .map(RolePermission::getPermissionId).collect(Collectors.toList());
            //去掉父级节点，只保留子级节点
            List<Integer> notIn = permissionTree.stream().filter( item -> item.getChildren() != null && item.getChildren().size() > 0)
                                                        .map(Permission::getId).collect(Collectors.toList());
            rolePermission.removeAll(notIn);
            map.put("rolePermission",rolePermission);
        });
        dataMap.put("roles",maps);
        return dataMap;
    }


    @RequestMapping("/saveRole")
    @ResponseBody
    public ReturnMessage saveRole(@RequestBody Map<String,Object> map){
        Object id = map.get("id");
        Object rolename = map.get("rolename");
        Object remarks = map.get("remarks");
        Object status = map.get("status");
        List<Integer> rolePermissionArray = (List<Integer>)map.get("rolePermission");
        User user = (User)request.getSession().getAttribute("user");

        //查询角色名称是否重复
        Role selectRole = roleService.getOne(new QueryWrapper<Role>().eq("rolename", rolename).last("limit 0,1"));
        Role role = null;
        if(id != null && !"".equals(id)){
            //编辑
            if(selectRole != null && !selectRole.getId().equals(id)){
                //表示该角色名称已经存在
                return  new ReturnMessage(ReturnMessage.ResultCode.ERROR,"该角色名称已经存在");
            }
            role = new Role(Integer.parseInt(id.toString()),rolename+"",remarks+"",status+"");
            role.setUpdateUserId(user.getId());
            role.setUpdateUserName(user.getName());
            role.setUpdateTime(new Date());
            roleService.updateById(role);
        }else{
            //新增
            if(selectRole != null){
                //表示该角色名称已经存在
                return  new ReturnMessage(ReturnMessage.ResultCode.ERROR,"该角色名称已经存在");
            }
            role = new Role(rolename+"",remarks+"",status+"");
            role.setCreateUserId(user.getId());
            role.setCreateUserName(user.getName());
            role.setCreateTime(new Date());
            roleService.save(role);
        }

        //角色-权限 中间表
        //先删除中间表数据
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id",role.getId()));
        //添加中间表数据
        List<RolePermission> list = new ArrayList<>();
        for(Integer item : rolePermissionArray){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(item);
            list.add(rolePermission);
        }
        rolePermissionService.saveBatch(list);
        return new ReturnMessage();
    }


    @RequestMapping("/delRole")
    @ResponseBody
    public ReturnMessage delPermission(Integer id){
        //删除当前角色信息
        roleService.removeById(id);
        //删除中间表信息
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id",id));
        return new ReturnMessage();
    }
}
