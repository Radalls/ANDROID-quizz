package com.example.quizzapp.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.quizzapp.R;
import com.example.quizzapp.models.Friend;
import java.util.ArrayList;

public class FriendAdapter extends ArrayAdapter<Friend> {

    public FriendAdapter(@NonNull Context context, @NonNull ArrayList<Friend> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Friend friend = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_list_item, parent, false);
        }

        // Lookup view for data population
        TextView username = (TextView) convertView.findViewById(R.id.friendName);
        TextView score = (TextView) convertView.findViewById(R.id.friendScore);
        ImageView avatar = convertView.findViewById(R.id.quizzIcon);

        // Populate the data into the template view using the data object
        username.setText(friend.getName());
        score.setText(friend.getScore() + " points");
        Glide.with(convertView).load(friend.getAvatar()).into(avatar);

        // Return the completed view to render on screen
        return convertView;
    }

}