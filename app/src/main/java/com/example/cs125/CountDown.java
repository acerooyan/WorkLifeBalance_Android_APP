package com.example.cs125;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;


public class CountDown extends AppCompatActivity {
    public int counter;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        final TextView counttime=findViewById(R.id.counttime);

        EditText T;
        T = findViewById(R.id.editTextSimple);





        int nc;
        nc = GetWorkTime.mins.NC * 1000;
        T.setText(Integer.toString(nc));

        //T.setText(Integer.toString(nc));
        new CountDownTimer(nc,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish() {
                counttime.setText("Finished");



            }
        }.start();
    }




}