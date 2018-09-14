package com.mojota.succulent.model;

/**
 * 笔记通用结构
 * Created by mojota on 18-7-24.
 */
public class NoteInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    UserInfo userInfo;
    String noteId;//id
    String noteTitle;// 标题
    String updateTime;// 更新时间
    int permission; // 0保密 1公开
    int likeyCount; // 赞数
    int isLikey; // 0未赞 1已赞
    String picUrls; // 封面图片地址们
    int noteType; // 笔记类型 1-成长笔记 2-造景

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
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
        this.permission = permission;
    }

    public int getLikeyCount() {
        return likeyCount;
    }

    public void setLikeyCount(int likeyCount) {
        this.likeyCount = likeyCount;
    }

    public int getIsLike() {
        return isLikey;
    }

    public void setIsLike(int isLikey) {
        this.isLikey = isLikey;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }
}