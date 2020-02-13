package HQ.Planner.controller;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import HQ.Planner.MainActivity;

public class BottomNavListener implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    MainActivity activity;

    public BottomNavListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        activity.changeTo(menuItem);
        return true;

    }
}
