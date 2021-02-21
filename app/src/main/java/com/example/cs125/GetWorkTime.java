package com.example.cs125;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetWorkTime#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetWorkTime extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GetWorkTime() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetWorkTime.
     */
    // TODO: Rename and change types and number of parameters
    public static GetWorkTime newInstance(String param1, String param2) {
        GetWorkTime fragment = new GetWorkTime();
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
        return inflater.inflate(R.layout.fragment_get_work_time, container, false);
    }

    public static class mins extends AppCompatActivity{
        TextView t;

        public static int NC;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button StartWorkButton;
        TimePicker time_picker;


        StartWorkButton = getView().findViewById(R.id.time_sure);
        time_picker = getView().findViewById(R.id.time_picker);




        StartWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText E;
                E = getView().findViewById(R.id.SetMins);
                String m;
                m  = E.getText().toString();

                mins.NC =   Integer.parseInt(m) * 60;
                showNormalDialog();
                NavController controller = Navigation.findNavController(v);
                //controller.navigate(R.id.action_getWorkTime_to_countDown6);

            }
        });


    }
    private void showNormalDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("Do you want give a to save current location");
        normalDialog.setMessage("If yes please give please enter a nickname to the location");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}