package com.example.iotapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iotapplication.API.AsyncCallBack;
import com.example.iotapplication.Adapter.AsyncPostDataIoT.GetDataIoT;
import com.example.iotapplication.Async.GetAsyncTask;
import com.example.iotapplication.Async.PostAsyncTask;

public class GetPostActivity extends AppCompatActivity implements AsyncCallBack {

    Button btnGetRequest, btnPostRequest;
    TextView txtResultValue;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_post);

        init();

        btnGetRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new GetAsyncTask().setInstance(GetPostActivity.this, progressBar)
                        .execute();
            }
        });

        btnPostRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "castus tree";
                GetDataIoT getDataIoT = new GetDataIoT("DV00001","Cay_Dinh_Lang", 29.8f,37.5f,715);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new PostAsyncTask().setInstance(GetPostActivity.this,progressBar).execute(getDataIoT);
                    }
                },12000);


//                new PostAsyncTask().setInstance(GetPostActivity.this, progressBar)
//                        .execute(getDataIoT);
//                Log.e("TAG", )
            }
        });
    }

//    @Override
//    public void setResult(String result){
//        txtResultValue.setText(result);
//    }

    public void init(){
        btnGetRequest = findViewById(R.id.btnGetRequest);
        btnPostRequest = findViewById(R.id.btnPostRequest);
        txtResultValue = findViewById(R.id.txtResultValue);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void setResult(String s) {
        txtResultValue.setText(s.toString());
    }
}