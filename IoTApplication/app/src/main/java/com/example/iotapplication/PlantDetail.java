package com.example.iotapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.iotapplication.API.UserAPIs;
import com.example.iotapplication.Adapter.AddNewDevice.GetAllInfo;
import com.example.iotapplication.Adapter.ResponseStatus;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantDetail extends AppCompatActivity {
    public static final int CAMERA_ACTION_CODE = 1; //1: for picture; 2: for video
    TextView tvTitle, tvDesc, tvDate, tvStatusControl;
    TextView setTemp, setMois, setHumid, setpH;
    CircleImageView ivImage;
    ImageButton camera;
    ImageView ivEditInfo;
    ShapeableImageView sivCamera;
    SharedPreferences sharedDeviceDetail, sharedPreferences;
    MaterialButton mbStatistik,mbPopupCancel,mbPopupDelete;
    Dialog deleteDialog;
    ConstraintLayout clDeleteBut;
    Switch autoSwitch;
    LinearLayout llLamp, llPump, llTap;

    SampleModel sampleModel;
    Toolbar toolbar ;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ChipGroup chipGroup;
    public static final String detailInfo = "detail_dev";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_detail);
        setSupportActionBar(toolbar);

        init();
        deleteDialog = new Dialog(this);
        sharedDeviceDetail = getSharedPreferences(detailInfo, Context.MODE_PRIVATE);



        Intent intent = getIntent();
        if(intent.getExtras() != null){
            sampleModel = (SampleModel) intent.getSerializableExtra("data");
//            detailDesc = (SampleModel) intent.getSerializableExtra("data");


            ivImage.setImageResource(sampleModel.getImage());

            tvTitle.setText(sampleModel.getHeader());
            tvDesc.setText(sampleModel.getDesc());
            tvDate.setText(sampleModel.getApplyAt());


        }
        sharedPreferences = getSharedPreferences("dataIot", Context.MODE_PRIVATE);
//        String newValue = sharedPreferences.getString("user_id","");

        setTemp.setText(String.valueOf(sharedPreferences.getFloat("iot_temp",0.0f)));
        setMois.setText(String.valueOf(sharedPreferences.getFloat("iot_mois",0.0f)));
        setHumid.setText(String.valueOf(sharedPreferences.getFloat("iot_hum",0.0f)));




        ivEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor detailEditor = sharedDeviceDetail.edit();
                detailEditor.putString("device_name",sampleModel.getHeader());
                detailEditor.commit();
                startActivity(new Intent(PlantDetail.this, EditDeviceActivity.class));
            }
        });

        mbStatistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlantDetail.this, StatistikActivity.class));
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                autoSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (autoSwitch.isChecked()){
                            tvStatusControl.setText("Auto");
                            llLamp.setVisibility(View.GONE);
                            llPump.setVisibility(View.GONE);
                            llTap.setVisibility(View.GONE);
                        }else{
                            tvStatusControl.setText("Manual");
                            llLamp.setVisibility(View.VISIBLE);
                            llPump.setVisibility(View.VISIBLE);
                            llTap.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        },0);


//        clDeleteBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ShowPopup(sampleModel.getHeader());
//            }
//        });



        camera = (ImageButton) findViewById(R.id.btnCapture);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(intent, CAMERA_ACTION_CODE);
                    }else{
                        Toast.makeText(PlantDetail.this, "There is no app that support this",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(sampleModel.getHeader());
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        SimpleDateFormat stringFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi-VN"));
//
//        try {
//            tvDate.setText(stringFormat.format(Objects.requireNonNull(inputFormat.parse(sampleModel.getApplyAt().toString()))));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

    }

    public void ShowPopup(View v){
        deleteDialog.setContentView(R.layout.popup_delete_activity);


        mbPopupDelete = (MaterialButton) deleteDialog.findViewById(R.id.mbPopupDelete);
        mbPopupCancel = (MaterialButton) deleteDialog.findViewById(R.id.mbPopupCancel);

        mbPopupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
            }
        });
        mbPopupDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDevice(sampleModel.getHeader());
            }
        });
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && data != null){
            Bundle bundle = data.getExtras();
            Bitmap finalPhoto = (Bitmap) bundle.get("data");
            sivCamera.setImageBitmap(finalPhoto);
            

        }
    }

    public void deleteDevice(String device_name){
        String id_delete = checkNamefindID(device_name);
        Log.e("TAG","FIND THIS: "+id_delete);
        UserAPIs.userApi.deleteDevice(id_delete).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                Toast.makeText(PlantDetail.this, "Delete "+device_name+" successfully", Toast.LENGTH_SHORT).show();
                if (!sharedPreferences.getString("id_devicess", "").isEmpty()) {
                    sharedPreferences = getSharedPreferences(MainActivity.fileName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("id_devicess"); //we are removing prodId by key
                    editor.commit();

                }
//                Log.e("TAG","FIND THIS: "+response.body().getContent());

                startActivity(new Intent(PlantDetail.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                Toast.makeText(PlantDetail.this, "Cannot delete "+device_name, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public String checkNamefindID(String device_name){

        sharedPreferences = getSharedPreferences(MainActivity.fileName, Context.MODE_PRIVATE);
        String newValue = sharedPreferences.getString("user_id","");


        //GET all information
        UserAPIs.userApi.callAllInfo(newValue).enqueue(new Callback<GetAllInfo>() {
            @Override
            public void onResponse(Call<GetAllInfo> call, Response<GetAllInfo> response) {
                Log.e("TAG", "response 2406: " + device_name);
                if(response.isSuccessful()) {
                    GetAllInfo deviceResponse = response.body();
//                    Log.e("TAG", "response 70: " + deviceResponse.getData().get(0).getId().toString());
                    Integer sizeData = deviceResponse.getData().size();
                    SharedPreferences.Editor findEditor = sharedPreferences.edit();

                    for (int i = 0; i < sizeData; i++) {
                        String temp = deviceResponse.getData().get(i).getName().toString();
                        String temp_id = deviceResponse.getData().get(i).getId().toString();

                        if (temp.compareTo(device_name)==0) {
                            findEditor.putString("id_devicess",temp_id);
                            findEditor.commit();
                            Log.e("TAG", "response 2206: " + temp);
                            Log.e("TAG", "response 2206: " + temp_id);
                            continue;

                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<GetAllInfo> call, Throwable t) {
                Toast.makeText(PlantDetail.this, "Cannot Find ID", Toast.LENGTH_SHORT).show();
                Log.e("ErrorFindID Message: ",t.getMessage());
            }
        });

        String temp1 = sharedPreferences.getString("id_devicess","");
        Log.e("TAG", "response 99: " + temp1);

        return temp1;
    }

    public void init() {
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvDate = findViewById(R.id.tvDate);
        sivCamera = findViewById(R.id.sivCamera);
        ivImage = findViewById(R.id.ivImage);
        ivEditInfo = findViewById(R.id.ivEditInfo);
        mbStatistik = findViewById(R.id.mbStatistik);
        clDeleteBut = findViewById(R.id.clDeleteBut);
        autoSwitch = findViewById(R.id.autoSwitch);
        tvStatusControl = findViewById(R.id.tvStatusControl);
        llLamp = findViewById(R.id.llTemp2);
        llPump = findViewById(R.id.llTemp3);
        llTap = findViewById(R.id.llTemp4);

        setTemp = findViewById(R.id.setTemp);
        setMois = findViewById(R.id.setMois);
        setHumid = findViewById(R.id.setHumid);
//        setp = findViewById(R.id.setTemp);
        mbPopupCancel = findViewById(R.id.mbPopupCancel);
        toolbar =  findViewById(R.id.toolBar);
        appBarLayout = findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);

        chipGroup = findViewById(R.id.chipGroup);
    }


}
