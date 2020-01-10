package com.muzi.database01.juzimi.pipeline;

import com.muzi.database01.juzimi.entity.Juzi;
import com.muzi.database01.juzimi.service.JuziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class MyPipelines implements Pipeline {

    @Autowired
    private JuziService juziService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Juzi> juzis = (List<Juzi>)resultItems.get("juzis");
        juzis.forEach( juzi -> {
            juziService.save(juzi);
        });
    }
}
