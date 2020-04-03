package com.muzi.system.config;

import com.muzi.system.filter.ChannelFilter;
import com.muzi.system.interceptor.LoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("执行自定义拦截器..........");
        registry.addInterceptor(new LoginInterceptor())
                            .addPathPatterns("/**")
                            .excludePathPatterns("/error")
                            .excludePathPatterns("/system/manager/login/userLogin")  //登录
                            .excludePathPatterns("/system/manager/permission/getRouterAll")  //项目一加载获取全部路由
                            .excludePathPatterns("/system/manager/user/up");  //图片上传
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public FilterRegistrationBean tokenFilter() {
        System.out.println("执行自定义过滤器..........");
        FilterRegistrationBean filterReg = new FilterRegistrationBean(new ChannelFilter());
        //优先级
        filterReg.setOrder(70);
        filterReg.setDispatcherTypes(DispatcherType.REQUEST);
        //匹配路径
        List<String> urlPatterns = new ArrayList<>();
        //urlPatterns.add("/*");
        filterReg.addUrlPatterns("/*");
        filterReg.addInitParameter("exclusions","/system/manager/user/up");
        //filterReg.setUrlPatterns(urlPatterns);
        return filterReg;
    }
}
