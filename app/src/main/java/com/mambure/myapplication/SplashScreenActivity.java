package com.mambure.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        imageView = findViewById(R.id.img_logo);
        ConstraintLayout rootView = findViewById(R.id.root_view);

        // Get the animated background (drawable/animated_background.xml)
        animationDrawable = (AnimationDrawable) rootView.getBackground();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set imageview transparency to 0
        imageView.setAlpha(0f);

        new Handler(getMainLooper())
                .postDelayed(() -> {
                    animateBackground();
                    animateLogo();
                }, 500);
    }

    private void animateBackground() {
        // Animate the background drawable
        animationDrawable.setEnterFadeDuration(300);
        animationDrawable.setExitFadeDuration(600);
        animationDrawable.start();
    }

    private void animateLogo() {
        // Animate imageview transparency to 1 (fully visible)
        imageView.animate()
                .alpha(1.0f)
                .setDuration(700)
                .setStartDelay(400)
                // Choose animation interpolator which determines the flow of the animation
                // More info on animation interpolators:
                // https://medium.com/mindorks/understanding-interpolators-in-android-ce4e8d1d71cd
                // https://thoughtbot.com/blog/android-interpolators-a-visual-guide
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                .start();
    }

}