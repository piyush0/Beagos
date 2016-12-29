package com.example.beagosand;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by piyush0 on 29/12/16.
 */

public class GetScoreTask extends AsyncTask<String, Void, Float> {
    @Override
    protected Float doInBackground(String... strings) {
        Float score = null;
        URL url = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String con = content(inputStream);
            score = Float.valueOf(con);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return score;
    }

    private String content(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String buffer = "";

        StringBuilder stringBuilder = new StringBuilder();

        while (buffer != null) {
            buffer = bufferedReader.readLine();
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();

    }
}
