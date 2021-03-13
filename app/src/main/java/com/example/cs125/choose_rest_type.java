package com.example.cs125;


/*

contributes by: Haowei Song



This fragment directs user to choose rest type after work


 */
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link choose_rest_type#newInstance} factory method to
 * create an instance of this fragment.
 */
public class choose_rest_type extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public choose_rest_type() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment choose_rest_type.
     */
    // TODO: Rename and change types and number of parameters
    public static choose_rest_type newInstance(String param1, String param2) {
        choose_rest_type fragment = new choose_rest_type();
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
        return inflater.inflate(R.layout.fragment_choose_rest_type, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton walkb, breathb;

        walkb = getView().findViewById(R.id.imageButton2);
        breathb = getView().findViewById(R.id.imageButton3);

        walkb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavController controller = Navigation.findNavController(v);
                //controller.navigate(R.id.action_login_interface_to_signUpFragment6);


                Intent myIntent = new Intent(getContext(), StepsCounter.class);
                startActivity(myIntent);

            }
        });


        breathb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavController controller = Navigation.findNavController(v);
                //controller.navigate(R.id.action_login_interface_to_signUpFragment6);


                Intent myIntent = new Intent(getContext(), breath.class);

                startActivity(myIntent);

            }
        });


    }
}