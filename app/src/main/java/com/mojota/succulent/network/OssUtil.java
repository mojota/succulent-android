package com.mojota.succulent.network;

import com.mojota.succulent.utils.UrlConstants;

/**
 * Created by wangjing on 18-11-21.
 */
public class OssUtil {

    /**
     * 拼接上传图片的key
     */
    public static String getImageObjectKey(String ossDir, String filename) {
        return ossDir + "/" + filename + ".jpg";
    }

    /**
     * 拼接下载图片的url
     */
    public static String getWholeImageUrl(String imageUrl) {
        return UrlConstants.OSS_SERVER + imageUrl;
    }
}
