package com.yanxing.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.documentfile.provider.DocumentFile;

import java.io.FileOutputStream;

/**
 * 针对AndroidQ文件操作
 *
 * @author 李双祥 on 2019/10/30.
 */
public class DocumentFileUtil {

    /**
     * 创建目录
     *
     * @param context
     * @param intent
     * @param dirName 目录名  目录名 "test/test"二级目录，比如"test"一次目录
     * @return
     */
    public static boolean createDir(Context context, Intent intent, String dirName) {
        try {
            Uri treeUri = intent.getData();
            final int takeFlags = intent.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);
            DocumentFile root = DocumentFile.fromTreeUri(context, treeUri);
            if (dirName.contains("/")) {
                DocumentFile rootTemp = root;
                String[] temp = dirName.split("/");
                for (String s : temp) {
                    root = rootTemp.findFile(s);
                    if (root == null) {
                        root = rootTemp.createDirectory(s);
                        if (root == null) {
                            return false;
                        }
                    }
                    rootTemp = root;
                }
            } else {
                root = root.findFile(dirName);
                //没有这个目录
                if (root == null) {
                    return root.createDirectory(dirName) != null;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @param context
     * @param intent
     * @param dirName  目录名 "test/test"，"test"
     * @param mime     文件类型
     * @param fileName 文件名
     * @return
     */
    public static boolean createFile(Context context, Intent intent, String dirName, String mime, String fileName) {
        try {
            Uri treeUri = intent.getData();
            final int takeFlags = intent.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);
            DocumentFile root = DocumentFile.fromTreeUri(context, treeUri);
            if (dirName.contains("/")) {
                DocumentFile rootTemp = root;
                String[] temp = dirName.split("/");
                for (String s : temp) {
                    root = rootTemp.findFile(s);
                    if (root == null) {
                        root = rootTemp.createDirectory(s);
                        if (root == null) {
                            return false;
                        }
                    }
                    rootTemp = root;
                }
                if (rootTemp.findFile(fileName) == null) {
                    return rootTemp.createFile(mime, fileName) != null;
                }
            } else {
                root = root.findFile(dirName);
                //没有这个目录
                if (root == null) {
                    root = root.createDirectory(dirName);
                }
                if (root != null) {
                    if (root.findFile(fileName) == null) {
                        return root.createFile(mime, fileName) != null;
                    }
                } else {
                    //目录没有创建成功
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 查找文件
     *
     * @param context
     * @param intent
     * @param fileName 文件名"test/test.txt
     * @return
     */
    public static DocumentFile findFile(Context context, Intent intent, String fileName) {
        try {
            Uri treeUri = intent.getData();
            final int takeFlags = intent.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);
            DocumentFile root = DocumentFile.fromTreeUri(context, treeUri);
            if (fileName.contains("/")) {
                DocumentFile rootTemp = root;
                String[] temp = fileName.split("/");
                for (int i = 0; i < temp.length; i++) {
                    root = rootTemp.findFile(temp[i]);
                    if (root == null) {
                        return null;
                    }
                    if (i == temp.length - 1) {
                        return root;
                    }
                    rootTemp = root;
                }
                return null;
            } else {
                return root.findFile(fileName);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除文件
     *
     * @param context
     * @param intent
     * @param fileName 文件名"test/test.txt"删除的是test.txt，"test"删除文件夹
     * @return
     */
    public static boolean deleteFile(Context context, Intent intent, String fileName) {
        try {
            Uri treeUri = intent.getData();
            final int takeFlags = intent.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.getContentResolver().takePersistableUriPermission(treeUri, takeFlags);
            DocumentFile root = DocumentFile.fromTreeUri(context, treeUri);
            if (fileName.contains("/")) {
                DocumentFile rootTemp = root;
                String[] temp = fileName.split("/");
                for (int i=0;i<temp.length;i++) {
                    root = rootTemp.findFile(temp[i]);
                    if (root == null) {
                        return false;
                    }
                    if (i==temp.length-1){
                        return root.delete();
                    }
                    rootTemp = root;
                }
            } else {
                root = root.findFile(fileName);
                if (root != null) {
                    return root.delete();
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 一次性写文件
     *
     * @param context
     * @param uri     DocumentFile.getUri()
     * @param buf
     * @param off
     * @param len
     */
    public static void writeFile(Context context, Uri uri, byte[] buf, int off, int len) {
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(buf, off, len);
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }

    /**
     * 一次性写文件
     *
     * @param context
     * @param uri
     * @param buf
     */
    public static void writeFile(Context context, Uri uri, byte[] buf) {
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(buf);
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }

}
