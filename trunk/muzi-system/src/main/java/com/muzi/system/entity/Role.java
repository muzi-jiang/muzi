package com.muzi.system.entity;

import java.util.List;

import com.muzi.system.supperentity.DefaultIntegerEntity;

public class Role extends DefaultIntegerEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rolename;
	
	private String remarks;   //备注
	
	private String status;

	//当前角色拥有所有的菜单id
	private List<String> menuIds;
	
	public List<String> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Role [rolename=" + rolename + ", remarks=" + remarks + ", status=" + status + "]";
	}
	
	
}
