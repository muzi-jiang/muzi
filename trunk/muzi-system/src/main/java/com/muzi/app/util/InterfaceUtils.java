package com.muzi.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * 三方接口调用工具类
 * @author Administrator
 *
 */
public class InterfaceUtils {

	private static Logger log = LoggerFactory.getLogger(InterfaceUtils.class);

	/**
	 * 调用所有接口发送数据
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String  getInterface(String param,String url){
		URL urlAdd = null;
		try {
			urlAdd = new URL(url);
			//创建连接
			URLConnection openConnection = urlAdd.openConnection();
			//表示可以向连接中写入的参数
			openConnection.setDoOutput(true);
			//表示可以向链接中读取信息
			openConnection.setDoInput(true);
			//设置链接超时时间
			openConnection.setConnectTimeout(3000);
			//创建当前链接的输入流
			OutputStream stream = openConnection.getOutputStream();
			//创建字符的输入流对象
			OutputStreamWriter bw  = new OutputStreamWriter(stream, "utf-8");
			//把请求的参数发送给对方
			bw.write(param);
			bw.flush();
			//接受返回来的信息
			InputStreamReader sr = new InputStreamReader(openConnection.getInputStream(),"utf-8");
			//定义一个StringBuilder
			StringBuilder sb = new StringBuilder();
			char[] b = new char[1024];
			int len;
			while((len=sr.read(b)) != -1){
				sb.append(new String(b,0,len));
			}
			
//			/**
//			 * 获取结果中的result值
//			 */
//			JSONObject js01 = JSONObject.fromObject(sb.toString());
//			if("200".equals(js01.get("resultcode"))){
//				return js01.getString("result");
//			}
			return sb.toString();
		} catch (Exception e) {
			
		} 
		return null;
	}
	
	
	
	
	/**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {

        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("utf-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
