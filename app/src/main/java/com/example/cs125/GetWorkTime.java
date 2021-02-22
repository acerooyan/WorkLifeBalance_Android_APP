package com.example.cs125;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
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

        public static int NC;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        // method to get the location



        Button StartWorkButton;
        TimePicker time_picker;


        StartWorkButton = getView().findViewById(R.id.time_sure);
        time_picker = getView().findViewById(R.id.time_picker);
        MyPoint.Start =  Integer.toString(time_picker.getHour()) + ":" + Integer.toString(time_picker.getMinute()) ;




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
                mins.NC =   Integer.parseInt(m) * 60;






                showNormalDialog(v);









            }
        });


    }


    private void LocationDialog(View v){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("Location");
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

    private void showNormalDialog(View v){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("Do you want save current location");
        //normalDialog.setMessage("If yes please give please enter a nickname to the location");
        normalDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LocationDialog(v);
                    }
                });
        normalDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference database;
                        //String UID = Login_interface.UserUid.uid;
                        String UID = "fNyAWmuBpGMrekwgGUQ9h3Tp8Hx1";
                        database = FirebaseDatabase.getInstance().getReference("user");

                        String longtiude_latitude = Double.toString(MyPoint.Longti) + " " + Double.toString(MyPoint.Lati);

                        UserData data = new UserData(Login_interface.UserUid.Useremail, longtiude_latitude, MyPoint.Start, "1");

                        //database.child(Login_interface.UserUid.uid).child("456").setValue(data);

                        database.child(UID).child("456").setValue(data);


                        NavController controller = Navigation.findNavController(v);

                        controller.navigate(R.id.action_getWorkTime_to_countDown6);





                    }
                });
        // 显示
        normalDialog.show();

    }




    // 地址
    public static class MyPoint extends AppCompatActivity{

        public static double Longti;
        public static double Lati;
        public static boolean check = false;
        public static String Start;

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (MyPoint.check){
            return;
        }

        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (!MyPoint.check){
                        requestPermissions();
                        requestNewLocationData();}



                    }
                });
            } else {
                Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();


            }
        } else {
            // if permissions aren't available,
            // request for permissions
            //Log.d()
            requestPermissions();

        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            MyPoint.Longti = mLastLocation.getLongitude();
            MyPoint.Lati = mLastLocation.getLatitude();


            Geocoder geocoder ;
            List<Address> addresses = null;
            geocoder = new Geocoder(getContext(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(MyPoint.Lati,  MyPoint.Longti, 1);
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


            TextView workplace, geolo;

            workplace = getView().findViewById(R.id.workplace1);
            geolo= getView().findViewById(R.id.geoloc);
            geolo.setText(country+ " " + address );
            workplace.setText("Working place");


            RequestParams params=new RequestParams();
            params.put("q",city);
            params.put("appid",APP_ID);
            ConnectWeather(params);
        }
    };

    private  void ConnectWeather(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        TextView Weather, Weather1;
        Weather = getView().findViewById(R.id.Weather);
        Weather1 = getView().findViewById(R.id.weather1);




        client.get(WEATHER_URL,params,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getContext(),"Data Get Success",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                Weather.setText("Local Weather " + weatherD.getmWeatherType());
                Weather1.setText(  "Temperature: " + weatherD.getmTemperature() + " " + weatherD.getMcity());
                MyPoint.check = true;
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
                MyPoint.check = true;
            }



        });


    }




    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}