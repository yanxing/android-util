package com.yanxing.downloadlibrary;

import java.io.File;

/**
 * 下载配置
 * Created by lishuangxiang on 2016/9/19.
 */
final public class DownloadConfiguration {

    //下载保存到的路径
    private File mSavePath;
    private boolean mLog;

    public DownloadConfiguration(Builder builder) {
        this.mSavePath = builder.mSavePath;
        this.mLog=builder.mLog;
    }

    public boolean isLog() {
        return mLog;
    }

    /**
     * 获取下载保存的路径
     *
     * @return
     */
    public File getSavePath() {
        return mSavePath;
    }

    public static class Builder {

        private File mSavePath;
        /**
         * true打印日志
         */
        private boolean mLog;

        public DownloadConfiguration builder() {
            return new DownloadConfiguration(this);
        }

        /**
         * 配置下载保存路径
         *
         * @param saveDir 存储路径
         * @return
         */
        public Builder savePath(File saveDir) {
            this.mSavePath = saveDir;
            if (saveDir == null) {
                throw new IllegalArgumentException("saveDir argument must be not null");
            }
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            return this;
        }

        public Builder setLog(boolean print){
            this.mLog=print;
            return this;
        }
    }
}
