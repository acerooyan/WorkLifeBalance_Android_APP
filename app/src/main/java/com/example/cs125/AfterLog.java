package com.example.cs125;
/*


contribute by:  Hongji Yan

This fragment directs user to choose their working intensity.


 */
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AfterLog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AfterLog extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AfterLog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AfterLog.
     */
    // TODO: Rename and change types and number of parameters
    public static AfterLog newInstance(String param1, String param2) {
        AfterLog fragment = new AfterLog();
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
        return inflater.inflate(R.layout.fragment_after_log, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button high, mid, low , recommend;



        high = getView().findViewById(R.id.high);
        mid = getView().findViewById(R.id.medium );
        low = getView().findViewById(R.id.low);
        recommend = getView().findViewById(R.id.Recommend);

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetWorkTime.MyPoint.Intensity = "high";
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_afterLog_to_getWorkTime);

            }
        });

        mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetWorkTime.MyPoint.Intensity = "mid";
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_afterLog_to_getWorkTime);

            }
        });


        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetWorkTime.MyPoint.Intensity = "low";
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_afterLog_to_getWorkTime);

            }
        });

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_afterLog_to_recommendFragment);



            }
        });

    }
}