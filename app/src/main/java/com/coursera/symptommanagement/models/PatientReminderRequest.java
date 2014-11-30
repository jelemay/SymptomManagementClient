package com.coursera.symptommanagement.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by victorialemay on 11/29/14.
 */
public class PatientReminderRequest implements Serializable {

    private List<Reminder> reminders;


    public PatientReminderRequest() {}

    public PatientReminderRequest(List<Reminder> reminders) {
        this.reminders = reminders;
    }


    public List<Reminder> getReminders() {
        return reminders;
    }
    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
