package com.moonsted.pilors.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.moonsted.pilors.ActivityBase;
import com.moonsted.pilors.AppConstants;
import com.moonsted.pilors.R;
import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;

public class ActivityChangePasscode extends ActivityBase implements BlurLockView.OnPasswordInputListener, BlurLockView.OnLeftButtonClickListener
{
    private BlurLockView blurlockview;

    private boolean isFirstTime = true, isNewPasswordTime, isNewPassAdded;

    private String newPasscode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_pin);

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
        blurlockview = (BlurLockView)findViewById(R.id.blurlockview);

        if(newPasscode.equals(""))
            blurlockview.setCorrectPassword(libFile.getPasscode());
        else
            blurlockview.setCorrectPassword(newPasscode);

        if(isFirstTime)
        {
            blurlockview.setTitle(getString(R.string.txt_change_passcode));
        }
        else
        {
            blurlockview.setTitle(getString(R.string.txt_confirm_passcode));
        }

        blurlockview.setLeftButton(getString(R.string.txt_cancel));
        blurlockview.setRightButton(getString(R.string.txt_delete));
        blurlockview.setTypeface(appData.getFontRegular());
        blurlockview.setType(Password.NUMBER, false);

        //blurlockview.show(500, ShowType.FROM_TOP_TO_BOTTOM, EaseType.Linear);

        blurlockview.setOnLeftButtonClickListener(this);
        blurlockview.setOnPasswordInputListener(this);
    }

    @Override
    public void correct(String inputPassword)
    {
        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : CORRECT : "+inputPassword);

        //Toast.makeText(ActivityEnterPin.this, "Password Correct", Toast.LENGTH_SHORT).show();

        //blurlockview.hide(500, HideType.FROM_BOTTOM_TO_TOP, EaseType.Linear);

        if(isFirstTime)
        {
            isFirstTime = false;

            initUi();

            blurlockview.setTitle(getString(R.string.txt_new_passcode));

            isNewPasswordTime = true;
        }
        else if(isNewPasswordTime)
        {
            isNewPasswordTime = false;

            newPasscode = inputPassword;

            blurlockview.setCorrectPassword(newPasscode);

            isNewPassAdded = true;

            blurlockview.setTitle(getString(R.string.txt_confirm_passcode));

            initUi();
        }
        else
        {
            if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : CORRECT : ELSE : "+inputPassword);

            if(isNewPassAdded)
            {
                if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : CORRECT : ELSE IF: "+inputPassword);

                if(newPasscode.equals(inputPassword))
                {
                    if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : CORRECT : ELSE IF1 : "+inputPassword);

                    Toast.makeText(ActivityChangePasscode.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                    libFile.setPasscode(inputPassword);

                    onBackPressed();
                }
                else
                {
                    if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : CORRECT : ELSE ELSE1 : "+inputPassword);

                    Toast.makeText(ActivityChangePasscode.this, "Password does not match", Toast.LENGTH_SHORT).show();

                    initUi();
                }
            }
            else
            {
                if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : CORRECT : ELSE ELSE: "+inputPassword);

                Toast.makeText(ActivityChangePasscode.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                libFile.setPasscode(inputPassword);

                onBackPressed();
            }
        }
    }

    @Override
    public void incorrect(String inputPassword)
    {
        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INCORRECT : "+inputPassword);

        if(isFirstTime)
        {
            Toast.makeText(ActivityChangePasscode.this, "Incorrect password", Toast.LENGTH_SHORT).show();
        }
        else if(isNewPasswordTime)
        {
            isNewPasswordTime = false;

            newPasscode = inputPassword;

            isNewPassAdded = true;

            blurlockview.setTitle(getString(R.string.txt_confirm_passcode));

            initUi();
        }
        else
        {
            if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INCORRECT : ELSE : "+inputPassword);

            if(isNewPassAdded)
            {
                if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INCORRECT : ELSE IF: "+newPasscode + "::" + inputPassword);

                if(newPasscode.equals(inputPassword))
                {
                    if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INCORRECT : ELSE IF1 : "+inputPassword);

                    Toast.makeText(ActivityChangePasscode.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                    libFile.setPasscode(inputPassword);

                    onBackPressed();
                }
                else
                {
                    if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INCORRECT : ELSE ELSE1 : "+inputPassword);

                    Toast.makeText(ActivityChangePasscode.this, "Password does not match", Toast.LENGTH_SHORT).show();

                    initUi();
                }
            }
            else
            {
                if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INCORRECT : ELSE ELSE: "+inputPassword);

                Toast.makeText(ActivityChangePasscode.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                libFile.setPasscode(inputPassword);

                onBackPressed();
            }
        }
    }

    @Override
    public void input(String inputPassword)
    {
        if(AppConstants.DEBUG) Log.v(AppConstants.DEBUG_TAG, "PASSWORD : INPUT : "+inputPassword);
    }

    @Override
    public void onClick()
    {
        onBackPressed();
    }
}
