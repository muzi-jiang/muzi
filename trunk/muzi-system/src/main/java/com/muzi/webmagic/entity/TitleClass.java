package com.muzi.webmagic.entity;

import com.muzi.system.supperentity.DefaultIntegerEntity;

public class TitleClass  extends DefaultIntegerEntity {

    private String name;
    private String time;
    private String url;
    private String sequence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public TitleClass() {
    }

    public TitleClass(String name, String time, String url, String sequence) {
        this.name = name;
        this.time = time;
        this.url = url;
        this.sequence = sequence;
    }
}
