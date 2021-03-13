package com.example.cs125;
/*
contributes by:  Hongji Yan

This fragment has enables user to choose rest type

 */
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFragment extends Fragment {

    String introduactionArray[] = new String[]{
            "This option is based on your data. system have looked up the working intensity and working time data of this location before, and then select your most frequently used data to recommend to you.",
            "This option is based on your data. system have looked up the working intensity and working time data of this location before, and then select your top 3 data to recommend to you.",
            "This option is based on your data. system have looked up the working intensity and working time data of this location before, and then select your top 2 data to recommend to you.",

            "This option is based on the data of other users. system have looked up the data of all users who have worked in this location before, and select the most used data to recommend to you.",
            "This option is based on the data of other users. system have looked up for the data of all users who have worked in this location before, and select the top 2 data to recommend to you.",
            "This option is based on the data of other users. system have looked up for the data of all users who have worked in this location before, and select the top 3 data to recommend to you." ,
            "This option is based on the data of other users. system have looked up for the data of all users who have worked in this location before, and select the top 4 data to recommend to you.",
            "This option is based on the data of other users. system have looked up for the data of all users who have worked in this location before, and select the top 5 data to recommend to you.",
            "The system will arrange suitable work intensity and working hours for you according to your working start time.",
            "The system will get your last work intensity and working time at this location and recommend it to you."
    };

    String items[] = new String[] {"your top one choice in this location", "your top two choice in this location", "your top three choice in this location", "Top 1 choice from other user in this location",
    "Top 2 choice from other user in this location", "Top 3 choice from other user in this location", "Top 4 choice from other user in this location", "Top 5 choice from other user in this location"
    , "System choice for you", "Your last time choice"};


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecommendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendFragment newInstance(String param1, String param2) {
        RecommendFragment fragment = new RecommendFragment();
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
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ListView listView = getView().findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, items);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recommand_data.Position = position;

                if (position > 2 & position != 9){
                    position -= 3;

                    Recommand_data.Recommand_Intensity = DatabaseListner.ALL_UserData.Inensity1.get(position);
                    Recommand_data.Recommand_Work_mins = DatabaseListner.ALL_UserData.work1.get(position);


                }

                else if(position == 9){

                    position = 4;
                    Log.d("Mytest", String.valueOf(position));
                    Log.d("Mytest", DatabaseListner.ALL_UserData.work.get(position));
                    Recommand_data.Recommand_Intensity = DatabaseListner.ALL_UserData.Inensity.get(position);
                    Recommand_data.Recommand_Work_mins = DatabaseListner.ALL_UserData.work.get(position);
                }
                else {

                    Recommand_data.Recommand_Intensity = DatabaseListner.ALL_UserData.Inensity.get(position);
                    Recommand_data.Recommand_Work_mins = DatabaseListner.ALL_UserData.work.get(position);
                }


                GetWorkTime.MyPoint.Intensity = Recommand_data.Recommand_Intensity;
                GetWorkTime.MyPoint.NC = Recommand_data.Recommand_Work_mins;

                GetWorkTime.mins.NC = Integer.parseInt(Recommand_data.Recommand_Work_mins);
                showNormalDialog(view);


            }
        });





    }


    public static class Recommand_data extends AppCompatActivity {


        public static String Recommand_Intensity;
        public static String Recommand_Work_mins;

        public static int Position;



    }

    private void showNormalDialog(View v){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setIcon(R.drawable.icon_dialog);





        normalDialog.setTitle("System Reconmmend You");
        String introduction =   "\n" + introduactionArray[Recommand_data.Position];
        String MakeSure = "\n Click Yes if you want this options";
        normalDialog.setMessage("Work Intensity:" + Recommand_data.Recommand_Intensity + "\n" + "Work Time:" + Recommand_data.Recommand_Work_mins + " Mins\n" + introduction + MakeSure);







        //normalDialog.setMessage("If yes please give please enter a nickname to the location");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        NavController controller = Navigation.findNavController(v);
                        controller.navigate(R.id.action_recommendFragment_to_myTimer);


                    }


                });
        normalDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {





                    }
                });
        // 显示
        normalDialog.show();

    }
}