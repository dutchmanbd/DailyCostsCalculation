package com.example.dutchman.dailycostscalculation;

/**
 * Created by dutchman on 5/13/16.
 */
public class Price {

    private int id;
    private String date;
    private double price;
    private String month;
    private String year;

    public Price(){

    }
    public Price(double price){

        this.price = price;

    }
    public Price(int id, String date,double price) {
        this.id = id;
        this.price = price;
        this.date = date;
    }
    public Price(int id, String month, String year, String date,double price) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
