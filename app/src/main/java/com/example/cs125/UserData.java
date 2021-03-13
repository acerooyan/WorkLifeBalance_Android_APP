package com.example.cs125;

// contributes by: Haowei song, Lu Yang
// User data format
public class UserData  {



    String Label;
    String Start_Time;
    String WorkTime;
    String WorkIntensity;


    public String getWorkIntensity() {
        return WorkIntensity;
    }

    public UserData(String nickname, String start_Time, String workTime, String WorkIn) {
        Label = nickname;
        //User_location = user_location;
        Start_Time = start_Time;
        WorkTime = workTime;
        WorkIntensity = WorkIn;
        //End_time = end_time;
    }



    public String getLabel() {
        return Label;
    }

    public String getWorkTime() {
        return WorkTime;
    }

    public String getStart_Time() {
        return Start_Time;
    }





}