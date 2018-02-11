package com.barwen.daik.flyttguiden.screens.flytt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by daik on 2018-02-08.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationHelper notificationHelper;

    private static final String TAG = "AlarmReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        notificationHelper = new NotificationHelper(context);

        String title = intent.getStringExtra("title");
        String longText = intent.getStringExtra("longText");
        int id = intent.getIntExtra("id", -1);


        notificationHelper.notify(id,title, longText);
        Log.d(TAG, "onReceive: ALARM MOTTAGET " + id);

    }
}
