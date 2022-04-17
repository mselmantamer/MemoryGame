package com.example.gameapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    ImageView btnMenu2;
    ListView listView;
    ArrayList<Score> arrayListScore = new ArrayList<>();
    HolderAdapter holderAdapter;
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

//        arrayListScore.add(new Score("1", "PLAYER1", "95"));
//        arrayListScore.add(new Score("2", "PLAYER2", "80"));
//        arrayListScore.add(new Score("3", "PLAYER3", "60"));
//        arrayListScore.add(new Score("4", "PLAYER4", "35"));

        btnMenu2 = findViewById(R.id.btnMenu2);
        btnMenu2.setOnClickListener(view -> {
            startActivity(new Intent(this, MenuActivity.class));
            overridePendingTransition(0, 0);
        });

        listView = findViewById(R.id.listViewScore);
        arrayListScore = database.ListScore();

        holderAdapter = new HolderAdapter(this, R.layout.score_row_layout, arrayListScore);
        listView.setAdapter(holderAdapter);
    }
}