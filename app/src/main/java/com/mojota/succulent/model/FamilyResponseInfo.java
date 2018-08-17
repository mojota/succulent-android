package com.mojota.succulent.model;

import java.util.List;

/**
 *
 * Created by mojota on 18-8-16.
 */
public class FamilyResponseInfo extends ResponseInfo {
    private List<Family> list;

    public List<Family> getList() {
        return list;
    }

    public void setList(List<Family> list) {
        this.list = list;
    }
}
