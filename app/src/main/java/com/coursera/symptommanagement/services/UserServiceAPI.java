package com.coursera.symptommanagement.services;

import com.coursera.symptommanagement.models.UserMedical;
import com.coursera.symptommanagement.models.UserRequest;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UserServiceAPI {

    public static final String TITLE_PARAMETER = "title";

    public static final String PASSWORD_PARAMETER = "password";

    public static final String USERNAME_PARAMETER = "username";

    public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";

    public static final String SVC_PATH = "/user";


    @GET(SVC_PATH )
    public List<UserMedical> getAllUsers() ;

    @GET(SVC_PATH +"/user/{userId}")
    public UserMedical getUserById(@Path("userId") Long id);

    @GET(SVC_PATH +"/username/{username}")
    public UserMedical findByUsername(@Path("username") String username);

    @POST(SVC_PATH)
    public UserMedical createUser(@Body UserRequest request);


}
