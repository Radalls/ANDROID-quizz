package com.example.quizzapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.quizzapp.R;
import com.example.quizzapp.models.Friend;
import com.example.quizzapp.services.DbService;
import com.example.quizzapp.services.FriendAdapter;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    private ArrayList<Friend> friends;
    private ArrayAdapter<Friend> adapter;
    private ListView friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        friends = new ArrayList();
        friendsList = findViewById(R.id.friendList);

        Bundle bundle = getIntent().getExtras();
        String friendId = bundle.getString("id");

        DbService dbService = new DbService();
        dbService.getFriend(friendId).observe(this, new Observer<Friend>() {

            @Override
            public void onChanged(Friend friend) {
                if (friend.getFriends().size() > 0) {
                    friends = friend.getFriends();
                    adapter = new FriendAdapter(FriendListActivity.this, friends);
                    friendsList.setAdapter(adapter);
                }
            }
        });
    }
}