package com.muzi.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.muzi.app.util.InterfaceUtils;
import com.muzi.system.dict.DictEnum;
import com.muzi.system.supperentity.QQLoginParam;

import net.sf.json.JSONObject;

/**
 * qq第三方登录的控制层
 * @author Administrator
 *
 */
@Controller
public class QQLoginController {

	/**
	 * 数据注入
	 */
	@Autowired
	private QQLoginParam qqLogin;
	
	/**
	 * QQ登录回调函数
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/muzi")
	public String muzi(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
		
		
		
		/**
		 * 根据code获取access_token
		 */
		String url = qqLogin.getAccessTokenUrl();
		String appid = qqLogin.getAppid();
		String appkey = qqLogin.getAppkey();
		String redirectURI = qqLogin.getRedirectURI();
		String param = "grant_type=authorization_code&client_id=" + appid + "&client_secret="+ appkey + "&redirect_uri=" + redirectURI + "&code=" + code;
		String  resultAccessToken= InterfaceUtils.getInterface(param, url);
		String access_token = ((resultAccessToken.split("&")[0]).split("="))[1];
		
		/**
		 * 根据access_token 获取openID
		 */
		String openIDUrl = qqLogin.getOpenidUrl();
		String openIDResult = InterfaceUtils.getInterface("access_token="+access_token, openIDUrl);
		String resultHandel = openIDResult.replaceAll("callback", "").replaceAll("[(]", "").replaceAll("[)]","").replaceAll(";","");
		JSONObject fromObject = JSONObject.fromObject(resultHandel);
		String openid = fromObject.get("openid")+"";
		
		/**
		 * 获取用户信息
		 */
		String userUrl = qqLogin.getUserInfoUrl();
		String userParam = "access_token="+access_token+"&oauth_consumer_key="+appid+"&openid="+openid;
		
		String initParam = userUrl +"?"+ userParam;
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(initParam);
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		String resultUserInfo = "";
		if(entity != null){
            resultUserInfo = EntityUtils.toString(entity,"UTF-8");
            System.out.println(resultUserInfo);
        }
		httpGet.releaseConnection();
		return "redirect:"+DictEnum.LoginPath.TOINDEX.getValue();
	   
	}
	
}
