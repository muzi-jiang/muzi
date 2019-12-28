package com.muzi.database02.system.webmagic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muzi.database02.system.webmagic.entity.Title;
import com.muzi.database02.system.webmagic.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libin
 * @since 2019-12-20
 */
@Controller
@RequestMapping("/base02/system/manager/webmagic")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @RequestMapping("/titleList")
    @ResponseBody
    public  IPage test(Title title) {
        Map map = new HashMap();
        IPage<Title> page = new Page<>(1, 10);
        IPage page1 = titleService.page(page, new QueryWrapper<>().allEq(map));
        List records = page1.getRecords();
        return page1;
    }
}
