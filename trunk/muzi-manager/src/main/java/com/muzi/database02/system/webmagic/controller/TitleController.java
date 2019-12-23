package com.muzi.database02.system.webmagic.controller;


import com.muzi.database02.system.webmagic.entity.Title;
import com.muzi.database02.system.webmagic.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libin
 * @since 2019-12-20
 */
@Controller
@RequestMapping("/title")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @RequestMapping("/test")
    @ResponseBody
    public  Title test() {
        Title title = titleService.getById(414);
        return title;
    }
}
