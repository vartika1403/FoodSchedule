package com.example.vartikasharma.foodschedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MY_PREFS_NAME = "MyPrefs";
    private String[] mealList = {"Breakfast", "MorningSnack", "Lunch", "Evening Snack", "Dinner"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        createALayoutForMeals();
    }

    private void createALayoutForMeals() {
        for (final String item : mealList) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.meal_item_layout, null);
            final TextView mealText = (TextView) v.findViewById(R.id.meal_name_text);
            mealText.setText(item);
            final TextView mealTime = (TextView) v.findViewById(R.id.meal_time_text);
            final Calendar calendar = new GregorianCalendar();
            mealTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar currentTime = Calendar.getInstance();
                    int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                    final int date = currentTime.get(Calendar.DATE);
                    int minute = currentTime.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog;
                    timePickerDialog = new TimePickerDialog(MainActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour,
                                              int selectedMinute) {
                            String time = selectedHour + ":" + selectedMinute;
                            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                            try {
                                Date dateObj = dateFormat.parse(time);
                                String newTime = _12HourSDF.format(dateObj);
                                mealTime.setText(newTime);
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                calendar.set(Calendar.MINUTE, selectedMinute);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, hour, minute, false);
                    timePickerDialog.setTitle("Select Time");
                    timePickerDialog.show();
                }
            });
            CheckBox checkBoxMeal = ( CheckBox )v.findViewById( R.id.check_box);
            checkBoxMeal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked)
                    {
                        scheduleNotification(calendar, item, mealTime.getText().toString());

                    }

                }
            });
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.main_layout);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }


    private void scheduleNotification(Calendar calendar, String itemText, String newTime) {
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("MealText", itemText);
        notificationIntent.putExtra("MealTime", newTime);

        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                24 * 60 * 60 * 1000, pendingIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String mealName = intent.getStringExtra("mealName");
        String mealTime = intent.getStringExtra("mealTime");
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        if (mealName != null && mealTime != null) {
            editor.putString(mealName, mealTime);
            editor.apply();
        }
    }

    @OnClick(R.id.track_events)
    public void trackEvents() {
        Intent intent = new Intent(this, TrackMeals.class);
        startActivity(intent);
    }
}
