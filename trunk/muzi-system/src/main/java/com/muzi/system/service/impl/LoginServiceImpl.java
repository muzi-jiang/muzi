package com.muzi.system.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.system.dict.DictEnum;
import com.muzi.system.entity.Permission;
import com.muzi.system.entity.User;
import com.muzi.system.mapper.DictMapper;
import com.muzi.system.mapper.LoginMapper;
import com.muzi.system.service.LoginService;

/**
 * 登录业务处理
 * @author Administrator
 *
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private DictMapper dictMapper;
	
	 private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 *处理登录逻辑
	 */
	@Override
	public String userLogin(String userName, String passWord,Model model) {
		Subject subject = SecurityUtils.getSubject();
		if(null == passWord){
			passWord = "";
		}
		//密码加密
		String MD5PassWord = new Md5Hash(passWord,userName).toHex();
		UsernamePasswordToken token  = new UsernamePasswordToken(userName, MD5PassWord);
		/**
		 * 执行登录逻辑
		 */
		try {
			subject.login(token);
			//设置认证过期时间
			
			/**
			 * 此处时间单位为毫秒:   1000 * 60 * 30  = 30分钟
			 */
			String code = dictMapper.findCodeByTypeCode(DictEnum.DictTypeCode.AUTHENTICATIONTIMEOUT.getValue());
			subject.getSession().setTimeout(Integer.parseInt(code));
			return "redirect:"+DictEnum.LoginPath.TOINDEX.getValue();
		} catch (UnknownAccountException e) {   
			model.addAttribute("msg", DictEnum.LoginMsg.USERNAMEISNOTEXIST.getValue());
			return DictEnum.LoginPath.FORWARDTOLOGIN.getValue();
		} catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", DictEnum.LoginMsg.PASSWORDISERROR.getValue());
			return DictEnum.LoginPath.FORWARDTOLOGIN.getValue();
		} catch (LockedAccountException e) {
			model.addAttribute("msg", DictEnum.LoginMsg.ACCOUNTLOCKED.getValue());
			return DictEnum.LoginPath.FORWARDTOLOGIN.getValue();
		}
	}
	
	@Autowired
	private HttpServletRequest request;
	
	
	/**
	 * 主页
	 */
	@Override
	public ModelAndView toIndex() {
		ModelAndView mav = new ModelAndView(DictEnum.LoginPath.FORWARDTOINDEX.getValue());
		User user = (User)request.getSession().getAttribute("user");
		mav.addObject("user", user);
		/**  拼接用户拥有的菜单  以及页面需要的数据       */
		//通过用户id查找对应的菜单权限
		//先查找出用户所拥有的父级菜单    一级菜单
		List<Permission> parentPermission = loginMapper.findParentPermissionByUserId(user.getId());
		
		//List<Integer> ids = parentPermission.stream().map(Permission::getId).collect(Collectors.toList());
		//String parentId = StringUtils.join(ids.toArray(),",");
		
		//查询所有的子菜单
		List<Permission> permissions = loginMapper.findPermissionByParentId(user.getId()+"");
		
		StringBuffer sb = new StringBuffer();
		
		if(null != parentPermission && parentPermission.size()!=0){
			parentPermission.forEach((Permission parentMenu)  ->{
				logger.info("=====>"+parentMenu.getPermissionName());
				//拼接父级菜单
				sb.append("<li class=\"layui-nav-item\">")
				  .append("<a href=\"javascript:;\">")
			      .append("<i class=\""+parentMenu.getPermissionIcon()+"\" ></i>")
				  .append("<span>"+parentMenu.getPermissionName()+"</span>")
			      .append("<em class=\"layui-nav-more\"></em>")
				  .append("</a>");
				
				sb.append("<dl class=\"layui-nav-child\">");	
				//拼接该子集菜单
				if(null != permissions && permissions.size()!=0){
					permissions.forEach((Permission p) ->{
						if(p.getPermissionParentid() == parentMenu.getId()){
							logger.info("=====>"+p.getPermissionName()+"----"+p.getPermissionUrl());
							sb.append("<dd>")
							  .append("<a href=\"javascript:;\" data-url=\""+p.getPermissionUrl()+"\">")
							  .append("<i class=\""+p.getPermissionIcon()+"\" data-icon='icon-geren1'></i>")
							  .append("<span>"+p.getPermissionName()+"</span>")
							  .append("</a>")
							  .append("</dd>");
						}
					});
				}	  
				sb.append("</dl>");	
				sb.append("</li>");
							
			});
		}
		if(null != sb){
			mav.addObject("MyMenu",sb.toString());
		}
		return mav;
	}

}
