package com.example.cs125;


/*


contributes by: Yang Lu, Haowei Song


Main component of the software, this file handles I/O of user's working data to database.
Each time have complete a work system automatically saves user data to firebase, which includes the Lable of the location,
work time, work intensity.

 */


import android.os.Build;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DatabaseListner {


    public static LocalDateTime timePicker;

    public static void insertNewData(String Uid){

        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference("user");
        String longtiude_latitude1 = Double.toString(GetWorkTime.MyPoint.Longti) + " " + Double.toString(GetWorkTime.MyPoint.Lati);

        longtiude_latitude1 = longtiude_latitude1.replace('.', '_');
        UserData data = new UserData(GetWorkTime.MyPoint.NICKname, GetWorkTime.MyPoint.Start, GetWorkTime.MyPoint.NC, GetWorkTime.MyPoint.Intensity);
        database.child(Uid).child(longtiude_latitude1).setValue(data);
    }



    public static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
    {
        public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
        {
            return n.getValue()-m.getValue();
        }
    }

    // Get Recommend work time and work intensity for user based on other's user's location and time ;
    public static void choice_user_top(List<String> same_location_intensity, List<String> same_location_work, List<String> same_location_start, boolean top3){
        int index = same_location_intensity.size() -1;
        HashMap<String, Integer> Myrate = new HashMap<String, Integer>();

        for(int i = 0; i < index; i++){

            int j = Integer.parseInt(same_location_start.get(i))  - ALL_UserData.time;



            if( -1 <= j && j  <= 1){
                String time_inten = same_location_work.get(i) + "_" + same_location_intensity.get(i);
                if(Myrate.containsKey(time_inten)){
                    Integer get_number = Myrate.get(time_inten);
                    Myrate.replace(time_inten, get_number, get_number + 1);
                }
                else{
                    Myrate.put(time_inten, 1);
                }
            }
        }
        String[] splitmyrate = new String[20];
        String listitem;
        List<Map.Entry<String,Integer>> list=new ArrayList<>();
        list.addAll(Myrate.entrySet());
        ValueComparator vc = new ValueComparator();
        Collections.sort(list,vc);
        List<String> myrate_result = new ArrayList<String>();
        for(Iterator<Map.Entry<String,Integer>> it=list.iterator();it.hasNext();)
        {
            listitem = it.next().toString();
            splitmyrate = listitem.split("=");
            myrate_result.add(splitmyrate[0]);

        }

        if(top3){

            for(int j = 0 ; j < 3 ; j++){


                String[] Data = myrate_result.get(j).split("_");
                String work = Data[0];
                String IN = Data[1];
                ALL_UserData.work.add(j, work);
                ALL_UserData.Inensity.add(j, IN);

            }
        }
        else{
            for(int K = 3 ; K < 8 ; K++){
                String[] Data = myrate_result.get(K -3).split("_");
                String work = Data[0];
                String IN = Data[1];
                ALL_UserData.work1.add(K-3, work);
                ALL_UserData.Inensity1.add(K-3, IN);



            }
        }
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
        public static String Intensity = "Null";



    }


    public static class ALL_UserData extends AppCompatActivity {

        public static List <String> work = new ArrayList<String>(Arrays.asList(new String[20]));
        public static List <String> Inensity = new ArrayList<String>(Arrays.asList(new String[20]));


        public static List <String> work1 = new ArrayList<String>(Arrays.asList(new String[10]));
        public static List <String> Inensity1 = new ArrayList<String>(Arrays.asList(new String[10]));

        public static int time;




    }




    public static void choice_self_lasttime(List<String> same_location_intensity, List<String> same_location_work)
    {



        int index = same_location_intensity.size() -1;
        //ALL_UserData.Inensity.add(4,same_location_intensity.get(index));
        //ALL_UserData.work.add(4,same_location_work.get(index));


    }


    public static void getAlldatas(DataSnapshot snapshot){

  List<String> same_location_intensity = new ArrayList<String>();
        List<String> same_location_work = new ArrayList<String>();
        List<String> same_location_start =  new ArrayList<String>();

        for (DataSnapshot snapshot3 : snapshot.getChildren()) {

            String[] location = snapshot3.getKey().replace('_', '.').split(" ");
            float longtiude = Float.parseFloat(location[0]);
            float latitude =  Float.parseFloat(location[1]);

            if ((longtiude - 0.0005) < GetWorkTime.MyPoint.Longti && GetWorkTime.MyPoint.Longti < (longtiude + 0.0005)) {
                if ((latitude - 0.0005) <GetWorkTime.MyPoint.Lati && GetWorkTime.MyPoint.Lati < (latitude + 0.0005)) {

                    for (DataSnapshot snapshot4 : snapshot3.getChildren()) {


                        if (snapshot4.getKey().equals("start_Time")) {


                            same_location_start = Arrays.asList(snapshot4.getValue().toString().split("\\,"));

                        }

                        if (snapshot4.getKey().equals("workTime")) {


                            same_location_work= Arrays.asList(snapshot4.getValue().toString().split("\\,"));
                        }

                        if (snapshot4.getKey().equals("workIntensity")) {

                            same_location_intensity= Arrays.asList(snapshot4.getValue().toString().split("\\,"));

                        }
                    }

                }
            }
        }
        choice_self_lasttime(same_location_intensity, same_location_work);
        choice_user_top(same_location_intensity,  same_location_work, same_location_start, true);
    }


    // get user's location data
    public static void Get_everyUserData(DataSnapshot snapshot){



        List<String> final_same_location_intensity = new ArrayList<String>();
        List<String> final_same_location_work = new ArrayList<String>();
        List<String> final_same_location_start =  new ArrayList<String>();

        for (DataSnapshot snapshot5 : snapshot.getChildren()) {
            for (DataSnapshot snapshot3 : snapshot5.getChildren()) {
                if (!snapshot3.getKey().contains("?")) {
                    String[] location = snapshot3.getKey().replace('_', '.').split(" ");
                    float longtiude = Float.parseFloat(location[0]);
                    float latitude = Float.parseFloat(location[1]);

                    if ((longtiude - 0.0005) < GetWorkTime.MyPoint.Longti && GetWorkTime.MyPoint.Longti < (longtiude + 0.0005)) {
                        if ((latitude - 0.0005) < GetWorkTime.MyPoint.Lati && GetWorkTime.MyPoint.Lati < (latitude + 0.0005)) {
                            for (DataSnapshot snapshot4 : snapshot3.getChildren()) {
                                if (snapshot4.getKey().equals("start_Time")) {
                                    List<String> same_location_start = new ArrayList<String>();
                                    same_location_start = Arrays.asList(snapshot4.getValue().toString().split("\\,"));
                                    final_same_location_start.addAll(same_location_start);
                                }

                                if (snapshot4.getKey().equals("workTime")) {
                                    List<String> same_location_work = new ArrayList<String>();
                                    same_location_work = Arrays.asList(snapshot4.getValue().toString().split("\\,"));
                                    final_same_location_work.addAll(same_location_work);
                                }
                                if (snapshot4.getKey().equals("workIntensity")) {
                                    List<String> same_location_intensity =  new ArrayList<String>();
                                    same_location_intensity = Arrays.asList(snapshot4.getValue().toString().split("\\,"));
                                    final_same_location_intensity.addAll(same_location_intensity);
                                }
                            }

                        }
                    }
                }
            }
        }

        choice_user_top(final_same_location_intensity,  final_same_location_work, final_same_location_start, false);
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
        Login_interface.UserUid.new_account = true;

        //DatabaseReference database = FirebaseDatabase.getInstance().getReference("user/fNyAWmuBpGMrekwgGUQ9h3Tp8Hx1");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("user");
        //DatabaseReference database = FirebaseDatabase.getInstance().getReference("user" + "/" + Login_interface.UserUid.uid);
        database.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getAlldata){
                    Get_everyUserData(snapshot);

                }
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.getKey().equals(Uid)) {
                        if(getAlldata){
                            getAlldatas(snapshot1);
                            return;
                        }
                        Login_interface.UserUid.new_account = false;
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            String right_location = snapshot2.getKey().replace("_", ".");
                            String[] longtiude_latitude = right_location.split(" ");
                            String Longtiude_ = longtiude_latitude[0];
                            String latitude_ = longtiude_latitude[1];
                            double longtiude = Double.parseDouble(Longtiude_);
                            double latitude = Double.parseDouble(latitude_);

                            if ((longtiude - 0.0005) < NewLong && NewLong < (longtiude + 0.0005))
                            {
                                if ((latitude - 0.0005) < NewLati && NewLati < (latitude + 0.0005))
                                {
                                    if (change)
                                    {
                                        ChangeData(snapshot2, Uid);
                                    }
                                    else
                                    {
                                        ReterieveData(snapshot2, Uid);
                                    }
                                    database.removeEventListener(this);
                                }
                            }
                        }
                        if (insert) {insertNewData(Uid);
                    }

                }

                }
                if ( Login_interface.UserUid.new_account && !Uid.contains("?")){

                    insertNewData(Uid);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                return;
            }
        });
    }


    // value event listener trigger
    public static void eventListener_trigger(String UID){

        DatabaseReference database;
        String UID1 = UID + "?";
        database = FirebaseDatabase.getInstance().getReference("user");
        String longtiude_latitudes = Double.toString(GetWorkTime.MyPoint.Longti) + " " + Double.toString(GetWorkTime.MyPoint.Lati);

        longtiude_latitudes = longtiude_latitudes.replace('.', '_') + "??" ;
        UserData data1 = new UserData(GetWorkTime.MyPoint.NICKname, GetWorkTime.MyPoint.Start, GetWorkTime.MyPoint.NC, GetWorkTime.MyPoint.Intensity);
        database.child(UID1).child(longtiude_latitudes).setValue(data1);


    }

}
