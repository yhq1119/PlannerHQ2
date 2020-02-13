package HQ.Planner.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

import HQ.Planner.R;
import HQ.Planner.model.Attendee;
import HQ.Planner.model.Event;
import HQ.Planner.model.Movie;

public class Edit_Event_Dialog extends AppCompatDialogFragment {

    EditText title;
    EditText venue;
    EditText location;



    ActionMenuItemView ok;
    ActionMenuItemView cancel;
    Toolbar toolbar;
    Event event;



    String event_title;
    String event_venue;
    String event_location;
    Date event_start_date;
    Date event_end_date;
    List<Movie> event_movies;
    List<Attendee> event_attendees;

    public void setEvent(Event event){
        this.event = event;
    }


    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_event, null);
        toolbar = view.findViewById(R.id.dialog_add_event_toolbar);
        toolbar.setTitle("Edit Event");
        toolbar.inflateMenu(R.menu.dialog_menu_new_event);


        ok = view.findViewById(R.id.dialog_save_event);
        cancel = view.findViewById(R.id.dialog_cancel_save);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        builder.setView(view);
        title = view.findViewById(R.id.title_label);
        venue = view.findViewById(R.id.venue_label);
        location = view.findViewById(R.id.location_label);

        title.setText(event.getTitle());
        venue.setText(event.getVenue());
        location.setText(event.getLocation());

        return builder.create();
    }

    public interface New_Event_Dialog_Listener{
        void applyTexts(String title, String venue, String location);
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
