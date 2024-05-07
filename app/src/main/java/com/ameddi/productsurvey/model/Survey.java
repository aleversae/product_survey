package com.ameddi.productsurvey.model;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.ameddi.productsurvey.R;

import java.util.List;
import java.util.UUID;

public class Survey {
    String name;
    int Id;
    String details;
    List<Field> fields;
    List<Product> products;

    public static Survey random() {

        Survey survey = new Survey();
        survey.setName( "Survey "+UUID.randomUUID().toString());
        survey.setDetails("descripción descriptiva que describida descriptivamente, describe describaciones");
        return survey;
    }

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

    @NonNull
    @Override
    public String toString() {
        return getName()+ "|"+ getDetails();
    }
}
