package com.mojota.succulent.model;

/**
 * 属
 * Created by mojota on 18-8-16.
 */
public class Genus extends BaseBean {
    private static final long serialVersionUID = 1L;

    String genusId;
    String genusName;

    public String getGenusId() {
        return genusId;
    }

    public void setGenusId(String genusId) {
        this.genusId = genusId;
    }

    public String getGenusName() {
        return genusName;
    }

    public void setGenusName(String genusName) {
        this.genusName = genusName;
    }
}
