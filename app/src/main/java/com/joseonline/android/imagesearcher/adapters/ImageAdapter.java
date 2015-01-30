package com.joseonline.android.imagesearcher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.joseonline.android.imagesearcher.R;
import com.joseonline.android.imagesearcher.models.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Image Adapter
 * 
 * @author jfmontesdeoca
 */
public class ImageAdapter extends ArrayAdapter<Image> {

    public ImageAdapter(Context context, List<Image> images) {
        super(context, R.layout.item_image, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

        Picasso.with(getContext()).load(image.getThumbnailUrl()).into(ivImage);

        return convertView;
    }
}
