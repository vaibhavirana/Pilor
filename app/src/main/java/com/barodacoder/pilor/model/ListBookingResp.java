package com.barodacoder.pilor.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vaibhavi on 5/2/2017.
 */

public class ListBookingResp implements Serializable {

    public String msg = "";
    public int statusCode;

    public ArrayList<ListBooking> info;
    public ArrayList<ListBooking> accepted_info;

    public ListBookingResp() {
        info=new ArrayList<>();
        accepted_info=new ArrayList<>();
    }


}
