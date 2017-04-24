package com.barodacoder.pilor.api;

import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.apimodel.LoginResponse;
import com.barodacoder.pilor.apimodel.SignupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Vaibhavi on 4/23/2017.
 */

public interface SignupApi {
    @POST(AppConstants.URL_SIGNUP)
    Call<LoginResponse> signup(@Body SignupRequest request);
}
