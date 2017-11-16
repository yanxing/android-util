package com.yanxing.dialog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 参数配置
 * Created by lishuangxiang on 2016/9/6.
 */
public class PhotoParam implements Parcelable {

    /**
     * 图片保存路径
     */
    private String path;
    /**
     * 图片名称
     */
    private String name;
    /**
     * 图片裁剪x,cut为true有效
     */
    private int outputX;
    private int outputY;
    /**
     * 默认不裁剪
     */
    private boolean cut;

    public boolean isCut() {
        return cut;
    }

    public void setCut(boolean cut) {
        this.cut = cut;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOutputY() {
        return outputY;
    }

    public void setOutputY(int outputY) {
        this.outputY = outputY;
    }

    public int getOutputX() {
        return outputX;
    }

    public void setOutputX(int outputX) {
        this.outputX = outputX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.name);
        dest.writeInt(this.outputX);
        dest.writeInt(this.outputY);
        dest.writeByte(this.cut ? (byte) 1 : (byte) 0);
    }

    public PhotoParam() {
    }

    protected PhotoParam(Parcel in) {
        this.path = in.readString();
        this.name = in.readString();
        this.outputX = in.readInt();
        this.outputY = in.readInt();
        this.cut = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PhotoParam> CREATOR = new Parcelable.Creator<PhotoParam>() {
        @Override
        public PhotoParam createFromParcel(Parcel source) {
            return new PhotoParam(source);
        }

        @Override
        public PhotoParam[] newArray(int size) {
            return new PhotoParam[size];
        }
    };
}
