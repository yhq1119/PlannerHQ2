package HQ.Planner.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import HQ.Planner.MainActivity;
import HQ.Planner.R;
import HQ.Planner.adapter.DateGridViewAdaptor;
import HQ.Planner.model.Event;
import HQ.Planner.model.Planner;
import HQ.Planner.utilities.MDate;
import HQ.Planner.utilities.MToast;

public class CalendarFragment extends Fragment {

    Planner planner = Planner.getShared();
    Context context;
    View view;
    TextView dateInfo;
    Toolbar toolbar;
//    ActionMenuItemView new_event_menu_item;
//    ActionMenuItemView setting_menu_item;
    GridView gridView;
    ListView listView;
    Button last_month;
    Button next_month;
    DateGridViewAdaptor date_adaptor;
    Calendar currentDateTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initUI();

        return view;
    }

    public void clearListView(){
        listView.setAdapter(null);
    }

    public void refreshView() {
//        if (view == null) {
//            return;
//        }


        List<Event> eventsInSystem = planner.getAllEvents();
        List<List<Event>> list_list_events =
                MDate.generateEventsInCalendar(eventsInSystem, currentDateTime);

        date_adaptor = new DateGridViewAdaptor(
                context, listView,
                list_list_events,
                currentDateTime,this,((MainActivity)getContext()));

        gridView.setAdapter(date_adaptor);
        dateInfo.setText(MDate.getMonthYearString(currentDateTime));

    }


    private void initUI() {

        context = getContext();
        toolbar = getActivity().findViewById(R.id.activity_home_toolbar);
        toolbar.getMenu().clear();
        toolbar.setTitle("Calendar");
        toolbar.inflateMenu(R.menu.fragment_calendar_toolbar_menu);
        toolbar.setOnMenuItemClickListener(barListener);

        last_month = view.findViewById(R.id.calendar_last_month_btn);
        next_month = view.findViewById(R.id.calendar_next_month_btn);
        dateInfo = view.findViewById(R.id.calendar_text_view_date_info);
        last_month.setOnClickListener(new monthButtonListener(-1));
        next_month.setOnClickListener(new monthButtonListener(1));
        dateInfo.setOnClickListener(new setToCurrentDate());
        currentDateTime = Calendar.getInstance();
        gridView = view.findViewById(R.id.calendar_date_grid_view);
        listView = view.findViewById(R.id.calendar_list_view);
        refreshView();
        listView.setOnItemClickListener(listListener);
    }

    public void addEvent(Event newEvent) {
        planner.add_event(newEvent);
        refreshView();
    }

    public void deleteEvent(Event event) {
        planner.remove_event(event);
        refreshView();
    }

    private class monthButtonListener implements View.OnClickListener {
        int factor;
        public monthButtonListener(int factor) {
            this.factor = factor;
        }
        @Override
        public void onClick(View v) {
            currentDateTime.add(Calendar.MONTH, factor);
            refreshView();
        }
    }
     private class setToCurrentDate implements View.OnClickListener {
        int factor;
        public setToCurrentDate() {

        }
        @Override
        public void onClick(View v) {
            currentDateTime=Calendar.getInstance();
            refreshView();
        }
    }


    private AdapterView.OnItemClickListener listListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Event event = (Event) listView.getAdapter().getItem(position);
                    ((MainActivity) Objects.requireNonNull(getActivity()))
                            .openEventDetailDialog(getSelfFragment(), event);
                    refreshView();
                }
            };


    private Fragment getSelfFragment(){
        return this;
    }




    private Toolbar.OnMenuItemClickListener barListener =
            new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            MainActivity activity = ((MainActivity) getActivity());
            switch (item.getItemId()) {
                case R.id.menu_item_new_event:
                    say("Add New Event");
                    activity.openNewEventDialog(getSelfFragment());
                    break;
                case R.id.menu_item_setting_time:
                    say("Remind's Settings");
                    activity.openSettingTimeDialog();
                    break;

                default:
                    say(String.valueOf(item.getItemId()));
                    break;
            }
            return true;
        }
    };

    private void say(String msg) {
        MToast.say(getContext(), msg);
    }
}
