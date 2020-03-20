package com.yanxing.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 用于retrofit上传文件，封装MultipartBody对象
 * @author 李双祥 on 2018/3/23.
 */
public class UploadFileUtil {

    /**
     * 多个文件需要的Part
     * @param fileKeys
     * @param files
     * @return
     */
    public static MultipartBody.Part[] multipartBodyPart(List<String> fileKeys, List<File> files){

        if (fileKeys!=null&&files!=null){
            MultipartBody.Part parts[]=new MultipartBody.Part[fileKeys.size()];
            for (int i=0;i<fileKeys.size();i++){
                RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),files.get(i));
                parts[i]=MultipartBody.Part.createFormData(fileKeys.get(i),files.get(i).getName(),requestBody);
            }
            return parts;
        }
        return null;
    }

    /**
     * 多个文件需要的Part
     * @param fileKeys
     * @param files
     * @return
     */
    public static MultipartBody.Part[] multipartByteBodyPart(List<String> fileKeys,List<byte[]> files){
        if (fileKeys!=null&&files!=null){
            MultipartBody.Part parts[]=new MultipartBody.Part[fileKeys.size()];
            for (int i=0;i<fileKeys.size();i++){
                RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),files.get(i));
                parts[i]=MultipartBody.Part.createFormData(fileKeys.get(i),null,requestBody);
            }
            return parts;
        }
        return null;
    }

    /**
     * 多个文件需要的Part，本应用传入byte[]为了适配Android Q权限（沙盒外图片，权限不足问题），上传接口需要传入文件名
     * @param fileKeys
     * @param fileNames 文件名称
     * @param files
     * @return
     */
    public static MultipartBody.Part[] multipartByteBodyPart(List<String> fileKeys, List<String> fileNames,List<byte[]> files){
        if (fileKeys!=null&&files!=null){
            MultipartBody.Part parts[]=new MultipartBody.Part[fileKeys.size()];
            for (int i=0;i<fileKeys.size();i++){
                RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),files.get(i));
                parts[i]=MultipartBody.Part.createFormData(fileKeys.get(i),fileNames.get(i),requestBody);
            }
            return parts;
        }
        return null;
    }

    /**
     * 单个文件需要的Part
     * @param fileKey
     * @param file
     * @return
     */
    public static MultipartBody.Part multipartBodyPart(String fileKey, File file){
        List<String> fileKeys=new ArrayList<>();
        List<File> files=new ArrayList<>();
        fileKeys.add(fileKey);
        files.add(file);
        MultipartBody.Part parts[]=multipartBodyPart(fileKeys,files);
        if (parts!=null){
            return parts[0];
        }
        return null;
    }
}
