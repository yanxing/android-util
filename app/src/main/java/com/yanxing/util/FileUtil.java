package com.yanxing.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 磁盘数据存储操作
 * Created by lishuangxiang on 2016/1/25.
 */
public class FileUtil {

    /**
     * 获取android文件系统，外置存储
     */
    public static final String STORAGE_PATH = Environment.getExternalStorageDirectory() + "/";
    public static final String TAG="FileUtil";

    /**
     * 获取存储根目录
     *
     * @return
     */
    public static String getStoragePath() {
        return STORAGE_PATH;
    }

    /**
     * 检查SD是否存在，存在返回true
     *
     * @return
     */
    public static boolean checkStorage() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 在创建文件
     *
     * @throws IOException
     */
    public static File createFile(String fileName){
        File file = new File(fileName);
        if (checkStorage()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.i(TAG, "外存设备不存在");
        }
        return file;
    }

    /**
     * 文件是否存在，true存在
     * @param filePath 文件路径
     * @return 存在 返回true 否则false
     */
    public static Boolean isExistFile(String filePath){
        File file = new File(filePath);
        if (checkStorage()) {//存储设备存在
            if (file.isDirectory()){//如果是目录返回
                return false;
            }
            return file.exists();
        } else {
            LogUtil.i(TAG, "外存设备不存在");
        }
        return false;
    }

    /**
     * 创建目录,如果存在，则不创建
     *
     * @param dirName  文件路径
     */
    public static File createDir(String dirName) {
        File dir = new File(dirName);
        if (checkStorage()) {//存储设备存在
            if (!dir.exists()) {
                Boolean bo=dir.mkdir();
                LogUtil.i(TAG, bo.toString());
            }
        } else {
            LogUtil.i(TAG, "外存设备不存在");
        }
        return dir;
    }

    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (checkStorage()) {//存储设备存在
            if (file.exists()) {
                file.delete();
            }
        } else {
            LogUtil.i(TAG, "外存设备不存在");
        }
    }

    /**
     * 将一个InputStream里面的数据写入到外置存储中
     *
     * @param path     路径名
     * @param fileName 文件名
     */
    public static File writeStInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            createDir(path);
            file = createFile(path + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            while ((input.read(buffer)) != -1) {
                output.write(buffer);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 重命名文件
     * @param path  文件本地路径 比如Environment.getExternalStorageDirectory() + "/123.png"
     * @param newName 文件新命名  456.png  结果Environment.getExternalStorageDirectory() + "/456.png"
     */
    public static void rename(String path,String newName){
        File file=new File(path);
        if (checkStorage()){
            if (file.exists()) {
                File file1=new File(path.substring(0,path.lastIndexOf("/")+1)+newName);
                file.renameTo(file1);
            }
        }else {
            LogUtil.i(TAG, "外存设备不存在");
        }
    }
}
