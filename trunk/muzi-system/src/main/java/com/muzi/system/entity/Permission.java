package com.muzi.system.entity;

import com.muzi.system.supperentity.DefaultIntegerEntity;

/**
 * 权限表
 * @author Administrator
 *
 */
public class Permission extends DefaultIntegerEntity{
	
	private String permissionName;
	
	private String permissionUrl;
	
	private String permissionIcon;
	
	private Integer permissionParentid;  //父级菜单名称
	
	private String status;    //用户状态  0：异常   1：正常

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


	public String getPermissionIcon() {
		return permissionIcon;
	}

	public void setPermissionIcon(String permissionIcon) {
		this.permissionIcon = permissionIcon;
	}

	public Permission(String permissionName, String permissionUrl, String permissionIcon, Integer permissionParentid,
			String status) {
		super();
		this.permissionName = permissionName;
		this.permissionUrl = permissionUrl;
		this.permissionIcon = permissionIcon;
		this.permissionParentid = permissionParentid;
		this.status = status;
	}

	public Permission() {
		super();
	}

	@Override
	public String toString() {
		return "Permission [permissionName=" + permissionName + ", permissionUrl=" + permissionUrl + ", permissionIcon="
				+ permissionIcon + ", permissionParentid=" + permissionParentid + ", status=" + status + ", getId()="
				+ getId() + ", getCreateUserId()=" + getCreateUserId() + ", getCreateUserName()=" + getCreateUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateUserId()=" + getUpdateUserId()
				+ ", getUpdatedUserName()=" + getUpdatedUserName() + ", getUpdateTime()=" + getUpdateTime()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

	
	

}
