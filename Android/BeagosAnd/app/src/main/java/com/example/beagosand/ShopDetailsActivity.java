package com.example.beagosand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beagosand.models.Shop;
import com.example.beagosand.utils.FontsOverride;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

public class ShopDetailsActivity extends AppCompatActivity {

    Shop shop;

    private ImageView iv_shopPic;
    private TextView tv_name, tv_address;
    private SimpleRatingBar ratingBar;
    private String source;
    private Button btn_camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());
        btn_camera = (Button) findViewById(R.id.activity_shop_details_btn_camera);
        getShop();
        initViews();
    }

    private void initViews() {
        iv_shopPic = (ImageView) findViewById(R.id.activity_shop_details_iv_photo);

        tv_address = (TextView) findViewById(R.id.activity_shop_details_tv_address);
        tv_name = (TextView) findViewById(R.id.activity_shop_details_tv_name);
        ratingBar = (SimpleRatingBar) findViewById(R.id.activity_shop_details_rating_bar);
        ratingBar.setIndicator(true);
        initRatingBar();
        setDetails();
    }

    private void getShop(){
        Intent i = getIntent();
        String UUID = i.getStringExtra("UUID");
        this.source = i.getStringExtra("source");

        if(this.source.equals("NearbyShopsActivity")){
            btn_camera.setVisibility(View.GONE);
        }
        else{
            btn_camera.setVisibility(View.VISIBLE);
        }

        for(Shop s : Shop.getDummyShops()){
            if(s.getUUID().equals(UUID)){
                this.shop = s;
                break;
            }
        }
    }

    private void setDetails() {
        tv_name.setText(shop.getName());
        tv_address.setText(shop.getAddress());
        //TODO: Set pic
        ratingBar.setRating(calculateStars(shop.getRating()));

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Send intent to camera activity
            }
        });
    }

    private float calculateStars(float rating) {
        float perc = rating / 100;
        return perc * 5;
    }



    private void initRatingBar() {
        ratingBar.setStarSize(80);
        ratingBar.setNumberOfStars(5);
        ratingBar.setStepSize(0.1f);
        ratingBar.setBorderColor(getResources().getColor(R.color.colorPrimaryDark));
        ratingBar.setFillColor(getResources().getColor(R.color.colorAccent));
        ratingBar.setStarCornerRadius(10);
    }

}
