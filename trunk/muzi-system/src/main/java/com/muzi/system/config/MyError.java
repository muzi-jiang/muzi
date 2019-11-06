package com.muzi.system.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.muzi.system.dict.DictEnum;
/**
 * 配置系统错误页面信息
 * @author Administrator
 *
 */
@Component
public class MyError implements ErrorPageRegistrar {

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		 /*1、按错误的类型显示错误的网页*/
        /*错误类型为404，找不到网页的，默认显示404.html网页*/
		ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, DictEnum.ErrorPath.STATUS404.getValue());
		/*错误类型为500，表示服务器响应错误，默认显示500.html网页*/
		ErrorPage e500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, DictEnum.ErrorPath.STATUS404.getValue());
		 /*2、按具体某个异常显示错误的网页*/
		registry.addErrorPages(e404, e500);
		
	}

}
