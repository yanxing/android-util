package com.yanxing.networklibrary.model;

/**
 * 方便响应成功和提示统一处理
 * Created by 李双祥 on 2017/5/23.
 */
public class BaseModel {

    /**
     * 返回状态
     */
    protected String status;
    /**
     * 返回说明
     */
    protected String message;
    /**
     * 存放错误信息字段
     */
    protected String operation;

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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
