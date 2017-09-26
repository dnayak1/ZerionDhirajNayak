package com.example.dhirajnayak.zeriondhirajnayak.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhirajnayak on 9/24/17.
 */

public class UserId {
    @SerializedName("RECORDS")
    private ArrayList<IdValue> RECORDS;

    @SerializedName("STATUS")
    private String STATUS;

    public ArrayList<IdValue> getRECORDS() {
        return RECORDS;
    }

    public void setRECORDS(ArrayList<IdValue> RECORDS) {
        this.RECORDS = RECORDS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
