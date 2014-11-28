package com.coursera.symptommanagement.services;

import android.content.Context;
import android.content.Intent;

import com.coursera.symptommanagement.activities.LoginActivity;
import com.coursera.symptommanagement.oauth.SecuredRestBuilder;
import com.coursera.symptommanagement.unsafe.EasyHttpClient;

import retrofit.RestAdapter;
import retrofit.client.ApacheClient;



public class DoctorService {

    public static final String CLIENT_ID = "mobile";

    private static DoctorServiceAPI doctorSvc_;

    public static synchronized DoctorServiceAPI getOrShowLogin(Context ctx) {
        if (doctorSvc_ != null) {
            return doctorSvc_;
        } else {
            Intent i = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(i);
            return null;
        }
    }

    public static synchronized DoctorServiceAPI init(String server, String user,
                                                     String pass) {

        doctorSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + DoctorServiceAPI.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(DoctorServiceAPI.class);

        return doctorSvc_;
    }
}