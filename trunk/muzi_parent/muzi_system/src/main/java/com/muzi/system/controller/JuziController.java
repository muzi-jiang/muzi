package com.muzi.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.muzi.system.entity.Juzi;
import com.muzi.system.service.JuziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/juzi")
public class JuziController {

    @Autowired
    private JuziService juziService;

    @RequestMapping("/findPages")
    @ResponseBody
    public IPage getJuziList(Juzi juzi){
        QueryWrapper<Juzi> query = new QueryWrapper<>();
        query.eq("author","大老师");
        IPage page = juziService.page(juzi, query);
        return page;
    }
}
