package com.mojota.succulent.model;

/**
 * 问题结构
 * Created by mojota on 18-8-21.
 */
public class QuestionInfo extends BaseBean {
    private static final long serialVersionUID = 1L;

    UserInfo userInfo;

    String questionId;//问题id;
    String questionTitle;//问题;
    String questionPicUrl; // 问题图片;
    String questionTime;//提问时间;
    String answerCount;//回答数;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionPicUrl() {
        return questionPicUrl;
    }

    public void setQuestionPicUrl(String questionPicUrl) {
        this.questionPicUrl = questionPicUrl;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    public String getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }
}
