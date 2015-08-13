package com.example.alekszilagyi.moraviandailytexts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alekszilagyi.moraviandailytexts.settings.SettingsActivity;
import com.example.alekszilagyi.moraviandailytexts.utilities.DailyTextUtility;
import com.example.alekszilagyi.moraviandailytexts.utilities.NotificationUtility;
import com.example.alekszilagyi.moraviandailytexts.utilities.TimeUtility;


public class MainActivity extends ActionBarActivity {

    private DailyTextUtility myDailyTextUtility;
    private SharedPreferences sharedPreferences;
    public NotificationUtility notificationUtility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationUtility = new NotificationUtility(this);
        notificationUtility.initializeNotifications();

        setContent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings_intent = new Intent(this, SettingsActivity.class);
            startActivity(settings_intent);
        }
        else if (id == R.id.action_about) {
            Intent about_intent = new Intent(this, AboutActivity.class);
            startActivity(about_intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setContent()
    {
        System.out.println("SETTING THE CONTENT!!");
        myDailyTextUtility = new DailyTextUtility();
        String dailyText = myDailyTextUtility.getTodaysText();

        TextView textView = (TextView)(findViewById(R.id.daily_text_textview));
        textView.setText(dailyText);

        TextView settingsView = (TextView)(findViewById(R.id.settingsTestView));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefListener);
        boolean usePreference = sharedPreferences.getBoolean(getString(R.string.notify_checkbox_key), false);
        long timePreference = sharedPreferences.getLong(getString(R.string.notify_time_key), -1);
        //String timePreference = "unknown";
        settingsView.setText(usePreference + " -- " + TimeUtility.getLongTimeHour(timePreference) + ":" + TimeUtility.getLongTimeMinute(timePreference));
    }

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener()
    {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.compareTo(getString(R.string.notify_checkbox_key)) == 0) {
                notificationUtility.initializeNotifications();
            }
            if (key.compareTo(getString(R.string.notify_time_key)) == 0) {
                notificationUtility.initializeNotifications();
            }
        }
    };
}
