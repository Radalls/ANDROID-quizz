package com.example.quizzapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizzapp.R;
import com.example.quizzapp.models.Friend;
import com.example.quizzapp.services.DbService;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static Friend logFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getExtras();
        String friendId = bundle.getString("id");

        TextView scoreValue = findViewById(R.id.scoreValue);

        DbService dbService = new DbService();
        dbService.getFriend(friendId).observe(HomeActivity.this, new Observer<Friend>() {

            @Override
            public void onChanged(Friend friend) {
                if (friend.getId() != "") {
                    logFriend = friend;
                    scoreValue.setText("" + logFriend.getScore());
                }
            }
        });

        ImageButton friendListButton = findViewById(R.id.friendListButton);
        Glide.with(this).load("https://previews.123rf.com/images/sodis/sodis1910/sodis191000016/134809790-friends-young-people-hugging-meeting-flat-design-vector-illustration.jpg").into(friendListButton);
        friendListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FriendListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", logFriend.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ImageButton quizzListButton = findViewById(R.id.quizzListButton);
        Glide.with(this).load("https://img.freepik.com/free-vector/quiz-background-with-items-flat-design_23-2147599082.jpg?w=2000").into(quizzListButton);
        quizzListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, QuizzListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", logFriend.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}