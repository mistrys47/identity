package com.example.identity;

public class User_details {
    public String Field;
    public String Info;
    public String Verified;
    public User_details(String f1, String i1,String v1) {
        this.Field = f1;
        this.Info = i1;
        this.Verified = v1;
    }
    public String getfields() {
        return Field;
    }
    public String getInfo() {
        return Info;
    }
    public String getVerified() {
        return Verified;
    }

}
