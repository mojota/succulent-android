package com.mojota.succulent.model;

import java.util.List;

/**
 * Created by mojota on 18-8-1.
 */
public class LinkResponseInfo extends ResponseInfo {
    private List<LinkInfo> list;

    public List<LinkInfo> getList() {
        return list;
    }

    public void setList(List<LinkInfo> list) {
        this.list = list;
    }
}
