package com.barwen.daik.flyttguiden.screens.flytt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;

import com.barwen.daik.flyttguiden.R;


class NotificationHelper extends ContextWrapper {

    private NotificationManager mNotificationManager;

    public static final String CHANNEL_ONE_ID = "com.jessicathornsby.myapplication.ONE";
    public static final String CHANNEL_ONE_NAME = "Channel One";

    public NotificationHelper(Context context) {
        super(context.getApplicationContext());
        createChannels();
    }

    private void createChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(notificationChannel);
        }
    }

    public void notify(int id, String title, String longText) {
        notify(id, getNotification(title, longText));
    }

    private Notification.Builder getNotification(String title, String longText) {
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return new Notification.Builder(this, CHANNEL_ONE_ID)
                    .setSmallIcon(R.drawable.ic_stat_notificon)
                    .setContentTitle(title)
                    .setStyle(new Notification.BigTextStyle().bigText(longText))
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        } else {
            return new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat_notificon)
                    .setContentTitle(title)
                    .setStyle(new Notification.BigTextStyle().bigText(longText))
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
    }

    private void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

    private NotificationManager getManager() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }
}
