package com.moonsted.pilors.model;

import java.io.Serializable;

/**
 * Created by Vaibhavi on 5/4/2017.
 */

public class Service implements Serializable {
    public String service_id;
    public String service_name;
    public String is_service_active;
    public String rate;
    public String user_id;

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getIs_service_active() {
        return is_service_active;
    }

    public void setIs_service_active(String is_service_active) {
        this.is_service_active = is_service_active;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Service{" +
                "service_id='" + service_id + '\'' +
                ", service_name='" + service_name + '\'' +
                ", is_service_active='" + is_service_active + '\'' +
                ", rate='" + rate + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
