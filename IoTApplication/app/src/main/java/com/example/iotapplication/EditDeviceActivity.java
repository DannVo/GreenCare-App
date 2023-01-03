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
import com.example.iotapplication.Adapter.EditDeviceData.EditDetailInfo;
import com.example.iotapplication.Adapter.AddNewDevice.GetAllInfo;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDeviceActivity extends AppCompatActivity {

    EditText etName, etDesc;
    MaterialButton mbUpdate, mbCancel;
    SharedPreferences sharedDeviceDetail,sharedPreferences;
    Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_device);


        init();

        sharedDeviceDetail = getSharedPreferences("detail_dev", Context.MODE_PRIVATE);
        String checkName = sharedDeviceDetail.getString("device_name","");
        mbUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateNewInfo(checkName);

            }
        });
        mbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditDeviceActivity.this, MainActivity.class));
            }
        });
    }

    public void init(){
        etName = findViewById(R.id.deviceName);
        etDesc = findViewById(R.id.devDesc);
        mbUpdate = findViewById(R.id.mbUpdate);
        mbCancel = findViewById(R.id.mbCancel);
        toolbar =  findViewById(R.id.toolBar);
    }

    public void UpdateNewInfo(String checkName){
        String newName = etName.getText().toString();
        String newDesc = etDesc.getText().toString();

        String ID_SLUG = checkNamefindID(checkName);
        Log.e("TAG", "response 63" + newDesc);

        UserAPIs.userApi.updateInfo(newName, newDesc, "ON", ID_SLUG).enqueue(new Callback<EditDetailInfo>() {
            @Override
            public void onResponse(Call<EditDetailInfo> call, Response<EditDetailInfo> response) {
                Log.e("TAG", "response 1306: " + response.toString());
                if (response.isSuccessful()) {

                    Log.e("UPDATE", "Update successfully");
                    Toast.makeText(EditDeviceActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                    if (!sharedPreferences.getString("id_device", "").isEmpty()) {
                        sharedPreferences = getSharedPreferences(MainActivity.fileName, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("id_device"); //we are removing prodId by key
                        editor.commit();

                    }
                    startActivity(new Intent(EditDeviceActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<EditDetailInfo> call, Throwable t) {
                Toast.makeText(EditDeviceActivity.this, "Cannot Update", Toast.LENGTH_SHORT).show();
                Log.e("ErrorUpdate Message: ", t.getMessage());
            }
        });


    }
    
    public String checkNamefindID(String device_name){

        sharedPreferences = getSharedPreferences(MainActivity.fileName, Context.MODE_PRIVATE);

        String newValue = sharedPreferences.getString("user_id","");

//        Log.e("TAG", "response 1406: " + newValue);
        //GET all information
        UserAPIs.userApi.callAllInfo(newValue).enqueue(new Callback<GetAllInfo>() {
            @Override
            public void onResponse(Call<GetAllInfo> call, Response<GetAllInfo> response) {
                Log.e("TAG", "response 1406: " + device_name);
                if(response.isSuccessful()) {
                    GetAllInfo deviceResponse = response.body();
//                    Log.e("TAG", "response 70: " + deviceResponse.getData().get(0).getId().toString());
                    Integer sizeData = deviceResponse.getData().size();
                    SharedPreferences.Editor idEditor = sharedPreferences.edit();

                    String tempp = "";
                    for (int i = 0; i < sizeData; i++) {
                        String temp = deviceResponse.getData().get(i).getName().toString();
                        String temp_id = deviceResponse.getData().get(i).getDevice_id().toString();

                        if (temp.compareTo(device_name)==0) {
                            idEditor.putString("id_device",temp_id);
                            idEditor.commit();

                            break;

                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<GetAllInfo> call, Throwable t) {
                Toast.makeText(EditDeviceActivity.this, "Cannot Find ID", Toast.LENGTH_SHORT).show();
                Log.e("ErrorFindID Message: ",t.getMessage());
            }
        });

        String temp1 = sharedPreferences.getString("id_device","");
        Log.e("TAG", "response 99: " + temp1);

        return temp1;
    }

}