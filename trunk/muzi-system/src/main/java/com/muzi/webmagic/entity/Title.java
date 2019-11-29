package com.muzi.webmagic.entity;

import com.muzi.system.supperentity.DefaultIntegerEntity;

public class Title extends DefaultIntegerEntity {


     private String name;
     private String href;
     private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Title(String name, String href, String imageUrl) {
        this.name = name;
        this.href = href;
        this.imageUrl = imageUrl;
    }

    public Title() {
    }
}
