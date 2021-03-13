package com.example.cs125;

/*

contribute by: Yang Lu


A type of rest, Breath activity when help user to get rest after work.
 */







import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class breath extends AppCompatActivity {
private final int BREATH_INTERVAL_TIME = 3000; //设置呼吸灯时间间隔
private AlphaAnimation animationFadeIn;
private AlphaAnimation animationFadeOut;

public int counter= 300;

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_breath);
    ImageView breathImageView;
    breathImageView = (ImageView)findViewById(R.id.breathImageView);
    Button bacK_button;
    bacK_button = (Button)findViewById(R.id.bacK_button);

    new CountDownTimer( 50000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            TextView T1;
            T1 = (TextView)findViewById(R.id.textView12);


            int p1 = counter % 60;
            int p2= counter / 60;
            int p3 = p2 % 60;
            p2 = p2 / 60;
            String words;

            if (p2 == 0){
                words =   String.valueOf(p3) + "Min "+ String.valueOf(p1) + "sec";

            }
            else if (p3 == 0 ){
                words =   String.valueOf(p1) + "sec";
            }
            else{
                words =  String.valueOf(p2) + "Hour"  + " " + String.valueOf(p3) + "Min "+ String.valueOf(p1) + "sec";
            }

            words += " remaining..";
            counter--;

            T1.setText(words);

        }
        @Override
        public void onFinish() {

        }
    }.start();


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
