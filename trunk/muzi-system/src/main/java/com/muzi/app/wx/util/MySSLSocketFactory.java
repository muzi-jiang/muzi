package com.muzi.app.wx.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


import org.apache.http.conn.ssl.SSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySSLSocketFactory extends SSLSocketFactory{
	
	 protected static Logger logger = LoggerFactory.getLogger(MySSLSocketFactory.class);

		static{
			mySSLSocketFactory = new MySSLSocketFactory(createSContext());
		}

		private static MySSLSocketFactory mySSLSocketFactory = null;



		private static SSLContext createSContext(){
			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContext.getInstance("SSL");
			} catch (NoSuchAlgorithmException e) {
				  logger.error(e.getMessage(), e);
			}
			try {
				if(sslcontext!=null){
					sslcontext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, null);
				}
			} catch (KeyManagementException e) {
				  logger.error(e.getMessage(), e);
				return null;
			}
			return sslcontext;
		}

		private MySSLSocketFactory(SSLContext sslContext) {
			super(sslContext);
			this.setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
		}

		public static MySSLSocketFactory getInstance(){
			if(mySSLSocketFactory != null){
				return mySSLSocketFactory;
			}else{
				return mySSLSocketFactory = new MySSLSocketFactory(createSContext());
			}
		}

}
