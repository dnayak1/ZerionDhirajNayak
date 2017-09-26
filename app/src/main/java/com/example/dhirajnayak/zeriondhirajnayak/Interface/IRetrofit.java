package com.example.dhirajnayak.zeriondhirajnayak.Interface;

import com.example.dhirajnayak.zeriondhirajnayak.model.LoginRequest;
import com.example.dhirajnayak.zeriondhirajnayak.model.LoginResponse;
import com.example.dhirajnayak.zeriondhirajnayak.model.Record;
import com.example.dhirajnayak.zeriondhirajnayak.model.RecordDetail;
import com.example.dhirajnayak.zeriondhirajnayak.model.UserId;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dhirajnayak on 9/24/17.
 */

public interface IRetrofit {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("records")
    Call<UserId> getUserIds(@Header("Authorization") String token);

    @GET("records/{recordid}/feed?FORMAT=json")
    Call<ArrayList<Record>> getDetailRecord(@Header("Authorization") String token,
                                            @Path("recordid") String recordId);

}
