package com.muzi.system.entity;

import java.util.List;

public class MenuTree {

    private String id;
    private String icon;
    private String index;
    private String title;
    private List<MenuTree> subs;
    private String parentFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MenuTree> getSubs() {
        return subs;
    }

    public void setSubs(List<MenuTree> subs) {
        this.subs = subs;
    }

    public String getParentFlag() {
        return parentFlag;
    }

    public void setParentFlag(String parentFlag) {
        this.parentFlag = parentFlag;
    }

    public MenuTree(String id, String icon, String index, String title, String parentFlag) {
        this.id = id;
        this.icon = icon;
        this.index = index;
        this.title = title;
        this.parentFlag = parentFlag;
    }

    public MenuTree() {
    }
}
