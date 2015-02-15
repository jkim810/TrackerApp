/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.actionbarcompat.styled;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.text.format.Time;
import java.sql.Timestamp;
import java.lang.Math;

/**
 * This sample shows you how to use ActionBarCompat with a customized theme. It utilizes a split
 * action bar when running on a device with a narrow display, and show three tabs.
 *
 * This Activity extends from {@link ActionBarActivity}, which provides all of the function
 * necessary to display a compatible Action Bar on devices running Android v2.1+.
 *
 * The interesting bits of this sample start in the theme files
 * ('res/values/styles.xml' and 'res/values-v14</styles.xml').
 *
 * Many of the drawables used in this sample were generated with the
 * 'Android Action Bar Style Generator': http://jgilfelt.github.io/android-actionbarstylegenerator
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    Timestamp ts1, ts2;
    Time tm1, tm2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);

        // Set the Action Bar to use tabs for navigation
        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add three tabs to the Action Bar for display
        ab.addTab(ab.newTab().setText("Sleep Monitor").setTabListener(this));
        ab.addTab(ab.newTab().setText("Food Monitor").setTabListener(this));
        ab.addTab(ab.newTab().setText("Tab 3").setTabListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu from menu resource (res/menu/main)
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a tab is selected.
        if(tab.getText().equals("Sleep Monitor")){
            setContentView(R.layout.sleeptab);
        } else if (tab.getText().equals("Food Monitor")){
            setContentView(R.layout.foodtab);
        } else if (tab.getText().equals("Tab 3")){
            setContentView(R.layout.sample_main);
        } else {
            System.out.println("Non-existent Tab.");
        }
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is unselected.
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is selected again.
    }

    public void toggle(View view) {
        boolean toggled = ((ToggleButton) view).isChecked();
        EditText et = (EditText) this.findViewById(R.id.editText);
        TextView sleep = (TextView) this.findViewById(R.id.sleepTimeText);
        TextView wake = (TextView) this.findViewById(R.id.wakeUpTimeText);
        TextView time_ellapsed = (TextView) this.findViewById(R.id.timeSleptText);

        if (toggled) {
            // Enable vibrate
            //Record Time
            tm1 = new Time();
            tm1.setToNow();
            ts1 = new Timestamp(tm1.toMillis(true));
            //Output
            et.setText(ts1.toString());
            sleep.setText(ts1.toString());
            System.out.println(ts1.toString());
        } else {
            // Disable vibrate
            // Record Time2
            tm2 = new Time();
            tm2.setToNow();
            ts2 = new Timestamp(tm2.toMillis(true));
            //Output
            et.setText(ts2.toString());
            wake.setText(ts2.toString());
            System.out.println(ts2.toString());
            //Calculate Time Difference
            Timestamp time_slept = compareTime(tm1, tm2);

            //Record Time Ellapsed
            String t_ellapsed = time_slept.getHours() + ":" + time_slept.getMinutes() + ":" + time_slept.getSeconds();
            time_ellapsed.setText(t_ellapsed);
        }
    }

    public Timestamp compareTime(Time t1, Time t2){
        long diff = Math.abs(t1.toMillis(true) - t2.toMillis(true));
        Timestamp temp = new Timestamp(diff);
        temp.setHours(temp.getHours() - 19);
        temp.setMonth(temp.getHours() - 12);
        temp.setYear(temp.getYear() - 1969);
        return temp;
    }
}
