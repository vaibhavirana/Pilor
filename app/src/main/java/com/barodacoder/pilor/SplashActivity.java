package com.barodacoder.pilor;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.barodacoder.pilor.utils.AppData;
import com.barodacoder.pilor.utils.LibFile;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    protected AppData appData;

    protected LibFile libFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUi();
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

        ((TextView) findViewById(R.id.tvAppName)).setTypeface(appData.getFontMedium());

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

    private void goToLandingScreen() {

    }


    private void userLogin() {
    }

    private void facebookLogin() {
    }
}
