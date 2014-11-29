package com.coursera.symptommanagement.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by victorialemay on 11/7/14.
 */
public class Patient implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String medicalRecordId;

    private List<Medication> medications;
    private Doctor doctor;

    private static final String TAB = "\t";

    public Patient() {}

    public Patient(Long id, String firstName, String lastName, String medicalRecordId,
                   List<Medication> medications) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalRecordId = medicalRecordId;
        this.medications = medications;
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

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getMedicalRecordId() { return medicalRecordId; }
    public void setMedicalRecordId(String medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public List<Medication> getMedications() {
        return medications;
    }
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        String patient = this.id + TAB +
                         this.firstName + TAB +
                         this.lastName + TAB +
                         this.medicalRecordId + TAB;
        return patient;
    }
}
