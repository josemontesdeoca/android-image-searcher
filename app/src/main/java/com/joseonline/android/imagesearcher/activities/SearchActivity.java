package com.joseonline.android.imagesearcher.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.joseonline.android.imagesearcher.R;
import com.joseonline.android.imagesearcher.adapters.ImageAdapter;
import com.joseonline.android.imagesearcher.helpers.GoogleImageSearchClient;
import com.joseonline.android.imagesearcher.listeners.EndlessScrollListener;
import com.joseonline.android.imagesearcher.models.Image;

import java.util.List;


public class SearchActivity extends ActionBarActivity {

    private static final String LOG_TAG = "SearchActivity";

    private SearchView searchView;
    private GridView gvImages;

    private List<Image> images;
    private ImageAdapter imageAdapter;
    private String query;
    private GoogleImageSearchClient googleImageSearchClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupViews();

        googleImageSearchClient = new GoogleImageSearchClient();

        images = Lists.newArrayList();
        imageAdapter = new ImageAdapter(this, images);
        gvImages.setAdapter(imageAdapter);
    }

    /**
     * Initial view setup
     */
    public void setupViews() {
        gvImages = (GridView) findViewById(R.id.gvImages);

        gvImages.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Google Image API limit queries to 8 pages
                if (page >0 && page < 8) {
                    searchImages(query, page * GoogleImageSearchClient.GOOGLE_IMAGE_RESULT_SIZE);
                } else {
                    Toast.makeText(getApplicationContext(), "Aw, Snap! No more images to load", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query = q;
                searchImages(query, 0);
                imageAdapter.clear();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param query
     * @param offset
     */
    private void searchImages(String query, int offset) {
        Log.i(LOG_TAG, "Querying: '" + query + "' offset: " + offset);
        googleImageSearchClient.search(query, offset,
                new GoogleImageSearchClient.GoogleImageSearchServiceResponseHandler() {
                    @Override
                    public void onSuccess(List<Image> results) {
                        for (Image result: results) {
                            imageAdapter.add(result);
                        }
                        Log.i(LOG_TAG, "Result size " + results.size());
                    }
                });

    }
}
