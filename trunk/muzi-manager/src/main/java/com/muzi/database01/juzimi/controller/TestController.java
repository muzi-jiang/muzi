package com.muzi.database01.juzimi.controller;

import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author: libin
 * @Date: 2020/10/28 17:44
 */

@Controller
@RequestMapping("/base01/system/test")
public class TestController {

    @RequestMapping("/test001")
    @ResponseBody
    public void test001(Map<String,Object> map){
        System.out.println(map.toString());
        System.out.println(map);
    }

    @RequestMapping("/test002")
    @ResponseBody
    public void test002(JSONObject jsonObject){
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject);
    }

}
