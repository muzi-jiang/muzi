package com.muzi.app.wx.config;

import java.io.Serializable;

public class WxPayItem implements Serializable{

	 private String appId;
	    private String mch_id;
	    private String signType;

	    public String getMch_id() {
	        return mch_id;
	    }

	    public void setMch_id(String mch_id) {
	        this.mch_id = mch_id;
	    }

	    private String timeStamp;
	    private String nonceStr;
	    private String packageObj;
	    private String paySign;
	    private String prepay_id;

	    public String getPrepay_id() {
	        return prepay_id;
	    }

	    public void setPrepay_id(String prepay_id) {
	        this.prepay_id = prepay_id;
	    }

	    public String getTimeStamp() {
	        return timeStamp;
	    }

	    public void setTimeStamp(String timeStamp) {
	        this.timeStamp = timeStamp;
	    }

	    public String getNonceStr() {
	        return nonceStr;
	    }

	    public void setNonceStr(String nonceStr) {
	        this.nonceStr = nonceStr;
	    }

	    public String getPackageObj() {
	        return packageObj;
	    }

	    public void setPackageObj(String packageObj) {
	        this.packageObj = packageObj;
	    }

	    public String getPaySign() {
	        return paySign;
	    }

	    public void setPaySign(String paySign) {
	        this.paySign = paySign;
	    }

	    public String getAppId() {

	        return appId;
	    }

	    public void setAppId(String appId) {
	        this.appId = appId;
	    }

	    public String getSignType() {
	        return signType;
	    }

	    public void setSignType(String signType) {
	        this.signType = signType;
	    }
}
