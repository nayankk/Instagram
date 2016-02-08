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

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    @Bind(R.id.tv_caption) TextView tvCaption;
    @Bind(R.id.imageView) ImageView ivPhoto;
    @Bind(R.id.tv_author) TextView tvUsername;
    @Bind(R.id.iv_avatar) ImageView ivAvatar;
    @Bind(R.id.location) ViewGroup vg;
    @Bind(R.id.tv_location) TextView tvLocation;
    @Bind(R.id.tv_likesCount) TextView likesCntView;
    @Bind(R.id.tv_comment1) TextView comment1;
    @Bind(R.id.tv_comment2) TextView comment2;

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        ButterKnife.bind(this, convertView);
        if (photo.caption != null) tvCaption.setText(photo.caption);
        if (photo.userName != null) tvUsername.setText(photo.userName);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.image_area).into(ivPhoto);
        Picasso.with(getContext()).load(photo.avatarUrl).into(ivAvatar);
        if (photo.location != null && !photo.location.isEmpty()) {
            vg.setVisibility(View.VISIBLE);
            tvLocation.setText(photo.location);
        }
        likesCntView.setText(photo.likesCount + " likes");
        comment1.setText(Html.fromHtml(photo.comment1));
        comment2.setText(Html.fromHtml(photo.comment2));

        return convertView;
    }
}
