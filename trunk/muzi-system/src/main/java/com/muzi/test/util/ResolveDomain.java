package com.muzi.test.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 域名动态解析
 * @author Administrator
 *
 */
public class ResolveDomain {

	 private static Logger log = LoggerFactory.getLogger(ResolveDomain.class);
	
	 public static void resolveDomain(String domain, String DNS, ConcurrentLinkedQueue<String> queue){
	        System.setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");
	        System.setProperty("sun.net.spi.nameservice.nameservers", DNS);        
	        InetAddress[] addresses;
	        try {
	            addresses = InetAddress.getAllByName(domain);    //IP or domain
	            for (int i = 0; i < addresses.length; i++) {
	                String ip = addresses[i].getHostAddress();
	                log.info(DNS + "\t" + domain + "\t" + ip);
	                queue.add(DNS + "\t" + domain + "\t" + ip);
	            } 
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }
	  }
	 public static void main(String[] args) throws UnknownHostException {
		 //resolveDomain("www.muzijiang.work", "114.114.114.114", new ConcurrentLinkedQueue<String>());
		 InetAddress[] addresses = InetAddress.getAllByName("www.muzijiang.work");
		 for(InetAddress inetAddress:addresses){
			 String ip = inetAddress.getHostAddress();
			 System.out.println(ip);
		 }
	 }
}
