package com.muzi.app.dict;


/**
 * APP端自定义常量
 * @author Administrator
 *
 */
public class DictApp {
	
	/**
	 * 分页  每页展示数据量
	 */
	public static final int PAGE_SIZE = 5;
	/**
	 * mongoBD数据库集合名称定义
	 * @author Administrator
	 *
	 */
	public enum ConnectionName{
		MUZI_JUZI {public String getValue(){return "muzi_juzi";}},
		//session认证过期时间
		MUZI_USER {public String getValue(){return "muzi_user";}};
		public abstract String getValue();
	}
}
