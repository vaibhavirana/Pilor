package com.moonsted.pilors.model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserCutter extends UserData implements Serializable {

    private String distance = "";
    private String min_rate = "";
    private int avarage_rating ;
    private ArrayList<Service> service_info ;


    //private String winCount = "";


    public String getMin_rate() {
        return min_rate;
    }

    public void setMin_rate(String min_rate) {
        this.min_rate = min_rate;
    }

    public int getAvarage_rating() {
        return avarage_rating;
    }

    public void setAvarage_rating(int avarage_rating) {
        this.avarage_rating = avarage_rating;
    }

    public ArrayList<Service> getService_info() {
        return service_info;
    }

    public void setService_info(ArrayList<Service> service_info) {
        this.service_info = service_info;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }


}
