package com.yanxing.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 文件上传进度，file形式
 * @author 李双祥 on 2020/12/4.
 */
public class UploadFileRequestBody2 extends RequestBody {

    public interface OnProgressListener{
        /**
         * 进度回调
         * @param tag 标识
         * @param contentLength 文件总长度
         * @param uploadLength 已经上传的字节大小
         * @param progress  当前进度 100代表完成
         */
        void onProgress(String tag, long contentLength, long uploadLength, int progress);
    }

    private OnProgressListener mOnProgressListener;
    private File mFile;
    private MediaType mContentType;
    private String mTag;


    public UploadFileRequestBody2(String tag, File file, MediaType contentType, OnProgressListener progressListener) {
        this.mFile=file;
        this.mTag=tag;
        this.mContentType=contentType;
        this.mOnProgressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return mContentType;
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        byte[] buffer = new byte[1024];
        InputStream in = new FileInputStream(mFile);
        long uploaded = 0;
        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                if (mOnProgressListener!=null){
                    mOnProgressListener.onProgress(mTag, contentLength(), uploaded, (int) (uploaded*100.0 / contentLength()));
                }
            }
            sink.flush();
        }catch (Exception ignored){}
        finally {
            in.close();
        }
    }
}
