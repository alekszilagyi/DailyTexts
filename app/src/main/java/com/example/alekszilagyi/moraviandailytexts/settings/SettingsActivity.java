package com.example.alekszilagyi.moraviandailytexts.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

import com.example.alekszilagyi.moraviandailytexts.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alekszilagyi on 3/14/15.
 */
public class SettingsActivity extends ActionBarActivity
{
    private static List<String> fragments = new ArrayList<String>();
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedStateInstance)
    {
        super.onCreate(savedStateInstance);
        setContentView(R.layout.settings_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getFragmentManager().beginTransaction().add(android.R.id.content, new NotificationSettingsFragment()).commit();
    }

    /**
     * This fragment shows the preferences for notification settings.
     */
    public static class NotificationSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.notification_preference_fragment);
        }
    }
}
