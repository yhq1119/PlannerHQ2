package HQ.Planner.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import HQ.Planner.MainActivity;
import HQ.Planner.model.Event;
import HQ.Planner.utilities.MDate;
import HQ.Planner.utilities.MToast;
import HQ.Planner.view.fragments.CalendarFragment;

public class DateGridViewAdaptor extends BaseAdapter {

    private Context context;
    private ListView events_of_that_day;
    private int days_in_this_month;
    private int days_in_last_month;
    private int start_day_index;
    private int end_day_index;
    private int this_month_days;
    private int last_month_days;
    private List<List<Event>> eventsInThisView;
    private Button[] buttons;
    private final int length = 42;
    private int[] daysNums;
    private CalendarFragment fragment;
    private MainActivity activity;
    private List<Date> dateList;

    public DateGridViewAdaptor(Context context,
                               ListView events_of_that_day,
                               List<List<Event>> eventsInThisView,
                               Calendar calendar, CalendarFragment calendarFragment, MainActivity activity) {
        this.context = context;
        this.events_of_that_day = events_of_that_day;
        this.eventsInThisView = eventsInThisView;
        this.start_day_index = MDate.getDayOfWeekOfMonthFirstDay(calendar);
        this.days_in_this_month = MDate.getCurrentMonthMaximumDay(calendar);
        this.days_in_last_month = MDate.getLastMonthMaximumDay(calendar);
        this.fragment = calendarFragment;
        this.activity = activity;
        this.dateList = MDate.getDateList(calendar);
        initNums();
        initButtons();
    }


    private void initNums() {
        this.end_day_index = this.days_in_this_month + this.start_day_index - 1;
        this.this_month_days = this.start_day_index - 1;/////
        this.last_month_days = this.days_in_last_month - this.this_month_days;

        daysNums = new int[length];
        for (int i = 0; i < length; i++) {
            if (i < start_day_index) {
                daysNums[i] = last_month_days + i;
            } else if (i >= start_day_index && i <= end_day_index) {
                daysNums[i] = i - this_month_days;
            } else if (i > end_day_index) {
                daysNums[i] = i - end_day_index;
            }
        }
    }

    private void initButtons() {
        buttons = new Button[length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(context);
            buttons[i].setText(String.valueOf(daysNums[i]));
            buttons[i].setBackgroundColor(Color.DKGRAY);
            if (eventsInThisView.get(i) != null) {
                buttons[i].setTextColor(Color.GREEN);
            }
            if (i < start_day_index || i > end_day_index) {

           //     buttons[i].setTextColor(Color.WHITE);
                buttons[i].setBackgroundColor(Color.rgb(90, 90, 90));
                buttons[i].setEnabled(false);
                if (eventsInThisView.get(i) == null) {
                    buttons[i].setTextColor(Color.DKGRAY);
                } else {
                    buttons[i].setTextColor(Color.rgb(180, 180, 180));
                }
            }
        }
    }

    @Override
    public int getCount() {
        return length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = buttons[position];
            btn.setLayoutParams(new GridView.LayoutParams(150, 120));
            btn.setPadding(1, 1, 1, 0);
        } else {
            btn = (Button) convertView;
        }
        // filenames is an array of strings
        btn.setId(position);
        btn.setOnClickListener(new dateBtnOnClickListener(position));
        btn.setOnLongClickListener(new dateBtnLongPressListener(position));
        return btn;
    }


    private class dateBtnLongPressListener implements View.OnLongClickListener {

        int position;

        public dateBtnLongPressListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            activity.openNewEventDialog(fragment, dateList.get(position));
            return true;
        }
    }

    private class dateBtnOnClickListener implements View.OnClickListener {

        private dateBtnOnClickListener(int position) {
            this.position = position;
        }

        int position;

        @Override
        public void onClick(View v) {
            List<Event> events = eventsInThisView.get(position);
            String title = "Event not found";
            List<Event> empty = new ArrayList<>();
            EventListViewAdapter adapter;
            if (events != null) {
                title = "There are " + events.size() + " events in this day";
                adapter = new EventListViewAdapter(context, events);
            } else {
                adapter = new EventListViewAdapter(context, empty);
            }
            events_of_that_day.setAdapter(adapter);
            say(title);
        }
    }

    private void say(String msg) {
        MToast.say(context, msg);
    }
}
