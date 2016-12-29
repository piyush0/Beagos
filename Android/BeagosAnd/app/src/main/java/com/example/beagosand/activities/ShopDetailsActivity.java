package com.example.beagosand.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beagosand.R;
import com.example.beagosand.models.Shop;
import com.example.beagosand.utils.FontsOverride;
import com.example.beagosand.utils.Requesthandler;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import static android.R.attr.bitmap;

public class ShopDetailsActivity extends AppCompatActivity {

    Shop shop;

    private ImageView iv_shopPic;
    private TextView tv_name, tv_address;
    private SimpleRatingBar ratingBar;
    private String source;
    private FloatingActionButton btn_camera;

    public static final String UPLOAD_KEY = "image";
    public static final String UPLOAD_URL = "http://";

    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());
        btn_camera = (FloatingActionButton) findViewById(R.id.activity_shop_details_btn_camera);
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

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            Requesthandler rh = new Requesthandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShopDetailsActivity.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

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



    private void initRatingBar() {
        ratingBar.setStarSize(80);
        ratingBar.setNumberOfStars(5);
        ratingBar.setStepSize(0.1f);
        ratingBar.setBorderColor(getResources().getColor(R.color.colorPrimaryDark));
        ratingBar.setFillColor(getResources().getColor(R.color.colorAccent));
        ratingBar.setStarCornerRadius(10);
    }

}
