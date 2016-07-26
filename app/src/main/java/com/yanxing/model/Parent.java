package com.yanxing.model;

import java.util.List;

/**
 * Created by lishuangxiang on 2016/7/25.
 */
public class Parent {
    private boolean check;
    private String content;

    private List<Child> childList;

    public Parent(boolean check, String content, List<Child> childList) {
        this.check = check;
        this.content = content;
        this.childList = childList;
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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
