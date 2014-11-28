package com.coursera.symptommanagement.task;

import android.app.Application;

import com.coursera.symptommanagement.services.DoctorServiceAPI;
import com.coursera.symptommanagement.services.PatientServiceAPI;
import com.coursera.symptommanagement.services.UserServiceAPI;


/**
 * Created by lemay on 11/26/14.
 */
public  class SvcStore extends Application {

    public static DoctorServiceAPI dService;
    public static  UserServiceAPI uService;
    public static PatientServiceAPI pService;

    public static void setUserService(UserServiceAPI uServiceIn){
        uService = uServiceIn;
    }

    public static void setDoctorService(DoctorServiceAPI dServiceIn){
        dService = dServiceIn;
    }
    public static void setPatientService(PatientServiceAPI pServiceIn){
        pService = pServiceIn;
    }

    public static UserServiceAPI getUserService(){
        return uService ;
    }
    public static PatientServiceAPI getPatientService(){
        return pService ;
    }

    public static DoctorServiceAPI getDoctorService(){
        return dService ;
    }



}
