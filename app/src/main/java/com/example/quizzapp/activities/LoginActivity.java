package com.example.quizzapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizzapp.R;
import com.example.quizzapp.services.DbService;

import java.util.Observable;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DbService dbService = new DbService();
                dbService.signIn(emailInput.getText().toString(), passwordInput.getText().toString())
                        .observe(LoginActivity.this, new Observer<String>() {

                    @Override
                    public void onChanged(String id) {
                        if (id.equals("")) {
                            Toast.makeText(LoginActivity.this, "Identifiants incorrects. Veuillez réessayer", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}