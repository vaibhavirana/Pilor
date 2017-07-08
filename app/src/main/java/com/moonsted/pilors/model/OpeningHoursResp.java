package com.moonsted.pilors.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vaibhavi on 5/2/2017.
 */

public class OpeningHoursResp implements Serializable {

    public String msg = "";
    public int statusCode;

    public ArrayList<Hours> info;

    public OpeningHoursResp() {
        info = new ArrayList<>();
    }

    public ArrayList<Hours> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Hours> info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
