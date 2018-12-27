package com.mojota.succulent.utils;

import android.text.TextUtils;

import com.mojota.succulent.model.UserInfo;

/**
 * Created by mojota on 18-9-3.
 */
public class UserUtil {


    private static SpManager mSp = new SpManager("user");
    private static SpManager mSpLast = new SpManager("last_user");

    /**
     * 保存登录信息到sp
     */
    public static void saveUser(UserInfo userInfo) {
        mSp.clear();
        mSp.putString(KeyConstants.KEY_USER_ID, userInfo.getUserId());
        mSp.putString(KeyConstants.KEY_USER_NAME, userInfo.getUserName());
        mSp.putString(KeyConstants.KEY_NICKNAME, userInfo.getNickname());
        mSp.putString(KeyConstants.KEY_AVATAR_URL, userInfo.getAvatarUrl());
        mSp.putString(KeyConstants.KEY_REGION, userInfo.getRegion());
        mSp.putString(KeyConstants.KEY_EMAIL, userInfo.getEmail());
        mSp.putString(KeyConstants.KEY_PHONE, userInfo.getPhone());
        mSp.putString(KeyConstants.KEY_COVER_URL, userInfo.getCoverUrl());

        // 保存最后登录的用户
        mSpLast.clear();
        mSpLast.putString(KeyConstants.KEY_USER_NAME, userInfo.getUserName());
    }

    /**
     * 获取最后登录的用户名
     */
    public static String getLastUserName() {
        return mSpLast.getString(KeyConstants.KEY_USER_NAME);
    }

    public static UserInfo getUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(mSp.getString(KeyConstants.KEY_USER_ID));
        userInfo.setUserName(mSp.getString(KeyConstants.KEY_USER_NAME));
        userInfo.setNickname(mSp.getString(KeyConstants.KEY_NICKNAME));
        userInfo.setAvatarUrl(mSp.getString(KeyConstants.KEY_AVATAR_URL));
        userInfo.setRegion(mSp.getString(KeyConstants.KEY_REGION));
        userInfo.setEmail(mSp.getString(KeyConstants.KEY_EMAIL));
        userInfo.setPhone(mSp.getString(KeyConstants.KEY_PHONE));
        userInfo.setCoverUrl(mSp.getString(KeyConstants.KEY_COVER_URL));
        return userInfo;
    }

    /**
     * 清除用户信息
     */
    public static void clearUser() {
        mSp.clear();
    }

    public static void saveAvatar(String avatarUrl) {
        mSp.putString(KeyConstants.KEY_AVATAR_URL, avatarUrl);
    }

    public static void saveCover(String coverUrl) {
        mSp.putString(KeyConstants.KEY_COVER_URL, coverUrl);
    }

    public static String getCover(){
        return mSp.getString(KeyConstants.KEY_COVER_URL);
    }

    /**
     * 获取当前用户id
     */
    public static String getCurrentUserId() {
        return mSp.getString(KeyConstants.KEY_USER_ID);
    }

    public static boolean isLogin() {
        if (!TextUtils.isEmpty(getCurrentUserId())) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否是当前登录用户
     */
    public static boolean isCurrentUser(String userId) {
        return isLogin() && getCurrentUserId().equals(userId);
    }

    public static String getDisplayName(UserInfo userInfo) {
        if (userInfo == null) {
            return "";
        }
        return TextUtils.isEmpty(userInfo.getNickname()) ? userInfo.getUserName() :
                userInfo.getNickname();
    }


    /**
     * 校验email
     */
    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * 校验密码
     */
    public static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

}
