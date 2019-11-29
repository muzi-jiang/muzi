package com.muzi.webmagic.process;

import com.muzi.webmagic.entity.Title;
import com.muzi.webmagic.pipelines.MyPipelines;
import com.muzi.webmagic.util.WebmagicUtils;
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
        List<Selectable> nodes = html.css("div#tpl-img-content li a").nodes();
        for (Selectable selectable:nodes){

            System.out.println(selectable);
            String name = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "title");

            String href = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "href");

            Selectable img = selectable.css("img");
            String image = WebmagicUtils.getValueByKeyInHtml(img.toString(), "data-original");
            //将图片下载到本地
            String newImage = WebmagicUtils.downloadImage(image);

            Title title = new Title(name,href,newImage);
            titles.add(title);
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
                .addUrl("https://www.69aup.com/meinv/index.html")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)))
                .thread(10)
                .addPipeline(this.myPipelines)
                .run();

    }
}
