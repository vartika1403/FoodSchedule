package com.example.vartikasharma.foodschedule;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import static java.security.AccessController.getContext;

/**
 * Created by vartikasharma on 26/06/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context,
                MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews notificationView = new RemoteViews(
                context.getPackageName(),
                R.layout.custom_notification
        );

        notificationView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        notificationView.setTextViewText(R.id.title, "Custom notification");
        notificationView.setTextViewText(R.id.text, "This is a custom layout");

      //  Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Evening snack")
                .setContentText("Custom Notification")
                .setAutoCancel(true)
               // .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent);
               // .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(0, mNotifyBuilder.build());


    }
}
