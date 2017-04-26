package com.barodacoder.pilor;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.barodacoder.pilor.utils.ParseJson;
import com.barodacoder.pilor.utils.UserData;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends ActivityBase {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
        //printHashKey();
        initUi();
    }

    protected void initData()
    {
        super.initData();
    }
    private void initUi()
    {
        try
        {
            String tokenId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            libFile.setDeviceId(String.valueOf(tokenId));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        Glide.with(this)
                .load(R.drawable.bg_frontpage)
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into((ImageView)findViewById(R.id.imgBg));

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(libFile.isFbLogin())
                            facebookLogin();
                        else if(!libFile.getEmailId().equals("") && !libFile.getPassword().equals(""))
                            userLogin();
                        else
                            goToLandingScreen();
                    }
                });
            }
        }, 2000);
    }

    private void facebookLogin()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("thirdparty_id", libFile.getFbId());
        params.put("display_name", libFile.getDisplayName());
        params.put("email", libFile.getEmailId());
        //params.put("gender", gender);
        params.put("localtime", dateFormat.format(new Date()));
        params.put("device_type", AppConstants.DEVICE_TYPE);
        params.put("device_token", libFile.getDeviceToken());
        params.put("device_id", libFile.getDeviceId());

        client.post(AppConstants.URL_LOGIN_FACEBOOK, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();

                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                //cancelProgressDialog();

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "FB LOGIN RESPONSE : " + response);

                    //{"msg":"Email invalid","status_code":9}

                    UserData userData = ParseJson.parseSignUp(response);

                    if(userData.getStatusCode() == 1)
                    {
                        appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        if(appData.getUserData().getRole().equals("1"))
                            goToHomeScreen();//goToMainAdminScreen();
                        /*else if(appData.getUserData().getRole().equals("2"))
                            goToBusinessMainScreen();*/
                        else
                            goToHomeScreen();
                    }
                    else
                    {
                        goToLandingScreen();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                //cancelProgressDialog();

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "FB LOGIN RESPONSE : FAILED : " + response);

                    goToLandingScreen();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void userLogin()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("email", libFile.getEmailId());
        params.put("password", libFile.getPassword());
        params.put("localtime", dateFormat.format(new Date()));
        params.put("latitude", libFile.getLatitude());
        params.put("longitude", libFile.getLongitude());
        params.put("device_type", AppConstants.DEVICE_TYPE);
        params.put("device_token", libFile.getDeviceToken());
        params.put("device_id", libFile.getDeviceId());

        client.post(AppConstants.URL_LOGIN, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();

                //showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                //cancelProgressDialog();

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "LOGIN RESPONSE : " + response);

                    UserData userData = ParseJson.parseSignUp(response);

                    if(userData.getStatusCode() == 1)
                    {
                        appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        if(appData.getUserData().getRole().equals("1"))
                            goToHomeScreen();//goToMainAdminScreen();
                       /* else if(appData.getUserData().getRole().equals("2"))
                            goToBusinessMainScreen();*/
                        else
                            goToHomeScreen();
                    }
                    else
                    {
                        goToLandingScreen();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();

                    goToLandingScreen();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                //cancelProgressDialog();

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "LOGIN RESPONSE : FAILED : " + response);

                    goToLandingScreen();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public  void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.barodacoder.pilor",PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("hashkey", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }

}
