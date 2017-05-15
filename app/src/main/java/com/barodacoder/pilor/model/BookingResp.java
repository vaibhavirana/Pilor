package com.barodacoder.pilor.model;

import java.io.Serializable;

/**
 * Created by Vaibhavi on 5/2/2017.
 */

public class BookingResp implements Serializable {

    public String msg = "";
    public int statusCode;

    public BookService info;

    public BookingResp() {

    }


}
