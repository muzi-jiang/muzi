package com.muzi.webmagic.process;

import com.muzi.webmagic.entity.Title;
import com.muzi.webmagic.entity.TitleClass;
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
import java.util.regex.Pattern;

@Component
public class JobProcess implements PageProcessor {

    private static final String URL = "https://www.acn86.com";

    @Autowired
    private MyPipelines myPipelines;

    private Site site = Site.me().setRetryTimes(3).setRetrySleepTime(3000);

    @Override
    public Site getSite() {
        return site;
    }

   /* @Scheduled(initialDelay =5*1000,fixedDelay = 100*1000)
    public void job(){
        Spider.create(new JobProcess())
                .addUrl(URL+"/meinv/index.html")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)))
                .thread(10)
                .addPipeline(this.myPipelines)
                .run();

    }*/
    String pattern = "^.*(\\期)$";

    @Override
    public void process(Page page) {

        List<Title> titles = new ArrayList<>();
        List<TitleClass> titleClasses = new ArrayList<>();

        Html html = page.getHtml();
        List<Selectable> nodes = html.css("div#tpl-img-content li a").nodes();

        //判斷是數據是否為空
        if(nodes != null && nodes.size() >0){
            //表示這是列表頁面
            for (Selectable selectable:nodes){

                String name = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "title");
                if(Pattern.matches(pattern,name)){
                    //表示是class
                    //获取当前主题
                    String topic = html.css("span.cat_pos_l a").nodes().get(2).css("a", "text").toString();
                    String href = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "href");
                    page.addTargetRequest(URL+href);
                    TitleClass titleClass = saveClass(selectable,topic);
                    titleClasses.add(titleClass);
                }else{
                    //表示是title
                    String href = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "href");
                    page.addTargetRequest(URL+href);
                    Title title = saveTitle(selectable);
                    titles.add(title);
                }
                //保存
            }
            page.putField("titles",titles);
            page.putField("titleClasses",titleClasses);
        }else{
            //表示這是詳情頁面
            //saveClass(page);
        }
    }


    /**
     * 保存title
     * @param selectable
     * @return
     */
    public Title saveTitle(Selectable selectable){
        System.out.println(selectable);
        String name = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "title");

        String href = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "href");

        Selectable img = selectable.css("img");
        String image = WebmagicUtils.getValueByKeyInHtml(img.toString(), "data-original");
        //将图片下载到本地
        String newImage = WebmagicUtils.downloadImage(image);

        Title title = new Title(name,href,newImage);
        return title;
    }

    /**
     * 保存class分類信息
     * @param selectable
     */
    public TitleClass saveClass(Selectable selectable,String topic){
        System.out.println(selectable);
        String name = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "title");
        String href = WebmagicUtils.getValueByKeyInHtml(selectable.toString(), "href");
        Selectable img = selectable.css("img");
        String image = WebmagicUtils.getValueByKeyInHtml(img.toString(), "data-original");
        //将图片下载到本地
        String newImage = WebmagicUtils.downloadImageNewName(image,name,topic);

        //time
        String time = selectable.css("span", "text").toString();
        TitleClass titleClass = new TitleClass(name,time,href,newImage);
        return titleClass;
    }

}
