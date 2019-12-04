package com.muzi.test.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 视频下载
 */
public class DownloadVideoUtils {


    //设置初始缓存大小
    private static final int MAX_BUFFER_SIZE = 1000000;

    private static final String downloadFilePath = "D:\\test";
    /**
     *
     * 下载视频 未加密的视频连接
     */
    public static void DownloadVideoNotaddPass(String videoUrl){

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(videoUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置读取范围 Range表示读取的范围  表示读取从0开始到最大的字节，意味着读取所有的资源
            connection.setRequestProperty("Range","bytes=0-");
            connection.connect();
            if(connection.getResponseCode()/100 != 2){
                System.out.println("=================资源连接失败======================");
                return;
            }
            inputStream = connection.getInputStream();

            //定义已经下载的大小
            int downloaded = 0;
            //获取总文件的大小
            int fileSize = connection.getContentLength();
            //获取文件名的后缀
            String fileName = url.getFile();
            fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
            outputStream = new FileOutputStream(new File(downloadFilePath + "/" + fileName));
            while (downloaded < fileSize){
                //先设置缓存的大小
                byte[] buffer = null;
                if(fileSize - downloaded >= MAX_BUFFER_SIZE){
                    buffer = new byte[MAX_BUFFER_SIZE];
                }else{
                    buffer = new byte[fileSize - downloaded];
                }

                //将每次缓存的数据写入文件
                int read = -1;
                int currentDownload = 0;
                long startTime = System.currentTimeMillis();
                while (currentDownload < buffer.length){
                    read = inputStream.read();
                    buffer[currentDownload++] = (byte) read;

                }
                double speed = 0.0;
                long endTime = System.currentTimeMillis();
                if(endTime - startTime > 0){
                    speed = currentDownload / 1024 / ((double) (endTime -startTime) /1000);
                }
                outputStream.write(buffer);
                downloaded += currentDownload;
                System.out.printf("下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n",downloaded * 1.0 / fileSize * 10000 / 100,speed,speed/1000);
            }


        }catch (Exception e){
            e.getStackTrace();
        }finally {
            try {
                connection.disconnect();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DownloadVideoNotaddPass("http://aqiniu.tangdou.com/C79EBFF6107CE4389C33DC5901307461-20.mp4");
    }

}
