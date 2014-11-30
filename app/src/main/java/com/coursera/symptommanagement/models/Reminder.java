package com.coursera.symptommanagement.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by victorialemay on 11/29/14.
 */
public class Reminder implements Serializable {

    private Long id;
    private Long time;


    public Reminder() {}

    public Reminder(Long time) {
        this.time = time;
    }

    public Reminder(Long id, Long time) {
        this.id = id;
        this.time = time;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}
