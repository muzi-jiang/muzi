package com.muzi.system.dict;

/**
 * 定义常量
 * @author Administrator
 *
 */
public class DictEnum {
	/**
	 * 初始版本
	 */
	final static String InitVerNo = "1";
	
	
	/**
	 * 常数定义管理
	 * @author Administrator
	 *
	 */
	public enum DictTypeCode{
		//定义创建用户的初始密码
		PASSWORD_INIT {public String getValue(){return "password_init";}},
		//session认证过期时间
		AUTHENTICATIONTIMEOUT {public String getValue(){return "Authentication_TimeOut";}};
		public abstract String getValue();
	}
	
	
	/**
	 * 定义新增，修改，删除 操作返回信息
	 * @author Administrator
	 *
	 */
	public enum SystemMsg{
		
		//菜单管理  
		MENUNAMEIS_EXIST {public String getValue(){return "该菜单名已存在";}},
		
		//角色管理
		ROLENAMEIS_EXIST {public String getValue(){return "该角色名已存在";}},
		
		//用户管理
		ACCOUNT_EXIST {public String getValue(){return "该账号已存在";}},
		PASSWORDRESET {public String getValue(){return "密码重置成功";}},
		
		
		ADDSUCCESS {public String getValue(){return "添加成功";}},
		ADDFAIL {public String getValue(){return "添加失败";}},
		UPDATESUCCESS {public String getValue(){return "修改成功";}},
		UPDATEFAIL {public String getValue(){return "修改失败";}},
		DELETESUCCESS {public String getValue(){return "删除成功";}},
		DELETEFAIL {public String getValue(){return "删除失败";}},
		NETWORKCONNECTIONEXCEPTION {public String getValue(){return "网络连接异常";}};
		public abstract String getValue();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 定义登录返回的错误消息
	 *
	 */
	public enum LoginMsg{
		USERNAMEISNOTEXIST {public String getValue(){return "用户名不存在";}},
		PASSWORDISERROR {public String getValue(){return "密码错误";}},
		ACCOUNTLOCKED {public String getValue(){return "账号被锁定";}};
		public abstract String getValue();
	}
	
	
	/**
	 * 定义用户账号状态类型      
	 *	
	 */
	public enum AccountStatus{
		NORMAL {public String getValue(){return "1";}},//正常
		ABNORMAL {public String getValue(){return "0";}};//异常
		public abstract String getValue();
	}
	
	/**
	 * 定义登陆认证的路径
	 * @author Administrator
	 *
	 */
	public enum LoginPath{
		FORWARDTOLOGIN {public String getValue(){return "login/login";}},//转发到登录页面
		FORWARDTOINDEX {public String getValue(){return "index";}},//转发到主页面
		TOLOGIN {public String getValue(){return "/security/login/tologin";}},//重定向到达登陆页面
		TOINDEX {public String getValue(){return "/security/login/toindex";}},//到达主界面
		LOGOUT {public String getValue(){return "/logout";}},//退出登录
		USERLOGIN {public String getValue(){return "/security/login/userLogin";}};//认证申请
		public abstract String getValue();
	}
	
	
	
	/**
	 * 定义返回状态码错误页面路径
	 * @author Administrator
	 *
	 */
	public enum ErrorPath{
		STATUS404 {public String getValue(){return "/system/error/status404";}},
		STATUS500 {public String getValue(){return "/system/error/status500";}};
		public abstract String getValue();
	}
	
	
	
	
	
	
	
	
	
}
