package com.example.beagosand.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.beagosand.BeagosApp;
import com.example.beagosand.R;
import com.example.beagosand.adapters.NearbyShopsAdapter;
import com.example.beagosand.models.Shop;
import com.example.beagosand.utils.FontsOverride;

import java.util.ArrayList;

public class NearbyShopsActivity extends AppCompatActivity {

    ArrayList<Shop> shops;
    NearbyShopsAdapter adapter;

    RecyclerView rv_shops;

//    BeagosApp.OnListRefreshListener onListRefreshListener;

    public static final String TAG = "MainAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_shops);

        //This is done in every activity to change the font.
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());

        initShops();
        rv_shops = (RecyclerView) findViewById(R.id.activity_nearby_shops_rv);
        adapter = new NearbyShopsAdapter(this, shops);
        rv_shops.setAdapter(adapter);
        rv_shops.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

//        onListRefreshListener = new BeagosApp.OnListRefreshListener() {
//            @Override
//            public void onListRefresh() {
//                adapter.notifyDataSetChanged();
//            }
//        };


//        BeagosApp.getInstance().context = this;
//        BeagosApp.getInstance().onListRefreshListener = onListRefreshListener;
    }

    private void initShops() {
        shops = Shop.getDummyShops();
    }
}
