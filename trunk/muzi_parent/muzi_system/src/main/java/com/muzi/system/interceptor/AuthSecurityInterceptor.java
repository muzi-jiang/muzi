package com.muzi.system.interceptor;

import com.muzi.system.http.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("authSecurityInterceptor")
public class AuthSecurityInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(AuthSecurityInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {
            RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
            String body = requestWrapper.getBody();
            System.out.println(body);
            return true;
        }catch (Exception e){
            logger.error("权限判断出错",e);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
