package com.coursera.symptommanagement.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by victorialemay on 11/30/14.
 */
public class CheckInRequest implements Serializable {

    private String appetite;
    private String pain;
    private Long time;
    private List<MedicationEvent> medicationEventList;


    public CheckInRequest() {}


    public String getAppetite() {
        return appetite;
    }
    public void setAppetite(String appetite) {
        this.appetite = appetite;
    }

    public String getPain() {
        return pain;
    }
    public void setPain(String pain) {
        this.pain = pain;
    }

    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }

    public List<MedicationEvent> getMedicationEventList() {
        return medicationEventList;
    }
    public void setMedicationEventList(List<MedicationEvent> medicationEventList) {
        this.medicationEventList = medicationEventList;
    }
}
