package com.example.dhirajnayak.zeriondhirajnayak.Interface;

import com.example.dhirajnayak.zeriondhirajnayak.model.IdValue;
import com.example.dhirajnayak.zeriondhirajnayak.model.Record;
import com.example.dhirajnayak.zeriondhirajnayak.model.RecordDetail;
import com.example.dhirajnayak.zeriondhirajnayak.model.UserId;

import java.util.ArrayList;

/**
 * Created by dhirajnayak on 9/25/17.
 */

public interface ICallBackHandler {

    void onSuccessIdValues(ArrayList<IdValue> values);
    void onSuccessRecordDetail(Record record);
}
