package com.muzi.system.supperentity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 * @author Administrator
 *
 */
public class DefaultStringEntity implements Serializable{

	private String id;
	//创建用户的id
	private String createUserId;
	//创建用户的姓名
	private String createUserName;
	//创建用户的时间
	private Date createTime;
	//更新用户的id
	private String updateUserId;
	//更新用户的姓名
	private String updatedUserName;
	//更新用户的时间
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
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
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdatedUserName() {
		return updatedUserName;
	}
	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "EntityParent [id=" + id + ", createUserId=" + createUserId + ", createUserName=" + createUserName
				+ ", createTime=" + createTime + ", updateUserId=" + updateUserId + ", updatedUserName="
				+ updatedUserName + ", updateTime=" + updateTime + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
}
