package com.muzi.webmagic.process;

import com.muzi.webmagic.entity.Title;
import com.muzi.webmagic.pipelines.MyPipelines;
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

import java.util.ArrayList;
import java.util.List;

@Component
public class JobProcess implements PageProcessor {

    @Autowired
    private MyPipelines myPipelines;

    private Site site = Site.me().setRetryTimes(3).setRetrySleepTime(3000);

    @Override
    public void process(Page page) {
        List<Title> titles = new ArrayList<>();
        Html html = page.getHtml();
        List<Selectable> nodes = html.css("div#primary_menu > ul.nav-menu > li").nodes();
        for (Selectable selectable:nodes){
            String url = selectable.css("a").links().toString();
            String title = selectable.css("a div.nav-name", "text").toString();
            if (title == null){
                continue;
            }
            Title title1 = new Title();

            title1.setName(title);
            title1.setUrl(url);
            titles.add(title1);
        }
        page.putField("titles",titles);
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Scheduled(initialDelay =5*1000,fixedDelay = 100*1000)
    public void job(){
        Spider.create(new JobProcess())
                .addUrl("https://www.bilibili.com")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)))
                .thread(10)
                .addPipeline(this.myPipelines)
                .run();

    }
}
