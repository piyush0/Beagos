package com.example.beagosand.models;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by piyush0 on 29/12/16.
 */

public class Shop extends RealmObject {

    String name;
    String UUID;
    String address;
    String imageURL;
    Float rating;


    public Shop() {
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Shop(String name, Float rating, String address, String UUID) {
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.UUID = UUID;
    }

    public Shop(String name, String UUID, String address, String imageURL) {
        this.name = name;
        this.UUID = UUID;
        this.address = address;
        this.imageURL = imageURL;
    }
}
