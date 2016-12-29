package com.example.beagosand.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.beagosand.R;

import me.itangqi.waveloadingview.WaveLoadingView;

public class LoadingActivity extends AppCompatActivity {

    WaveLoadingView loadingView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        btn = (Button) findViewById(R.id.loading_temp);
        loadingView = (WaveLoadingView) findViewById(R.id.activity_loading_waveLoading);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingView.setProgressValue(100);
            }
        });

    }
}
