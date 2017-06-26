package com.example.vartikasharma.foodschedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String[] mealList = {"Breakfast", "MorningSnack", "Lunch", "Evening Snack", "Dinner"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        createALayoutForMeals();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createALayoutForMeals() {
        for (String item : mealList) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.meal_item_layout, null);
            final TextView mealText = (TextView) v.findViewById(R.id.meal_name_text);
            mealText.setText(item);
            final TextView mealTime = (TextView) v.findViewById(R.id.meal_time_text);
            mealTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar currentTime = Calendar.getInstance();
                    int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = currentTime.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog;
                    timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String time = selectedHour + ":" + selectedMinute;
                            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                            try {
                                Date dateObj = dateFormat.parse(time);
                                String newTime = _12HourSDF.format(dateObj);
                                Log.i(LOG_TAG, "newTime, " + newTime);
                                mealTime.setText(newTime);
                                Log.i(LOG_TAG, "selected hour, " + selectedHour);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, hour, minute, false);
                    timePickerDialog.setTitle("Select Time");
                    timePickerDialog.show();
                }
            });
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.main_layout);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
}
