package com.example.vartikasharma.foodschedule;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import static java.security.AccessController.getContext;

/**
 * Created by vartikasharma on 26/06/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = AlarmReceiver.class.getSimpleName();
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

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

        RemoteViews notificationView = new RemoteViews(
                context.getPackageName(),
                R.layout.custom_notification
        );

        notificationView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        notificationView.setTextViewText(R.id.title, "Custom notification");
        notificationView.setTextViewText(R.id.text, "This is a custom layout");


        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Evening snack")
                .setContentText(mealText)
                .setAutoCancel(true)
               // .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher,"Track"+ mealText, pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Edit Settings", pendingIntent);
        notificationManager.notify(0, mNotifyBuilder.build());


    }
}
