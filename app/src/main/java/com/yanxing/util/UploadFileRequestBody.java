package com.yanxing.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 文件上传进度
 * @author 李双祥 on 2020/12/4.
 */
public class UploadFileRequestBody extends RequestBody {

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
    private File file;
    private byte[] mContent;
    private MediaType mContentType;
    private String mTag;


//    public UploadFileRequestBody(File file, OnProgressListener progressListener) {
//        this.file=file;
//        this.mProgressListener = progressListener;
//    }

    public UploadFileRequestBody(String tag, byte[] content, MediaType contentType, OnProgressListener onProgressListener) {
        this.mTag=tag;
        this.mContent = content;
        this.mContentType=contentType;
        this.mOnProgressListener = onProgressListener;
    }

    @Override
    public MediaType contentType() {
        return mContentType;
    }

    @Override
    public long contentLength() throws IOException {
        return mContent.length;
    }

/*    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //这里会有一个问题，开始没有进度回调，之后一下子将接近100%，然后100%
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调上传接口
                mProgressListener.onProgress(contentLength,bytesWritten);
            }
        };
    }*/

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        byte[] buffer = new byte[1024];
        java.io.InputStream in = new ByteArrayInputStream(mContent);
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
