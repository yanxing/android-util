package com.yanxing.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lishuangxiang on 2017/4/4.
 */
public class Table implements Parcelable {

    private String firstColumn;

    private List<String> mOtherColumn;

    public String getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(String firstColumn) {
        this.firstColumn = firstColumn;
    }

    public List<String> getOtherColumn() {
        return mOtherColumn;
    }

    public void setOtherColumn(List<String> otherColumn) {
        mOtherColumn = otherColumn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstColumn);
        dest.writeStringList(this.mOtherColumn);
    }

    public Table() {
    }

    protected Table(Parcel in) {
        this.firstColumn = in.readString();
        this.mOtherColumn = in.createStringArrayList();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel source) {
            return new Table(source);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };
}
