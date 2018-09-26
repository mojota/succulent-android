package com.mojota.succulent.model;

import java.util.List;

/**
 * 问题列表
 * Created by mojota on 18-8-21.
 */
public class QaResponseInfo extends ResponseInfo {

    private List<QuestionInfo> list;

    public List<QuestionInfo> getList() {
        return list;
    }

    public void setList(List<QuestionInfo> list) {
        this.list = list;
    }
}
