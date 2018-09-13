package com.mojota.succulent.model;

/**
 * 笔记详情item结构
 * Created by mojota on 18-8-14.
 */
public class NoteDetail extends BaseBean {

    private static final long serialVersionUID = 1L;

    String detailId;//id
    String content;// 笔记内容
    String createTime;// 记录时间
    String picUrls; // 图片地址们

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }
}
