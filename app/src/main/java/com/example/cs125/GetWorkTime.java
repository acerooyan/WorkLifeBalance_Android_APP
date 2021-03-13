package com.example.cs125;



/*

contribute by: Yang Lu, Hongji Yan, Leerjiya Bao

This fragment gets user's work time manually, and also obtain and display context information, such as local time, local weather
and location data.
 */


import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetWorkTime#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetWorkTime extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    final String APP_ID = "30453cfd7241d277683fd96ed2791198";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;


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



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        // method to get the location





        Button StartWorkButton;



        StartWorkButton = getView().findViewById(R.id.time_sure);

        TimePicker time_picker;
        time_picker = getView().findViewById(R.id.time_picker);

        if (time_picker.getMinute() >  31){
            MyPoint.Start =  Integer.toString(time_picker.getHour() + 1)  ;
        }
        else {
            MyPoint.Start =  Integer.toString(time_picker.getHour() )  ;
        }




        Geocoder geocoder ;
        List<Address> addresses = null;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(GetWorkTime.MyPoint.Lati,  GetWorkTime.MyPoint.Longti, 1);
        } catch (IOException e) {
            Toast.makeText(getContext(),"Failed to get address data",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        if (GetWorkTime.MyPoint.check){
            return;
        }
        TextView workplace, geolo;

        workplace = getView().findViewById(R.id.workplace);
        geolo= getView().findViewById(R.id.geoloc);
        geolo.setText(country+ " " + address );
        workplace.setText("Working place");


        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APP_ID);
        ConnectWeather(params);




        StartWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText E;
                E = getView().findViewById(R.id.SetMins);
                String m;
                m  = E.getText().toString();
                if (TextUtils.isEmpty(m)){
                    E.setError("Please Enter a Number ");
                    return;
                }

                MyPoint.NC = m;
                mins.NC =   Integer.parseInt(m) ;


                showNormalDialog(v);

            }
        });


    }

    private  void ConnectWeather(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        TextView Weather, Weather1;
        Weather = getView().findViewById(R.id.Weather);
        Weather1 = getView().findViewById(R.id.weather1);




        client.get(WEATHER_URL,params,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getContext(),"Weather Data Get Success",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                Weather.setText("Local Weather: " + weatherD.getmWeatherType());
                Weather1.setText(  "Temperature: " + weatherD.getmTemperature() + " " + weatherD.getMcity());
                GetWorkTime.MyPoint.check = true;

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                String errorcode = "Error";
                String message = "Unexcepet data";

                try {
                    errorcode = errorResponse.getString("cod");
                    message = errorResponse.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Weather.setText("code " + errorcode);
                Weather1.setText(message);
                Toast.makeText(getContext(),"Failed to get weather data",Toast.LENGTH_SHORT).show();
                GetWorkTime.MyPoint.check = true;
                MyPoint.WatherType = "unknow";

            }



        });


    }

    private void showNormalDialog(View v){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setIcon(R.drawable.icon_dialog);



        if (!DatabaseListner.Old_UserData.NICKname.equals("Null")){

            normalDialog.setTitle("You are working at "+ DatabaseListner.Old_UserData.NICKname);

        }

        else {

            normalDialog.setTitle("Enter Label For Current Location: ");
        }


        final  EditText NicknameInput = new EditText(getContext());
        NicknameInput.setInputType(InputType.TYPE_CLASS_TEXT);


        normalDialog.setView(NicknameInput);
        //normalDialog.setMessage("If yes please give please enter a nickname to the location");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String s  = NicknameInput.getText().toString();
                        if (TextUtils.isEmpty(s)){
                            NicknameInput.setError("Label Cannot Be Empty!");
                        }

                        MyPoint.NICKname = s;


                        NavController controller = Navigation.findNavController(v);
                        controller.navigate(R.id.action_getWorkTime_to_myTimer);


                    }


                });
        normalDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        NavController controller = Navigation.findNavController(v);

                        controller.navigate(R.id.action_getWorkTime_to_myTimer);

                 }
                });
        // 显示
        normalDialog.show();

    }





    public static class MyPoint extends AppCompatActivity{

        public static double Longti;
        public static double Lati;
        public static String WatherType ;
        public static boolean check = false;
        public static String Start;
        public static String NICKname = "Null";
        public static String Intensity;
        public static String NC;




    }


    public static class mins extends AppCompatActivity{

        public static int NC;
    }


}