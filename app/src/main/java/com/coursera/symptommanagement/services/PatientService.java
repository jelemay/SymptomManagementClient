package com.coursera.symptommanagement.services;

import android.content.Context;
import android.content.Intent;

import com.coursera.symptommanagement.activities.LoginActivity;
import com.coursera.symptommanagement.oauth.SecuredRestBuilder;
import com.coursera.symptommanagement.unsafe.EasyHttpClient;

import retrofit.RestAdapter;
import retrofit.client.ApacheClient;



public class PatientService {

    public static final String CLIENT_ID = "mobile";

    private static PatientServiceAPI patientSvc_;

    public static synchronized PatientServiceAPI getOrShowLogin(Context ctx) {
        if (patientSvc_ != null) {
            return patientSvc_;
        } else {
            Intent i = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(i);
            return null;
        }
    }

    public static synchronized PatientServiceAPI init(String server, String user,
                                                      String pass) {

        patientSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + PatientServiceAPI.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(PatientServiceAPI.class);

        return patientSvc_;
    }
}