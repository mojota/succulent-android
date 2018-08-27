package com.mojota.succulent.model;

/**
 * 用户信息
 * Created by mojota on 18-8-23.
 */
public class UserInfo extends BaseBean {

    private static final long serialVersionUID = 1L;

    String userId;//用户id
    String userName; //用户名
    String nickname;//用户昵称
    String avatarUrl;//用户头像地址
    String region; //地区
    String momentsCoverUrl; // 邻家肉园封面图

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMomentsCoverUrl() {
        return momentsCoverUrl;
    }

    public void setMomentsCoverUrl(String momentsCoverUrl) {
        this.momentsCoverUrl = momentsCoverUrl;
    }
}
