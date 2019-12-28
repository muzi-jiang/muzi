package com.muzi.database02.system.webmagic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.muzi.communal.util.Page;


/**
 * <p>
 * 
 * </p>
 *
 * @author libin
 * @since 2019-12-20
 */
@TableName("t_title")
public class Title extends Page {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String href;

    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
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

    @Override
    public String toString() {
        return "Title{" +
            "id=" + id +
            ", name=" + name +
            ", href=" + href +
            ", imageUrl=" + imageUrl +
        "}";
    }
}
