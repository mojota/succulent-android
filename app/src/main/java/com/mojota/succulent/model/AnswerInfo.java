package com.mojota.succulent.model;

/**
 * 回答结构
 * Created by mojota on 18-8-21.
 */
public class AnswerInfo extends BaseBean {
    private static final long serialVersionUID = 1L;

    UserInfo userInfo;

    String answerId;//回答id;
    String questionId; // 问题id;
    String answerContent;//回答正文;
    String answerTime;//回答时间;
    int upCount;//回答点赞数;
    int isUp;//点赞状态，0：未点，1：已点;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }
}
