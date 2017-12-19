package com.dianmei.analyzelibrary;


import android.support.v4.util.ArrayMap;


/**
 * 记录停留页面名称及其开始时间，结束时间数据结构
 *
 * @author 李双祥 on 2017/12/19.
 */
public class PageMap {

    private static ArrayMap<String, Page> mPageArrayMap = new ArrayMap<>();

    public void put(String name) {
        Page page = mPageArrayMap.get(name);
        if (page == null) {
            page = new Page(System.currentTimeMillis(), 0, name);
        } else {
            //如果存在，保留累计时长
            page.setStartTime(System.currentTimeMillis());
        }
        mPageArrayMap.put(name, page);
    }

    public Page get(String key) {
        return mPageArrayMap.get(key);
    }

    public ArrayMap getMap() {
        return mPageArrayMap;
    }

    public static class Page {
        private long startTime;
        private long endTime;
        /**
         * 累计停留页面时间，可能多次浏览此页面
         */
        private long totalTime;
        private String pageName;

        /**
         * @param startTime 时间戳 开始时间
         * @param endTime   时间戳 结束时间
         * @param pageName  页面名称
         */
        public Page(long startTime, long endTime, String pageName) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.pageName = pageName;
        }

        public long getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(long totalTime) {
            this.totalTime = totalTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getPageName() {
            return pageName;
        }

        public void setPageName(String pageName) {
            this.pageName = pageName;
        }

        @Override
        public String toString() {
            return "Page{" +
                    "startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", totalTime=" + totalTime +
                    ", pageName='" + pageName + '\'' +
                    '}';
        }
    }
}
