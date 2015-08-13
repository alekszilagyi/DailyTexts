package com.example.alekszilagyi.moraviandailytexts.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by alekszilagyi on 8/11/15.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notManager = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));

        Notification notification = intent.getParcelableExtra(NotificationUtility.NOTIFICATION);
        int id = intent.getIntExtra(NotificationUtility.NOTIFICATION_ID, 0);
        System.out.println("ON RECEIVE " + id);
        if (notification != null) {
            notManager.notify(id, notification);
        }
        else
        {
            System.out.println("Notification is null");
        }
    }
}
