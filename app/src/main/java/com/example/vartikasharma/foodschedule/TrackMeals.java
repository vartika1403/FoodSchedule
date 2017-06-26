package com.example.vartikasharma.foodschedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrackMeals extends AppCompatActivity {
    private static final String LOG_TAG = TrackMeals.class.getSimpleName();
    private static final String MY_PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_meals);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String mealName = prefs.getString("mealName", null);
        String mealTime = prefs.getString("mealTime", null);
        Log.i(LOG_TAG, "meal name, " + mealName);
        Log.i(LOG_TAG, "mealTime, " + mealTime);
        if (mealName != null && mealTime != null) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.meal_tracking_layout, null);
            final TextView mealNameText = (TextView) v.findViewById(R.id.meal_name_track_text);
            mealNameText.setText(mealName);
            final TextView mealTimeText = (TextView) v.findViewById(R.id.meal_time_track_text);
            mealTimeText.setText(mealTime);
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.activity_track_meals);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

    }
}
