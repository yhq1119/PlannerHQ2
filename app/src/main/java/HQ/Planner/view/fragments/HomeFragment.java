package HQ.Planner.view.fragments;

import android.database.DataSetObservable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import HQ.Planner.MainActivity;
import HQ.Planner.R;

import HQ.Planner.adapter.EventListViewAdapter;
import HQ.Planner.model.Event;
import HQ.Planner.model.Planner;
import HQ.Planner.utilities.MToast;
import HQ.Planner.view.dialogs.Confirm_Dialog;
import HQ.Planner.view.dialogs.Setting_Dialog;

public class HomeFragment extends Fragment {


    Planner planner = Planner.getShared();
    List<Event> events;
    View view;
    ListView listView;
    EventListViewAdapter adapter;
    Toolbar toolbar;
    ActionMenuItemView new_event_menu_item;
    ActionMenuItemView ascend_menu_item;
    ActionMenuItemView descend_menu_item;
    ActionMenuItemView settingBtn;
    //   HomeFragmentAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mlayoutManager;

    public void blend() {

        this.events = planner.getAllEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.menu_item_setting_time:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = getActivity().findViewById(R.id.activity_home_toolbar);
        toolbar.getMenu().clear();
        toolbar.setTitle("Home");
        toolbar.inflateMenu(R.menu.fragment_home_toolbar_menu);
        toolbar.setOnMenuItemClickListener(barListener);
        settingBtn = getActivity().findViewById(R.id.menu_item_setting_time);
        init();
        settingBtn.setOnClickListener(settingListener);
        new_event_menu_item = view.findViewById(R.id.menu_item_new_event);
        blend();
        initList();

        //  new_event_menu_item = view.findViewById(R.id.menu_item_new_event);
        return view;
    }

    public void refreshList() {
        if (view == null) {
            return;
        }
        adapter = new EventListViewAdapter(getContext(), planner.getAllEvents());
        listView.setAdapter(adapter);
    }

    private void initList() {
        listView = view.findViewById(R.id.fragment_home_listView);

        refreshList();
        DataSetObservable observable = new DataSetObservable();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                Event event = (Event) listView.getAdapter().getItem(position);
                openEventDetail(event);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {
                final Event event = (Event) listView.getAdapter().getItem(position);
                final Confirm_Dialog confirm_dialog = new Confirm_Dialog();
                confirm_dialog.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.remove(event);
                        confirm_dialog.dismiss();
                    }
                });
                confirm_dialog.setMsg("Delete Event", "You sure to delete :\n" + event.getTitle() + " ?");

                confirm_dialog.show(((MainActivity) getActivity()).getSupportFragmentManager(), "");

                return true;
            }
        });
    }


    private void openEventDetail(Event event) {
        ((MainActivity) getActivity()).openEventDetailDialog(this, event);
        refreshList();
    }

    private View.OnClickListener settingListener;
    private View.OnClickListener newEventlistener;
    private View.OnClickListener ascendListener;
    private View.OnClickListener descendListener;


    private void init() {
        settingListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Setting_Dialog setting_dialog = new Setting_Dialog();
                        setting_dialog.show(getActivity().getSupportFragmentManager(), "");
                    }
                };
        newEventlistener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                };
        ascendListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                };
        descendListener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                };
    }

    public void addEvent(Event event) {
        planner.add_event(event);
        refreshList();
    }

    public void deleteEvent(Event event) {
        planner.remove_event(event);
        refreshList();
    }


    public void notifyChange() {

    }

    private void say(String msg) {
        MToast.say(getContext(), msg);
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void action(MenuItem item) {
        MainActivity activity = ((MainActivity) getActivity());
        switch (item.getItemId()) {
            case R.id.menu_item_new_event:
                say("Add New Event");
                activity.openNewEventDialog(this);
                break;
            case R.id.menu_item_setting_time:
                say("Remind's Settings");
                activity.openSettingTimeDialog();
                break;
            case R.id.menu_item_ascend:
                planner.ascend();
                refreshList();
                say("Sort in Ascending Order");
                break;
            case R.id.menu_item_descend:
                planner.descend();
                refreshList();
                say("Sort in Descending Order");
                break;

            default:
                say(String.valueOf(item.getItemId()));
                break;
        }
    }


    private Toolbar.OnMenuItemClickListener barListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            action(item);
            return true;
        }
    };

}
