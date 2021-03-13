package com.example.cs125;

/*

contribute by: Leerjiya Bao




First interface of the software, software has to successfully retrieve the location data in x-y coordinate format before
user use the software.

User shall able to log in with their email account and password after successfully sign up, if they don't have an account.
It's linked with firebase  authentication
 */


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
 * Use the {@link Login_interface#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_interface extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final String APP_ID = "30453cfd7241d277683fd96ed2791198";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_interface() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login_interface.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_interface newInstance(String param1, String param2) {
        Login_interface fragment = new Login_interface();
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

    public static class UserUid extends AppCompatActivity {

        public static String uid;
        public static String Useremail;
        public static boolean check = false;
        public static boolean new_account = false;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_interface, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ProgressBar progressBar;
        EditText Email, password;
        FirebaseAuth firebase;

        firebase = FirebaseAuth.getInstance();
        Email = getView().findViewById(R.id.editTextTextEmailAddress2);
        password =  getView().findViewById(R.id.editTextTextPassword2);
        progressBar = getView().findViewById(R.id.progressBar2);


        Button button;
        Button SignUpButton;

        button = getView().findViewById(R.id.LogninButton);
        SignUpButton = getView().findViewById(R.id.SignUpbutton);
        TimePicker timePicker;
        timePicker = getView().findViewById(R.id.time_picker1);

        DatabaseListner.ALL_UserData.time = timePicker.getHour();

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.INVISIBLE);
                Login_interface.UserUid.uid = "9n13qsDpvzess72Ky1Vwsy9vWSs1";



                String username = Email.getText().toString().trim();
                String mima = password.getText().toString().trim();
                if(TextUtils.isEmpty(username)  ){

                    Email.setError("Email address cannot be empty");

                    return;

                }
                if(TextUtils.isEmpty(mima)){
                    password.setError("\"cannot be empty\"");

                    return;

                }


                if(mima.length() < 8){
                    password.setError("password must be at least 8 characters");
                    return;

                }




                firebase.signInWithEmailAndPassword(username, mima).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "login suessuflly!", Toast.LENGTH_LONG).show();


                            String uids = firebase.getUid();
                            UserUid.uid = uids;
                            UserUid.Useremail = username;


                            DatabaseListner.CheckSameLocation(Login_interface.UserUid.uid, false, false, false);
                            DatabaseListner.CheckSameLocation(Login_interface.UserUid.uid, false, false, true);

                            DatabaseListner.eventListener_trigger( Login_interface.UserUid.uid );






                            NavController controller = Navigation.findNavController(v);
                            controller.navigate(R.id.action_login_interface_to_afterLog);
                        }
                        else{
                            Toast.makeText(getContext(), "incorrect email/password" + task.getException().getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }
                });


            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_login_interface_to_signUpFragment6);

            }
        });
    }



    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (UserUid.check){

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
                        if (!UserUid.check){
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

            GetWorkTime.MyPoint.Longti = mLastLocation.getLongitude();
            GetWorkTime.MyPoint.Lati = mLastLocation.getLatitude();
            Toast.makeText(getContext(),"Location Data Get Success",Toast.LENGTH_SHORT).show();
            UserUid.check = true;
            ProgressBar loadingLocation;
            loadingLocation = getView().findViewById(R.id.location_load);
            loadingLocation.setVisibility(View.INVISIBLE);


            Button button;
            Button SignUpButton;

            button = getView().findViewById(R.id.LogninButton);
            SignUpButton = getView().findViewById(R.id.SignUpbutton);

            button.setVisibility(View.VISIBLE);
            SignUpButton.setVisibility(View.VISIBLE);


        }
    };






    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
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
        if (checkPermissions() && !UserUid.check) {
            getLastLocation();
        }
    }
}