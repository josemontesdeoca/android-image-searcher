package com.joseonline.android.imagesearcher.helpers;

import android.net.Uri;
import android.util.Log;

import com.joseonline.android.imagesearcher.models.Image;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Simple Google Image Search API client to run searches
 * 
 * @author Jose Montes de Oca
 */
public class GoogleImageSearchClient {

    private static final String LOG_TAG = "GoogleImageSearchClient";
    private static final String GOOGLE_IMAGE_SEARCH_API_SCHEME = "https";
    private static final String GOOGLE_IMAGE_SEARCH_API_AUTHORITY = "ajax.googleapis.com";
    private static final String GOOGLE_IMAGE_SEARCH_API_PATH = "/ajax/services/search/images";
    private static final int GOOGLE_IMAGE_RESULT_SIZE = 8;

    public interface GoogleImageSearchServiceResponseHandler {
        void onSuccess(List<Image> results);
    }

    private final AsyncHttpClient httpClient = new AsyncHttpClient();

    public void search(String query, int offset,
            final GoogleImageSearchServiceResponseHandler responseHandler) {

        String request = buildRequest(query, offset);
        httpClient.get(request, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    List<Image> results = Image.fromJSONArray(
                            response.getJSONObject("responseData").getJSONArray("results"));
                    responseHandler.onSuccess(results);
                } catch (JSONException e) {
                    Log.w(LOG_TAG, "Unable to parse json response: " + response.toString(), e);
                }
            }
        });
    }

    private String buildRequest(String query, int offset) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(GOOGLE_IMAGE_SEARCH_API_SCHEME)
                .authority(GOOGLE_IMAGE_SEARCH_API_AUTHORITY)
                .path(GOOGLE_IMAGE_SEARCH_API_PATH)
                .appendQueryParameter("v", "1.0")
                .appendQueryParameter("rsz", String.valueOf(GOOGLE_IMAGE_RESULT_SIZE))
                .appendQueryParameter("start", String.valueOf(offset))
                .appendQueryParameter("q", query);

        return builder.build().toString();
    }
}