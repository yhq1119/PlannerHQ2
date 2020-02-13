package HQ.Planner.controller;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import HQ.Planner.MainActivity;
import HQ.Planner.model.Event;

public class CalendarListViewListener implements AdapterView.OnItemClickListener {

    MainActivity activity;
    ListView listView;

    public CalendarListViewListener(MainActivity activity, ListView listView) {
        this.activity = activity;
        this.listView = listView;
    }

    @Override
    public void onItemClick(
            AdapterView<?> parent,
            View view,
            int position,
            long id) {

        Event event = (Event) listView.getAdapter().getItem(position);

        if (event != null) {
         //   activity.openEventDetailDialog(event);
        }
    }
}
