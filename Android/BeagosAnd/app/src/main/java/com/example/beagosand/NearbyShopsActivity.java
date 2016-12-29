package com.example.beagosand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.beagosand.adapters.NearbyShopsAdapter;
import com.example.beagosand.models.Shop;

import java.util.ArrayList;

public class NearbyShopsActivity extends AppCompatActivity {

    ArrayList<Shop> shops;

    RecyclerView rv_shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_shops);


        initShops();
        rv_shops = (RecyclerView) findViewById(R.id.activity_nearby_shops_rv);
        rv_shops.setAdapter(new NearbyShopsAdapter(this, shops));
        rv_shops.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initShops() {
        shops = Shop.getDummyShop();
    }
}
