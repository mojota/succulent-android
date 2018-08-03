package com.mojota.succulent.model;

/**
 * Created by mojota on 18-7-24.
 */
public class GrowthDiary extends BaseBean {

    private static final long serialVersionUID = 1L;

    String diaryId;//id
    String title;// 标题
    String updateTime;// 更
    int permission; // 0保密 1公开
    int likeCount; // 赞数
    int hasLike; // 0未赞 1已赞
    String picUrl; // 图片地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        permission = permission;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getHasLike() {
        return hasLike;
    }

    public void setHasLike(int hasLike) {
        this.hasLike = hasLike;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}