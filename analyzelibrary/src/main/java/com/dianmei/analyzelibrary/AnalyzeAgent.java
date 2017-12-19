package com.dianmei.analyzelibrary;

import android.content.Context;
import android.content.Intent;

/**
 * 统计页面停落时间，启动次数，今日启动过应用的用户
 *
 * @author 李双祥 on 2017/12/19.
 */
public class AnalyzeAgent {

    private static AnalyzeConfig mAnalyzeConfig;
    private static PageMap mPageMap = new PageMap();

    /**
     * 配置信息，设置渠道号、用户Id，设备系统等信息自动获取
     *
     * @param analyzeConfig
     */
    public static void init(AnalyzeConfig analyzeConfig) {
        AnalyzeAgent.mAnalyzeConfig = analyzeConfig;
    }

    /**
     * 对应Activity/Fragment的onResume，若Activity包含Fragment,Activity不应加这句，
     * 应在{@link #init(AnalyzeConfig)}之后调用
     * @param pageName 名称,应具有唯一性
     */
    public static void onResume(Context context, String pageName) {
        Intent intent = new Intent(context, AnalyzeService.class);
        if (mAnalyzeConfig != null) {
            intent.putExtra("channel", mAnalyzeConfig.channel);
            intent.putExtra("userId", mAnalyzeConfig.userId);
            intent.putExtra("log", mAnalyzeConfig.log);
            intent.putExtra("tactic",mAnalyzeConfig.tactic);
        }
        context.startService(intent);
        mPageMap.put(pageName);
    }

    /**
     * 对应Activity/Fragment的onPause
     *
     * @param pageName 名称,对应{@link #onResume(Context, String)}中的pageName}
     */
    public static void onPause(String pageName){
        PageMap.Page page = mPageMap.get(pageName);
        if (page==null){
            throw new RuntimeException("没有调用AnalyzeAgent的onResume方法");
        }
        page.setEndTime(System.currentTimeMillis());
        long startTime=page.getStartTime();
        long endTime=page.getEndTime();
        //保留同一页面累计时长
        page.setTotalTime(page.getTotalTime()+startTime-endTime);
    }

    public static PageMap getPageMap(){
        return mPageMap;
    }

    public static class AnalyzeConfig {
        private String channel;
        private String userId;
        private boolean log;
        private String tactic;

        /**
         * 配置
         * @param tactic {@link Tactic}
         * @param channel 渠道号
         * @param userId  用户ID
         * @param log     true打印日志
         */
        public AnalyzeConfig(String tactic,String channel, String userId, boolean log) {
            this.tactic=tactic;
            this.channel = channel;
            this.userId = userId;
            this.log = log;
        }
    }
}
