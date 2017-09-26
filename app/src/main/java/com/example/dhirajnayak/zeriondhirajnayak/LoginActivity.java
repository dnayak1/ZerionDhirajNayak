package com.example.dhirajnayak.zeriondhirajnayak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhirajnayak.zeriondhirajnayak.Constants.ApiClient;
import com.example.dhirajnayak.zeriondhirajnayak.Interface.IRetrofit;
import com.example.dhirajnayak.zeriondhirajnayak.model.LoginRequest;
import com.example.dhirajnayak.zeriondhirajnayak.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    TextView _signupLink;
    Button _loginButton;
    EditText _passwordText;
    IRetrofit iRetrofit;
    String token;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText= (EditText) findViewById(R.id.input_email);
        _passwordText= (EditText) findViewById(R.id.input_password);
        _loginButton= (Button) findViewById(R.id.btn_login);
        _signupLink= (TextView) findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        if (!validate()) {
            onLoginFailed("Login failed");
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        LoginRequest loginRequest=new LoginRequest();
                        loginRequest.setEmail(email);
                        loginRequest.setPassword(password);
                        onLoginSuccess(loginRequest);
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess(LoginRequest loginRequest) {
        _loginButton.setEnabled(true);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service=retrofit.create(IRetrofit.class);
        Call<LoginResponse> call=service.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getCode().equals("200")){
                    SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("loginToken",response.body().getToken());
                    setResult(RESULT_OK);
                    editor.apply();
                    finish();
                }else{
                    onLoginFailed(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("text",t.getMessage());
            }
        });
//        finish();
    }

    public void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
