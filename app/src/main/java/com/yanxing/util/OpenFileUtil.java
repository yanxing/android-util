package com.yanxing.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 打开文件
 * @author yanxing
 */
public class OpenFileUtil {

    /**
     * 为兼容7.0 需要配置provider
     */
    private static final String AU="com.yanxing.openFile.fileProvider";
    /**
     * 查找打开word文件的intent
     * @param file 文件
     * @return
     */
    public static Intent getWordFileIntent(Context context,File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri= FileProvider.getUriForFile(context,AU,file);
        }else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * 查找打开PPT文件的intent
     * @param file
     * @return
     */
    public static Intent getPPTFileIntent(Context context,File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri= FileProvider.getUriForFile(context,AU,file);
        }else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * 查找打开excel文件的intent
     * @param file
     * @return
     */
    public static Intent getExcelFileIntent(Context context,File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri= FileProvider.getUriForFile(context,AU,file);
        }else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * 查找打开pdf文件的intent
     * @param file
     * @return
     */
    public static Intent getPdfIntent(Context context,File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri= FileProvider.getUriForFile(context,AU,file);
        }else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/pdf");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 检查文件是否是Excel文件
     * @param fileName 文件名
     * @return true 是Excel文件,false不是
     */
    public static boolean isFileExcel(String fileName){
        String fileExcel[]=new String[]{".xls",".xlsx"}; //excel文件后缀
        String suffixStr=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        for (int i=0;i<fileExcel.length;i++){
            if (suffixStr.equals(fileExcel[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查文件是否是PPT文件
     * @param fileName 文件名
     * @return true 是PPT文件,false不是
     */
    public static boolean isFilePPT(String fileName){
        String filePPT[]=new String[]{".ppt",".pptx"}; //ppt文件后缀
        String suffixStr=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        for (int i=0;i<filePPT.length;i++){
            if (suffixStr.equals(filePPT[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查文件是否是word文件
     * @param fileName 文件名
     * @return true 是word文件,false不是
     */
    public static boolean isFileWord(String fileName){
        String fileWord[]=new String[]{".doc",".docx"};//word文件后缀
        String suffixStr=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        for (int i=0;i<fileWord.length;i++){
            if (suffixStr.equals(fileWord[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Intent 是否存在 防止崩溃
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        if (intent.resolveActivity(context.getPackageManager())!=null){//存在
            return true;
        }else {
            return false;
        }
    }

    /**
     * 检查文件是否是word文件
     * @param fileName 文件名
     * @return true 是pdf文件,false不是
     */
    public static boolean isFilePdf(String fileName){
        String filePdf[]=new String[]{".pdf",".PDF"};//pdf文件后缀
        String suffixStr=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        for (int i=0;i<filePdf.length;i++){
            if (suffixStr.equals(filePdf[i])){
                return true;
            }
        }
        return false;
    }

}
