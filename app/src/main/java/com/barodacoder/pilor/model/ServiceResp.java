package com.barodacoder.pilor.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vaibhavi on 5/2/2017.
 */

public class ServiceResp implements Serializable {

    public String msg = "";
    public int statusCode;

    public ArrayList<Service> info;


    public ServiceResp() {
        info=new ArrayList<>();
    }


}
