package com.muzi.app.juzi.entity;

import com.muzi.system.supperentity.DefaultStringEntity;

/*
import org.springframework.data.mongodb.core.mapping.Document;

import com.muzi.system.supperentity.DefaultStringEntity;

@Document(collection="muzi_user")*/
public class User extends DefaultStringEntity{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;
	
	private String nick;
	
	/**
	 * 用户头像
	 */
	private String inage;
	
	private int age;
	
	private int sex;
	
	private String tel;
	
	private String address;
	
	/**
	 * 判断是空间是否开放   0:不开放  1：开放  2：只对好友开发
	 */
	private int visit;
	
	/**
	 * 用户硬币数量	
	 */
	private int coinnum;
	
	
	private int status;


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


	public String getNick() {
		return nick;
	}


	public void setNick(String nick) {
		this.nick = nick;
	}


	public String getInage() {
		return inage;
	}


	public void setInage(String inage) {
		this.inage = inage;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getVisit() {
		return visit;
	}


	public void setVisit(int visit) {
		this.visit = visit;
	}


	public int getCoinnum() {
		return coinnum;
	}


	public void setCoinnum(int coinnum) {
		this.coinnum = coinnum;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", nick=" + nick + ", inage=" + inage
				+ ", age=" + age + ", sex=" + sex + ", tel=" + tel + ", address=" + address + ", visit=" + visit
				+ ", coinnum=" + coinnum + ", status=" + status + ", getUsername()=" + getUsername()
				+ ", getPassword()=" + getPassword() + ", getNick()=" + getNick() + ", getInage()=" + getInage()
				+ ", getAge()=" + getAge() + ", getSex()=" + getSex() + ", getTel()=" + getTel() + ", getAddress()="
				+ getAddress() + ", getVisit()=" + getVisit() + ", getCoinnum()=" + getCoinnum() + ", getStatus()="
				+ getStatus() + ", getId()=" + getId() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateUserName()=" + getCreateUserName() + ", getCreateTime()=" + getCreateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdatedUserName()=" + getUpdatedUserName()
				+ ", getUpdateTime()=" + getUpdateTime() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	

}
