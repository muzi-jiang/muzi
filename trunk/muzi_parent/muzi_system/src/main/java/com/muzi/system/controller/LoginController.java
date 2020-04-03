package com.muzi.system.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muzi.communal.util.JWTUtil;
import com.muzi.communal.util.ReturnMessage;
import com.muzi.system.entity.User;
import com.muzi.system.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/system/manager/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;
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
        //无任何异常,登录成功
        Map<String,Object> returnMap = new HashMap<>();
        //生成token 使用MD5盐值加密结果作为key秘钥
        String access_token = JWTUtil.createJWT(1000 * 60 * 30, selectUser.getUsername(), selectUser.getPassword());

        returnMap.put("access_token",access_token);
        returnMap.put("user",selectUser);

        request.getSession().setAttribute("user",selectUser);
        request.getSession().setAttribute("access_token",access_token);

        return new ReturnMessage(ReturnMessage.ResultCode.OK,"登录成功",returnMap);
    }
}
