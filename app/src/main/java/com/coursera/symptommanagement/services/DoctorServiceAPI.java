package com.coursera.symptommanagement.services;


import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.DoctorRequest;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.models.PatientRequest;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface DoctorServiceAPI {

    public static final String TITLE_PARAMETER = "title";

    public static final String PASSWORD_PARAMETER = "password";

    public static final String USERNAME_PARAMETER = "username";

    public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";



    // The path where we expect the DiabetesSvc to live
    public static final String SVC_PATH = "/doctor";


    @GET(SVC_PATH +"test")
    public String doctorTest();


    @GET(SVC_PATH)
    public List<Doctor> getDoctorList();

    @POST(SVC_PATH )
    public Doctor createDoctor(@Body DoctorRequest request) ;

    @GET(SVC_PATH +"/{doctorId}")
    public Doctor getDoctorById(@Path("doctorId") Long id);

    @GET(SVC_PATH + "/{doctorId}/patients")
    public List<Patient> getPatientList(@Path("doctorId") Long id);

    @GET(SVC_PATH + "/{doctorId}/medications")
    public List<Medication> getMedicationList(@Path("doctorId") Long id);

    @POST(SVC_PATH +"/doctor/{doctorId}")
    public Doctor addPatientToDoctor(@Body PatientRequest request, @Path("doctorId") Long id) ;

    @GET(SVC_PATH +"/username/{username}")
    public Doctor getDoctorByUsername(@Path("username") String username);


}
