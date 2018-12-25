package com.mojota.succulent.model;

import java.util.List;

/**
 * Created by wangjing on 18-12-25.
 */
public class NoticeResponseInfo extends ResponseInfo {
    List<NoticeInfo> list;

    public List<NoticeInfo> getList() {
        return list;
    }

    public void setList(List<NoticeInfo> list) {
        this.list = list;
    }
}
