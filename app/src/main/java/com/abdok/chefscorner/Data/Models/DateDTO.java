package com.abdok.chefscorner.Data.Models;

import java.io.Serializable;

public class DateDTO implements Serializable {

    private String date;
    private String subDate;
    private String day;


    public DateDTO() {
    }

    public DateDTO(String date, String subDate, String day) {
        this.date = date;
        this.subDate = subDate;
        this.day = day;
    }


    public String getSubDate() {
        return subDate;
    }

    public void setSubDate(String subDate) {
        this.subDate = subDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
