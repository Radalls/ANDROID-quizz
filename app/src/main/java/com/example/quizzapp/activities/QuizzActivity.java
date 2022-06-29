package com.example.quizzapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizzapp.R;
import com.example.quizzapp.models.Friend;
import com.example.quizzapp.models.Quizz;
import com.example.quizzapp.services.DbService;

import java.util.ArrayList;

public class QuizzActivity extends AppCompatActivity {

    private static Friend logFriend;
    private ArrayList<Quizz> quizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        Bundle bundle = getIntent().getExtras();
        String friendId = bundle.getString("id");

        int nbQuestion = bundle.getInt("nbQuestion");

        TextView quizzQuestion = findViewById(R.id.quizzQuestion);
        Button truebutton = findViewById(R.id.trueButton);
        Button falseButton = findViewById(R.id.falseButton);
        ImageView quizzIcon = findViewById(R.id.quizzIcon);

        DbService dbService = new DbService();

        dbService.getFriend(friendId).observe(this, new Observer<Friend>() {

            @Override
            public void onChanged(Friend friend) {
                if (friend.getId() != "") {
                    logFriend = friend;
                }
            }
        });

        dbService.getQuizz(nbQuestion).observe(this, new Observer<ArrayList<Quizz>>() {

            @Override
            public void onChanged(ArrayList<Quizz> questions) {
                System.out.println(questions.size());
                if (questions.size() > 0) {
                    quizz = questions;
                    quizzQuestion.setText(quizz.get(0).getQuestion());
                    Glide.with(QuizzActivity.this).load(quizz.get(0).getImage()).into(quizzIcon);
                }
            }
        });



        truebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (quizz.get(0).getAnswer() == true) {
                    logFriend.setScore(logFriend.getScore() + 1);
                    Toast.makeText(QuizzActivity.this, "BONNE RÉPONSE ! 🎉", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(QuizzActivity.this, "MAUVAISE RÉPONSE ! 😢", Toast.LENGTH_SHORT).show();
                }
                quizz.remove(0);

                if (quizz.size() > 0) {
                    quizzQuestion.setText(quizz.get(0).getQuestion());
                    Glide.with(QuizzActivity.this).load(quizz.get(0).getImage()).into(quizzIcon);
                }
                else {
                    dbService.updateScore(logFriend).observe(QuizzActivity.this, new Observer<Boolean>() {

                        @Override
                        public void onChanged(Boolean aBoolean) {
                            Intent intent = new Intent(QuizzActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", logFriend.getId());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (quizz.get(0).getAnswer() == false) {
                    logFriend.setScore(logFriend.getScore() + 1);
                    Toast.makeText(QuizzActivity.this, "BONNE RÉPONSE ! 🎉", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(QuizzActivity.this, "MAUVAISE RÉPONSE ! 😢", Toast.LENGTH_SHORT).show();
                }
                quizz.remove(0);

                if (quizz.size() > 0) {
                    quizzQuestion.setText(quizz.get(0).getQuestion());
                    Glide.with(QuizzActivity.this).load(quizz.get(0).getImage()).into(quizzIcon);
                }
                else {
                    dbService.updateScore(logFriend).observe(QuizzActivity.this, new Observer<Boolean>() {

                        @Override
                        public void onChanged(Boolean aBoolean) {
                            Intent intent = new Intent(QuizzActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", logFriend.getId());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }
}