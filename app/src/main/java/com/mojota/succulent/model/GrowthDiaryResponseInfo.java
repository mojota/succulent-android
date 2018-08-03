package com.mojota.succulent.model;

import java.util.List;

/**
 * Created by wangjing on 18-8-1.
 */
public class GrowthDiaryResponseInfo extends ResponseInfo {
    private List<GrowthDiary> list;

    public List<GrowthDiary> getList() {
        return list;
    }

    public void setList(List<GrowthDiary> list) {
        this.list = list;
    }
}
