package com.example.quizzapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.quizzapp.R;

import java.util.ArrayList;

public class QuizzListActivity extends AppCompatActivity {

    private ArrayList<String> quizz;
    private ArrayAdapter<String> adapter;
    private ListView quizzList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_list);

        Bundle bundle = getIntent().getExtras();
        String friendId = bundle.getString("id");

        quizz = new ArrayList() {{
            add("Quizz 2");
            add("Quizz 4");
            add("Quizz 6");
        }};

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, quizz);
        quizzList = findViewById(R.id.quizzList);
        quizzList.setAdapter(adapter);
        quizzList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(QuizzListActivity.this, QuizzActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", friendId);
                bundle.putInt("nbQuestion", (i + 1) * 2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}