package com.example.beagosand.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.beagosand.models.Shop;

import java.util.ArrayList;

/**
 * Created by piyush0 on 29/12/16.
 */

public class NearbyShopsAdapter extends RecyclerView.Adapter<NearbyShopsAdapter.ShopViewHolder> {

    Context context;
    ArrayList<Shop> shops;

    public NearbyShopsAdapter(Context context, ArrayList<Shop> shops) {
        this.context = context;
        this.shops = shops;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return null;
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_shop;
        TextView tv_shopName;

        public ShopViewHolder(View itemView) {
            super(itemView);
        }
    }
}
