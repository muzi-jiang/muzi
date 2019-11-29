package com.muzi.test.util;

import com.muzi.system.dict.DictEnum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class ImageUtils {


    /**
     * 保存图片
     * @param imageUrl
     * @return
     */
    public static String saveImages(String imageUrl){

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = null;

        HttpGet httpGet = new HttpGet(imageUrl);
        OutputStream outputStream = null;

        try {
            response = httpClient.execute(httpGet);
            //获取下载图片的后缀
            String extName = imageUrl.substring(imageUrl.lastIndexOf("."));
            //文件全路径
            String picName = UUID.randomUUID().toString() + extName;

             outputStream = new FileOutputStream(new File(DictEnum.FILEPATH_TITLE+picName));

            HttpEntity entity = response.getEntity();
            entity.writeTo(outputStream);
            return picName;
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            try {
                ((CloseableHttpClient) httpClient).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    /*public static void main(String[] args) {
       for (int i =0 ;i<50;i++){
           //new Thread(() -> {
               String path = saveImages("http://47.99.220.61:8001/muzi/01.jpg");
               System.out.println(path);
           //}).run();

       }
    }*/
}
