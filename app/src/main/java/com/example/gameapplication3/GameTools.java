package com.example.gameapplication3;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameTools {

    Context context;

    public GameTools(Context context) {
        this.context = context;
    }

    public void AnimateCard(CardView cardFront, CardView cardBack, boolean state) {
        AnimatorSet front_animation2 = (AnimatorSet) AnimatorInflater.
                loadAnimator(cardFront.getContext(), R.animator.front_animator);
        AnimatorSet back_animation2 = (AnimatorSet) AnimatorInflater.
                loadAnimator(cardBack.getContext(), R.animator.back_animator);

        if (!state) {
            front_animation2.setTarget(cardFront);
            back_animation2.setTarget(cardBack);
        } else {
            front_animation2.setTarget(cardBack);
            back_animation2.setTarget(cardFront);
        }
        front_animation2.start();
        back_animation2.start();
    }

    public int CheckForScore(List<Integer> pastOfClick, int id1, int id2) {
        int sayac = 0;
        for (int past : pastOfClick) {
            if (past == id1 || past == id2) {
                sayac++;
            }
        }
        if (sayac < 3) return 15;
        else if (sayac < 5) return 10;
        else return 5;
    }

    public List<Integer> RandomNumbers() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15, 16);
        Collections.shuffle(numbers);

        return numbers;
    }

    public void ChangeImage(ImageView imageView, int i) {
        switch (i) {
            case 1:
                imageView.setImageResource(R.drawable.a1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.a2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.a3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.a4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.a5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.a6);
                break;
            case 7:
                imageView.setImageResource(R.drawable.a7);
                break;
            case 8:
                imageView.setImageResource(R.drawable.a8);
                break;
        }
    }

    public void OpenWinDialog(Context context, String score) {
        Activity activity = ((Activity) context);
        Dialog winDialog = new Dialog(context);

        winDialog.setContentView(R.layout.win_dialog_layout);
        winDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        winDialog.show();

        Database database = new Database(context);
        database.DeleteMinScore();

        Button btnMenu = winDialog.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(view -> {
            activity.startActivity(new Intent(context, MenuActivity.class));
            activity.overridePendingTransition(0, 0);
        });

        Button btnReplay = winDialog.findViewById(R.id.btnReplay);
        btnReplay.setOnClickListener(view -> {
            winDialog.dismiss();
            activity.finish();
            activity.startActivity(activity.getIntent());
            activity.overridePendingTransition(0, 0);
        });

        TextView textScore = winDialog.findViewById(R.id.textScore);
        textScore.setText(score);
    }

    public void OpenWinHighDialog(Context context, String score) {
        Activity activity = ((Activity) context);
        Dialog winDialog = new Dialog(context);

        winDialog.setContentView(R.layout.win_high_dialog_layout);
        winDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        winDialog.show();

        Button btnSave = winDialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> {
            Database database = new Database(context);

            EditText etName = winDialog.findViewById(R.id.etName);
            database.AddScore(new Score(etName.getText().toString(), score));

            activity.startActivity(new Intent(context, ScoreActivity.class));
            activity.overridePendingTransition(0, 0);
        });

        TextView textScore = winDialog.findViewById(R.id.textScore);
        textScore.setText(score);
    }

    public void CompareScore(int score) {
        Database database = new Database(context);

        if (score > database.GetHighScore() || database.GetHighScore() == 0)
            OpenWinHighDialog(context, String.valueOf(score));
        else
            OpenWinDialog(context, String.valueOf(score));
    }
}
