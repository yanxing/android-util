package com.yanxing.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yanxing.model.Student2;

/**
 * @author 李双祥 on 2018/3/20.
 */
@Database(entities = {Student2.class}, version = 1)
public abstract class RoomDataBaseHelper extends RoomDatabase {

    public abstract Student2Dao getStudentDao();

}
