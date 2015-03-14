package com.example.alekszilagyi.moraviandailytexts;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Utilities.DailyTextUtility;

/**
 * Created by alekszilagyi on 3/11/15.
 */
public class DailyTextFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_text_fragment, container, false);
    }
}
