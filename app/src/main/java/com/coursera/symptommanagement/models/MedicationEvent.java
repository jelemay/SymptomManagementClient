package com.coursera.symptommanagement.models;

import java.io.Serializable;

/**
 * Created by victorialemay on 11/30/14.
 */
public class MedicationEvent implements Serializable {

    private Medication medication;
    private Long time;


    public MedicationEvent() {}

    public MedicationEvent(Medication medication, Long time) {
        this.medication = medication;
        this.time = time;
    }


    public Medication getMedication() {
        return medication;
    }
    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}
