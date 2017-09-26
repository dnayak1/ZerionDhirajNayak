package com.example.dhirajnayak.zeriondhirajnayak.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dhirajnayak on 9/25/17.
 */

public class RecordDetail {
    @SerializedName("name")
    private String name;
    @SerializedName("age")
    private String age;
    @SerializedName("phone")
    private String phone;
    @SerializedName("image")
    private String image;
    @SerializedName("dob")
    private String dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
