package com.example.beagosand.models;

import java.util.ArrayList;

/**
 * Created by piyush0 on 29/12/16.
 */

public class Shop {

    String name;
    String UUID;
    String address;
    String imageURL;
    Float rating;

    public static ArrayList<Shop> getDummyShops() {

        ArrayList<Shop> retVal = new ArrayList<>();

        retVal.add(new Shop("Apple", 70.0F, "iOS Room", "0x0117c55fc452"));
        retVal.add(new Shop("Adidas", 42.2F, "Python Room", "0x0117c555c65f"));
        retVal.add(new Shop("Reebok", 31.4F, "Ruby Room", "0x0117c55ec086"));
        retVal.add(new Shop("Kfc", 77.5F, "Git Room", "0x0117c55be3a8"));


        return retVal;
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
