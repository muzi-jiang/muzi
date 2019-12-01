package com.muzi.webmagic.pipelines;

import com.muzi.webmagic.entity.Title;
import com.muzi.webmagic.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class MyPipelines implements Pipeline {

    @Autowired
    private TitleService titleService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //保存title
        List<Title> titles = resultItems.get("titles");
        if(titles != null && titles.size() > 0){
            titles.forEach(title -> {
                titleService.saveTitle(title);
            });
        }

        //保存class
    }
}
