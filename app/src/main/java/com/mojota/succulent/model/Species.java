package com.mojota.succulent.model;

/**
 * 种
 * Created by mojota on 18-8-16.
 */
public class Species extends BaseBean {
    private static final long serialVersionUID = 1L;

    String speciesId;
    String speciesName;
    String speciesPicUrl;
    String speciesLinkUrl;

    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getSpeciesPicUrl() {
        return speciesPicUrl;
    }

    public void setSpeciesPicUrl(String speciesPicUrl) {
        this.speciesPicUrl = speciesPicUrl;
    }

    public String getSpeciesLinkUrl() {
        return speciesLinkUrl;
    }

    public void setSpeciesLinkUrl(String speciesLinkUrl) {
        this.speciesLinkUrl = speciesLinkUrl;
    }
}
