package com.yanxing.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yanxing.model.Student2;

import java.util.List;

/**
 * @author 李双祥 on 2018/3/20.
 */
@Dao
public interface Student2Dao {

    /**
     *
     * @return
     */
    @Query("select * from tb_student")
    List<Student2> findAll();

    @Insert
    void insert(Student2... student2s);

    @Delete
    void delete(Student2 student2);

    @Update
    void update(Student2 student2);
}
