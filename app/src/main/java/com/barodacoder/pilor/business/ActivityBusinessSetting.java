package com.barodacoder.pilor.business;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.barodacoder.pilor.ActivityBase;
import com.barodacoder.pilor.R;

public class ActivityBusinessSetting extends ActivityBase {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_setting);

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
        ((TextView) findViewById(R.id.tvService)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvOpeningHours)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvPaymentHistory)).setTypeface(appData.getFontRegular());
        ((TextView) findViewById(R.id.tvSignout)).setTypeface(appData.getFontRegular());


        findViewById(R.id.rlMyProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBusinessProfileScreen();
            }
        });


        findViewById(R.id.rlService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBusinessServiceScreen();
            }
        });

        findViewById(R.id.rlOpeningHours).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBusinessOpeningHoursScreen();
            }
        });

        findViewById(R.id.rlPaymentHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBusinessPaymentHistoryScreen();
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
