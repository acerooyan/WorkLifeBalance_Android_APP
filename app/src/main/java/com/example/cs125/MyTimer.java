package com.example.cs125;
/*

contributes by:  Hongji Yan, Leerjiya Bao


This is fragment is where user working at

 */
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTimer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTimer extends Fragment {
    public int counter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int rest_time ;
    String IN;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyTimer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyTimer.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTimer newInstance(String param1, String param2) {
        MyTimer fragment = new MyTimer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_timer, container, false);
    }



    public void Set_rest_time(){


        if (GetWorkTime.MyPoint.Intensity.equals("high")){
            rest_time = 4500;
            rest_time = 5;
            IN = "High";
        }
        else if(GetWorkTime.MyPoint.Intensity.equals("mid")){
            rest_time = 3120;
            IN = "Midium";
        }
        else {
            rest_time = 1500;
            IN = "Low";

        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TextView counttime=getView().findViewById(R.id.counttime);
        int nc;
        nc = GetWorkTime.mins.NC * 1000 * 60;

        counter = GetWorkTime.mins.NC * 60;
        String words = "You Work intensity: ";
        TextView textView12;

        textView12= getView().findViewById(R.id.textView6);


        Set_rest_time();
        textView12.setText(words + IN);
        //nc = 100;
        if (GetWorkTime.MyPoint.Intensity.equals("high")){
            rest_time = 1500;
            StepsCounter.nums = 150;

        }
        else if(GetWorkTime.MyPoint.Intensity.equals("mid")){

            rest_time = 3120;


            StepsCounter.nums= 300;
        }
        else {

            rest_time = 4500;

            StepsCounter.nums= 500;


        }
        Button GiveUpButton = getView().findViewById(R.id.GiveUP);
        Button continue_work = getView().findViewById(R.id.countie_work);

        //''''''''''''''''''

        //nc = 5;
        new CountDownTimer(nc,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
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
                counttime.setText(words);
                counter--;
                rest_time--;


                if (rest_time == 0){
                    cancel();

                    continue_work.setVisibility(View.VISIBLE);
                    GiveUpButton.setText("REST NOW");
                    GetWorkTime.mins.NC = counter / 60000;
                    GiveUpButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NavController controller = Navigation.findNavController(v);
                            controller.navigate(R.id.action_myTimer_to_choose_rest_type);

                        }
                    });


                    continue_work.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Set_rest_time();
                            start();
                            continue_work.setVisibility(View.INVISIBLE);
                            GiveUpButton.setText("Give up");
                            return;

                        }
                    });



                }



            }
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFinish() {


                counttime.setText("Finished");



                GiveUpButton.setText("REST NOW");
                String UID = Login_interface.UserUid.uid;
                DatabaseListner.CheckSameLocation(UID , true, true, false);
                DatabaseListner.eventListener_trigger(UID);



                GiveUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavController controller = Navigation.findNavController(v);
                        controller.navigate(R.id.action_myTimer_to_choose_rest_type);

                    }
                });

            }
        }.start();







    }


}