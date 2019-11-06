package com.muzi.system.supperentity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * QQ登录参数封装
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties("muzi.qq.login")
@PropertySource("classpath:qqlogin.properties")
public class QQLoginParam {
				
		private String appkey;
		
		private String appid;
		
		/**
		 * 获取accessToken请求路径
		 */
		private String accessTokenUrl;
		
		/**
		 * QQ登录回调路径
		 */
		private String redirectURI;
		
		/**
		 * 获取openidUrl请求路径
		 */
		private String openidUrl;
		
		/**
		 * 获取userInfoUrl请求路径
		 */
		private String userInfoUrl;
		
		public String getAppkey() {
			return appkey;
		}
		public void setAppkey(String appkey) {
			this.appkey = appkey;
		}
		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getAccessTokenUrl() {
			return accessTokenUrl;
		}
		public void setAccessTokenUrl(String accessTokenUrl) {
			this.accessTokenUrl = accessTokenUrl;
		}
		public String getRedirectURI() {
			return redirectURI;
		}
		public void setRedirectURI(String redirectURI) {
			this.redirectURI = redirectURI;
		}
		public String getOpenidUrl() {
			return openidUrl;
		}
		public void setOpenidUrl(String openidUrl) {
			this.openidUrl = openidUrl;
		}
		public String getUserInfoUrl() {
			return userInfoUrl;
		}
		public void setUserInfoUrl(String userInfoUrl) {
			this.userInfoUrl = userInfoUrl;
		}
		@Override
		public String toString() {
			return "QQLoginParam [appkey=" + appkey + ", appid=" + appid + ", accessTokenUrl=" + accessTokenUrl
					+ ", redirectURI=" + redirectURI + ", openidUrl=" + openidUrl + ", userInfoUrl=" + userInfoUrl
					+ "]";
		}
			
			
}
