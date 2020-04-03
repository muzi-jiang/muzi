package com.muzi.database01.juzimi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.muzi.communal.baseentity.DefaultIPage;
import com.muzi.communal.util.Page;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author libin
 * @since 2020-01-10
 */
@TableName("t_juzi")
public class Juzi /*extends Page*/ {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String text;

    private String author;

    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Juzi{" +
            "id=" + id +
            ", text=" + text +
            ", author=" + author +
            ", source=" + source +
        "}";
    }

    public Juzi(String text, String author, String source) {
        this.text = text;
        this.author = author;
        this.source = source;
    }

    public Juzi() {
    }
}
