package com.example.myapplication;

public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;
    private String access_token;

    public BaseResponse() {
    }

    public BaseResponse(String status, String message, T data, String access_token) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.access_token = access_token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
