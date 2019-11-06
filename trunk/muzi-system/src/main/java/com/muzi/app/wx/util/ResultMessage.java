package com.muzi.app.wx.util;

import java.io.Serializable;

public class ResultMessage implements Serializable{

	private Boolean result;
    private String message;
    private Object object;

    public ResultMessage(){
        result = false;
        message="";
        object = null;
    }
     public ResultMessage(Boolean result,String message){
        this.result = result;
        this.message = message;
        this.object="";
    }

    public ResultMessage(Boolean result,String message,Object object){
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
