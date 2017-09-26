package com.example.dhirajnayak.zeriondhirajnayak.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dhirajnayak on 9/25/17.
 */

public class Record {
    @SerializedName("record")
    private RecordDetail recordDetail;

    public RecordDetail getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(RecordDetail recordDetail) {
        this.recordDetail = recordDetail;
    }
}
