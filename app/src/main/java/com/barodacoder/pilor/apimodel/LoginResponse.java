package com.barodacoder.pilor.apimodel;

/**
 * Created by Vaibhavi on 4/23/2017.
 */

public class LoginResponse {
    public int status_code;
    public String msg;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status_code=" + status_code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
