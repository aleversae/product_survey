package com.ameddi.productsurvey.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Survey {
    String name;
    int Id;
    String details;
    List<Field> fields;
    List<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addField(String name, Field field){

    }
}
