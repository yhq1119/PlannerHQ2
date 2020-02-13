package HQ.Planner.controller;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

import HQ.Planner.MainActivity;
import HQ.Planner.model.Attendee;
import HQ.Planner.model.Movie;
import HQ.Planner.view.dialogs.New_Event_Dialog;
import HQ.Planner.model.Planner;

public class NewEventSaveListener implements MenuItem.OnMenuItemClickListener {


    New_Event_Dialog dialog;
    View view;
    Context context;
    Planner planner = Planner.getShared();


    EditText inputTitle = dialog.getTitle();
    EditText inputVenue;
    EditText inputLocation;
    Date startDate;
    Date endDate;
    List<Movie> movieList;
    List<Attendee> attendeeList;

    public NewEventSaveListener(New_Event_Dialog dialog1,View view, Context context) {
        this.dialog = dialog1;
        this.view = view;
        this.context = context;
    }

    public NewEventSaveListener(New_Event_Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {





        return true;
    }
}
