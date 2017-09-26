package com.example.dhirajnayak.zeriondhirajnayak;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhirajnayak.zeriondhirajnayak.Interface.ICallBackHandler;
import com.example.dhirajnayak.zeriondhirajnayak.Interface.IRetrofit;
import com.example.dhirajnayak.zeriondhirajnayak.model.IdValue;
import com.example.dhirajnayak.zeriondhirajnayak.model.Record;
import com.example.dhirajnayak.zeriondhirajnayak.model.RecordDetail;
import com.example.dhirajnayak.zeriondhirajnayak.model.UserId;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.CLIENT_KEY;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.CLIENT_SECRET;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.GET_IDS_URL;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.URL_GRANT_TOKEN;

public class RecordDetailActivity extends AppCompatActivity implements ICallBackHandler,GenerateGrantTokenAsyncTask.IData{
    String id;
    SharedPreferences sharedPreferences;
    String userToken,grantToken,authHeader;
    ImageView imageViewExpandedImage;
    TextView textViewName;
    TextView textViewAge;
    TextView textViewPhone;
    TextView textViewDob;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        id=getIntent().getStringExtra("idValue").toString();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userToken=sharedPreferences.getString("loginToken",null);
        grantToken=sharedPreferences.getString("grantToken",null);
        imageViewExpandedImage= (ImageView) findViewById(R.id.expandedImage);
        textViewName= (TextView) findViewById(R.id.textViewName);
        textViewAge= (TextView) findViewById(R.id.textViewAge);
        textViewPhone= (TextView) findViewById(R.id.textViewPhone);
        textViewDob= (TextView) findViewById(R.id.textViewDob);
        progressBar= (ProgressBar) findViewById(R.id.ProgressBarUserDetail);

        //grantToken="0fac46e62cb94deefe934250bebff8ca1a884066";
        authHeader="Bearer "+grantToken;
        getRecordDetails(authHeader,id);


//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(GET_IDS_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        IRetrofit service=retrofit.create(IRetrofit.class);
//        Call<ArrayList<Record>> call = service.getDetailRecord(authHeader,id);
//        call.enqueue(new Callback<ArrayList<Record>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Record>> call, Response<ArrayList<Record>> response) {
//                Log.d("gg",response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Record>> call, Throwable t) {
//                Log.d("tt","ii");
//            }
//        });

    }


    @Override
    public void onSuccessIdValues(ArrayList<IdValue> values) {

    }

    @Override
    public void onSuccessRecordDetail(Record record) {
        progressBar.setVisibility(View.INVISIBLE);
        RecordDetail recordDetail=record.getRecordDetail();
        Picasso.with(RecordDetailActivity.this).load(recordDetail.getImage()).into(imageViewExpandedImage);
        textViewName.setText("Name : "+recordDetail.getName());
        textViewAge.setText("Age : "+recordDetail.getAge());
        textViewPhone.setText("Phone : "+recordDetail.getPhone());
        textViewDob.setText("DOB : "+recordDetail.getDob());

    }

    @Override
    public void setupData(String token) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("grantToken",token);
        editor.apply();
        authHeader="Bearer "+token;
        getRecordDetails(authHeader,id);

    }

    public void getRecordDetails(String authHeader, String id){
        final Record record = new Record();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(GET_IDS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service=retrofit.create(IRetrofit.class);
        Call<ArrayList<Record>> call=service.getDetailRecord(authHeader,id);
        call.enqueue(new Callback<ArrayList<Record>>() {
            @Override
            public void onResponse(Call<ArrayList<Record>> call, Response<ArrayList<Record>> response) {
                if(response.isSuccessful()){
                    record.setRecordDetail(response.body().get(0).getRecordDetail());
                    onSuccessRecordDetail(record);

                    Log.d("text",response.toString());
                }else{
                    new GenerateGrantTokenAsyncTask(RecordDetailActivity.this).execute(CLIENT_KEY,CLIENT_SECRET,URL_GRANT_TOKEN);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Record>> call, Throwable t) {

            }
        });

    }
}
