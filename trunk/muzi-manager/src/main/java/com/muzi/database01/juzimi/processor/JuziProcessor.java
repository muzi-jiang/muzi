package com.muzi.database01.juzimi.processor;


import com.muzi.database01.juzimi.Constants;
import com.muzi.database01.juzimi.entity.Juzi;
import com.muzi.database01.juzimi.pipeline.MyPipelines;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class JuziProcessor implements PageProcessor {

    private Site site =Site.me().setRetrySleepTime(3000).setRetryTimes(3);

    @Autowired
    private MyPipelines myPipelines;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        List<Selectable> nodes = html.css("div#content div").nodes();
        Map<Boolean, List<Object>> collect = IntStream.rangeClosed(1, nodes.size()).boxed().map(i -> new AbstractMap.SimpleEntry(i, nodes.get(i - 1)))
                  .collect(Collectors.partitioningBy(simpleEntry -> Integer.parseInt(simpleEntry.getKey().toString()) % 2 == 0,
                            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        List<Object> objects = collect.get(Boolean.TRUE);

        List<Juzi> juzis = new LinkedList<>();
        objects.forEach( obj -> {
            Selectable selectable  = (Selectable) obj;
            String text = selectable.css("div", "text").toString();
            text = text.substring(text.indexOf("：") +1);
            System.out.println(text);
            if(StringUtils.isNotBlank(text)){
                //截取内容
                String info = text.substring(0, text.contains("--") ? text.lastIndexOf("--") : text.lastIndexOf("《"));
                //截取作者
                String author = text.contains("--") ? text.substring(text.lastIndexOf("--") +2,text.lastIndexOf("《")) : "";
                //System.out.println(author);
                //截取书名
                String source = text.substring(text.lastIndexOf("《")+1,text.lastIndexOf("》"));

                Juzi juzi = new Juzi(info,author,source);
                juzis.add(juzi);
            }
        });
        page.putField("juzis",juzis);
    }

    @Override
    public Site getSite() {
        return site;
    }

//    @Scheduled(initialDelay =5*1000,fixedDelay = 100*1000)
//    public void job(){
//        Spider.create(new JuziProcessor())
//                  .addUrl(Constants.JUZI_URL)
//                  .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)))
//                  .thread(10)
//                  .addPipeline(this.myPipelines)
//                  .run();
//    }
}
