package com.example.beagosand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.beagosand.models.Shop;

import java.util.ArrayList;

public class NearbyShopsActivity extends AppCompatActivity {

    ArrayList<Shop> shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_shops);
    }
}
