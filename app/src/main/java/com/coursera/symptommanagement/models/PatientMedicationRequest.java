package com.coursera.symptommanagement.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by victorialemay on 11/29/14.
 */
public class PatientMedicationRequest implements Serializable {

    private List<Medication> medications;


    public PatientMedicationRequest() {}

    public PatientMedicationRequest(List<Medication> medications) {
        this.medications = medications;
    }


    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

}
