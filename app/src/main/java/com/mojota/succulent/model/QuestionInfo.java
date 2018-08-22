package com.mojota.succulent.model;

/**
 * 问题结构
 * Created by mojota on 18-8-21.
 */
public class QuestionInfo extends BaseBean {
    private static final long serialVersionUID = 1L;

    String userId;//用户id;
    String nickname;//用户昵称;
    String avatarUrl;//用户头像;
    String questionId;//问题id;
    String questionTitle;//问题;
    String questionPicUrl; // 问题图片;
    String questionTime;//提问时间;
    String answerCount;//回答数;

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
