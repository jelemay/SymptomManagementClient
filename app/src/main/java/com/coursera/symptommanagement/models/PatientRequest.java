package com.coursera.symptommanagement.models;

import java.io.Serializable;
import java.util.List;

public class PatientRequest implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Long medicalRecordId;

    private List<Medication> medications;


    public PatientRequest() {}


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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMedicalRecordId() {
        return medicalRecordId;
    }
    public void setMedicalRecordId(Long medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public List<Medication> getMedications() {
        return medications;
    }
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
