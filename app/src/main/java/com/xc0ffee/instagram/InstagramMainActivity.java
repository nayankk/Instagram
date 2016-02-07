package com.xc0ffee.instagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class InstagramMainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    private ArrayList<InstagramPhoto> photos;

    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_main);

        photos = new ArrayList<>();

        aPhotos = new InstagramPhotosAdapter(this, photos);
        ListView lv = (ListView) findViewById(R.id.lv_photos);
        lv.setAdapter(aPhotos);

        // Send out A request to POPULAR PHOTOS
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJson = null;
                try {
                    photosJson = response.getJSONArray("data");
                    for (int i = 0; i < photosJson.length(); i++) {
                        // Get the JSON object
                        JSONObject obj = photosJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.userName = obj.getJSONObject("user").getString("username");
                        photo.caption = obj.getJSONObject("caption").getString("text");
                        photo.imageUrl = obj.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = obj.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = obj.getJSONObject("likes").getInt("count");
                        photos.add(photo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                aPhotos.notifyDataSetChanged();
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("NAYAN", "onFailure: " + statusCode);
            }
        });

    }
}
