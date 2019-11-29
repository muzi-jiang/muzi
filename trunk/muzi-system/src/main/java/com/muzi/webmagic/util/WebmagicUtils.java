package com.muzi.webmagic.util;

import com.muzi.system.dict.DictEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebmagicUtils {

    public static String getValueByKeyInHtml(String src, String key) {
        Pattern pattern = Pattern.compile("(?:" + key + "\\s*=\\s*)" + "['\"](.*?)['\"]");
        Matcher matcher = pattern.matcher(src);
        if (matcher.find()) {
            return matcher.group().replaceAll(key + "\\s*=\\s*", "").replaceAll("\"", "");
        }
        return null;
    }

    /**
     * 将网络图片下载到本地
     * @param imageUrl  网络图片路径
     * @return  本地图片路径
     */
    public static String downloadImage(String imageUrl){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = null;
        HttpGet httpGet = new HttpGet(imageUrl);
        OutputStream outputStream = null;
        try {
            response = httpclient.execute(httpGet);
            //获取下载图片的后缀
            String lastName = imageUrl.substring(imageUrl.lastIndexOf("/"));
            String dateFormat = DateUtils.getDateFormat();
            //创建新的路径
            File file = new File(DictEnum.FILEPATH_TITLE+dateFormat);
            if(!file.exists()){
                file.mkdirs();
            }
            String newImage = DictEnum.FILEPATH_TITLE+dateFormat+lastName;
            //将图片输出到该路径
            outputStream = new FileOutputStream(new File(newImage));
            HttpEntity entity = response.getEntity();
            entity.writeTo(outputStream);
            return newImage;

        }catch (Exception e){
            e.getStackTrace();
        }finally {
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
