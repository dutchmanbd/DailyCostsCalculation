package com.example.dutchman.dailycostscalculation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dutchman on 5/13/16.
 */
public class Product {

    private int id;
    private String date,time;
    private String category;
    private double price;
    private String month;
    private String year;


    public Product(){

    }

    public Product(String month, String year, String date,String time, String category,double price) {
        this.month = month;
        this.year = year;
        this.date = date;
        this.time = time;
        this.price = price;
        this.category = category;
    }

    public Product(int id,String month, String year, String date, String time, String category,double price) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.date = date;
        this.time = time;
        this.price = price;
        this.category = category;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date t = null;

        try {
            t = timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.time = timeFormat.format(t);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.date = dateFormat.format(d);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    @Override
    public String toString() {
        return this.getDate()+" "+this.getPrice();
    }
}
