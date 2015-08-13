package com.example.alekszilagyi.moraviandailytexts.utilities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.alekszilagyi.moraviandailytexts.MainActivity;
import com.example.alekszilagyi.moraviandailytexts.R;

import java.util.Calendar;

/**
 * Created by alekszilagyi on 3/21/15.
 */
public class NotificationUtility
{
    Context context;
    NotificationManager notificationManager;
    AlarmManager alarmManager;

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public NotificationUtility(Context context)
    {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        initializeNotifications();
    }

    public void initializeNotifications()
    {
        System.out.println("Notifications Initializing");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean useNotifications = sharedPreferences.getBoolean(context.getString(R.string.notify_checkbox_key), false);
        if (useNotifications)
        {
            System.out.println("Notifications Used");
            long notifyTime = sharedPreferences.getLong(context.getString(R.string.notify_time_key), 0);

            int hour = TimeUtility.getLongTimeHour(notifyTime);
            int minute = TimeUtility.getLongTimeMinute(notifyTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            Intent intent = new Intent(context, NotificationReceiver.class);
            Notification notification = getNotification("Test Title", "Test Text", notifyTime);
            intent.putExtra(NOTIFICATION_ID, 1);
            intent.putExtra(NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public Notification getNotification(String title, String text, long when)
    {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.drawable.abc_ic_clear_mtrl_alpha);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        //builder.setWhen(when);

        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        return builder.build();
    }
}
