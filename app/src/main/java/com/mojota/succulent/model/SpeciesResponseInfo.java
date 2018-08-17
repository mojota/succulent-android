package com.mojota.succulent.model;

import java.util.List;

/**
 * Created by mojota on 18-8-16.
 */
public class SpeciesResponseInfo extends ResponseInfo {

    List<Species> speciesList;

    public List<Species> getSpeciesList() {
        return speciesList;
    }

    public void setSpeciesList(List<Species> speciesList) {
        this.speciesList = speciesList;
    }
}
