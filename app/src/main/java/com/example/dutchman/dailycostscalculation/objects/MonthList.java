package com.example.dutchman.dailycostscalculation.objects;

import com.example.dutchman.dailycostscalculation.Product;

import java.util.List;

/**
 * Created by dutchman on 2/15/17.
 */

public class MonthList {

    private String month;
    private String year;

    private List<Product> products;

    private double tatalCost;

    public MonthList(String month, String year,List<Product> products, double tatalCost){

        this.month = month;
        this.year = year;
        this.products = products;
        this.tatalCost = tatalCost;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTatalCost() {
        return tatalCost;
    }

    public void setTatalCost(double tatalCost) {
        this.tatalCost = tatalCost;
    }
}
