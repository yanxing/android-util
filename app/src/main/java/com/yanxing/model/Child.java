package com.yanxing.model;

/**
 * Created by lishuangxiang on 2016/7/25.
 */
public class Child {
    private boolean check;
    private String content;

    public Child(boolean check, String content) {
        this.check = check;
        this.content = content;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
