package com.example.beagosand;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.beagosand.utils.FontsOverride;
import com.github.anastr.speedviewlib.TubeSpeedometer;

public class RatingLogoActivity extends AppCompatActivity {

    private TubeSpeedometer tubeSpeedometer;
    private Float rating;
    private Button btn;
    private TextView tv_rating;


    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_logo);

        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());
        handler = new Handler();
        btn = (Button) findViewById(R.id.btnTemp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRating();
                initViews();
            }
        });



    }

    private void getRating() {
        //TODO: Vasu will give.
        this.rating = 93.3f;
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
        }
    };
}
