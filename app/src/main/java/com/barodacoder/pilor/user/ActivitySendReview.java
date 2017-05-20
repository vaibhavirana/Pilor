package com.barodacoder.pilor.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;
import com.barodacoder.pilor.model.ListBooking;
import com.barodacoder.pilor.rating.ProperRatingBar;
import com.barodacoder.pilor.rating.RatingListener;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class ActivitySendReview extends ActivityBase {
    private Toolbar toolbar;

    private CircularImageView ivImage;

    private EditText etDesc;

    private ProperRatingBar ratingBar;

    private ListBooking booking = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_rating);

        if (getIntent().getSerializableExtra("booking") != null)
            booking = (ListBooking) getIntent().getSerializableExtra("booking");
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

        ((TextView) findViewById(R.id.tvAddPhoto)).setTypeface(appData.getFontRegular());
        ((Button) findViewById(R.id.btnSend)).setTypeface(appData.getFontRegular());

        ((Button) findViewById(R.id.btnSend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProfileData();
            }
        });

        ivImage = (CircularImageView) findViewById(R.id.ivImage);

        ratingBar = (ProperRatingBar) findViewById(R.id.rating);
        ratingBar.setListener(ratingListener);
        if (!appData.getUserData().getProfile().equals("")) {
            Log.e("image", appData.getUserData().getProfile());
            if (appData.getUserData().getProfile().contains("graph.facebook.com")) {
                String profilePic = appData.getUserData().getProfile().replace("http", "https");

                Glide.with(getApplicationContext()).load(profilePic).placeholder(R.drawable.icon_no_image).into(ivImage);
            } else {
                Glide.with(getApplicationContext()).load(appData.getUserData().getProfile()).placeholder(R.drawable.icon_no_image).into(ivImage);
            }
        }

        etDesc = (EditText) findViewById(R.id.etDesc);
        etDesc.setTypeface(appData.getFontRegular());
        //  etDesc.setText(URLDecoder.decode(appData.getUserData().getDisplayName()));


    }

    private void validateProfileData() {
        hideSoftKeyboard();
        if (booking != null)
            sendRating();
    }

    private RatingListener ratingListener = new RatingListener() {
        @Override
        public void onRatePicked(ProperRatingBar ratingBar1) {
            Log.e("rating", ratingBar1.getRating() + "");
            ratingBar.setRating(ratingBar1.getRating());
            /*Snackbar.make(rootView,
                    String.format(getString(R.string.rating_listener_snack_caption), ratingBar.getRating()),
                    Snackbar.LENGTH_SHORT).show();*/
        }
    };

    private void sendRating() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        try {
            params.put("user_id", libFile.getUserId());
            params.put("user_token", libFile.getUserToken());
            params.put("review_text", etDesc.getText().toString());
            params.put("review_star", ratingBar.getRating());
            params.put("cutter_id", booking.service_provide_by);
            params.put("book_id", booking.book_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        client.post(AppConstants.URL_ADD_RATING, params, new AsyncHttpResponseHandler() {
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
                        Log.v(AppConstants.DEBUG_TAG, "UPDATE PROFILE RESPONSE : " + response);

                    /*UserData userData = ParseJson.parseSignUp(response);

                    if (userData.getStatusCode() == 1) {
                        appData.setUserData(userData);

                        libFile.setUserId(appData.getUserData().getUserId());

                        libFile.setUserToken(appData.getUserData().getUserToken());

                        libFile.setEmailId(userData.getEmail());

                        Toast.makeText(ActivitySendReview.this, getString(R.string.txt_success_update_profile), Toast.LENGTH_LONG).show();

                        onBackPressed();
                    } else {
                        showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_failed_update_profile), false);
                    }*/
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
                        Log.v(AppConstants.DEBUG_TAG, "UPDATE PROFILE RESPONSE : FAILED : " + response);

                    showOKAlertMsg(getString(R.string.txt_failed), getString(R.string.txt_failed_update_profile), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
