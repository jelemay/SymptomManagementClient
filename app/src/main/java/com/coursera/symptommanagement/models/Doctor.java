package com.coursera.symptommanagement.models;

import java.io.Serializable;

/**
 * Created by victorialemay on 11/18/14.
 */
public class Doctor implements Serializable {

    private String firstName;
    private String lastName;


    public Doctor() {}

    public Doctor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
