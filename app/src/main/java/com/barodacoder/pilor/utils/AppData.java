package com.barodacoder.pilor.utils;

import android.content.Context;
import android.graphics.Typeface;

public class AppData
{
    private static AppData instance;
    private Context context;

    private Typeface fontRegular;
    private Typeface fontLight;
    private Typeface fontBold;
    private Typeface fontMedium;

    private double latitude;
    private double longitude;

    private UserData userData;

    private AppData(Context context)
    {
        this.context = context;
        userData = new UserData();

        initFonts();
    }

    public static AppData getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new AppData(context);
        }
        return instance;
    }

    private void initFonts()
    {
        setFontRegular(Typeface.createFromAsset(context.getAssets(), "SF-UI-Text-Regular.otf"));

        setFontLight(Typeface.createFromAsset(context.getAssets(), "SF-UI-Text-Light.otf"));

        setFontBold(Typeface.createFromAsset(context.getAssets(), "SF-UI-Text-Semibold.otf"));

        setFontMedium(Typeface.createFromAsset(context.getAssets(), "SF-UI-Text-Medium.otf"));
    }

    public Typeface getFontRegular() {
        return fontRegular;
    }

    public void setFontRegular(Typeface fontRegular) {
        this.fontRegular = fontRegular;
    }

    public Typeface getFontBold() {
        return fontBold;
    }

    public void setFontBold(Typeface fontBold) {
        this.fontBold = fontBold;
    }

    public Typeface getFontLight() {
        return fontLight;
    }

    public void setFontLight(Typeface fontLight) {
        this.fontLight = fontLight;
    }

    public Typeface getFontMedium() {
        return fontMedium;
    }

    public void setFontMedium(Typeface fontMedium) {
        this.fontMedium = fontMedium;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
