package com.muzi.app.wx.controller;


import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.app.wx.config.Config;
import com.muzi.app.wx.config.WxPayItem;
import com.muzi.app.wx.util.RequestHandler;
import com.muzi.app.wx.util.ResultMessage;
import com.muzi.app.wx.util.Sha1Util;
import com.muzi.app.wx.util.TenpayUtil;
import com.muzi.app.wx.util.WxPayOrder;

/**
 * 微信接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/app/weixin")
public class WXPayController {

	private final static Logger logger = LoggerFactory.getLogger(WXPayController.class);
	
	@RequestMapping("/toWxpay")
	public ModelAndView toWxpay(){
		ModelAndView mav = new ModelAndView("test/wxpay");
		return mav;
	}
	
	
	/**
	 * 微信支付
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/api/pay",method=RequestMethod.POST)
	@ResponseBody
	public Object callWxPay(HttpServletRequest request,HttpServletResponse response){
		String orderId = request.getParameter("orderId");
		/**
		 * 支付金额   单位为分
		 */
		int actuallypaid = 10 * 10 ;
		
		/**
		 * 商品详情
		 */
		String body = "商品详细信息";
		
		/**
		 * 订单编号
		 */
		String code = UUID.randomUUID().toString().replaceAll("-", "");
		
		/**
		 * 支付回调时可以拿到的参数
		 */
		String attach = "{\"type\":\"商品订单支付\",\"orderId\":\"" + orderId + "\",\"payValue\":\"" + actuallypaid + "\"}"; 
		
		/**
		 * 支付系统的订单号
		 */
		String clientIP = request.getRemoteAddr();
		
		String openId = "oXClauMN0upor7rhDxJxgN5GWXr8";
		
		ResultMessage result = invokeUnifiedorderByWeixinWeb(request, response, Config.wx_mch_id,
				Config.wx_appid, Config.wx_appsecret, Config.wx_key,
				body, attach, code, 
				clientIP, Config.wx_notify_url,
				"JSAPI", openId, actuallypaid+"");
		if (!result.getResult()) {
            throw new RuntimeException("支付接口异常");
        }
		return result.getObject();
	}
	
	/**
     * 调用 invokeUnifiedorderByWeixinWeb
     *
     * @param
     * @return
     */
    @SuppressWarnings("static-access")
	public ResultMessage invokeUnifiedorderByWeixinWeb(HttpServletRequest request, HttpServletResponse response,
    		String mch_id,            String appid,                        String appsecret,
    		String key,               String body,                         String attach, 
    		String out_trade_no,      String spbill_create_ip,             String notify_url, 
    		String trade_type,        String openid,                       String total_fee) {
    	
    	String prepay_id = "";
    	String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
	    String strRandom = TenpayUtil.buildRandom(4) + "";
	    String nonce_str = strTime + strRandom;
	    SortedMap<String, String> packageParams = new TreeMap<String, String>();
	    packageParams.put("appid", appid);
	    packageParams.put("mch_id", mch_id);
	    packageParams.put("nonce_str", nonce_str);
	    packageParams.put("body", body);
	    packageParams.put("attach", attach);
	    packageParams.put("out_trade_no", out_trade_no);
	    packageParams.put("total_fee", total_fee);
	    packageParams.put("spbill_create_ip", spbill_create_ip);
	    packageParams.put("notify_url", notify_url);
	    packageParams.put("trade_type", trade_type);
	    if (!openid.trim().equals("")) {
	    	packageParams.put("openid", openid);
	    }
	    
	    RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, key);

        String sign = reqHandler.createSign(packageParams); 
        String xml = "<xml>"
                + "<appid>" + appid + "</appid>"
                + "<mch_id>" + mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<sign>" + sign + "</sign>"
                + "<body><![CDATA[" + body + "]]></body>" +
                "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<attach>" + "<![CDATA[" + attach + "]]>" + "</attach>"
                +
                "<total_fee>"
                + total_fee
                + "</total_fee>"
                +
                "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                + "<notify_url>" + notify_url + "</notify_url>"
                + "<trade_type>" + trade_type + "</trade_type>";
        
        if (!openid.trim().equals("")) {
            xml += "<openid>" + openid + "</openid>";
        }
        xml += "</xml>";
        
        try {
            prepay_id = new WxPayOrder().getPayNo("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
            if (prepay_id.equals("")) {
                return new ResultMessage(false, "统一支付接口获取预支付订单出错");
            }
        } catch (Exception e1) {
            logger.error(e1.getMessage(),e1);
        }
        
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = Sha1Util.getTimeStamp();
        finalpackage.put("appId", appid);
        
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("key", key);
        finalpackage.put("package", "prepay_id=" + prepay_id);
        
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);

        WxPayItem wxPayItem = new WxPayItem();
        wxPayItem.setAppId(appid);
        wxPayItem.setMch_id(mch_id);
        wxPayItem.setNonceStr(nonce_str);
        
        wxPayItem.setPackageObj("prepay_id=" + prepay_id);
        wxPayItem.setPaySign(finalsign);
        wxPayItem.setPrepay_id(prepay_id);
        wxPayItem.setTimeStamp(timestamp);
        wxPayItem.setSignType("MD5");
        
        return new ResultMessage(true, "", wxPayItem);
    }
	
}
