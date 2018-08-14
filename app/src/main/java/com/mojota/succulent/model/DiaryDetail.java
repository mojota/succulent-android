package com.mojota.succulent.model;

import java.util.ArrayList;

/**
 * 笔记详情item结构
 * Created by mojota on 18-8-14.
 */
public class DiaryDetail extends BaseBean {

    private static final long serialVersionUID = 1L;

    String diaryId;//id
    String content;// 笔记内容
    String createTime;// 记录时间
    ArrayList<String> picUrls; // 图片地址们

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
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

    public ArrayList<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(ArrayList<String> picUrls) {
        this.picUrls = picUrls;
    }
}
