package com.muzi.database01.juzimi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.muzi.database01.juzimi.entity.Juzi;
import com.muzi.database01.juzimi.service.JuziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/base01/system/juzi")
public class JuziController {

    @Autowired
    private JuziService juziService;

    @RequestMapping("/list")
    @ResponseBody
    public List<Juzi> getJuziList(Juzi juzi){
        IPage page = juziService.page(juzi, new QueryWrapper<Juzi>());
        List<Juzi> records = page.getRecords();
        return records;
    }
}
