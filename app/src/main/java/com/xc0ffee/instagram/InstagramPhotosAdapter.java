package com.xc0ffee.instagram;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

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
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tv_author);
        ImageView ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        if (photo.caption != null) tvCaption.setText(photo.caption);
        if (photo.userName != null) tvUsername.setText(photo.userName);
        // Clear out the imageview
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.avatarUrl).into(ivAvatar);
        if (photo.location != null && !photo.location.isEmpty()) {
            ViewGroup vg = (ViewGroup)convertView.findViewById(R.id.location);
            vg.setVisibility(View.VISIBLE);
            TextView tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
            tvLocation.setText(photo.location);
        }
        TextView likesCntView = (TextView) convertView.findViewById(R.id.tv_likesCount);
        likesCntView.setText(photo.likesCount + " likes");

        TextView comment1 = (TextView) convertView.findViewById(R.id.tv_comment1);
        comment1.setText(Html.fromHtml(photo.comment1));

        TextView comment2 = (TextView) convertView.findViewById(R.id.tv_comment2);
        comment2.setText(Html.fromHtml(photo.comment2));

        return convertView;
    }
}
