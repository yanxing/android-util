package com.yanxing.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author 李双祥 on 2018/3/20.
 */
@Entity(tableName = "tb_student",indices = @Index(value = {"name"}))
public class Student2 {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String sex;

    @ColumnInfo
    private String birth;

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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
