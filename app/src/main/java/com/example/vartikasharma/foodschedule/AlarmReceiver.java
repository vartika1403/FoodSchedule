package com.example.vartikasharma.foodschedule;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String mealText = intent.getStringExtra("MealText");
        Log.i(LOG_TAG, "mealText, " + mealText);

        String mealTime = intent.getStringExtra("MealTime");
        Log.i(LOG_TAG, "mealTime, " + mealTime);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context,
                MainActivity.class);
        notificationIntent.putExtra("mealName", mealText);
        notificationIntent.putExtra("mealTime", mealTime);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Evening snack")
                .setContentText(mealText)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .addAction(0, "Track" + mealText, pendingIntent)
                .addAction(0, "Edit Settings", pendingIntent);
        notificationManager.notify(0, mNotifyBuilder.build());
    }
}
