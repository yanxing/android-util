package com.yanxing.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 省市区
 * Created by lishuangxiang on 2016/4/8.
 */
public class Area implements Parcelable {


    /**
     * name : 北京
     * city : [{"name":"北京","area":["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]}]
     */

    private String name;
    /**
     * name : 北京
     * area : ["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]
     */

    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean implements Parcelable {


        private String name;
        private List<String> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeStringList(this.area);
        }

        public CityBean() {
        }

        protected CityBean(Parcel in) {
            this.name = in.readString();
            this.area = in.createStringArrayList();
        }

        public static final Creator<CityBean> CREATOR = new Creator<CityBean>() {
            @Override
            public CityBean createFromParcel(Parcel source) {
                return new CityBean(source);
            }

            @Override
            public CityBean[] newArray(int size) {
                return new CityBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.city);
    }

    public Area() {
    }

    protected Area(Parcel in) {
        this.name = in.readString();
        this.city = new ArrayList<CityBean>();
        in.readList(this.city, CityBean.class.getClassLoader());
    }

    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel source) {
            return new Area(source);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
}


