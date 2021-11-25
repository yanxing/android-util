package com.yanxing.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * target androidQ时，本地uri和file互相转化
 *
 * @author 李双祥 on 2019/10/31.
 */
public class FileUriUtil {

    /**
     * 获取图片/视频绝对路径，兼容AndroidQ
     *
     * @param type 1图片，2视频
     */
    public static String getUriToFile(final Context context, final Uri uri, int type) {
        //androidQ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getNewUriToFile(context, uri, type);
        }
        return getOldUriToFile(context, uri, type);

    }

    /**
     * AndroidQ以前版本，获取图片/视频绝对路径
     *
     * @param context
     * @param uri
     * @return 文件路径
     */
    private static String getOldUriToFile(final Context context, final Uri uri, int type) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            if (type == 1) {
                Cursor cursor = context.getContentResolver()
                        .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                    cursor.close();
                }

            } else {
                Cursor cursor = context.getContentResolver()
                        .query(uri, new String[]{MediaStore.Video.VideoColumns.DATA}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                    cursor.close();
                }
            }
        }
        return data;
    }

    /**
     * androidQ 获取图片/视频绝对路径
     *
     * @param type 1图片，2视频
     */
    @RequiresApi(api = 29)
    private static String getNewUriToFile(final Context context, final Uri uri, int type) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            if (type == 1) {
                Cursor cursor = context.getContentResolver()
                        .query(uri, new String[]{MediaStore.Images.ImageColumns.RELATIVE_PATH, MediaStore.Images.Media.DISPLAY_NAME}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH));
                    cursor.close();
                    return path + name;
                }
            } else {
                Cursor cursor = context.getContentResolver()
                        .query(uri, new String[]{MediaStore.Video.VideoColumns.RELATIVE_PATH, MediaStore.Video.Media.DISPLAY_NAME}
                                , null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.RELATIVE_PATH));
                    cursor.close();
                    return path + name;
                }
            }
        }
        return data;
    }

    /**
     * 图片/视频file转Uri，兼容AndroidQ
     *
     * @param type 1图片，2视频
     */
    public static Uri getFileToUri(Context context, File file, int type) {
        //androidQ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getNewFileToUri(context, file, type);
        }
        return getOldFileToUri(context, file, type);
    }

    /**
     * androidQ以前 file转Uri
     *
     * @param type 1图片，2视频
     */
    private static Uri getOldFileToUri(Context context, File file, int type) {
        String filePath = file.getAbsolutePath();
        if (type == 1) {
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
        } else {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Video.Media._ID},
                    MediaStore.Video.Media.DATA + "=? ",
                    new String[]{filePath}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/video/media");
                return Uri.withAppendedPath(baseUri, "" + id);
            } else {
                return Uri.parse(filePath);
            }
        }
    }

    /**
     * androidQ file转Uri
     *
     * @param type 1图片，2视频
     */
    @RequiresApi(api = 29)
    private static Uri getNewFileToUri(Context context, File file, int type) {
        if (file == null) {
            return null;
        }
        String filePath = file.getAbsolutePath();
        String path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        if (type == 1) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID},
                    MediaStore.Images.Media.DISPLAY_NAME + "=? and " + MediaStore.Images.Media.RELATIVE_PATH + "=? ",
                    new String[]{file.getName(), path}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                cursor.close();
                return Uri.withAppendedPath(baseUri, "" + id);
            } else {
                //不再对MediaStore.Images.Media.EXTERNAL_CONTENT_URI进行插入数据，再返回content://,若图片被删除，将会冗余，
                // 另外Android Q上私有目录图片不能插入到MediaStore.Images.Media.EXTERNAL_CONTENT_URI，以及沙盒外图片都需要授权，
                // 所以如果不存在此处不再转为content://
                return Uri.parse(filePath);
            }
        } else {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Video.Media._ID},
                    MediaStore.Video.Media.DISPLAY_NAME + "=? and " + MediaStore.Video.Media.RELATIVE_PATH + "=? ",
                    new String[]{file.getName(), path}, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/video/media");
                cursor.close();
                return Uri.withAppendedPath(baseUri, "" + id);
            } else {
                return Uri.parse(filePath);
            }
        }
    }

    /**
     * 转成byte流数据
     *
     * @param context
     * @param uri
     * @return
     * @throws IOException
     */
    public static byte[] getByteStreamFromUri(Context context, Uri uri) {
        try {
            if (uri == null) {
                return null;
            }
            final String scheme = uri.getScheme();
            if (scheme == null || ContentResolver.SCHEME_FILE.equals(scheme)) {
                String path = uri.getPath();
                File file = new File(path);
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] byt = new byte[fileInputStream.available()];
                fileInputStream.read(byt);
                return byt;
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                ParcelFileDescriptor parcelFileDescriptor =
                        context.getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
                byte[] byt = new byte[fileInputStream.available()];
                fileInputStream.read(byt);
                return byt;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
