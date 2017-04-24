package com.barodacoder.pilor.api;

import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.apimodel.LoginRequest;
import com.barodacoder.pilor.apimodel.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Vaibhavi on 4/23/2017.
 */

public interface LoginApi {
    @POST(AppConstants.URL_LOGIN)
    Call<LoginResponse> login(@Body LoginRequest request);
}
