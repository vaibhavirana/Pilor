package com.moonsted.pilors.model;

import java.io.Serializable;

/**
 * Created by Vaibhavi on 5/4/2017.
 */

public class Rating implements Serializable {
    private String rate_id;
    private String cutter_id;
    private String review_text;
    private int review_star;
    private String created_date;
    private String user_id;
    private String display_name;
    private String profile_pic;

    public String getRate_id() {
        return rate_id;
    }

    public void setRate_id(String rate_id) {
        this.rate_id = rate_id;
    }

    public String getCutter_id() {
        return cutter_id;
    }

    public void setCutter_id(String cutter_id) {
        this.cutter_id = cutter_id;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public int getReview_star() {
        return review_star;
    }

    public void setReview_star(int review_star) {
        this.review_star = review_star;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rate_id='" + rate_id + '\'' +
                ", cutter_id='" + cutter_id + '\'' +
                ", review_text='" + review_text + '\'' +
                ", review_star='" + review_star + '\'' +
                ", created_date='" + created_date + '\'' +
                ", user_id='" + user_id + '\'' +
                ", display_name='" + display_name + '\'' +
                ", profile_pic='" + profile_pic + '\'' +
                '}';
    }
}
