package com.example.gameapplication3;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameTools {

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

    public int CheckForScore(List<Integer> pastOfClilck, int id1, int id2) {
        int sayac = 0;
        for (int past : pastOfClilck) {
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
}
