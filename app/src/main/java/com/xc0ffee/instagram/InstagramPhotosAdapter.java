package com.xc0ffee.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by nayankk on 2/7/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    // Whay data do we need from Activity
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tv_caption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.imageView);
        tvCaption.setText(photo.caption);
        // Clear out the imageview
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        return convertView;
    }
}
