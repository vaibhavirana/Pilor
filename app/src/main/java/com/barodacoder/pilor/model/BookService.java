package com.barodacoder.pilor.model;

import java.io.Serializable;

/**
 * Created by Vaibhavi on 5/4/2017.
 */

public class BookService implements Serializable {
    public String service_id,book_id,service_provide_by;
    public String date_of_booking;
    public String localtime,localtime_UTC;
    public String payment_status,payment_status_msg,price;
    public String user_id;


}
