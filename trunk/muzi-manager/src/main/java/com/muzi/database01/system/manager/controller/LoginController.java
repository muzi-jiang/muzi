package com.muzi.database01.system.manager.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muzi.communal.util.ReturnMessage;
import com.muzi.database01.system.manager.entity.User;
import com.muzi.database01.system.manager.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
@Controller
@RequestMapping("/base01/system/manager/login")
public class LoginController {

    @Autowired
    private UserService userService;


    /**
     * 登录认证
     * @param user
     * @return
     */
    @PostMapping("/userLogin")
    @ResponseBody
    public ReturnMessage userLogin(@RequestBody User user){
        Map<String,Object> map = new HashMap<>(1);
        map.put("username",user.getUsername());
        User selectUser = userService.getOne((Wrapper<User>) new QueryWrapper().allEq(map));

        if(selectUser == null){
            return new ReturnMessage(ReturnMessage.ResultCode.ERROR,"用户名不存在");
        }

        String passwordMD5 = new Md5Hash(user.getPassword(), user.getUsername()).toHex();
        if(!passwordMD5.equals(selectUser.getPassword())){
            return new ReturnMessage(ReturnMessage.ResultCode.ERROR,"密码错误");
        }

        if(!"1".equals(selectUser.getStatus())){
            return new ReturnMessage(ReturnMessage.ResultCode.ERROR,"账号被锁定");
        }
        return new ReturnMessage(ReturnMessage.ResultCode.OK,"登录成功");
    }
}
