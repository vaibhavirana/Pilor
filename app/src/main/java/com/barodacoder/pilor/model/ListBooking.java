package com.barodacoder.pilor.model;

import java.io.Serializable;

/**
 * Created by Vaibhavi on 5/4/2017.
 */

public class ListBooking implements Serializable {
    public String user_id;
    public String service_id;
    public String book_id;
    public String service_provide_by;
    public String date_of_booking;
    public String accepted_date;
    public String created_date;
    public String localtime;
    public String localtime_UTC;
    public String price;
    public int is_service_accepted;
    public String is_reschedule;
    public String is_done;
    public String is_review_added;
    public String hide_by_business;
    public String display_name;
    public String mobile;
    public String email;
    public String latitude;
    public String longitude;
    public String category_name;
    public String profile_pic;
    public String first_name;
    public String last_name;
}

