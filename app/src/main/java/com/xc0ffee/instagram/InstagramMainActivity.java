package com.xc0ffee.instagram;

import android.support.v4.widget.SwipeRefreshLayout;
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

    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });

        photos = new ArrayList<>();

        aPhotos = new InstagramPhotosAdapter(this, photos);
        ListView lv = (ListView) findViewById(R.id.lv_photos);
        lv.setAdapter(aPhotos);

        // Send out A request to POPULAR PHOTOS
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        photos.clear();
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
                        if (!obj.isNull("user")){
                            photo.userName = obj.getJSONObject("user").getString("username");
                        }
                        if (!obj.isNull("caption")) {
                            photo.caption = obj.getJSONObject("caption").getString("text");
                        }
                        photo.imageUrl = obj.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = obj.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = obj.getJSONObject("likes").getInt("count");
                        photo.avatarUrl = obj.getJSONObject("user").getString("profile_picture");
                        if (!obj.isNull("location")) {
                            JSONObject locaton = obj.getJSONObject("location");
                            photo.location = locaton.getString("name");
                        }

                        JSONObject commentObj = obj.getJSONObject("comments");
                        JSONArray commentArray = commentObj.getJSONArray("data");
                        String comment1 = commentArray.getJSONObject(0).getString("text");
                        String comment1User = commentArray.getJSONObject(0).getJSONObject("from").getString("username");
                        photo.comment1 = "<b>" + comment1User + "</b>  " + comment1;

                        String comment2 = commentArray.getJSONObject(1).getString("text");
                        String comment2User = commentArray.getJSONObject(0).getJSONObject("from").getString("username");
                        photo.comment2 = "<b>" + comment2User + "</b>  " + comment2;

                        photos.add(photo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);
                aPhotos.notifyDataSetChanged();
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("NAYAN", "onFailure: " + statusCode);
                swipeContainer.setRefreshing(false);
            }
        });

    }
}
