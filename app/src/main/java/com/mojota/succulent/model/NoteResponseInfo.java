package com.mojota.succulent.model;

import java.util.List;

/**
 * Created by mojota on 18-8-1.
 */
public class NoteResponseInfo extends ResponseInfo {
    private List<NoteInfo> list;

    public List<NoteInfo> getList() {
        return list;
    }

    public void setList(List<NoteInfo> list) {
        this.list = list;
    }
}
