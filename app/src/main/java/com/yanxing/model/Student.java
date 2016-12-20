package com.yanxing.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Student {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String sex;
    private String birth;

    @Generated(hash = 1119978323)
    public Student(Long id, String name, String sex, String birth) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Student(String name, String sex, String birth) {
        this.name = name;
        this.sex = sex;
        this.birth = birth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

}
