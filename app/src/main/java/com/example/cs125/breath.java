package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class breath extends AppCompatActivity {
private final int BREATH_INTERVAL_TIME = 3000; //设置呼吸灯时间间隔
private AlphaAnimation animationFadeIn;
private AlphaAnimation animationFadeOut;
    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_breath);
    ImageView breathImageView;
    breathImageView = (ImageView)findViewById(R.id.breathImageView);

    animationFadeIn = new AlphaAnimation(0.1f, 1.0f);
    animationFadeIn.setDuration(BREATH_INTERVAL_TIME);
//        animationFadeIn.setStartOffset(100);

    animationFadeOut = new AlphaAnimation(1.0f, 0.1f);
    animationFadeOut.setDuration(BREATH_INTERVAL_TIME);
//        animationFadeIn.setStartOffset(100);

    animationFadeIn.setAnimationListener(new Animation.AnimationListener(){

        @Override
        public void onAnimationEnd(Animation arg0) {
            breathImageView.startAnimation(animationFadeOut);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animation arg0) {
            // TODO Auto-generated method stub

        }

    });

    animationFadeOut.setAnimationListener(new Animation.AnimationListener(){

        @Override
        public void onAnimationEnd(Animation arg0) {
            breathImageView.startAnimation(animationFadeIn);
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animation arg0) {
            // TODO Auto-generated method stub

        }

    });
    breathImageView.startAnimation(animationFadeOut);

}
}
// cited from: https://www.cnblogs.com/geaosu/p/8781135.html
