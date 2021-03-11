package com.example.cs125;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseListner {


    public static void insertNewData( String Uid){





        DatabaseReference database;


        database = FirebaseDatabase.getInstance().getReference("user");


        String longtiude_latitude1 = Double.toString(GetWorkTime.MyPoint.Longti) + " " + Double.toString(GetWorkTime.MyPoint.Lati);

        longtiude_latitude1 = longtiude_latitude1.replace('.', '_');
        UserData data = new UserData(GetWorkTime.MyPoint.NICKname, GetWorkTime.MyPoint.Start, GetWorkTime.MyPoint.NC, GetWorkTime.MyPoint.Intensity);

        database.child(Uid).child(longtiude_latitude1).setValue(data);

    }





    public static void ChangeData(DataSnapshot snapshot, String Uid){

        for (DataSnapshot snapshot3 : snapshot.getChildren()) {

            if (snapshot3.getKey().equals("start_Time")) {
                String newSatrt = GetWorkTime.MyPoint.Start;
                GetWorkTime.MyPoint.Start = snapshot3.getValue() + "," + newSatrt;

            }

            if (snapshot3.getKey().equals("workTime")) {
                String newWork_time = GetWorkTime.MyPoint.NC;
                String old_time = snapshot3.getValue().toString();
                GetWorkTime.MyPoint.NC = old_time + "," + newWork_time;


            }

            if (snapshot3.getKey().equals("label") && !snapshot3.getValue().toString().equals("Null")) {

                GetWorkTime.MyPoint.NICKname = snapshot3.getValue().toString();

            }


            if (snapshot3.getKey().equals("workIntensity")) {
                String newin= GetWorkTime.MyPoint.Intensity;
                String old_in = snapshot3.getValue().toString();
                GetWorkTime.MyPoint.Intensity = newin + "," + old_in;


            }

            insertNewData(Uid);


        }


    }



    public static class Old_UserData extends AppCompatActivity {


        public static String NICKname = "Null";
        public static String WorkTime = "Null";
        public static String start_time = "Null";
        public static String Intensity;


    }


    public static class ALL_UserData extends AppCompatActivity {


        public static String[] same_location_start;
        public static String[] same_location_intensity;
        public static String[] same_location_work;
        public static String Intensity;


    }




    // get all location data
    public static void getAlldatas(DataSnapshot snapshot){

        for (DataSnapshot snapshot3 : snapshot.getChildren()) {

            String[] location = snapshot3.getKey().replace('_', '.').split(" ");
            float longtiude = Float.parseFloat(location[0]);
            float latitude =  Float.parseFloat(location[1]);

            if ((longtiude - 0.0005) < GetWorkTime.MyPoint.Longti && GetWorkTime.MyPoint.Longti < (longtiude + 0.0005)) {
                if ((latitude - 0.0005) <GetWorkTime.MyPoint.Lati && GetWorkTime.MyPoint.Lati < (latitude + 0.0005)) {

                    for (DataSnapshot snapshot4 : snapshot3.getChildren()) {


                        if (snapshot4.getKey().equals("start_Time")) {

                            Log.d("MyTAG", snapshot4.getValue().toString());

                            ALL_UserData.same_location_start = snapshot4.getValue().toString().split("\\,");


                            Log.d("MyTAG", ALL_UserData.same_location_start[0]);
                            Log.d("MyTAG", ALL_UserData.same_location_start[3]);
                        }

                        if (snapshot4.getKey().equals("workTime")) {


                            ALL_UserData.same_location_work= snapshot4.getValue().toString().split("\\,");
                        }




                        if (snapshot4.getKey().equals("workIntensity")) {

                            ALL_UserData.same_location_intensity= snapshot4.getValue().toString().split("\\,");

                        }


                    }

                }
            }




            for (DataSnapshot snapshot4 : snapshot3.getChildren()) {

                //Log.d("Mytagkey", snapshot4.getKey());
                //Log.d("Mytag", snapshot4.getValue().toString());


            }

        }

    }


    // reterieve current location data if there's
    public static void ReterieveData(DataSnapshot snapshot, String Uid){



        for (DataSnapshot snapshot3 : snapshot.getChildren()) {

            if (snapshot3.getKey().equals("start_Time")) {

                Old_UserData.start_time = snapshot3.getValue().toString();

            }

            if (snapshot3.getKey().equals("workTime")) {

                Old_UserData.WorkTime = snapshot3.getValue().toString();



            }

            if (snapshot3.getKey().equals("label")) {



                Old_UserData.NICKname = snapshot3.getValue().toString();

            }

            if (snapshot3.getKey().equals("WorkIntensity")){

                Old_UserData.Intensity = snapshot3.getValue().toString();
            }
        }

    }


    public static void CheckSameLocation(String Uid, boolean change, boolean insert, boolean getAlldata){


        double NewLong = GetWorkTime.MyPoint.Longti;
        double NewLati = GetWorkTime.MyPoint.Lati;

        //DatabaseReference database = FirebaseDatabase.getInstance().getReference("user/fNyAWmuBpGMrekwgGUQ9h3Tp8Hx1");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("user");
        //DatabaseReference database = FirebaseDatabase.getInstance().getReference("user" + "/" + Login_interface.UserUid.uid);


        database.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.getKey().equals(Uid)) {
                        if(getAlldata){
                            getAlldatas(snapshot1);
                            return;
                        }

                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                                    String right_location = snapshot2.getKey().replace("_", ".");
                                    String[] longtiude_latitude = right_location.split(" ");
                                    String Longtiude_ = longtiude_latitude[0];
                                    String latitude_ = longtiude_latitude[1];
                                    double longtiude = Double.parseDouble(Longtiude_);
                                    double latitude = Double.parseDouble(latitude_);

                                    if ((longtiude - 0.0005) < NewLong && NewLong < (longtiude + 0.0005)) {
                                        if ((latitude - 0.0005) < NewLati && NewLati < (latitude + 0.0005)) {
                                            if (change) {
                                                ChangeData(snapshot2, Uid);
                                            } else {
                                                ReterieveData(snapshot2, Uid);
                                            }
                                            database.removeEventListener(this);
                                        }
                                    }

                        }

                        if (insert) {
                            insertNewData(Uid);
                        } else {
                            Login_interface.UserUid.new_account = true;
                        }
                    }

                }

                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                return;
            }
        });


    }

    public static void eventListener_trigger(String UID){
        // value event listener trigger
        DatabaseReference database;

        String UID1 = UID + "?";
        database = FirebaseDatabase.getInstance().getReference("user");


        String longtiude_latitudes = Double.toString(GetWorkTime.MyPoint.Longti) + " " + Double.toString(GetWorkTime.MyPoint.Lati);



        longtiude_latitudes = longtiude_latitudes.replace('.', '_') + "??" ;
        UserData data1 = new UserData(GetWorkTime.MyPoint.NICKname, GetWorkTime.MyPoint.Start, GetWorkTime.MyPoint.NC, GetWorkTime.MyPoint.Intensity);

        database.child(UID1).child(longtiude_latitudes).setValue(data1);


    }

}
