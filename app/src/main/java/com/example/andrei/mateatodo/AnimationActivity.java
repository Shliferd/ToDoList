package com.example.andrei.mateatodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Andrei on 1/28/2018.
 */

public class AnimationActivity extends AppCompatActivity {

    public static Intent getStartIntent(Context context){
        return new Intent(context, AnimationActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity);
        ImageView animatedImage = (ImageView) findViewById(R.id.animated_image);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotation.setFillAfter(true);
        animatedImage.startAnimation(rotation);
    }
}
