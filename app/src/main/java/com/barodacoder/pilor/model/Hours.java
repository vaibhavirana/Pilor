package com.barodacoder.pilor.model;

/**
 * Created by Palak on 25-01-2017.
 */

public class Hours {

   // @SerializedName("start_time")
    String startTime;

   // @SerializedName("end_time")
    String endTime;

    @Override
    public String toString() {
        return "Hours{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
