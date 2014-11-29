package com.coursera.symptommanagement.models;

import java.io.Serializable;

/**
 * Created by victorialemay on 11/8/14.
 */
public class Medication implements Serializable {

    private Long id;
    private String name;


    public Medication() {}

    public Medication(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this. id + " \t" + this.name;
    }
}
