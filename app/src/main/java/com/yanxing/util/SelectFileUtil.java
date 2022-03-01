package com.yanxing.util;

import android.content.Intent;

/**
 * 选择文件，使用系统文件管理器
 * Created by 李双祥 on 2022/3/1.
 */
public class SelectFileUtil {

    /**
     * 调用系统文件管理器，选择文件
     *
     * @param mimeTypes 文件类型
     */
    public static Intent openSelectFile(String[] mimeTypes) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        return intent;
    }


    /**
     * 调用系统文件管理器，选择文件
     */
    public static Intent openSelectFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        return intent;
    }

    public static class MimeType {
        public static final String DOC = "application/msword";
        public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        public static final String XLS = "application/vnd.ms-excel application/x-excel";
        public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        public static final String PPT = "application/vnd.ms-powerpoint";
        public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        public static final String PDF = "application/pdf";
    }
}
