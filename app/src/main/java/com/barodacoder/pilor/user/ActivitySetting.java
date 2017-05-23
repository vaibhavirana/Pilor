package com.barodacoder.pilor.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.AppConstants;
import com.barodacoder.pilor.R;

public class ActivitySetting extends ActivityBase {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

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
        //toolbar.setTitle("Back");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.tvTitle)).setTypeface(appData.getFontMedium());

        ((TextView) findViewById(R.id.tvMyProfile)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvPassword)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvRedius)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvContactUs)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvHairCutterSignup)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvRecommendUs)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvTerms)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvFaq)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvSignout)).setTypeface(appData.getFontRegular());


        ((TextView) findViewById(R.id.tvRedius)).setText(getString(R.string.txt_redius)+ " "+libFile.getRedius() +" km");
        ((SeekBar) findViewById(R.id.sbRedius)).setProgress(libFile.getRedius());
        ((SeekBar) findViewById(R.id.sbRedius)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                libFile.setRedius(progress);
               // ((TextView) findViewById(R.id.tvRedius)).setText("");
                ((TextView) findViewById(R.id.tvRedius)).setText(getString(R.string.txt_redius)+ " "+libFile.getRedius() +" km");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if (libFile.isFbLogin()) {
            findViewById(R.id.rlPin).setVisibility(View.GONE);
            findViewById(R.id.viewChangePass).setVisibility(View.GONE);
        }

        findViewById(R.id.rlMyProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToYourProfileScreen();
            }
        });

        findViewById(R.id.rlHairCutterSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebsite(AppConstants.URL_BECOME_CUTTER);
            }
        });

        findViewById(R.id.rlPaymentCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEnterPinScreen(null);
            }
        });

        findViewById(R.id.rlPin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChangePasscodeScreen();
            }
        });

        findViewById(R.id.rlContactUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadContact();
                loadWebsite(AppConstants.URL_CONTACT);
            }
        });

        findViewById(R.id.rlRecommendUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebsite(AppConstants.URL_RECOMMENDUS);
            }
        });

        findViewById(R.id.rlTerms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebsite(AppConstants.URL_TERMS);
            }
        });

        findViewById(R.id.rlFaq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWebsite(AppConstants.URL_FAQ);
            }
        });

        findViewById(R.id.rlSignout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignOutAlertMsg(getString(R.string.txt_sign_out), getString(R.string.txt_are_you_sure));
            }
        });

    }

    private void showSignOutAlertMsg(String title, String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setPositiveButton(getString(R.string.txt_yes_logout), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String deciceId = libFile.getDeviceId();
                String deviceToken = libFile.getDeviceToken();

                libFile.clearCache();

                //libFile.setFirstTime(true);

                libFile.setDeviceId(deciceId);
                libFile.setDeviceToken(deviceToken);

                dialog.dismiss();

                goToLandingWithClearCache();
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.txt_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setTitle(title);

        dialogBuilder.setMessage(msg);

        dialogBuilder.show();
    }
}
