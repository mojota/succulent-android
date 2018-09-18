package com.mojota.succulent.model;

import java.util.List;

/**
 * 笔记详情返回数据结构
 * Created by mojota on 18-8-1.
 */
public class DiarysResponseInfo extends ResponseInfo {

    private List<NoteDetail> list;

    public List<NoteDetail> getList() {
        return list;
    }

    public void setList(List<NoteDetail> list) {
        this.list = list;
    }
}
