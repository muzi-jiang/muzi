package com.muzi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
@TableName("t_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String permissionName;

    //绑定的路由组件名称，固定不动
    private String component;

    private String permissionUrl;

    private String permissionIcon;

    private Integer permissionParentid;

    private String status;

    private Integer createUserId;

    private String createUserName;

    private Date createTime;

    private Integer updateUserId;

    private String updateUserName;

    private Date updateTime;

    @TableField(exist = false)
    private List<Permission> children;

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }
    public String getPermissionIcon() {
        return permissionIcon;
    }

    public void setPermissionIcon(String permissionIcon) {
        this.permissionIcon = permissionIcon;
    }
    public Integer getPermissionParentid() {
        return permissionParentid;
    }

    public void setPermissionParentid(Integer permissionParentid) {
        this.permissionParentid = permissionParentid;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "id=" + id +
            ", permissionName=" + permissionName +
            ", permissionUrl=" + permissionUrl +
            ", permissionIcon=" + permissionIcon +
            ", permissionParentid=" + permissionParentid +
            ", status=" + status +
            ", createUserId=" + createUserId +
            ", createUserName=" + createUserName +
            ", createTime=" + createTime +
            ", updateUserId=" + updateUserId +
            ", updateUserName=" + updateUserName +
            ", updateTime=" + updateTime +
        "}";
    }
}
