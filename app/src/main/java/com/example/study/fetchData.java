package com.example.study;

import android.widget.TextView;

public class fetchData {
    private String Name, Email,MobileNo,Gender;

    public fetchData(){

    }

    public fetchData(String name, String email, String mobile, String gender) {
        Name = name;
        Email = email;
        MobileNo = mobile;
        Gender = gender;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobile) {
        MobileNo = mobile;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

}
