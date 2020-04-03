package com.muzi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
/*@ServletComponentScan  //注册过滤器注解*/
@MapperScan("com.muzi.**.mapper")
public class MuziSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuziSystemApplication.class, args);
    }

}
