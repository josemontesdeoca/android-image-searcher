package com.joseonline.android.imagesearcher.models;

import android.util.Log;

import com.google.common.collect.Lists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;


/**
 * @author Jose Montes de Oca
 */
public class Image implements Serializable{

    private static final String LOG_TAG = "Image";

    private String imageUrl;
    private String thumbnailUrl;

    public Image(String imageUrl, String thumbnailUrl) {
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Image(JSONObject json) {
        try {
            this.imageUrl = json.getString("url");
            this.thumbnailUrl = json.getString("tbUrl");
        } catch (JSONException e) {
            this.imageUrl = null ;
            this.thumbnailUrl = null;
            Log.w(LOG_TAG, "Unable to parse Image from " + json.toString(), e);
        }
    }

    public static List<Image> fromJSONArray(JSONArray jsonArray) {
        List<Image> results = Lists.newArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                results.add(new Image(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                Log.w(LOG_TAG, "Unable to get image " + i + "from the json array: " + jsonArray.toString());
            }
        }

        return results;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
