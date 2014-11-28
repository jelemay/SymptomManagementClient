package com.coursera.symptommanagement.task;

import android.util.Log;

import com.coursera.symptommanagement.activities.LoginActivity;
import com.coursera.symptommanagement.models.UserMedical;
import com.coursera.symptommanagement.models.UserRequest;
import com.coursera.symptommanagement.services.UserServiceAPI;

import java.util.concurrent.Callable;

/**
 * Created by lemay on 11/27/14.
 */
public class UserCreateTest {

    public static void create() {

        final UserRequest newUser = new UserRequest();
        newUser.setUsername("alemay1");
        newUser.setPassword("password");
        newUser.setRole("DOCTOR");
        Long roleId = Long.valueOf("3");
        newUser.setRoleId(roleId);

        final UserServiceAPI userSvc = SvcStore.getUserService();

        CallableTask.invoke(new Callable<UserMedical>() {

            @Override
            public UserMedical call() throws Exception {
                return userSvc.createUser(newUser);
            }
        }, new TaskCallback<UserMedical>() {

            @Override
            public void success(UserMedical result) {
                // OAuth 2.0 grant was successful and we
                // can talk to the server, open up the video listing
                UserMedical resultIn = result;

            }

            @Override
            public void error(Exception e) {
                Log.e(LoginActivity.class.getName(), "Error logging in via OAuth.", e);
            }

        });

    }

}








