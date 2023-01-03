package com.example.iotapplication.Async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.iotapplication.API.AsyncCallBack;
import com.example.iotapplication.GetPostActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetAsyncTask extends AsyncTask<Void,Void,String> {
    private GetPostActivity activity;
    private AsyncCallBack asyncCallBack;
    private ProgressBar progressBar;

    public GetAsyncTask setInstance(Context context, ProgressBar progressBar){
        this.activity = (GetPostActivity) context;
        asyncCallBack = (AsyncCallBack) context;
        this.progressBar = progressBar;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpGet httpGet = new HttpGet("http://192.168.1.100/api/sensors/get-data/");
        HttpClient client = new DefaultHttpClient();
        String result = "";

        try{
            HttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            Log.d("TAG", String.valueOf(statusCode));
            if (statusCode == 200){
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                Log.d("TAG", String.valueOf(result));

            }
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        asyncCallBack.setResult(s);
    }
}
