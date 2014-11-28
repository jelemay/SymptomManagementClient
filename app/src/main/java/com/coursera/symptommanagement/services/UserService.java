
package com.coursera.symptommanagement.services;

import android.content.Context;
import android.content.Intent;

import com.coursera.symptommanagement.activities.LoginActivity;
import com.coursera.symptommanagement.oauth.SecuredRestBuilder;
import com.coursera.symptommanagement.unsafe.EasyHttpClient;

import retrofit.RestAdapter;
import retrofit.client.ApacheClient;



public class UserService {

    public static final String CLIENT_ID = "mobile";

    private static UserServiceAPI userSvc_;

    public static synchronized UserServiceAPI getOrShowLogin(Context ctx) {
        if (userSvc_ != null) {
            return userSvc_;
        } else {
            Intent i = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(i);
            return null;
        }
    }

    public static synchronized UserServiceAPI init(String server, String user,
                                                   String pass) {

        userSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + UserServiceAPI.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL).build()
                .create(UserServiceAPI.class);

        return userSvc_;
    }
}
