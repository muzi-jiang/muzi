package com.muzi.system.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义监听器    监听网站在线人数
 * @author Administrator
 *
 */
@Component
public class MyListener implements HttpSessionListener{
	
	private static final Logger logger = LoggerFactory.getLogger(MyListener.class);
	
	/**
     * 记录在线的用户数量
     */
    public Integer count = 0;
    
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("检测到新用户上线........");
		count++;
		se.getSession().getServletContext().setAttribute("count", count);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info("用户离开了........");
		count--;
		se.getSession().getServletContext().setAttribute("count", count);
	}
}
