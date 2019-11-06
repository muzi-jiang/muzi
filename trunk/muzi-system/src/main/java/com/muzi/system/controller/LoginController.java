package com.muzi.system.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.system.dict.DictEnum;
import com.muzi.system.service.LoginService;

/**
 * 权限认证控制器
 * @author libin
 * @date 2019/7/27 10:14
 */
@Controller()
@RequestMapping("/security/login")
public class LoginController {

	
	@Autowired
	private LoginService loginService;
	
	
	
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("/tologin")
    public ModelAndView tologin(){
        ModelAndView mav = new ModelAndView(DictEnum.LoginPath.FORWARDTOLOGIN.getValue());
        return mav;
    }

    

    /**
     * 跳转主界面
     * @return
     */
    @RequestMapping("/toindex")
    public ModelAndView toindex(){
    	logger.info("登录成功，跳转主界面.......");
        ModelAndView mav = loginService.toIndex();
        return mav;
    }
    
    /**
     * 登陆认证
     * @return
     */
    @PostMapping("/userLogin")
    public String  userLogin(String username,String password,Model model){
    	logger.info("登录的用户名："+username);
    	String userLogin = loginService.userLogin(username, password,model);
        return userLogin;
    }

    
    /**
     * 首页数据加载   时钟
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }

    
    /**
     * 首页数据加载  百度地图
     * @return
     */
    @RequestMapping("/map")
    public ModelAndView map(){
        ModelAndView mav = new ModelAndView("map");
        return mav;
    }
    
    
}

