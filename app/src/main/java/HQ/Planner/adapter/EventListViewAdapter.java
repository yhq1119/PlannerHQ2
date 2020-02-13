package HQ.Planner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import HQ.Planner.MainActivity;
import HQ.Planner.R;
import HQ.Planner.model.Event;
import HQ.Planner.view.dialogs.Confirm_Dialog;

public class EventListViewAdapter extends ArrayAdapter<Event> {

    Context context;
    List<Event> events;
    DialogFragment fragment;

    public EventListViewAdapter(Context context, List<Event> events,DialogFragment fragment){
        super(context,0,events);
        this.context = context;
        this.events = events;
        this.fragment = fragment;
    }



    public EventListViewAdapter(Context context, List<Event> events){
        super(context,0,events);
    }


    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        final Event event = getItem(position);
        if (convertView==null){
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(
                            R.layout.fragment_home_list_item,
                            parent,false);
        }

        TextView title = convertView.findViewById(R.id.list_item_title);
        TextView venue = convertView.findViewById(R.id.list_item_venue);
        TextView startDate = convertView.findViewById(R.id.list_item_start_date);
        TextView endDate = convertView.findViewById(R.id.list_item_end_date);

        TextView positionText = convertView.findViewById(R.id.list_item_position);

        if (event!=null)
        {
            title.setText(event.getTitle());
            venue.setText(event.getVenue());
            startDate.setText(event.getStartDateString());
            endDate.setText(event.getEndDateString());
        }
        positionText.setText(String.valueOf(position));

        return convertView;
    }
}
