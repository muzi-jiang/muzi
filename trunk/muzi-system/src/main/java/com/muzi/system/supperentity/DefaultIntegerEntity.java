package com.muzi.system.supperentity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 * @author Administrator
 *
 */
public class DefaultIntegerEntity implements Serializable{

	private Integer id;
	//创建用户的id
	private int createUserId;
	//创建用户的姓名
	private String createUserName;
	//创建用户的时间
	private Date createTime;
	//更新用户的id
	private int updateUserId;
	//更新用户的姓名
	private String updatedUserName;
	//更新用户的时间
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
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
	public int getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(int updateUserId) {
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
