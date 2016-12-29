package com.example.beagosand.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.beagosand.R;
import com.example.beagosand.models.Shop;
import com.example.beagosand.utils.FontsOverride;
import com.github.anastr.speedviewlib.TubeSpeedometer;

import io.realm.Realm;

public class RatingLogoActivity extends AppCompatActivity {

    private TubeSpeedometer tubeSpeedometer;
    private Float rating;
    private Button btn;
    private TextView tv_rating;
    String uuid;

    String URL = "http://192.168.1.43:8000/details";

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_logo);

        uuid = getIntent().getStringExtra("uuid");
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA, getWindow());
        handler = new Handler();
        getRating();
    }

    private void getRating() {

        this.rating = getIntent().getFloatExtra("Score", 0);
        initViews();
    }

    private void initViews() {
        tv_rating = (TextView) findViewById(R.id.activity_rating_logo_tv_rating);
        tubeSpeedometer = (TubeSpeedometer) findViewById(R.id.activity_rating_logo_speedometer);
        tubeSpeedometer.setUnitTextSize(0f);
        tubeSpeedometer.setSpeedTextSize(0f);
        tubeSpeedometer.setWithEffects3D(true);
        tubeSpeedometer.setIndicatorWidth(59f);
        tubeSpeedometer.setMaxSpeed(100);
        tubeSpeedometer.speedTo(this.rating.intValue());


        handler.postDelayed(runnable, 1500);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tubeSpeedometer.setWithTremble(false);
            tv_rating.setVisibility(View.VISIBLE);
            tv_rating.setText(String.valueOf(rating));

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Shop shop = realm.where(Shop.class).equalTo("UUID", uuid).findFirst();
            shop.setRating(rating);
            realm.commitTransaction();

        }
    };
}
