package com.example.dhirajnayak.zeriondhirajnayak;

import android.os.AsyncTask;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dhirajnayak on 9/24/17.
 */

public class GenerateGrantTokenAsyncTask extends AsyncTask<String,Void,String> {
    IData iData;

    public GenerateGrantTokenAsyncTask(IData iData) {
        this.iData = iData;
    }

    @Override
    protected String doInBackground(String... strings) {
        String clientKey=strings[0];
        String clientSecret=strings[1];
        String urlString=strings[2];

        String token = "";
        Map<String, Object> customHeader = new HashMap<String,Object>();
        customHeader.put("typ", "JWT");
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, null, null, null,
                null, null, null, null, null, null, null, customHeader, null);
        // Create HMAC signer
        JWSSigner signer = null;
        try {
            signer = new MACSigner(clientSecret);
        } catch (KeyLengthException e) {
            e.printStackTrace();
        }
        // Prepare JWT with claims set
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .claim("iss",clientKey)
                .audience(urlString)
                .expirationTime(new Date(System.currentTimeMillis()+5*60*1000))
                .issueTime(new Date())
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        // Apply the HMAC
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        // serialize to compact form
        String jwt = signedJWT.serialize();

        try {
            URL url=new URL(urlString);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setDoOutput (true);
            connection.setDoInput (true);

            LinkedHashMap<String, String> params=new LinkedHashMap<>();
            params.put("grant_type","urn:ietf:params:oauth:grant-type:jwt-bearer");
            params.put("assertion", jwt);

            OutputStream outputStream=connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataString(params));
            writer.flush();
            writer.close();
            outputStream.close();
            int statusCode=connection.getResponseCode();
            if(statusCode== HttpsURLConnection.HTTP_OK){
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder=new StringBuilder();
                String line=bufferedReader.readLine();
                while ((line!=null)){
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();
                }
                return stringBuilder.toString().substring(17, 57);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        iData.setupData(s);
    }

    static public interface IData{
        public void setupData(String token );
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
