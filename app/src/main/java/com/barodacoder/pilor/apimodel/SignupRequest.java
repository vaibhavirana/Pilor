package com.barodacoder.pilor.apimodel;

/**
 * Created by Vaibhavi on 4/23/2017.
 */

public class SignupRequest {
    public String email,password,device_token,device_id,profile_pic,display_name;
    public int device_type;
    public String latitude,longitude;

    @Override
    public String toString() {
        return "SignupRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", device_token='" + device_token + '\'' +
                ", device_id='" + device_id + '\'' +
                ", profile_pic='" + profile_pic + '\'' +
                ", display_name='" + display_name + '\'' +
                ", device_type=" + device_type +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
