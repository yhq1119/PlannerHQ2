package HQ.Planner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import HQ.Planner.R;
import HQ.Planner.model.Attendee;
import HQ.Planner.utilities.MToast;

public class AttendeeListViewAdapter extends ArrayAdapter<Attendee> {

    Context context;

    public AttendeeListViewAdapter(Context context, List<Attendee> attendees) {
        super(context, 0, attendees);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(final int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        final Attendee attendee = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(
                            R.layout.attendee_item_view_vertical_list,
                            parent, false);
        }

        TextView name = convertView.findViewById(R.id.text_view_attendee_item_name_vertical);
        TextView phone = convertView.findViewById(R.id.text_view_attendee_item_phone_vertical);


//        TextView positionText = convertView.findViewById(R.id.);

        name.setText("Name  "+attendee.getName());
        phone.setText("Phone  "+attendee.getPhone());



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                say("<"+position+"> Name "+attendee.getName()+"\n"+
                        "Phone "+attendee.getPhone());

            }
        });
//        positionText.setText(String.valueOf(position));
        return convertView;
    }

    private void say(String msg){
        MToast.say(context,msg);
    }

}
