package com.muzi.communal.util;

public class ReturnMessage {
    private int resultCode = 1;
    private String message;
    private Object object;

    public ReturnMessage() {
        this.resultCode = 1;
    }

    public ReturnMessage(int resultCode) {
        this.resultCode = resultCode;
    }

    public ReturnMessage(int resultCode, Object object) {
        this.resultCode = resultCode;
        this.object = object;
    }

    public ReturnMessage(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public ReturnMessage(int resultCode, String message, Object object) {
        this.resultCode = resultCode;
        this.message = message;
        this.object = object;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static class ResultCode {
        public static final int OK = 1;
        public static final int ERROR = 0;
        public static final int OTHER = -1;
        public static final int AUTHENTICATION = -9999;

        private ResultCode() {
        }
    }
}
