package com.mojota.succulent.model;

/**
 * 通知
 * Created by mojota on 18-12-25
 */
public class NoticeInfo extends BaseBean {
    private static final long serialVersionUID = 1L;

    String noticeId;//通知id
    String noticeTitle;// 标题
    String noticeContent;// 内容
    String noticeTime;// 时间

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }
}
