package com.muzi.app.juzi.beam;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 读取配置文件
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties("muzi")
@PropertySource("classpath:book-dev.properties")
public class Book {

	private String key;

	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
