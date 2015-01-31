package com.joseonline.android.imagesearcher.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joseonline.android.imagesearcher.R;

/**
 * Recent Queries cursor adapter
 */
public class RecentSearchesCursorAdapter extends CursorAdapter {
    public RecentSearchesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_recent_search, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvRecentSearch = (TextView) view.findViewById(R.id.tvRecentSearch);

        String query = cursor.getString(cursor.getColumnIndexOrThrow("query"));
        tvRecentSearch.setText(query);
    }
}
