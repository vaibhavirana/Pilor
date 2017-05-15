package com.barodacoder.pilor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.barodacoder.pilor.utils.ParseJson;
import com.barodacoder.pilor.model.UserData;
import com.barodacoder.pilor.utils.Validator;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends ActivityBase {
    private EditText etEmail;
    private EditText etPassword;

    private Button btnLogin;

    private TextView tvForgotPass;

    private boolean isBusinessLogin;

    private TextView tvDontHaveAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setStatusBarGradiant(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

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

        isBusinessLogin = getIntent().getBooleanExtra("IS_BUSINESS_LOGIN", false);
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
                .into((ImageView) findViewById(R.id.imgBg));
        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setTypeface(appData.getFontRegular());

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setTypeface(appData.getFontRegular());

        tvDontHaveAcct = (TextView) findViewById(R.id.tvDontHaveAcct);
        tvDontHaveAcct.setTypeface(appData.getFontRegular());

        if (isBusinessLogin)
            tvDontHaveAcct.setVisibility(View.VISIBLE);
        else
            tvDontHaveAcct.setVisibility(View.GONE);

        tvDontHaveAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignupScreen();
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTypeface(appData.getFontRegular());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();

                if (!validated())
                    return;

                userLogin();
            }
        });

        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        tvForgotPass.setTypeface(appData.getFontRegular());

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToForgotPasswordScreen();
            }
        });
    }

    private boolean validated() {
        if (!Validator.validateNameNotNull(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_email));

            return false;
        }

        if (!Validator.validateEmail(etEmail.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_valid_email));

            return false;
        }

        if (!Validator.validateNameNotNull(etPassword.getText().toString())) {
            showMsgDialog(getString(R.string.txt_err_password));

            return false;
        }

        return true;
    }

    private void userLogin() {

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("email", etEmail.getText().toString());
        params.put("password", etPassword.getText().toString());
        params.put("localtime", dateFormat.format(new Date()));
        params.put("device_type", AppConstants.DEVICE_TYPE);
        params.put("device_token", libFile.getDeviceToken());
        params.put("device_id", libFile.getDeviceId());

        client.post(AppConstants.URL_LOGIN, params, new AsyncHttpResponseHandler() {
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
                        Log.v(AppConstants.DEBUG_TAG, "LOGIN RESPONSE : " + response);

                    UserData userData = ParseJson.parseSignUp(response);
                    Log.e("resp", userData.toString());
                    if (userData.getStatusCode() == 1) {
                        appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        libFile.setEmailId(userData.getEmail());

                        libFile.setPassword(etPassword.getText().toString());

                        if (appData.getUserData().getRole().equals("1"))
                            goToHomeScreen();//goToMainAdminScreen();
                        else if(appData.getUserData().getRole().equals("2"))
                            goToBusinessMainScreen();
                        else
                            goToHomeScreen();
                    } else {
                        showMsgDialog(getString(R.string.txt_invalid_email));
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
                    Log.e("failure", responseBody.toString());
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "LOGIN RESPONSE : FAILED : " + response);

                    showMsgDialog(getString(R.string.txt_invalid_email));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
