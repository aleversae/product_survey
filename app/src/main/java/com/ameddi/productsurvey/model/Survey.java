package com.ameddi.productsurvey.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Survey implements  Parcelable {
    int id;
    String name;
    String details;
    String document;
    List<Field> fields;
    List<Product> products;
    private String remoteKey;


    public Survey() {

    }



    public static Survey random() {

        Survey survey = new Survey();
        survey.setName("Survey " + UUID.randomUUID().toString());
        survey.setDetails("descripción descriptiva que describida descriptivamente, describe describaciones");
        return survey;
    }

    public static Survey defaultValue() {
        Survey survey = new Survey();
        survey.setName("");
        survey.setDetails("");
        survey.setFields(new ArrayList<>());
        survey.setProducts(new ArrayList<>());
        return survey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void addField(String name, Field field) {


    }


    @NonNull
    @Override
    public String toString() {
        return getName() + "|" + getDetails();
    }

    //Parcelable interface
    protected Survey(Parcel in) {
        id = in.readInt();
        name = in.readString();
        details = in.readString();
        document = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(details);
        dest.writeString(document);
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }

    };

    public void setRemoteKey(String remoteKey) {
        this.remoteKey = remoteKey;
    }

    public String getRemoteKey() {
        return remoteKey;
    }
}
