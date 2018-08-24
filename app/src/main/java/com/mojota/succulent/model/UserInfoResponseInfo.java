package com.mojota.succulent.model;

/**
 * Created by mojota on 18-8-24.
 */
public class UserInfoResponseInfo extends ResponseInfo {
    UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
