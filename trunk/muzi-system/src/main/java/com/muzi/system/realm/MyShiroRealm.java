package com.muzi.system.realm;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.muzi.system.dict.DictEnum;
import com.muzi.system.entity.User;
import com.muzi.system.mapper.LoginMapper;

/**
 * 	用户认证
 * @author muzi
 *
 */
public class MyShiroRealm extends AuthorizingRealm{
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoginMapper loginMapper;
	
	/*@Autowired(required=true)
	private SessionDAO sessionDAO;*/
	/**
	 * 角色权限的添加
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		logger.info("认证成功，执行授权操作............");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//给用户进行授权
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getPrincipal();
		info.addStringPermission("");
		return info;
	}
	
	
	/**
	 * 该方法用于用户认证管理
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		logger.info("执行认证逻辑............");
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		String username = token.getUsername();
		User user = loginMapper.findUserByUserName(username);
		if(null == user){
			return null;
		}
		//1.判断用户名
		if(!username.equals(user.getUserName())){
			throw new UnknownAccountException();
		}
		//2.判断登录账号是否被锁定
		if(DictEnum.AccountStatus.ABNORMAL.getValue().equals(user.getStatus())){
			throw new LockedAccountException();
		}
		
		/*//单点登录  踢出已登录的用户
		Collection<Session> sessions  = sessionDAO.getActiveSessions();
		for(Session session : sessions){
			if(username.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))){
				session.setTimeout(0);
				break;
			}
		}*/
		
		//3.密码判断
		SimpleAuthenticationInfo simpleAuthenticationInfo = null;
		try {
			simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassWord(),ByteSource.Util.bytes(username),getName());
		} catch (Exception e) {
			throw new IncorrectCredentialsException();
		}
		
		//如果没有抛出异常则表示认证成功，则将user放进session
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	     HttpSession session = request.getSession();
	     session.setAttribute("user", user); 
		return simpleAuthenticationInfo;
	}

}
