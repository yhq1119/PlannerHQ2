package HQ.Planner.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import HQ.Planner.R;
import HQ.Planner.adapter.AttendeeListViewAdapter;
import HQ.Planner.model.Attendee;
import HQ.Planner.model.Planner;

public class Attendee_Select_Dialog extends AppCompatDialogFragment {


    Planner planner = Planner.getShared();
    ActionMenuItemView add;
    ActionMenuItemView back;
    Toolbar toolbar;
    ListView attendListView;
    AttendeeListViewAdapter adapter;
    private String msg;
    private List<Attendee> attendees;


    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_select_attendee, null);
        toolbar = view.findViewById(R.id.dialog_select_attendee_toolbar);
        toolbar.setTitle("Select Attendee");
        toolbar.inflateMenu(R.menu.dialog_menu_attendee_toolbar);

        attendees = planner.getTotalContact();
        attendListView = view.findViewById(R.id.dialog_attendee_select_list_view);
        adapter = new AttendeeListViewAdapter(getContext(),attendees);
        attendListView.setAdapter(adapter);
        add = view.findViewById(R.id.menu_item_add_new_attendee);
        back = view.findViewById(R.id.menu_item_back_attendee);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        builder.setView(view);
        return builder.create();
    }

    public void setMsg(String s) {
        this.msg = s;
    }

    public interface New_Event_Dialog_Listener {
        void applyTexts(String title, String venue, String location);
    }


}
