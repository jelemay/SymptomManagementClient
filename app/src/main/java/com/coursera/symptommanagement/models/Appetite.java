package com.coursera.symptommanagement.models;

/**
 * Created by victorialemay on 11/25/14.
 */
public class Appetite {

    private Long id;
    private String description;


    public Appetite() {}

    public Appetite(Long id, String description) {
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
