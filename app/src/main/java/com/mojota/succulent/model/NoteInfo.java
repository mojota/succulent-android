package com.mojota.succulent.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 笔记通用结构
 * Created by mojota on 18-7-24.
 */
public class NoteInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    String noteId;//id
    String noteTitle;// 标题
    String updateTime;// 更新时间
    int permission; // 0保密 1公开
    int likeCount; // 赞数
    int hasLike; // 0未赞 1已赞
    ArrayList<String> picUrls; // 封面图片地址们

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

    public ArrayList<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(ArrayList<String> picUrls) {
        this.picUrls = picUrls;
    }
}