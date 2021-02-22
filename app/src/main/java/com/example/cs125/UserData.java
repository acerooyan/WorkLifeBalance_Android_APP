package com.example.cs125;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserData  {






    String Useremail;
    String User_location;
    String Sart_Time;
    String End_time;


    public UserData(String useremail, String user_location, String sart_Time, String end_time) {
        Useremail = useremail;
        User_location = user_location;
        Sart_Time = sart_Time;
        End_time = end_time;
    }

    public String getUseremail() {
        return Useremail;
    }

    public String getUser_location() {
        return User_location;
    }

    public String getSart_Time() {
        return Sart_Time;
    }

    public String getEnd_time() {
        return End_time;
    }
}
