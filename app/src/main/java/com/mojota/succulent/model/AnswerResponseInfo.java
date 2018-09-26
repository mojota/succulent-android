package com.mojota.succulent.model;

import java.util.List;

/**
 * 问题详情回答列表
 * Created by mojota on 18-8-21.
 */
public class AnswerResponseInfo extends ResponseInfo {

    private List<AnswerInfo> list;

    public List<AnswerInfo> getList() {
        return list;
    }

    public void setList(List<AnswerInfo> list) {
        this.list = list;
    }
}
