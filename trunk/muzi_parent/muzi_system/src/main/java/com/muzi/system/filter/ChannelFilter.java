package com.muzi.system.filter;


import com.muzi.system.http.RequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@Component
//@WebFilter(urlPatterns = "/*")
public class ChannelFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        if("/system/manager/user/up".equals(requestURI)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            ServletRequest requestWrapper = new RequestWrapper(request);
            filterChain.doFilter(requestWrapper, servletResponse);
        }

//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//
//        ParamsRequestWrapper requestWrapper = new ParamsRequestWrapper(httpRequest);
//        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
