package com.example.iotapplication.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.iotapplication.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class SettingsFragment extends Fragment {

    TextView tvLamp, tvTurnCel, tvTurnPump, tvTurnTap;
    Switch switchLamp, switchCelcius, switchPump, switchTap;
    View view;
    Context context;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        init(view);
//        if (switchLamp.isChecked()){
//            tvLamp.setText("ON");
//            tvLamp.setTextColor(getResources().getColor(R.color.turn_on));
//        }else{
//            tvLamp.setText("OFF");
//            tvLamp.setTextColor(getResources().getColor(R.color.turn_off));
//        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("save", Context.MODE_PRIVATE);

        //Lamp Setting
        switchLamp.setChecked(sharedPreferences.getBoolean("valueLamp",false));
        switchLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchLamp.isChecked()){
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valueLamp", true);
                    editor.apply();
                    switchLamp.setChecked(true);
                    tvLamp.setText("ON");
                    tvLamp.setTextColor(getResources().getColor(R.color.turn_on));
                }else{
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valueLamp", false);
                    editor.apply();
                    switchLamp.setChecked(false);
                    tvLamp.setText("OFF");
                    tvLamp.setTextColor(getResources().getColor(R.color.turn_off));
                }
            }
        });

        //Tempurature Setting
        switchCelcius.setChecked(sharedPreferences.getBoolean("valueCel",false));
        switchCelcius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCelcius.isChecked()){
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valueCel", true);
                    editor.apply();
                    switchCelcius.setChecked(true);
                    tvTurnCel.setText("ON");
                    tvTurnCel.setTextColor(getResources().getColor(R.color.turn_on));
                }else{
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valueCel", false);
                    editor.apply();
                    switchCelcius.setChecked(false);
                    tvTurnCel.setText("OFF");
                    tvTurnCel.setTextColor(getResources().getColor(R.color.turn_off));
                }
            }
        });

        //Water Pump Setting
        switchPump.setChecked(sharedPreferences.getBoolean("valuePump",false));
        switchPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchPump.isChecked()){
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valuePump", true);
                    editor.apply();
                    switchPump.setChecked(true);
                    tvTurnPump.setText("ON");
                    tvTurnPump.setTextColor(getResources().getColor(R.color.turn_on));
                }else{
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valuePump", false);
                    editor.apply();
                    switchPump.setChecked(false);
                    tvTurnPump.setText("OFF");
                    tvTurnPump.setTextColor(getResources().getColor(R.color.turn_off));
                }
            }
        });


        //Water Tap Setting
        switchTap.setChecked(sharedPreferences.getBoolean("valueTap",false));
        switchTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchTap.isChecked()){
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valueTap", true);
                    editor.apply();
                    switchTap.setChecked(true);
                    tvTurnTap.setText("ON");
                    tvTurnTap.setTextColor(getResources().getColor(R.color.turn_on));

                }else{
                    SharedPreferences.Editor editor = context.getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("valueTap", false);
                    editor.apply();
                    switchTap.setChecked(false);
                    tvTurnTap.setText("OFF");
                    tvTurnTap.setTextColor(getResources().getColor(R.color.turn_off));
                }
            }
        });


    }

    public void init(View view){
        switchLamp = view.findViewById(R.id.lampSwitch);
        switchCelcius = view.findViewById(R.id.celSwitch);
        switchPump = view.findViewById(R.id.pumpSwitch);
        switchTap = view.findViewById(R.id.tapSwitch);
        tvLamp = view.findViewById(R.id.tvTurnLamp);
        tvTurnCel = view.findViewById(R.id.tvTurnCel);
        tvTurnPump = view.findViewById(R.id.tvTurnPump);
        tvTurnTap = view.findViewById(R.id.tvTurnTap);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    init(view);
                    checkCondition();

                }
            },0);
        }catch (Exception e) {
            Log.e("Exception", String.valueOf(e));
            e.printStackTrace();
        }

        context = container.getContext();
        return view;
    }

    public void checkCondition(){
        if (!switchLamp.isChecked()) {
            tvLamp.setText("OFF");
            tvLamp.setTextColor(getResources().getColor(R.color.turn_off));
        }

        if (!switchCelcius.isChecked()) {
            tvTurnCel.setText("OFF");
            tvTurnCel.setTextColor(getResources().getColor(R.color.turn_off));
        }
        if (!switchPump.isChecked()) {
            tvTurnPump.setText("OFF");
            tvTurnPump.setTextColor(getResources().getColor(R.color.turn_off));
        }
        if (!switchTap.isChecked()) {
            tvTurnTap.setText("OFF");
            tvTurnTap.setTextColor(getResources().getColor(R.color.turn_off));
        }
    }

}