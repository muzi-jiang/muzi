package com.muzi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.muzi.communal.baseentity.DefaultIntegerEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
@TableName("t_user")
public class User extends DefaultIntegerEntity {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String name;

    private String tel;

    private String sex;

    private String status;

    private String headportrait;

    public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public User(String username, String password, String name, String tel, String sex, String status, String headportrait) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.sex = sex;
        this.status = status;
        this.headportrait = headportrait;
    }

    public User(Integer id,String username, String password, String name, String tel, String sex, String status, String headportrait) {
        setId(id);
        this.username = username;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.sex = sex;
        this.status = status;
        this.headportrait = headportrait;
    }

    public User() {
    }
}
