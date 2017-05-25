package com.barodacoder.pilor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.barodacoder.pilor.utils.ParseJson;
import com.barodacoder.pilor.model.UserData;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LandingActivity extends ActivityBase {
    private Button btnSignupFb, btnLoginBusiness, btnLogin;

    private TextView tvLoginEmail;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private String fbId;
    private String firstName;
    private String lastName;
    private String email;
    private String displayName;
    private String gender;
    private String dob;
    private String fbImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setStatusBarGradiant(this);

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);

        // logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"));
        setContentView(R.layout.activity_landing);

        initData();

        callbackManager = CallbackManager.Factory.create();

        loginButton = ((LoginButton) findViewById(R.id.login_button));

        List<String> permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile");
        loginButton.setReadPermissions(permissionNeeds);

        logout();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v(AppConstants.DEBUG_TAG, "FIRST TOKEN : " + loginResult.getAccessToken().getToken());
                GraphRequest request = GraphRequest.newMeRequest
                        (loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (AppConstants.DEBUG)
                                    Log.v(AppConstants.DEBUG_TAG, "User Response : " + response.toString() + "::" + object.toString());

                                //{Response:  responseCode: 200, graphObject: {"id":"225856644505714","name":"Baroda Bjp","email":"barodacoders11@gmail.com","gender":"male"}, error: null}::{"id":"225856644505714","name":"Baroda Bjp","email":"barodacoders11@gmail.com","gender":"male"}

                                try {
                                    if (AppConstants.DEBUG)
                                        Log.v(AppConstants.DEBUG_TAG, "User Response : IF ");

                                    fbId = object.getString("id");
                                    displayName = object.getString("name");
                                    if (object.has("email"))
                                        email = object.getString("email");
                                    if (object.has("gender"))
                                        gender = object.getString("gender");
                                    if (object.has("birthday"))
                                        dob = object.getString("birthday");

                                    //appData.getCurrentUser().setUserEmail(email);
                                    //appData.getCurrentUser().setUserDisplayName(name);

                                    fbImageUrl = "http://graph.facebook.com/" + fbId + "/picture?type=large";

                                    if (AppConstants.DEBUG)
                                        Log.v(AppConstants.DEBUG_TAG, "FB IMAGE URL : " + fbImageUrl);

                                    //downloadImage(fbId, imageUrl);

                                    facebookLogin();

                                    if (AppConstants.DEBUG)
                                        Log.v(AppConstants.DEBUG_TAG, "User Response : " + fbId + ", " + dob + ", " + displayName + ", " + email + ", " + gender);

                                } catch (JSONException e) {
                                    if (AppConstants.DEBUG)
                                        Log.v(AppConstants.DEBUG_TAG, "User Response : ELSE ");
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                exception.printStackTrace();
                /*if (AppConstants.DEBUG)
                    Log.v(AppConstants.DEBUG_TAG, exception.getCause().toString());*/
            }
        });

        initUi();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void initData() {
        super.initData();
    }

    private void initUi() {
        //((TextView) findViewById(R.id.tvAppName)).setTypeface(appData.getFontMedium());

        //((TextView) findViewById(R.id.tvLandingMsg)).setTypeface(appData.getFontRegular());

        Glide.with(this)
                .load(R.drawable.bg_frontpage)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into((ImageView) findViewById(R.id.imgBg));


        btnSignupFb = (Button) findViewById(R.id.btnSignupFb);
        btnSignupFb.setTypeface(appData.getFontRegular());

        btnSignupFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

        btnLoginBusiness = (Button) findViewById(R.id.btnLoginBusiness);
        btnLoginBusiness.setTypeface(appData.getFontRegular());

        btnLoginBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen(true);
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTypeface(appData.getFontRegular());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginScreen(false);
            }
        });

        tvLoginEmail = (TextView) findViewById(R.id.tvLoginEmail);
        tvLoginEmail.setTypeface(appData.getFontRegular());

        tvLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignupScreen();
            }
        });
    }

    private void facebookLogin() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("thirdparty_id", fbId);
        params.put("display_name", displayName);
        params.put("email", email);
        params.put("gender", gender);
        params.put("localtime", dateFormat.format(new Date()));
        params.put("device_type", AppConstants.DEVICE_TYPE);
        params.put("device_token", libFile.getDeviceToken());
        params.put("device_id", libFile.getDeviceId());


        client.post(AppConstants.URL_LOGIN_FACEBOOK, params, new AsyncHttpResponseHandler() {
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
                        Log.v(AppConstants.DEBUG_TAG, "FB LOGIN RESPONSE : " + response);

                    //{"msg":"Email invalid","status_code":9}

                    UserData userData = ParseJson.parseSignUp(response);

                    if (userData.getStatusCode() == 1) {
                        appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        libFile.setEmailId(userData.getEmail());

                        libFile.setDisplayName(displayName);

                        libFile.setFbId(fbId);

                        libFile.setFbLogin(true);

                        if (appData.getUserData().getRole().equals("1"))
                            goToHomeScreen();//goToMainAdminScreen();
                        /*else if(appData.getUserData().getRole().equals("2"))
                            goToBusinessMainScreen();*/
                        else
                            goToHomeScreen();
                    } else {
                        showMsgDialog(getString(R.string.txt_invalid_email));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                cancelProgressDialog();

                try {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG)
                        Log.v(AppConstants.DEBUG_TAG, "FB LOGIN RESPONSE : FAILED : " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logout() {
        LoginManager.getInstance().logOut();
    }
}
