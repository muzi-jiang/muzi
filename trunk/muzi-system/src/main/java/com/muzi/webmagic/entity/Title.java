package com.muzi.webmagic.entity;

import com.muzi.system.supperentity.DefaultIntegerEntity;

public class Title extends DefaultIntegerEntity {


     private String name;
     private String url;
     private String sequence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
