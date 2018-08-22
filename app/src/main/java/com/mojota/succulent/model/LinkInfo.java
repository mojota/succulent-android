package com.mojota.succulent.model;

/**
 * 链接通用结构
 * Created by mojota on 18-8-21.
 */
public class LinkInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    String linkId; // id
    String linkTitle; // 链接名称
    String linkSummary; // 链接简介
    String linkPicUrl; // 链接图片
    String linkUrl; // 链接地址
    String linkTime; // 链接发布时间
    String linkReadCount; // 已看数

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkSummary() {
        return linkSummary;
    }

    public void setLinkSummary(String linkSummary) {
        this.linkSummary = linkSummary;
    }

    public String getLinkPicUrl() {
        return linkPicUrl;
    }

    public void setLinkPicUrl(String linkPicUrl) {
        this.linkPicUrl = linkPicUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(String linkTime) {
        this.linkTime = linkTime;
    }

    public String getLinkReadCount() {
        return linkReadCount;
    }

    public void setLinkReadCount(String linkReadCount) {
        this.linkReadCount = linkReadCount;
    }
}
