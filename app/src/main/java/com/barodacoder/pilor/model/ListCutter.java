package com.barodacoder.pilor.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vaibhavi on 5/2/2017.
 */

public class ListCutter implements Serializable {

    private String msg = "";
    private int statusCode;

    private ArrayList<ImageArray> image_array;

     private ArrayList<UserCutter> info;

    public ListCutter() {
        image_array = new ArrayList<>();

          info = new ArrayList<>();
    }

    public ArrayList<UserCutter> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<UserCutter> info) {
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

    public ArrayList<ImageArray> getImage_array() {
        return image_array;
    }

    public void setImage_array(ArrayList<ImageArray> image_array) {
        this.image_array = image_array;
    }
}
