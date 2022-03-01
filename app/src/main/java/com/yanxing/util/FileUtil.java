package com.yanxing.util;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 磁盘数据存储操作
 * 由于androidQ存储权限更改，使用SAF操作
 * @see com.yanxing.util.DocumentFileUtil
 * Created by lishuangxiang on 2016/1/25.
 */
public class FileUtil {

    /**
     * 获取android文件系统，外置存储
     */
    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory() + "/";
    private static final String TAG = "FileUtil";

    /**
     * 获取存储根目录
     *
     * @return
     */
    public static String getStoragePath() {
        return STORAGE_PATH;
    }

    /**
     * androidQ沙盒内缓存路径
     * @param context
     * @return
     */
    public static String getCachePath(Context context){
        File file=context.getExternalCacheDir();
        return file.toString();
    }

    /**
     * androidQ沙盒内存放文件路径
     * @param context
     * @return
     */
    public static String getFilesPath(Context context){
        File file=context.getExternalFilesDir("");
        return file.toString();
    }

    /**
     * 在创建文件，绝对路径
     *
     * @throws IOException
     */
    public static File createFile(String fileName) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
        }
        return file;
    }

    /**
     * 文件是否存在，true存在,绝对路径
     *
     * @param filePath 文件路径
     * @return 存在 返回true 否则false
     */
    public static Boolean isExistFile(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 创建目录,如果存在，则不创建 ,绝对路径
     *
     * @param dirName 文件路径
     */
    public static File createDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 根据路径删除图片,绝对路径
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    /**
     * 将一个InputStream里面的数据写入到外置存储中,绝对路径
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
            int offset;
            while ((offset = input.read(buffer)) != -1) {
                output.write(buffer, 0, offset);
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
     * file文件转成字节流
     *
     * @param filePath
     * @return
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 重命名文件
     *
     * @param path    文件本地路径 比如Environment.getExternalStorageDirectory() + "/123.png"
     * @param newName 文件新命名  456.png  结果Environment.getExternalStorageDirectory() + "/456.png"
     */
    public static void rename(String path, String newName) {
        File file = new File(path);
        if (file.exists()) {
            File file1 = new File(path.substring(0, path.lastIndexOf("/") + 1) + newName);
            file.renameTo(file1);
        }
    }
}
