package com.example.dhirajnayak.zeriondhirajnayak.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dhirajnayak on 9/24/17.
 */

public class IdValue {
    @SerializedName("ID")
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
