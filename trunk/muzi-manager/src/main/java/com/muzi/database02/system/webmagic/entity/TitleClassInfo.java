package com.muzi.database02.system.webmagic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author libin
 * @since 2019-12-20
 */
@TableName("t_title_class_info")
public class TitleClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String imageUrl;

    private String partSequence;

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
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getPartSequence() {
        return partSequence;
    }

    public void setPartSequence(String partSequence) {
        this.partSequence = partSequence;
    }

    @Override
    public String toString() {
        return "TitleClassInfo{" +
            "id=" + id +
            ", name=" + name +
            ", imageUrl=" + imageUrl +
            ", partSequence=" + partSequence +
        "}";
    }
}
