package com.example.dhirajnayak.zeriondhirajnayak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.CLIENT_KEY;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.CLIENT_SECRET;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.REQ_KEY;
import static com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient.URL_GRANT_TOKEN;

public class MainActivity extends AppCompatActivity implements GenerateGrantTokenAsyncTask.IData {

    String userToken, grantToken;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userToken=sharedPreferences.getString("loginToken",null);
        grantToken=sharedPreferences.getString("grantToken",null);
        if(userToken!=null && !userToken.isEmpty()){
            if(grantToken!=null && !grantToken.isEmpty()){
                Intent userListIntent=new Intent(MainActivity.this,UserListActivity.class);
                startActivity(userListIntent);
            }else{
                getGrantToken();
            }


        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent,REQ_KEY);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(grantToken!=null && !grantToken.isEmpty()){
                Intent userListIntent=new Intent(MainActivity.this,UserListActivity.class);
                startActivity(userListIntent);
            }else{
                getGrantToken();
            }
        }
    }

    public void getGrantToken(){
        new GenerateGrantTokenAsyncTask(MainActivity.this).execute(CLIENT_KEY,CLIENT_SECRET,URL_GRANT_TOKEN);
    }

    @Override
    public void setupData(String token) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("grantToken",token);
        editor.apply();
        Intent userListIntent=new Intent(MainActivity.this,UserListActivity.class);
        startActivity(userListIntent);
    }
}
