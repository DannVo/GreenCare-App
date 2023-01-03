package com.example.iotapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatistikActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView taskProgressBar,tvDayMonth, tvYear;
    ImageButton btnShowDate;
    SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistik);

        init();


        btnShowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
            }
        });



    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        init();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat year_date = new SimpleDateFormat("yyyy");
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        SimpleDateFormat day_date = new SimpleDateFormat("dd");
        String year_name = year_date.format(calendar.getTime());
        String month_name = month_date.format(calendar.getTime());
        String day_name = day_date.format(calendar.getTime());
//        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tvDayMonth.setText(month_name+" "+day_name);
        tvYear.setText(year_name);
    }

    public void init(){
        taskProgressBar = findViewById(R.id.tvProgressBar);
        btnShowDate = findViewById(R.id.btnShowDate);
        tvDayMonth = findViewById(R.id.tvDayMonth);
        tvYear = findViewById(R.id.tvYear);
        swipeLayout = findViewById(R.id.swipeLayout);

    }
}