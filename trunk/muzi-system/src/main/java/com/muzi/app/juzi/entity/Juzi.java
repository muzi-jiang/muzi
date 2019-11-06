package com.muzi.app.juzi.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.muzi.system.supperentity.DefaultStringEntity;

@Document(collection="muzi_juzi")
public class Juzi extends DefaultStringEntity{

	/**
	 *   版本号
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId;
	
	private String text;
	
	private String author;
	
	private String source;
	
	private int original;  //是否是原创
	
	private int status;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public int getOriginal() {
		return original;
	}

	public void setOriginal(int original) {
		this.original = original;
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
		return "Juzi [userId=" + userId + ", text=" + text + ", author=" + author + ", source=" + source + ", original="
				+ original + ", status=" + status + ", getUserId()=" + getUserId() + ", getText()=" + getText()
				+ ", getAuthor()=" + getAuthor() + ", getSource()=" + getSource() + ", getOriginal()=" + getOriginal()
				+ ", getStatus()=" + getStatus() + ", getId()=" + getId() + ", getCreateUserId()=" + getCreateUserId()
				+ ", getCreateUserName()=" + getCreateUserName() + ", getCreateTime()=" + getCreateTime()
				+ ", getUpdateUserId()=" + getUpdateUserId() + ", getUpdatedUserName()=" + getUpdatedUserName()
				+ ", getUpdateTime()=" + getUpdateTime() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
	

}
