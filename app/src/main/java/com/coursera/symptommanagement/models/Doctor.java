package com.coursera.symptommanagement.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by victorialemay on 11/18/14.
 */
public class Doctor implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;

    private List<Patient> patientList;

    private static final String TAB = "\t";

    public Doctor() {}

    public Doctor(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public List<Patient> getPatientList() {
        return patientList;
    }
    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @Override
    public String toString() {
        String doctor = this.firstName + TAB +
                        this.lastName + TAB;
        return doctor;
    }
}
