package com.muzi.test.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.List;

public class IDCardUtils implements PageProcessor {

    public static void main(String[] args) throws IOException {
       /* HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://qq.ip138.com/idsearch/index.asp?userid=513701199610273312&action=idcard");
        get.setHeader("Content-Type", "application/json");
        HttpResponse rese = client.execute(get);
        String redsa = EntityUtils.toString(rese.getEntity(),"UTF-8");
        System.out.println(redsa);*/
      /*  int i = 0;
        while (true){
            Spider.create(new IDCardUtils()).addUrl("http://qq.ip138.com/idsearch/index.asp?userid=51370119961027331"+i+"&action=idcard").run();
            i++;
        }
*/


    }


    private Site site = Site.me();

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Selectable selectable = null;
        try{
             selectable = html.css("div.bd").nodes().get(1);
        }catch (Exception e){
            return;
        }
        List<Selectable> nodes = selectable.css("table tbody tr td").nodes();
        for (Selectable node : nodes) {
            System.out.println(node.css("p","text").toString());
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
