package com.yanxing.util;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 磁盘数据存储操作,仅限于App沙盒内文件操作
 * 由于androidQ存储权限更改，使用SAF操作
 *
 * @see com.yanxing.util.DocumentFileUtil
 * Created by lishuangxiang on 2016/1/25.
 */
public class FileUtil {

    @Deprecated
    public static final String STORAGE_PATH = Environment.getExternalStorageDirectory() + "/";

    /**
     * androidQ沙盒内缓存路径
     */
    public static String getCachePath(Context context) {
        File file = context.getExternalCacheDir();
        return file.toString();
    }

    /**
     * androidQ沙盒内存放文件路径
     */
    public static String getFilesPath(Context context) {
        File file = context.getExternalFilesDir("");
        return file.toString();
    }

    /**
     * 沙盒内创建文件，绝对路径
     */
    public static File createFile(String fileName) {
        File file = new File(fileName);
        try {
            boolean result = file.createNewFile();
            if (result) return file;
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 沙盒内文件是否存在，true存在,绝对路径
     *
     * @param filePath 文件路径
     * @return 存在 返回true 否则false
     */
    public static Boolean isExistFile(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 沙盒创建目录,如果存在，则不创建 ,绝对路径
     *
     * @param dirName 文件路径
     */
    public static boolean createDir(String dirName) {
        File dir = new File(dirName);
        return dir.mkdir();
    }

    /**
     * 沙盒内删除文件,绝对路径
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * 将一个InputStream里面的数据写入到沙盒存储中,绝对路径
     *
     * @param path     路径名
     * @param fileName 文件名
     */
    public static boolean writeStInput(String path, String fileName, InputStream input) {
        File file;
        OutputStream output;
        try {
            createDir(path);
            file = createFile(path + fileName);
            if (file != null) {
                output = new FileOutputStream(file);
                byte[] buffer = new byte[4 * 1024];
                int offset;
                while ((offset = input.read(buffer)) != -1) {
                    output.write(buffer, 0, offset);
                }
                output.flush();
                output.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 沙盒file文件转成字节流
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
        } catch (IOException e) {
        }
        return buffer;
    }

}
