package com.example.gameapplication3;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    TextView puanTextView;

    GameTools gameTools = new GameTools();
    List<Integer> nums = gameTools.RandomNumbers();

    ImageView btnReplay, btnMenu, btnShow;

    boolean showJoker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetupCard();

        chronometer = findViewById(R.id.chronometer);
        chronometer.start();
        puanTextView = findViewById(R.id.puanTextView);
        puanTextView.setText(String.valueOf(totalScore));

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(v -> {
            for (int i = 0; i < 16; i++) {
                gameTools.AnimateCard(cardViewsFront.get(i), cardViewsBack.get(i), false);
                relativeLayouts.get(i).setClickable(false);
            }
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                for (int i = 0; i < 16; i++) {
                    gameTools.AnimateCard(cardViewsFront.get(i), cardViewsBack.get(i), true);
                    relativeLayouts.get(i).setClickable(true);
                }
                btnShow.setImageResource(R.drawable.ic_baseline_show_off);
                btnShow.setClickable(false);
                showJoker = false;
            }, 1500);
            btnShow.setClickable(false);
        });

        btnReplay = findViewById(R.id.btnReplay);
        btnReplay.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });
        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuActivity.class));
            overridePendingTransition(0, 0);
        });
    }

    boolean clickState = false, clickable = true;
    int imageState, imageState2;
    View viewS, viewS2;
    int viewSIndex, viewS2Index;

    List<Integer> pastOfClick = new ArrayList<>();
    int totalScore = 0, cardOfInvisible = 0;

    public void ClickedCard(View v) {
        if (clickable) {
            int index = nums.get(Integer.parseInt(v.getContentDescription().toString()));
            int layoutIndex = Integer.parseInt(v.getContentDescription().toString());
            v.setClickable(false);

            if (!clickState) {
                clickState = true;
                gameTools.AnimateCard(cardViewsFront.get(layoutIndex), cardViewsBack.get(layoutIndex), false);
                imageState = index;
                viewS = v;
                viewSIndex = Integer.parseInt(viewS.getContentDescription().toString());
                pastOfClick.add(viewSIndex);
                if (showJoker) btnShow.setClickable(false);
            } else {
                clickable = false;
                gameTools.AnimateCard(cardViewsFront.get(layoutIndex), cardViewsBack.get(layoutIndex), false);
                imageState2 = index;
                viewS2 = v;
                viewS2Index = Integer.parseInt(viewS2.getContentDescription().toString());
                pastOfClick.add(viewS2Index);

                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if (imageState == imageState2 + 8 || imageState2 == imageState + 8) {
                        viewS.setVisibility(View.INVISIBLE);
                        viewS2.setVisibility(View.INVISIBLE);
                        totalScore += gameTools.CheckForScore(pastOfClick, viewSIndex, viewS2Index);
                        puanTextView.setText(String.valueOf(totalScore));

                        puanTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.text_anim));

                        cardOfInvisible += 2;
                        if (cardOfInvisible == 16) {
                            gameTools.OpenWinDialog(this, String.valueOf(totalScore));
                            chronometer.stop();
                        }
                    } else {
                        viewS.setClickable(true);
                        viewS2.setClickable(true);
                        gameTools.AnimateCard(cardViewsFront.get(viewSIndex), cardViewsBack.get(viewSIndex), true);
                        gameTools.AnimateCard(cardViewsFront.get(viewS2Index), cardViewsBack.get(viewS2Index), true);
                    }
                    clickable = true;
                    if (showJoker) btnShow.setClickable(true);
                }, 1600);
                clickState = false;
            }
        }
    }

    List<CardView> cardViewsFront = new ArrayList<>();
    List<CardView> cardViewsBack = new ArrayList<>();
    List<RelativeLayout> relativeLayouts = new ArrayList<>();

    public void SetupCard() {
        LinearLayout linearLayoutRow1, linearLayoutRow2, linearLayoutRow3, linearLayoutRow4;
        List<LinearLayout> linearLayouts;

        linearLayoutRow1 = findViewById(R.id.linearLayoutRow1);
        linearLayoutRow2 = findViewById(R.id.linearLayoutRow2);
        linearLayoutRow3 = findViewById(R.id.linearLayoutRow3);
        linearLayoutRow4 = findViewById(R.id.linearLayoutRow4);

        linearLayouts = new ArrayList<>(Arrays.asList(linearLayoutRow1,
                linearLayoutRow2, linearLayoutRow3, linearLayoutRow4));

        LinearLayout.LayoutParams lpRelativeLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpRelativeLayout.weight = 1.0f;
        lpRelativeLayout.setMargins(10, 10, 10, 10);

        LinearLayout.LayoutParams lpCardView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout.LayoutParams lpImageView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout relativeLayout;
        CardView cardViewFront, cardViewBack;
        ImageView imageView;
        int cdIndex = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                relativeLayout = new RelativeLayout(this);
                relativeLayout.setLayoutParams(lpRelativeLayout);
                relativeLayout.setContentDescription(String.valueOf(cdIndex));
                relativeLayout.setClickable(true);
                relativeLayout.setOnClickListener(this::ClickedCard);
                relativeLayouts.add(relativeLayout);

                cardViewBack = new CardView(this);
                cardViewBack.setLayoutParams(lpCardView);
                cardViewBack.setRadius(30);
                cardViewBack.setCardElevation(0);
                cardViewBack.setCardBackgroundColor(getResources().getColor(R.color.specialgreen));
                cardViewsBack.add(cdIndex, cardViewBack);

                imageView = new ImageView(this);
                imageView.setLayoutParams(lpImageView);
                imageView.setImageResource(R.drawable.a1);

                int numIndex = nums.get(cdIndex);
                for (int k = 1; k < 9; k++) {
                    if (numIndex == k || numIndex == k + 8) {
                        gameTools.ChangeImage(imageView, k);
                    }
                }

                cardViewBack.addView(imageView);
                relativeLayout.addView(cardViewBack);

                cardViewFront = new CardView(this);
                cardViewFront.setLayoutParams(lpCardView);
                cardViewFront.setRadius(30);
                cardViewFront.setCardElevation(0);
                cardViewFront.setCardBackgroundColor(getResources().getColor(R.color.specialyellow));
                cardViewsFront.add(cdIndex, cardViewFront);

                imageView = new ImageView(this);
                imageView.setLayoutParams(lpImageView);

                cardViewFront.addView(imageView);
                relativeLayout.addView(cardViewFront);

                linearLayouts.get(i).addView(relativeLayout);
                cdIndex++;
            }
        }
    }
}