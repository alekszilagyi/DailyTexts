package Utilities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.alekszilagyi.moraviandailytexts.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by alekszilagyi on 3/21/15.
 */
public class NotificationUtility extends BroadcastReceiver
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean useNotifications = sharedPreferences.getBoolean(context.getString(R.string.notify_checkbox_key), false);
        if (useNotifications)
        {
            long notifyTime = sharedPreferences.getLong(context.getString(R.string.notify_time_key), 0);

            int hour = TimeUtility.getLongTimeHour(notifyTime);
            int minute = TimeUtility.getLongTimeMinute(notifyTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            Intent intent = new Intent(context, NotificationUtility.class);
            Notification notification = getNotification();
            intent.putExtra(NOTIFICATION_ID, 1);
            intent.putExtra(NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, null);
        }
    }

    private Notification getNotification()
    {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Test Title");
        builder.setContentText("Test Content");
        builder.setSmallIcon(R.drawable.abc_ic_clear_mtrl_alpha);
        return builder.build();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notManager = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }
}
