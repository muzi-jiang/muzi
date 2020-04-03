package com.muzi.database01.juzimi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.muzi.communal.util.Page;
import com.muzi.database01.juzimi.entity.Juzi;
import com.muzi.database01.juzimi.mapper.JuziMapper;
import com.muzi.database01.juzimi.service.JuziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/base01/system/juzi")
public class JuziController {

    @Autowired
    private JuziService juziService;

    @Autowired
    private JuziMapper juziMapper;

    @RequestMapping("/findPages")
    @ResponseBody
    public List<Juzi> getJuziList(HttpServletRequest request){
        Integer pageNo = Integer.valueOf(request.getParameter("current"));
        Integer pageSize = Integer.valueOf(request.getParameter("size"));
        IPage<Juzi> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Juzi> wrapper = new QueryWrapper<>();
        Juzi student = new Juzi();
        wrapper.setEntity(student);
        IPage<Juzi> page1 = juziService.page(page, wrapper);
        return page1.getRecords();
    }
}
