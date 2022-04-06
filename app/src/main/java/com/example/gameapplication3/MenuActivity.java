package com.example.gameapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnStartGame = findViewById(R.id.btnStartGame);
        btnStartGame.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}