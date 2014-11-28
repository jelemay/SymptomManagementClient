package com.coursera.symptommanagement.services;

import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.models.PatientRequest;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PatientServiceAPI {

    public static final String TITLE_PARAMETER = "title";

    public static final String PASSWORD_PARAMETER = "password";

    public static final String USERNAME_PARAMETER = "username";

    public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";



    // The path where we expect the DiabetesSvc to live
    public static final String SVC_PATH = "/patient";


    @GET(SVC_PATH +"test")
    public String patientTest();

    @GET(SVC_PATH)
    public List<Patient> getPatientList();

    @POST(SVC_PATH )
    public Patient createPatient(@Body PatientRequest request) ;

    @GET(SVC_PATH +"/{patientId}")
    public Patient getPatientById(@Path("patientId") Long id);


    @GET(SVC_PATH +"/username/{username}")
    public Patient getPatientByUsername(@Path("username") String username);


}
