package com.muzi.system.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.muzi.communal.baseentity.Constant;
import com.muzi.communal.util.ReturnMessage;
import com.muzi.system.entity.*;
import com.muzi.system.service.DictService;
import com.muzi.system.service.RoleService;
import com.muzi.system.service.UserRoleService;
import com.muzi.system.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.muzi.communal.baseentity.Constant.STSTEM_WINDOWS;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
@Controller
@RequestMapping("/system/manager/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DictService dictService;


    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> getList(User user){
        Map<String,Object> map = new HashMap<>();
        //查询所有可用的角色
        List<Role> roles = roleService.list(new QueryWrapper<Role>().eq("status", "1"));
        map.put("roles",roles);

        //查询初始密码
        Dict dict = dictService.getOne(new QueryWrapper<Dict>()
                    .eq("code_type", "password_init").eq("status","1"));
        map.put("passwordInit",dict.getCode());

        //分页查询用户数据
        IPage<Map<String,Object>> iPage = userService.pageMaps(user, new QueryWrapper<User>());
        List<Map<String, Object>> records = iPage.getRecords();
        if(records != null && records.size() > 0){
            //查询当前查询出的用户所拥有的角色
            List<UserRole> roleList = userRoleService.list(new QueryWrapper<UserRole>()
                    .in("user_id", records.stream().map(i -> i.get("id")).collect(Collectors.toList())));
            if(roleList != null && roleList.size() > 0){
                records.forEach( item -> {
                    //分别为每个用户赋予角色
                    List<Integer> hasRole = roleList.stream().filter(i -> i.getUserId().equals(item.get("id"))).map(UserRole::getRoleId)
                            .collect(Collectors.toList());
                    item.put("hasRole",hasRole);
                });
            }
            map.put("iPage",iPage);
            return map;
        }
        return null;
    }


    @RequestMapping(value = "/up",method = RequestMethod.POST)
    @ResponseBody
    public String up(@RequestParam("picFile") MultipartFile picture) {
        System.out.println(picture.getContentType());
        Date d = new Date();
        //获取文件在服务器的储存位置
        //判断当前系统是win 还是 Linux
        String path;
        SimpleDateFormat path_sdf = new SimpleDateFormat("yyyyMMdd");
        String newPath_sdf = path_sdf.format(d);
        String systemName = System.getProperty("os.name");  //Windows 7 Windows 8  Windows 10
        if(systemName.toLowerCase().startsWith(Constant.STSTEM_WINDOWS)){
            //win 系统
            path = Constant.WINDOWS_UPLOAD_PICTURE_PATH + "/" + newPath_sdf;
        }else{
            //Linux系统
            path = Constant.LINUX_UPLOAD_PICTURE_PATH + "/" + newPath_sdf;
        }
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdirs();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        System.out.println("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
            Dict dict = dictService.getOne(new QueryWrapper<Dict>().eq("code_type", "headportrait_head").eq("status","1"));
            return dict.getCode() + "/" + newPath_sdf + "/" + fileName;
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping("/saveUser")
    @ResponseBody
    public ReturnMessage saveUser(@RequestBody Map<String,Object> map){
        Object id = map.get("id");
        Object username = map.get("username");
        Object password = map.get("password");
        Object name = map.get("name");
        Object tel = map.get("tel");
        Object sex = map.get("sex");
        Object headportrait = map.get("headportrait");
        Object status = map.get("status");
        List<Integer> hasRole = (List<Integer>)map.get("hasRole");
        User user = (User)request.getSession().getAttribute("user");
        User selectUser = userService.getOne(new QueryWrapper<User>().eq("username", username).last("limit 0,1"));
        //用户是否上传头像
        headportrait = (headportrait == null || "".equals(headportrait)) ? Constant.DEFAULT_USER_HEADPORTRAIT:headportrait;

        User saveUser;
        if(id != null && !"".equals(id)){
            //编辑
            if(selectUser != null && !selectUser.getId().equals(id)){
                return  new ReturnMessage(ReturnMessage.ResultCode.ERROR,"该账号已经存在");
            }
            saveUser = new User( Integer.parseInt(id.toString()), username + "",  password + "",  name + "",
                    tel + "",  sex + "",  status + "",  headportrait + "");
            saveUser.setUpdateUserId(user.getId());
            saveUser.setUpdateUserName(user.getName());
            saveUser.setUpdateTime(new Date());
            userService.updateById(saveUser);
        }else{
            //新增
            if(selectUser != null){
                return  new ReturnMessage(ReturnMessage.ResultCode.ERROR,"该账号已经存在");
            }
            //MD5盐值加密
            password = new Md5Hash(password,username).toHex();

            saveUser = new User( username + "",  password + "",  name + "",
                    tel + "",  sex + "",  status + "",  headportrait + "");
            saveUser.setCreateUserId(user.getId());
            saveUser.setCreateUserName(user.getName());
            saveUser.setCreateTime(new Date());
            userService.save(saveUser);
        }

        //删除用户角色表
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id",saveUser.getId()));
        //添加中间表数据
        List<UserRole> list = new ArrayList<>();
        for(Integer item : hasRole){
            UserRole userRole = new UserRole();
            userRole.setUserId(saveUser.getId());
            userRole.setRoleId(item);
            list.add(userRole);
        }
        userRoleService.saveBatch(list);
        return new ReturnMessage();
    }


    @RequestMapping("/delUser")
    @ResponseBody
    public ReturnMessage delPermission(Integer id){
        //删除用户表数据
        userService.removeById(id);
        //删除用户-角色中间表
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id",id));
        return new ReturnMessage();
    }

}
