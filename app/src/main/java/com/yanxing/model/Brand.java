package com.yanxing.model;
import com.yanxing.networklibrary.model.BaseModel;

import java.util.List;

/**
 * @author 李双祥 on 2018/6/25.
 */
public class Brand extends BaseModel {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private int coverCityNum;
        private String brandName;
        private int companyId;
        private String brandProfile;
        private double distance;
        private int storeCityNum;
        private int brandId;
        private int orderNum;
        private String brandLogo;
        private List<ProductTypeBean> productType;

        public int getCoverCityNum() {
            return coverCityNum;
        }

        public void setCoverCityNum(int coverCityNum) {
            this.coverCityNum = coverCityNum;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getBrandProfile() {
            return brandProfile;
        }

        public void setBrandProfile(String brandProfile) {
            this.brandProfile = brandProfile;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getStoreCityNum() {
            return storeCityNum;
        }

        public void setStoreCityNum(int storeCityNum) {
            this.storeCityNum = storeCityNum;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getBrandLogo() {
            return brandLogo;
        }

        public void setBrandLogo(String brandLogo) {
            this.brandLogo = brandLogo;
        }

        public List<ProductTypeBean> getProductType() {
            return productType;
        }

        public void setProductType(List<ProductTypeBean> productType) {
            this.productType = productType;
        }

        public static class ProductTypeBean {

            private String PTYPENAME;

            public String getPTYPENAME() {
                return PTYPENAME;
            }

            public void setPTYPENAME(String PTYPENAME) {
                this.PTYPENAME = PTYPENAME;
            }
        }
    }
}
