package com.barodacoder.pilor.business;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.utils.Validator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ActivityBusinessChangePassword extends ActivityBase
{
    private Toolbar toolbar;

    private EditText etCurrentPassword, etNewPassword, etReNewPassword;

    private Button btnChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);

        initData();

        initUi();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right);

        finish();
    }

    protected void initData()
    {
        super.initData();
    }

    private void initUi()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.tvTitle)).setTypeface(appData.getFontMedium());

        etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etCurrentPassword.setTypeface(appData.getFontRegular());

        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPassword.setTypeface(appData.getFontRegular());

        etReNewPassword = (EditText) findViewById(R.id.etReNewPassword);
        etReNewPassword.setTypeface(appData.getFontRegular());

        btnChangePass = (Button) findViewById(R.id.btnChangePass);
        btnChangePass.setTypeface(appData.getFontRegular());

        btnChangePass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                hideSoftKeyboard();

                if(!validate())
                    return;

                changePassword();
            }
        });
    }

    private boolean validate()
    {
        if (!Validator.validateNameNotNull(etCurrentPassword.getText().toString()))
        {
            showSnackBar(findViewById(R.id.rlMain), getString(R.string.txt_err_curr_pass));

            return false;
        }

        if (!Validator.validateNameNotNull(etNewPassword.getText().toString()))
        {
            showSnackBar(findViewById(R.id.rlMain), getString(R.string.txt_err_new_pass));

            return false;
        }

        if (!Validator.validateNameNotNull(etReNewPassword.getText().toString()))
        {
            showSnackBar(findViewById(R.id.rlMain), getString(R.string.txt_err_renew_pass));

            return false;
        }

        if(!etCurrentPassword.getText().toString().equals(libFile.getPassword()))
        {
            showSnackBar(findViewById(R.id.rlMain), getString(R.string.txt_err_match_curr_pass));

            return false;
        }

        if(!etNewPassword.getText().toString().equals(etReNewPassword.getText().toString()))
        {
            showSnackBar(findViewById(R.id.rlMain), getString(R.string.txt_err_match_new_pass));

            return false;
        }

        return true;
    }

    private void changePassword()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        try
        {
            params.put("user_id", libFile.getUserId());
            params.put("user_token", libFile.getUserToken());
            params.put("current_password", etCurrentPassword.getText().toString());
            params.put("new_password", etNewPassword.getText().toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        client.post(AppConstants.URL_CHANGE_PASSWORD, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();

                showProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                cancelProgressDialog();

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "CHANGE PASSWORD RESPONSE : " + response);

                    JSONObject jsonRoot = new JSONObject(response);

                    if(jsonRoot.has("status_code") && jsonRoot.get("status_code").toString().equals("1"))
                    {
                        libFile.setPassword(etNewPassword.getText().toString());

                        Toast.makeText(ActivityBusinessChangePassword.this, getString(R.string.txt_success_change_pass), Toast.LENGTH_LONG).show();

                        onBackPressed();
                    } else
                    {
                        showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_failed_change_pass), false);
                    }

                 /* //  String resp = ParseJson.parseCreateTask(response);

                    if(resp.equals("OK"))
                    {
                        libFile.setPassword(etNewPassword.getText().toString());

                        Toast.makeText(ActivityChangePassword.this, getString(R.string.txt_success_change_pass), Toast.LENGTH_LONG).show();

                        onBackPressed();
                    }
                    else
                    {
                        showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_failed_change_pass), false);
                    }*/
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
                cancelProgressDialog();

                try
                {
                    String response = new String(responseBody, "UTF-8");

                    if (AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "CHANGE PASSWORD RESPONSE : FAILED : " + response);

                    showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_failed_change_pass), false);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
