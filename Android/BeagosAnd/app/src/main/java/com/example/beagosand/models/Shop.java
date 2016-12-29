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
    Double rating;

    public static ArrayList<Shop> getDummyShop() {

        ArrayList<Shop> retVal = new ArrayList<>();

        retVal.add(new Shop("Apple", 70.0, "IOS Room", "abcd"));
        retVal.add(new Shop("Adidas", 42.2, "Python Room", "efgh"));
        retVal.add(new Shop("Reebok", 31.4, "Ruby Room", "ijkl"));
        retVal.add(new Shop("Kfc", 77.5, "Git Room", "abcd"));


        return retVal;
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

    public Shop(String name, Double rating, String address, String UUID) {
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
