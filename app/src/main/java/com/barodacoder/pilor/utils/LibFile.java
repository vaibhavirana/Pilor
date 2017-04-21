package com.barodacoder.pilor.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LibFile
{
    Context context;

    SharedPreferences settings;

    public static final String PREFS_FILE_NAME_PARAM = "PILOR.PREF";

    private static LibFile instance;

    public static LibFile getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new LibFile(context);

        }
        return instance;
    }

    private LibFile(Context context)
    {
        this.context  = context;

        settings = context.getSharedPreferences(PREFS_FILE_NAME_PARAM , 0);
    }

    public String getUserId()
    {
        return settings.getString("user_id", "");
    }

    public void setUserId(String userId)
    {
        settings.edit().putString("user_id",userId).commit();
    }

    public String getUserToken()
    {
        return settings.getString("user_token", "");
    }

    public void setUserToken(String userToken)
    {
        settings.edit().putString("user_token",userToken).commit();
    }

    public String getLatitude()
    {
        return settings.getString("latitude", "");
    }

    public void setLatitude(String latitude)
    {
        settings.edit().putString("latitude",latitude).commit();
    }

    public String getLongitude()
    {
        return settings.getString("longitude", "");
    }

    public void setLongitude(String longitude)
    {
        settings.edit().putString("longitude",longitude).commit();
    }

    public String getEmailId()
    {
        return settings.getString("email_id", "");
    }

    public void setEmailId(String emailId)
    {
        settings.edit().putString("email_id",emailId).commit();
    }

    public String getPassword()
    {
        return settings.getString("password", "");
    }

    public void setPassword(String password)
    {
        settings.edit().putString("password",password).commit();
    }

    public boolean isFbLogin()
    {
        return settings.getBoolean("IS_FB_LOGIN", false);
    }

    public void setFbLogin(boolean password)
    {
        settings.edit().putBoolean("IS_FB_LOGIN", password).commit();
    }

    public String getDisplayName()
    {
        return settings.getString("display_name", "");
    }

    public void setDisplayName(String emailId)
    {
        settings.edit().putString("display_name", emailId).commit();
    }

    public String getFbId()
    {
        return settings.getString("fb_id", "");
    }

    public void setFbId(String emailId)
    {
        settings.edit().putString("fb_id",emailId).commit();
    }

    public String getDeviceId()
    {
        return settings.getString("device_id", "");
    }

    public void setDeviceId(String deviceId)
    {
        settings.edit().putString("device_id",deviceId).commit();
    }

    public String getDeviceToken()
    {
        return settings.getString("device_token", "");
    }

    public void setDeviceToken(String deviceToken)
    {
        settings.edit().putString("device_token", deviceToken).commit();
    }

    public String getCardNumber()
    {
        return settings.getString("card_number", "");
    }

    public void setCardNumber(String cardNumber)
    {
        settings.edit().putString("card_number",cardNumber).commit();
    }

    public String getSubscriptionId()
    {
        return settings.getString("subscriptionId", "");
    }

    public void setSubscriptionId(String subscriptionId)
    {
        settings.edit().putString("subscriptionId",subscriptionId).commit();
    }

    public String getPaymentType()
    {
        return settings.getString("payment_type", "");
    }

    public void setPaymentType(String paymentType)
    {
        settings.edit().putString("payment_type",paymentType).commit();
    }

    public String getPin()
    {
        return settings.getString("pin", "");
    }

    public void setPin(String pin)
    {
        settings.edit().putString("pin",pin).commit();
    }

    public String getPasscode()
    {
        return settings.getString("passcode", "6581");
    }

    public void setPasscode(String passcode)
    {
        settings.edit().putString("passcode",passcode).commit();
    }

    public void clearCache()
    {
        settings.edit().clear().commit();
        //settings.edit().remove("CurrentUserPass").commit();
    }
}
