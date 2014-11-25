package com.coursera.symptommanagement.models;

/**
 * Created by victorialemay on 11/23/14.
 */
public class Pain {

    private Long id;
    private String description;


    public Pain() {}

    public Pain(Long id, String description) {
        this.id = id;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
