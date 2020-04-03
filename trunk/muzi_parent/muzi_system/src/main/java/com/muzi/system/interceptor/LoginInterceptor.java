package com.muzi.system.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.muzi.communal.util.JWTUtil;
import com.muzi.system.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 自定义登录拦截
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断登录session用户是否过期
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            response.setStatus(401);
            return false;
        }
        String method = request.getMethod();
        if("GET".equals(method)){
            String access_token = request.getParameter("access_token");
            if (access_token != null && !"".equals(access_token) && JWTUtil.verify(access_token,user.getPassword())){
                return true;
            }
            response.setStatus(401);
            return false;
        }else if("POST".equals(method)){
            String body = "";
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            InputStream inputStream = null;
            try {
                inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                } else {
                    stringBuilder.append("");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            body = stringBuilder.toString();
            JSONObject jsonObject = JSONObject.parseObject(body);
            String access_token = jsonObject.get("access_token")+"";
            if (access_token != null && !"".equals(access_token) && JWTUtil.verify(access_token,user.getPassword())){
                return true;
            }
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
