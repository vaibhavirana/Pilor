package com.barodacoder.pilor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.barodacoder.pilor.api.SignupApi;
import com.barodacoder.pilor.apimodel.LoginResponse;
import com.barodacoder.pilor.apimodel.SignupRequest;
import com.barodacoder.pilor.utils.Validator;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends ActivityBase {
    private EditText etFullName, etEmail, etPhone, etPassword, etRePassword;

    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        initData();

        initUi();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }

    protected void initData() {
        super.initData();
    }

    private void initUi() {
        findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Glide.with(this)
                .load(R.drawable.bg_frontpage)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into((ImageView)findViewById(R.id.imgBG));

        etFullName = (EditText) findViewById(R.id.etFullName);
        etFullName.setTypeface(appData.getFontRegular());

        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setTypeface(appData.getFontRegular());

        etPhone = (EditText) findViewById(R.id.etPhone);
        etPhone.setTypeface(appData.getFontRegular());

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setTypeface(appData.getFontRegular());

        etRePassword = (EditText) findViewById(R.id.etRePassword);
        etRePassword.setTypeface(appData.getFontRegular());

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setTypeface(appData.getFontRegular());

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();

                if (!validated())
                    return;

                signUpUser();
            }
        });
    }

    private boolean validated() {
        if (!Validator.validateNameNotNull(etFullName.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_name));

            return false;
        }

        if (!Validator.validateNameNotNull(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_email));

            return false;
        }

        if (!Validator.validateEmail(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_valid_email));

            return false;
        }
/*
        if (!Validator.validateNameNotNull(etPhone.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_phone));

            return false;
        }*/

        if (!Validator.validateNameNotNull(etPassword.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_password));

            return false;
        }

        if (!Validator.validateNameNotNull(etRePassword.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_repassword));

            return false;
        }

        if (!etPassword.getText().toString().equals(etRePassword.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_password_match));

            return false;
        }

        return true;
    }

    private void signUpUser() {
        SignupApi ApiService = MyApplication.retrofit.create(SignupApi.class);
        SignupRequest signupRequest=new SignupRequest();
        signupRequest.display_name=etFullName.getText().toString();
        signupRequest.email=etEmail.getText().toString();
        signupRequest.password=etPassword.getText().toString();
       // signupRequest.localtime=dateFormat.format(new Date());
        signupRequest.latitude=libFile.getLatitude();
        signupRequest.longitude=libFile.getLongitude();
        signupRequest.device_token=libFile.getDeviceToken();
        signupRequest.device_id=libFile.getDeviceId();
        signupRequest.device_type=AppConstants.DEVICE_TYPE;
        showProgressDialog();
        Log.e("login req",signupRequest.toString());
        Call<LoginResponse> call = ApiService.signup(signupRequest);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("resp", response.body().toString());
                cancelProgressDialog();

                try
                {
                     //String response1 = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "LOGIN RESPONSE : " + response);

                    if(response.body().status_code== 1)
                    {
                        // appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        libFile.setEmailId(etEmail.getText().toString());

                        libFile.setPassword(etPassword.getText().toString());

                       /* if(appData.getUserData().getRole().equals("1"))
                            goToHomeScreen();//goToMainAdminScreen();
                        else if(appData.getUserData().getRole().equals("2"))
                            goToBusinessMainScreen();
                        else
                            goToHomeScreen();*/
                    }
                    else
                    {
                        showMsgDialog(getString(R.string.txt_invalid_email));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();

                    goToLandingScreen();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                cancelProgressDialog();
                try
                {
                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "LOGIN RESPONSE : FAILED : " + t.toString());
                    showMsgDialog(getString(R.string.txt_invalid_email));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
       /* AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("display_name", etFullName.getText().toString());
        params.put("localtime", dateFormat.format(new Date()));
        params.put("latitude", libFile.getLatitude());
        params.put("longitude", libFile.getLongitude());
        params.put("device_type", AppConstants.DEVICE_TYPE);
        params.put("device_token", libFile.getDeviceToken());
        params.put("device_id", libFile.getDeviceId());

        client.post(AppConstants.URL_SIGNUP, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                cancelProgressDialog();

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "SIGNUP RESPONSE : " + response);

                    UserData userData = ParseJson.parseSignUp(response);

                    if (userData.getStatusCode() == 1) {
                        appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        libFile.setEmailId(userData.getEmail());

                        libFile.setPassword(etPassword.getText().toString());

                        if (appData.getUserData().getRole().equals("1"))
                            goToHomeScreen();//goToMainAdminScreen();
                        else if (appData.getUserData().getRole().equals("2"))
                            goToBusinessMainScreen();
                        else
                            goToHomeScreen();
                    } else {
                        showSnackBar(findViewById(R.id.rlMain), getString(R.string.txt_invalid_email));
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    goToLandingScreen();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                cancelProgressDialog();

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "SIGNUP RESPONSE : FAILED : " + response);

                    showSnackBar(findViewById(R.id.rlMain), getString(R.string.txt_invalid_email));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
*/
        //goToHomeScreen();
    }
}
