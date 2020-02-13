package HQ.Planner.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import HQ.Planner.R;
import HQ.Planner.controller.DateTimePickListener;
import HQ.Planner.model.Attendee;
import HQ.Planner.model.Event;
import HQ.Planner.model.Movie;
import HQ.Planner.model.Planner;
import HQ.Planner.utilities.MDate;
import HQ.Planner.utilities.MToast;
import HQ.Planner.view.fragments.CalendarFragment;
import HQ.Planner.view.fragments.HomeFragment;

public class New_Event_Dialog extends AppCompatDialogFragment {

    View view;
    EditText title;
    EditText venue;
    EditText location;
    Planner planner = Planner.getShared();
    Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setStartDate(Date startDate) {

        event_start_date = startDate;

    }

    AlertDialog.Builder builder;
    ActionMenuItemView ok;
    ActionMenuItemView cancel;
    ActionMenuItemView back;
    Toolbar toolbar;
    Button set_start_date;
    Button set_end_date;
    Button set_event_movie;
    Button set_event_attendee;
    private DateTimePickListener startDateTimePicker;
    private DateTimePickListener endDateTimePicker;

    String event_title;
    String event_venue;
    String event_location;
    Date event_start_date;
    Date event_end_date;
    List<Movie> event_movies;
    List<Attendee> event_attendees;

    boolean add_Event_flag;

    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {

        initUI();
        initData();
        initListener();
        builder.setView(view);
        return builder.create();
    }

    private void initData() {
//        event_title = null;
//        event_venue = null;
//        event_location = null;
//        event_start_date = null;
//        event_end_date = null;
        event_movies = new ArrayList<>();
        event_attendees = new ArrayList<>();
    }

    private void initUI() {

        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_edit_event, null);

        toolbar = view.findViewById(R.id.dialog_add_event_toolbar);
        toolbar.setTitle("New Event");
        toolbar.inflateMenu(R.menu.dialog_menu_new_event);

        title = view.findViewById(R.id.title_label);
        venue = view.findViewById(R.id.venue_label);
        location = view.findViewById(R.id.location_label);

        ok = view.findViewById(R.id.dialog_save_event);
        cancel = view.findViewById(R.id.dialog_cancel_save);
        back = view.findViewById(R.id.dialog_back);
        set_start_date = view.findViewById(R.id.start_date_btn);
        set_end_date = view.findViewById(R.id.end_date_btn);
        set_event_movie = view.findViewById(R.id.edit_movie_btn);
        set_event_attendee = view.findViewById(R.id.edit_attendee_btn);

        startDateTimePicker = new DateTimePickListener(
                getContext(), set_start_date, event_start_date, "START DATE:");
        endDateTimePicker = new DateTimePickListener(
                getContext(), set_end_date, event_end_date, "END DATE:");

        set_start_date.setOnClickListener(startDateTimePicker);
        if (event_start_date != null) {
            set_start_date.setText("START DATE:" + MDate.toDate(event_start_date));
        }
        set_end_date.setOnClickListener(endDateTimePicker);
    }

    private void initListener() {

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellWhereWrong();
                addNewEvent();
                if (add_Event_flag) {

                    say("Event Added");
                    add_Event_flag = false;

                    dismiss();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                say("Back");
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
                say("Input Cleared");
            }
        });

        set_event_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie_Select_Dialog movie_select_dialog = new Movie_Select_Dialog();
                movie_select_dialog.show(getActivity().getSupportFragmentManager(), "");
            }
        });

        set_event_attendee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                say("Attendees: "+planner.getTotalContact().size());
             Attendee_Select_Dialog attendee_select_dialog = new Attendee_Select_Dialog();
             attendee_select_dialog.show(getActivity().getSupportFragmentManager(),"");
            }
        });
    }

    private void clearAll() {

        set_end_date.setText("SET END DATE");
        set_start_date.setText("SET START DATE");
        title.getText().clear();
        venue.getText().clear();
        location.getText().clear();

        initData();

    }

    private void tellWhereWrong() {

        event_title = title.getText().toString();
        event_venue = venue.getText().toString();
        event_location = location.getText().toString();
        if (event_start_date == null) {
            event_start_date = startDateTimePicker.getDate();
        }
        event_end_date = endDateTimePicker.getDate();

        /**
         * TO DO: how to add attendees and movies?
         */

        add_Event_flag = false;

        if (event_title == null || event_title.length() < 1) {
            say("Please input event title");
        } else if (event_venue == null || event_venue.length() < 1) {
            say("Please input event venue");
        } else if (event_location == null || event_location.length() < 1) {
            say("Please input event location");
        } else if (event_start_date == null || event_end_date == null) {
            say("Please input all dates");
        } else if (MDate.DateA_before_DateB(event_start_date, new Date())
                || MDate.DateA_before_DateB(event_end_date, new Date())) {
            say("Dates cannot be before today");
        } else if (event_end_date.before(event_start_date)) {
            say("End date must after start date");
        } else {
            add_Event_flag = true;
        }
    }

    private void addNewEvent() {
        if (add_Event_flag) {
            Event newEvent = new Event(
                    event_title,
                    event_venue,
                    event_location,
                    event_start_date,
                    event_end_date);
            if (planner.eventHasExisted(newEvent)) {
                add_Event_flag = false;
            } else {
                if (fragment instanceof HomeFragment) {
                    ((HomeFragment) fragment).addEvent(newEvent);
                }
                if (fragment instanceof CalendarFragment) {
                    ((CalendarFragment) fragment).clearListView();
                    ((CalendarFragment) fragment).addEvent(newEvent);
                }
            }
        }
    }

    private void say(String msg) {
        MToast.say(getContext(), msg);
    }

    public EditText getTitle() {
        return title;
    }

    public EditText getVenue() {
        return venue;
    }

    public EditText getLocation() {
        return location;
    }
}
