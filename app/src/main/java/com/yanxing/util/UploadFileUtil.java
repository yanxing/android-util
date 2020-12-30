package com.yanxing.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

/**
 * 用于retrofit上传文件，封装MultipartBody对象
 * @author 李双祥 on 2018/3/23.
 */
public class UploadFileUtil {

    /**
     * 文件一起上传附带参数,targetSdkVersion29+，不是沙盒内文件不用此方法
     * @param fileKeys  文件key，不重复就可以
     * @param fileNames 文件名称
     * @param files 文件
     * @param map 携带参数，可以为null
     * @param onProgressListener 进度监听回调，可以为null
     * @return
     */
    public static MultipartBody.Part[] multipartFileBodyPart(List<String> fileKeys, List<String> fileNames, List<File> files
            , Map<String,String> map,UploadFileRequestBody2.OnProgressListener onProgressListener){
        if (fileKeys!=null&&files!=null){
            MultipartBody.Part parts[]=new MultipartBody.Part[fileKeys.size()+(map==null?0:map.size())];
            int j=0;
            for (int i=0;i<fileKeys.size();i++){
                UploadFileRequestBody2 requestBody=new UploadFileRequestBody2(String.valueOf(i+1),files.get(i),MediaType.parse("multipart/form-data")
                        ,onProgressListener);
                parts[i]=MultipartBody.Part.createFormData(fileKeys.get(i),fileNames.get(i),requestBody);
                j=i;
            }
            if (map!=null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    j++;
                    parts[j] = MultipartBody.Part.createFormData(entry.getKey(), entry.getValue());
                }
            }
            return parts;
        }
        return null;
    }


    /**
     * 文件一起上传附带参数
     * @param fileKeys  文件key，不重复就可以
     * @param fileNames 文件名称
     * @param fileBytes 文件流
     * @param map 携带参数，可以为null
     * @param onProgressListener 进度监听回调，可以为null
     * @return
     */
    public static MultipartBody.Part[] multipartByteBodyPart(List<String> fileKeys, List<String> fileNames, List<byte[]> fileBytes
            , Map<String,String> map,UploadFileRequestBody.OnProgressListener onProgressListener){
        if (fileKeys!=null&&fileBytes!=null){
            MultipartBody.Part parts[]=new MultipartBody.Part[fileKeys.size()+(map==null?0:map.size())];
            int j=0;
            for (int i=0;i<fileKeys.size();i++){
                UploadFileRequestBody requestBody=new UploadFileRequestBody(String.valueOf(i+1),fileBytes.get(i),MediaType.parse("multipart/form-data")
                        ,onProgressListener);
                parts[i]=MultipartBody.Part.createFormData(fileKeys.get(i),fileNames.get(i),requestBody);
                j=i;
            }
            if (map!=null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    j++;
                    parts[j] = MultipartBody.Part.createFormData(entry.getKey(), entry.getValue());
                }
            }
            return parts;
        }
        return null;
    }
}
