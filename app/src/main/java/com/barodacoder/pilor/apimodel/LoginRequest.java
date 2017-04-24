package com.barodacoder.pilor.apimodel;

/**
 * Created by Vaibhavi on 4/23/2017.
 */

public class LoginRequest {
    public String email,password,localtime,device_token,device_id;
    public int device_type;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", localtime='" + localtime + '\'' +
                ", device_token='" + device_token + '\'' +
                ", device_id='" + device_id + '\'' +
                ", device_type=" + device_type +
                '}';
    }
}
