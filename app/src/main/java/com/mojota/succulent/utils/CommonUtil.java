package com.mojota.succulent.utils;

/**
 * Created by mojota on 18-12-25.
 */
public class CommonUtil {

    private static SpManager mSpCommon = new SpManager("common");


    /**
     * 保存最新通知时间
     */
    public static void setLatestNoticeTime(String latestNoticeTime) {
        mSpCommon.putString(KeyConstants.KEY_LATEST_NOTICE_TIME, latestNoticeTime);
    }

    /**
     * 获取已保存的最新通知时间
     */
    public static String getLatestNoticeTime() {
        return mSpCommon.getString(KeyConstants.KEY_LATEST_NOTICE_TIME);
    }
}
