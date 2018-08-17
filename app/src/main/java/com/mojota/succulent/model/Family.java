package com.mojota.succulent.model;

import java.util.List;

/**
 * 科
 * Created by mojota on 18-8-16.
 */
public class Family extends BaseBean {
    private static final long serialVersionUID = 1L;

    String familyId;
    String familyName;
    List<Genus> genusList;

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<Genus> getGenusList() {
        return genusList;
    }

    public void setGenusList(List<Genus> genusList) {
        this.genusList = genusList;
    }
}
