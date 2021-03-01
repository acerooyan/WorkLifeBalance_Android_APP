package com.example.cs125;


public class UserData  {






    String Label;
    String Sart_Time;
    String WorkTime;




    public UserData(String nickname, String sart_Time, String workTime) {
        Label = nickname;
        //User_location = user_location;
        Sart_Time = sart_Time;
        WorkTime = workTime;
        //End_time = end_time;
    }




    public String getWorkTime() {
        return WorkTime;
    }

    public String getSart_Time() {
        return Sart_Time;
    }





}
