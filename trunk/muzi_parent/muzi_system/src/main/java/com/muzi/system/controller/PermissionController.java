package com.muzi.system.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muzi.communal.util.PermissionTreeUtils;
import com.muzi.communal.util.ReturnMessage;
import com.muzi.system.entity.MenuTree;
import com.muzi.system.entity.Permission;
import com.muzi.system.entity.User;
import com.muzi.system.service.PermissionService;
import org.apache.shiro.util.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.soap.Addressing;
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
@RequestMapping("/system/manager/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping("/list")
    @ResponseBody
    public List<Permission> getList(){
        List<Permission> list = permissionService.list();
        List<Permission> permissionTree = PermissionTreeUtils.getPermissionTree(list);
        return permissionTree;
    }


    @RequestMapping("/savePermiss")
    @ResponseBody
    public ReturnMessage savePermiss(@RequestBody Permission permission){

        //判断该菜单名称是否已经存在
        Permission findPermission = permissionService.getOne(new QueryWrapper<Permission>().eq("permission_name", permission.getPermissionName()).last("limit 0,1"));
        User user = (User)request.getSession().getAttribute("user");
        if(permission.getId() != null){
            if(findPermission != null && !findPermission.getId().equals(permission.getId())){
                //表示该菜单名称已经存在
                return  new ReturnMessage(ReturnMessage.ResultCode.ERROR,"该菜单名称已经存在");
            }
            //修改
            permission.setUpdateUserId(user.getId());
            permission.setUpdateUserName(user.getName());
            permission.setUpdateTime(new Date());
            permissionService.updateById(permission);
        }else {
            if(findPermission != null){
                //表示该菜单名称已经存在
                return  new ReturnMessage(ReturnMessage.ResultCode.ERROR,"该菜单名称已经存在");
            }
            //新增
            permission.setCreateUserId(user.getId());
            permission.setCreateUserName(user.getName());
            permission.setCreateTime(new Date());
            permissionService.save(permission);
        }
        return new ReturnMessage();
    }


    @RequestMapping("/delPermission")
    @ResponseBody
    public ReturnMessage delPermission(Integer id){
        //删除当前节点
        permissionService.removeById(id);
        //删除当前节点下的所有子节点
        permissionService.remove(new QueryWrapper<Permission>().eq("permission_parentid",id));
        return new ReturnMessage();
    }

    /**
     * 获取当前登录用户的菜单权限
     * @return
     */
    @RequestMapping("/getUserMenu")
    @ResponseBody
    public List<Map<String,Object>> getUserMenu(){
        /*User user = (User)request.getSession().getAttribute("user");
        List<MenuTree> userMenu = permissionService.getUserMenu(user.getUsername());
        if(userMenu != null && userMenu.size() >0){
            //选出父级菜单
            List<MenuTree> parentMenus = userMenu.stream().filter(i -> "0".equals(i.getParentFlag())).collect(Collectors.toList());
            if(parentMenus != null && parentMenus.size()>0){
                for(int i = 0;i<parentMenus.size(); i++){
                    MenuTree parentMenu = parentMenus.get(i);
                    parentMenu.setIndex((i+1)+"");
                    List<MenuTree> collect = userMenu.stream().filter(j -> parentMenu.getId().equals(j.getParentFlag()))
                            .collect(Collectors.toList());
                    if(collect != null && collect.size() > 0){
                        parentMenu.setSubs(collect);
                    }
                }
            }
            parentMenus = test001(parentMenus);
            return parentMenus;
        }
        return null;*/
        List<Map<String,Object>> list = new ArrayList<>();
        User user = (User)request.getSession().getAttribute("user");
        List<MenuTree> userMenu = permissionService.getUserMenu(user.getUsername());
        if(userMenu != null && userMenu.size() >0){
            //选出父级菜单
            List<MenuTree> parentMenus = userMenu.stream().filter(i -> "0".equals(i.getParentFlag())).collect(Collectors.toList());
            if(parentMenus != null && parentMenus.size() >0){
                for(int i = 0;i<parentMenus.size(); i++) {
                    MenuTree item = parentMenus.get(i);
                    Map<String, Object> map = new HashMap<>();
                    map.put("icon", item.getIcon());
                    map.put("title", item.getTitle());
                    List<MenuTree> collect = userMenu.stream().filter(j -> item.getId().equals(j.getParentFlag()))
                            .collect(Collectors.toList());
                    if(collect != null && collect.size() > 0){
                        List<Map<String,Object>> zList = new ArrayList<>();
                        collect.forEach( k -> {
                            Map<String, Object> zMap = new HashMap<>();
                            zMap.put("index",k.getIndex());
                            zMap.put("title",k.getTitle());
                            zList.add(zMap);
                        });
                        map.put("subs",zList);
                        map.put("index",(i+1)+"");
                    }else {
                        map.put("index",item.getIndex());
                    }
                    list.add(map);
                }
                list  = addOtherMenu(list);
                return list;
            }
        }
        return null;
    }

    /**
     * 添加其他固定的菜单
     * @param list
     * @return
     */
    private List<Map<String,Object>> addOtherMenu(List<Map<String,Object>> list){
        Map<String,Object> map = new HashMap<String,Object>(){{
            put("icon","el-icon-lx-home");
            put("index","dashboard");
            put("title","系统首页");
        }};
        list.add(0,map);
        return list;
    }

    /**
     * 添加其他固定的组件
     * @param list
     * @return
     */
    private List<Map<String,Object>> addOtherRouter(List<Map<String,Object>> list){
        Map<String,Object> map = new HashMap<String,Object>(){{
            put("path","/dashboard");
            put("component","dashboard");
            put("meta",new HashMap<String,Object>(){
                {put("title","系统首页");}
                {put("requireAuth",true);}
            });
        }};
        list.add(0,map);
        return list;
    }

    /**
     * 动态加载所有路由
     * @return
     */
    @RequestMapping("/getRouterAll")
    @ResponseBody
    public List<Map<String,Object>> getRouterAll(){
        List<Map<String,Object>> routerList = new ArrayList<>();
        //加载所有可用的菜单
        List<Permission> permissions = permissionService.list(new QueryWrapper<Permission>().eq("status", 1).isNotNull("component"));
        if(permissions != null && permissions.size() >0) {
            Map<String,Object> map = new HashMap<>();
            map.put("path","/");
            map.put("component","menu");
            map.put("meta",new HashMap<String,Object>(){
                                                        {put("title","自述文件");}
                                                        {put("requireAuth",true);}
                                                       });
            //子集和
            List<Map<String,Object>> list = new ArrayList<>();
            for(Permission permission : permissions){
                Map<String,Object> permissionMap = new HashMap<>();
                permissionMap.put("path",permission.getPermissionUrl());
                permissionMap.put("component",permission.getComponent());
                permissionMap.put("meta",new HashMap<String,Object>(){
                    {put("title",permission.getPermissionName());}
                    {put("requireAuth",true);}
                });
                list.add(permissionMap);
            }
            list = addOtherRouter(list);
            map.put("children",list);
            routerList.add(map);
            return routerList;
        }
        return null;
    }
}
