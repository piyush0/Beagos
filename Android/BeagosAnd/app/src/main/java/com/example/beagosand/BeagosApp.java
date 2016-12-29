package com.example.beagosand;

import android.app.Application;

import com.example.beagosand.utils.FontsOverride;


/**
 * Created by piyush0 on 29/12/16.
 */

public class BeagosApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/" + FontsOverride.FONT_PROXIMA_NOVA);
    }
}
