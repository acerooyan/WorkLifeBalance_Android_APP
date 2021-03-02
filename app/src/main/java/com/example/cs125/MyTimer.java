package com.example.cs125;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TextView counttime=getView().findViewById(R.id.counttime);
        int nc;
        nc = GetWorkTime.mins.NC * 1000 * 60;

        counter = GetWorkTime.mins.NC * 60;
        new CountDownTimer(nc,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counttime.setText(String.valueOf(counter));
                counter--;
            }
            @Override
            public void onFinish() {
                counttime.setText("Finished");

                DatabaseReference database;
                //String UID = Login_interface.UserUid.uid;
                String UID = "fNyAWmuBpGMrekwgGUQ9h3Tp8Hx1";
                database = FirebaseDatabase.getInstance().getReference("user");

                String longtiude_latitude = Double.toString(GetWorkTime.MyPoint.Longti) + " " + Double.toString(GetWorkTime.MyPoint.Lati);

                longtiude_latitude = longtiude_latitude.replace('.', '_') ;
                UserData data = new UserData(GetWorkTime.MyPoint.NICKname, GetWorkTime.MyPoint.Start, Integer.toString(GetWorkTime.mins.NC));
                database.child(UID).child(longtiude_latitude).setValue(data);

            }
        }.start();

    }

}