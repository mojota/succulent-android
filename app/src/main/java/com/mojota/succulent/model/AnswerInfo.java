package com.mojota.succulent.model;

/**
 * 回答结构
 * Created by mojota on 18-8-21.
 */
public class AnswerInfo extends BaseBean {
    private static final long serialVersionUID = 1L;

    String userId;//用户id;
    String nickname;//用户昵称;
    String avatarUrl;//用户头像;
    String answerId;//回答id;
    String answerContent;//回答正文;
    String answerTime;//回答时间;
    String answerUpCount;//回答点赞数;
    String answerUpState;//点赞状态，0：未点，1：已点;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerUpCount() {
        return answerUpCount;
    }

    public void setAnswerUpCount(String answerUpCount) {
        this.answerUpCount = answerUpCount;
    }

    public String getAnswerUpState() {
        return answerUpState;
    }

    public void setAnswerUpState(String answerUpState) {
        this.answerUpState = answerUpState;
    }
}
