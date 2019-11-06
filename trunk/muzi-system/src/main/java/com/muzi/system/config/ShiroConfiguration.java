package com.muzi.system.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.muzi.system.dict.DictEnum;
import com.muzi.system.realm.MyShiroRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfiguration{

	 private Logger logger = LoggerFactory.getLogger(getClass());

	 
	 /**
	  * shiro标签可以在html页面使用
	  * @return
	  */
	 @Bean
	 public ShiroDialect getShiroDialect(){
		 return new ShiroDialect();
	 }
	 
	 /**
	  *	添加安全管理规则
	  * @param securityManager
	  * @return
	  */
	 @Bean
	 public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		 AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		 sourceAdvisor.setSecurityManager(securityManager);
		 return sourceAdvisor;
	 }
	 
	 /**
	  * Realm的管理认证
	  * @return
	  */
	 @Bean
	 public SecurityManager securityManager(){
		 logger.info("Realm的管理认证................");
		 DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		 securityManager.setRealm(myShiroRealm());
		 return securityManager;
	 }
	 
	/**
	 * 将自己的验证方式加入到容器中
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm(){
		logger.info("加载自己的校验规则................");
		MyShiroRealm myShiroRealm =new MyShiroRealm();
		return myShiroRealm;
	}
	 
	/**
	 * 自己的校验规则，判定那些路径需要拦截， 设置相对应的过滤条件
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
		logger.info("开始加载拦截路径权限................");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String,String> map = new HashMap<>();
		/**
		 * anon : 表示不需要认证就直接可以访问
		 * 
		 * authc： 表示需要认证才能访问
		 * 
		 * perms【role】 :表示访问该路径需要  role权限
		 * 
		 * logout: logout 登出路径
		 */
		//设置不拦截的路径   一般有三个  到达登录页面的路径    登录请求的路径    静态资源加载的路径
		map.put(DictEnum.LoginPath.TOLOGIN.getValue(), "anon");
		map.put(DictEnum.LoginPath.USERLOGIN.getValue(), "anon");
		map.put("/static/**", "anon");
		//不拦截api开头的路径
		map.put("/app/**", "anon");
		//退出登录
		map.put(DictEnum.LoginPath.LOGOUT.getValue(), "logout");
		//对剩下的所有路径进行认证
		map.put("/**","authc");
		//设置认证页面路径
		shiroFilterFactoryBean.setLoginUrl(DictEnum.LoginPath.TOLOGIN.getValue());
		//设置登录成功的首页
		shiroFilterFactoryBean.setSuccessUrl(DictEnum.LoginPath.TOINDEX.getValue());
		//设置登录失败的页面，即登录页面
		shiroFilterFactoryBean.setUnauthorizedUrl(DictEnum.LoginPath.TOLOGIN.getValue());
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}
	 
	 
	 
}
