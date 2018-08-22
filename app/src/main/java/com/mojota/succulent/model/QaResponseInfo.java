package com.mojota.succulent.model;

import java.util.List;

/**
 * 问题列表
 * Created by mojota on 18-8-21.
 */
public class QaResponseInfo extends ResponseInfo {

    private List<QuestionInfo> qaList;

    public List<QuestionInfo> getQaList() {
        return qaList;
    }

    public void setQaList(List<QuestionInfo> qaList) {
        this.qaList = qaList;
    }
}
