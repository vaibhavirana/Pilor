package com.barodacoder.pilor.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vaibhavi on 5/2/2017.
 */

public class ListRating implements Serializable {

    private String msg = "";
    private int statusCode;

     private ArrayList<Rating> info;

    public ListRating() {
          info = new ArrayList<>();
    }

    public ArrayList<Rating> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Rating> info) {
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
