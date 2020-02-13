package HQ.Planner.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import HQ.Planner.R;
import HQ.Planner.model.Event;
import HQ.Planner.view.fragments.CalendarFragment;
import HQ.Planner.view.fragments.HomeFragment;

public class Event_Detail_Dialog extends AppCompatDialogFragment {

    TextView title;
    TextView venue;
    TextView location;
    TextView startDate;
    TextView endDate;


    ActionMenuItemView remove;
    ActionMenuItemView edit;
    ActionMenuItemView back;
    Toolbar toolbar;
    Event event;
    Fragment fragment;


    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_event_detail, null);
        toolbar = view.findViewById(R.id.dialog_add_event_toolbar);
        toolbar.setTitle("Event Detail");
        toolbar.inflateMenu(R.menu.dialog_menu_event_detail);


        remove = view.findViewById(R.id.dialog_remove_event);
        edit = view.findViewById(R.id.dialog_edit_event);
        back = view.findViewById(R.id.dialog_back);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Confirm_Dialog confirm_dialog = new Confirm_Dialog();
                confirm_dialog.setMsg("Delete Event", "You sure to delete :\n" + event.getTitle() + " ?");
                confirm_dialog.setDialogFragment(getSelfFragment());
                confirm_dialog.show(getActivity().getSupportFragmentManager(), "");
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Edit_Event_Dialog edit_event_dialog = new Edit_Event_Dialog();
                edit_event_dialog.setEvent(event);
                edit_event_dialog.show(getActivity().getSupportFragmentManager(), "");
            }
        });


        builder.setView(view);
        title = view.findViewById(R.id.dialog_detail_title);
        venue = view.findViewById(R.id.dialog_detail_venue);
        location = view.findViewById(R.id.dialog_detail_location);
        startDate = view.findViewById(R.id.dialog_detail_start_date);
        endDate = view.findViewById(R.id.dialog_detail_end_date);

        if (event != null) {
            title.setText(event.getTitle());
            venue.setText(event.getVenue());
            location.setText(event.getLocation());
            startDate.setText(event.getStartDateString());
            endDate.setText(event.getEndDateString());
        }
        return builder.create();
    }

    private DialogFragment getSelfFragment() {
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public interface New_Event_Dialog_Listener {
        void applyTexts(String title, String venue, String location);
    }

    public void setRemove() {
        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).deleteEvent(event);
        } else if (fragment instanceof CalendarFragment) {
            ((CalendarFragment) fragment).clearListView();
            ((CalendarFragment) fragment).deleteEvent(event);
        }
        dismiss();
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getVenue() {
        return venue;
    }

    public TextView getLocation() {
        return location;
    }
}
