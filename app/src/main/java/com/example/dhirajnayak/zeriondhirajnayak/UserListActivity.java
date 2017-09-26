package com.example.dhirajnayak.zeriondhirajnayak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient;
import com.example.dhirajnayak.zeriondhirajnayak.Interface.ICallBackHandler;
import com.example.dhirajnayak.zeriondhirajnayak.Interface.IRetrofit;
import com.example.dhirajnayak.zeriondhirajnayak.model.IdValue;
import com.example.dhirajnayak.zeriondhirajnayak.model.LoginResponse;
import com.example.dhirajnayak.zeriondhirajnayak.model.Record;
import com.example.dhirajnayak.zeriondhirajnayak.model.RecordDetail;
import com.example.dhirajnayak.zeriondhirajnayak.model.UserId;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.CLIENT_KEY;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.CLIENT_SECRET;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.GET_IDS_URL;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.URL_GRANT_TOKEN;

public class UserListActivity extends AppCompatActivity implements GenerateGrantTokenAsyncTask.IData ,ICallBackHandler, IdRecyclerAdapter.IdListener{
    SharedPreferences sharedPreferences;
    String userToken,grantToken,authHeader;
    ArrayList<IdValue> idsList;
    IdRecyclerAdapter adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        progressBar= (ProgressBar) findViewById(R.id.listProgressBar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        userToken=sharedPreferences.getString("loginToken",null);
        grantToken=sharedPreferences.getString("grantToken",null);
        //grantToken="0fac46e62cb94deefe934250bebff8ca1a884066";
        authHeader="Bearer "+grantToken;
        getIdList(authHeader);
        adapter=new IdRecyclerAdapter(UserListActivity.this,idsList,UserListActivity.this);

    }

    @Override
    public void setupData(String token) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("grantToken",token);
        editor.apply();
        authHeader="Bearer "+token;
        getIdList(authHeader);

    }

    public void getIdList(String authHeader){
        final UserId userId = new UserId();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(GET_IDS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service=retrofit.create(IRetrofit.class);
        Call<UserId> call=service.getUserIds(authHeader);
        call.enqueue(new Callback<UserId>() {
            @Override
            public void onResponse(Call<UserId> call, Response<UserId> response) {
                if(response.isSuccessful()){
                    userId.setRECORDS(response.body().getRECORDS());
                    onSuccessIdValues(userId.getRECORDS());

                }else{
                    new GenerateGrantTokenAsyncTask(UserListActivity.this).execute(CLIENT_KEY,CLIENT_SECRET,URL_GRANT_TOKEN);
                }

            }

            @Override
            public void onFailure(Call<UserId> call, Throwable t) {
                Toast.makeText(UserListActivity.this,"Invalid token", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void idDetail(IdValue idValue) {
        Intent intent=new Intent(UserListActivity.this,RecordDetailActivity.class);
        intent.putExtra("idValue",String.valueOf(idValue.getId()));
        startActivity(intent);
    }

    @Override
    public void onSuccessIdValues(ArrayList<IdValue> values) {
        progressBar.setVisibility(View.INVISIBLE);
        idsList = values;
        adapter=new IdRecyclerAdapter(UserListActivity.this,idsList,UserListActivity.this);
        recyclerView.setAdapter(adapter);
        layoutManager=new LinearLayoutManager(UserListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessRecordDetail(Record record) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
