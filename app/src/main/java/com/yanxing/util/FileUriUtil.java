package com.yanxing.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.io.File;

/**
 * target androidQ时，本地文件uri和file互相转化
 * @author 李双祥 on 2019/10/31.
 */
public class FileUriUtil {

    /**
     * uri转file
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePath(final Context context, final Uri uri) {
        if (Build.VERSION.SDK_INT >= 29) {//androidQ
            return getAndroidQRealFilePath(context, uri);
        }
        return getRealFilePath(context, uri);

    }

    /**
     * AndroidQ以前版本，URI转化文件路径
     *
     * @param context
     * @param uri
     * @return 文件路径
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver()
                    .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * AndroidQ URI转化文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    @RequiresApi(api = 29)
    public static String getAndroidQRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver()
                    .query(uri, new String[]{MediaStore.Images.ImageColumns.RELATIVE_PATH, MediaStore.Images.Media.DISPLAY_NAME}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH));
                    return path + name;
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * file转Uri，针对android Q存储权限改动，使用uri访问文件
     *
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        if (Build.VERSION.SDK_INT >= 29) {//androidQ
            return getAndroidQContentUri(context, imageFile);
        }
        return getContentUri(context, imageFile);
    }

    /**
     * androidQ以前 file转Uri
     *
     * @param context
     * @param imageFile
     * @return
     */
    private static Uri getContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            //不再对MediaStore.Images.Media.EXTERNAL_CONTENT_URI进行插入数据，再返回content://,若图片被删除，将会冗余
            // 另外Android Q上私有目录图片不能插入到MediaStore.Images.Media.EXTERNAL_CONTENT_URI，以及沙盒外图片都需要授权，
            // 所以如果不存在此处不再转为content://
            return Uri.parse(filePath);
        }
    }

    /**
     * androidQ file转Uri
     *
     * @param context
     * @param imageFile
     * @return
     */

    @RequiresApi(api = 29)
    private static Uri getAndroidQContentUri(Context context, File imageFile) {
        if (imageFile == null) {
            return null;
        }
        String filePath = imageFile.getAbsolutePath();
        String path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DISPLAY_NAME + "=? and " + MediaStore.Images.Media.RELATIVE_PATH + "=? ",
                new String[]{imageFile.getName(), path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            //不再对MediaStore.Images.Media.EXTERNAL_CONTENT_URI进行插入数据，再返回content://,若图片被删除，将会冗余，
            // 另外Android Q上私有目录图片不能插入到MediaStore.Images.Media.EXTERNAL_CONTENT_URI，以及沙盒外图片都需要授权，
            // 所以如果不存在此处不再转为content://
            return Uri.parse(filePath);
        }
    }

}
