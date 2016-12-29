package com.example.beagosand.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beagosand.GetScoreTask;
import com.example.beagosand.R;
import com.example.beagosand.models.Shop;
import com.example.beagosand.utils.FontsOverride;
import com.example.beagosand.utils.Requesthandler;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import me.itangqi.waveloadingview.WaveLoadingView;

import static android.R.attr.bitmap;


public class ShopDetailsActivity extends AppCompatActivity {

    Shop shop;

    private ImageView iv_shopPic;
    private TextView tv_name, tv_address;
    private SimpleRatingBar ratingBar;
    private String source;
    private FloatingActionButton btn_camera;
    WaveLoadingView loadingView;

    public static final String UPLOAD_KEY = "image";
    public static final String UPLOAD_URL = "http://";

    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;

    public static final String TAG = "***************";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA, getWindow());
        btn_camera = (FloatingActionButton) findViewById(R.id.activity_shop_details_btn_camera);
        getShop();
        initViews();
    }


    public static ArrayList<Shop> getDummyShops() {

        ArrayList<Shop> retVal = new ArrayList<>();

        retVal.add(new Shop("Coding Ninjas", 70.0F, "Android Room", "1"));
        retVal.add(new Shop("Ventursity", 42.2F, "Python Room", "2"));
        retVal.add(new Shop("DTU", 31.4F, "iOS Room", "3"));
        retVal.add(new Shop("NSIT", 77.5F, "Ruby Room", "4"));
        retVal.add(new Shop("IITD", 77.5F, "Office", "5"));
        retVal.add(new Shop("IIITD", 69.5F, "Git Room", "6"));
        retVal.add(new Shop("Office", 78.5F, "Test Room", "7"));

        return retVal;
    }


    private void initViews() {
        iv_shopPic = (ImageView) findViewById(R.id.activity_shop_details_iv_photo);
        loadingView = (WaveLoadingView) findViewById(R.id.activity_loading_waveLoading);
        tv_address = (TextView) findViewById(R.id.activity_shop_details_tv_address);
        tv_name = (TextView) findViewById(R.id.activity_shop_details_tv_name);
        ratingBar = (SimpleRatingBar) findViewById(R.id.activity_shop_details_rating_bar);
        ratingBar.setIndicator(true);
        initRatingBar();
        setDetails();

        int num = Integer.valueOf(shop.getUUID());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (num) {
                case 1:
                    iv_shopPic.setImageDrawable(getDrawable(R.drawable.android_place));
                    break;
                case 2:
                    iv_shopPic.setImageDrawable(getDrawable(R.drawable.python));
                    break;
                case 3:
                    iv_shopPic.setImageDrawable(getDrawable(R.drawable.apple));
                    break;
                case 4:
                    iv_shopPic.setImageDrawable(getDrawable(R.drawable.ruby));
                    break;
                case 5:
                    iv_shopPic.setImageDrawable(getDrawable(R.drawable.test));
                    break;
                case 6:
                    iv_shopPic.setImageDrawable(getDrawable(R.drawable.place1));
                    break;
                case 7:
                    iv_shopPic.setImageDrawable(getDrawable(R.drawable.apple));
                    break;

            }
        }
    }

    private void getShop() {
        Intent i = getIntent();
        String UUID = i.getStringExtra("UUID");
        this.source = i.getStringExtra("source");

        if (this.source.equals("NearbyShopsActivity")) {
            btn_camera.setVisibility(View.GONE);
        } else {
            btn_camera.setVisibility(View.VISIBLE);
        }

        for (Shop s : getDummyShops()) {
            if (s.getUUID().equals(UUID)) {
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
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);
            }
        });
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.d(TAG, "onActivityResult: ");
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                iv_shopPic.setVisibility(View.GONE);
                tv_address.setVisibility(View.GONE);
                tv_name.setVisibility(View.GONE);
                ratingBar.setVisibility(View.GONE);
                btn_camera.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                uploadImage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            //            ProgressDialog loading;
            Requesthandler rh = new Requesthandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(ShopDetailsActivity.this, "Uploading Image", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                loadingView.setProgressValue(50);

                Handler handler = new Handler();
                handler.postDelayed(piyush, 3000);


            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private float calculateStars(float rating) {
        float perc = rating / 100;
        return perc * 5;
    }

    private Runnable piyush = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(ShopDetailsActivity.this, RatingLogoActivity.class);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ShopDetailsActivity.this);
            if (!sharedPreferences.getBoolean("firstTimePic", false)) {
                i.putExtra("Score", 80.3F);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("firstTimePic", true);
                editor.commit();
            } else {
                i.putExtra("Score", 16.6F);
            }
            i.putExtra("uuid", shop.getUUID());
            Log.d(TAG, "run: ");
            startActivity(i);

            finish();
        }
    };

    private void initRatingBar() {
        ratingBar.setStarSize(80);
        ratingBar.setNumberOfStars(5);
        ratingBar.setStepSize(0.1f);
        ratingBar.setBorderColor(getResources().getColor(R.color.colorPrimaryDark));
        ratingBar.setFillColor(getResources().getColor(R.color.colorAccent));
        ratingBar.setStarCornerRadius(10);
    }

}
