package com.muzi.system.entity;

import java.util.List;

import com.muzi.system.supperentity.DefaultIntegerEntity;

/**
 * 用户类
 * @author Administrator
 *
 */
public class User extends DefaultIntegerEntity{

	private String userName;
	private String passWord;
	private String name;
	private String tel;
	private String sex;     // 1:男   2：女
	private String status;   //用户状态    1：正常    0：异常 
	
	private List<String> roleIds;
	
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
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
	@Override
	public String toString() {
		return "User [userName=" + userName + ", passWord=" + passWord + ", name=" + name + ", tel=" + tel + ", sex="
				+ sex + ", status=" + status + ", roleIds=" + roleIds + "]";
	}
	
	
	
}
