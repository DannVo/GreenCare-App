package com.example.iotapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iotapplication.API.UserAPIs;
import com.example.iotapplication.Adapter.AddNewDevice.AddNewInfo;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDeviceActivity extends AppCompatActivity {

    EditText deviceid, devName, slug, description;
    MaterialButton mbAdd, mbCancel;
    SharedPreferences sharedPreferences;
    Toolbar toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_device_activity);



        init();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDeviceActivity.this, MainActivity.class));
            }
        });

        mbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewDevices();
            }
        });


    }

    public void addNewDevices(){
        String deviceID = deviceid.getText().toString();
        String deviceName = devName.getText().toString();
        String deviceDesc = description.getText().toString();
//        String newPath = "";
        Log.e("GET: ","Name device: "+ deviceName);
        Log.e("GET: ","Name device: "+ deviceID);
        Log.e("GET: ","Name device: "+ deviceDesc);

        sharedPreferences = getSharedPreferences(MainActivity.fileName, Context.MODE_PRIVATE);
        String newValue = sharedPreferences.getString("user_id","");

        Log.e("GET: ","Name device: "+ newValue);
        // POST the information device
        UserAPIs.userApi.addNewDevice1(newValue,deviceID,deviceName,deviceDesc).enqueue(new Callback<AddNewInfo>() {
            @Override
            public void onResponse(Call<AddNewInfo> call, Response<AddNewInfo> response) {
                if(response.isSuccessful()) {
                    AddNewInfo detailResponse = response.body();

//                    Log.e("TAG", "response 77: " + detailResponse.getDataNewInfo().getDevice_id());
                    Log.e("TAG", "response 77: " + response.toString());
                    Toast.makeText(AddDeviceActivity.this, "Add new data successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddDeviceActivity.this, MainActivity.class));


                }else{
                    Toast.makeText(AddDeviceActivity.this, "Cannot add new data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddNewInfo> call, Throwable t) {
                Toast.makeText(AddDeviceActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorLogin Message: ",t.getMessage());
            }
        });
    }

    public String createNewPath(String user_id, String name){

        String replaced_name = name.replace(" ","%20");
        Log.e("Create new path: ","Test 1: " + replaced_name);
        String newstr = "?"+"user_id="+user_id+"&"+"name="+name;

        Log.e("Create new path: ","Test 1: " + newstr);

        return newstr;



    }


    public void init(){
        deviceid = findViewById(R.id.etIdIoT);
        devName = findViewById(R.id.devName);
//        slug = findViewById(R.id.slug);
        description = findViewById(R.id.etDescript);
        mbAdd = findViewById(R.id.mbAdd);
        mbCancel = findViewById(R.id.mbCancel);
        toolbar =  findViewById(R.id.toolBar);

    }

}