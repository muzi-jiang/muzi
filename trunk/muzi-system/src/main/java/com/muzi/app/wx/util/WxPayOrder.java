package com.muzi.app.wx.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("deprecation")
public class WxPayOrder {

	protected static Logger logger = LoggerFactory.getLogger(WxPayOrder.class);
    public static CloseableHttpClient  httpclient;

    {
        httpclient = new DefaultHttpClient();
        httpclient = (CloseableHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
    }

    public ResultMessage refund(String url, String xmlParam, String mch_id, String certPath) throws Exception {
        logger.info("退款信息:xmlParam" + xmlParam + "  :" + mch_id + "   certPath:" + certPath);
        BasicHttpClientConnectionManager connManager;
        String return_code = "";
        FileInputStream certStream = null;
        try {
            // 证书
            char[] password = mch_id.toCharArray();
            File file = new File(certPath);
            certStream = new FileInputStream(file);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
            HttpClient client = HttpClientBuilder.create()
                    .setConnectionManager(connManager)
                    .build();
            HttpPost httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            httpPost.setConfig(requestConfig);

            StringEntity postEntity = new StringEntity(xmlParam, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + mch_id);  // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
            httpPost.setEntity(postEntity);

            HttpResponse httpResponse = client.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            String jsonStr = EntityUtils.toString(httpEntity, "UTF-8");
            Map map = doXMLParse(jsonStr);
            if (map != null) {
                return_code = (String) map.get("result_code");

                if (return_code.equals("SUCCESS")) {
                    return new ResultMessage(true, "退款成功");
                } else {
                    return new ResultMessage(false, (String) map.get("err_code_des"));
                }
            } else {
                return new ResultMessage(false, "解析xml错误");
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (certStream != null) {
                certStream.close();
            }
        }
    }

    public static String getPayNo(String url, String xmlParam) {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        String prepay_id = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            Map<String, Object> dataMap = new HashMap<String, Object>();
            System.out.println("jsonStr:" + jsonStr);
            if (jsonStr.indexOf("FAIL") != -1) {
                return "";
            }
            Map map = doXMLParse(jsonStr);
            if(map!=null){
                String return_code = (String) map.get("return_code");
                prepay_id = (String) map.get("prepay_id");
            }

        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }finally {
            if(client!=null){
                client.close();
            }
        }
        return prepay_id;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws
     * @throws
     */
    public static Map doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    //退款
    public ResultMessage refund(HttpServletRequest request, HttpServletResponse response, String appid, String mch_id, String transaction_id, String out_trade_no, String out_refund_no, int total_fee, int refund_fee, String appsecret, String key, String certPath) throws Exception {
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String nonce_str = strTime + strRandom;
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("transaction_id", transaction_id);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("out_refund_no", out_refund_no);
        packageParams.put("total_fee", total_fee + "");
        packageParams.put("refund_fee", refund_fee + "");
        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, key);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>"
                + "<appid>" + appid + "</appid>"
                + "<mch_id>" + mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<sign>" + sign + "</sign>"
                + "<transaction_id>" + transaction_id + "</transaction_id>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<out_refund_no>" + out_refund_no + "</out_refund_no>"
                + "<total_fee>" + total_fee + "</total_fee>"
                + "<refund_fee>" + refund_fee + "</refund_fee>"
                + "</xml>";

        return new WxPayOrder().refund("https://api.mch.weixin.qq.com/secapi/pay/refund", xml, mch_id, certPath);
    }
}
