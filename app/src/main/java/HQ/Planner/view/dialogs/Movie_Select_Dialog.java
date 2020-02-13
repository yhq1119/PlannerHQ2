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

import java.util.ArrayList;
import java.util.List;

import HQ.Planner.R;
import HQ.Planner.adapter.MovieListViewAdapter;
import HQ.Planner.model.Movie;
import HQ.Planner.model.Planner;

public class Movie_Select_Dialog extends AppCompatDialogFragment {


    Planner planner = Planner.getShared();
    ActionMenuItemView add;
    ActionMenuItemView back;
    Toolbar toolbar;
    ListView movieList;
    MovieListViewAdapter adapter;
    private String msg;
    private List<Movie> movies;


    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_select_movie, null);
        toolbar = view.findViewById(R.id.dialog_select_movie_toolbar);
        toolbar.setTitle("Select Movie");
        toolbar.inflateMenu(R.menu.dialog_menu_movie_toolbar);

        movies = planner.getAllMovies();




//        textView = view.findViewById(R.id.confrim_msg);
//        textView.setText(msg);
        movieList = view.findViewById(R.id.dialog_movie_select_list_view);
        adapter = new MovieListViewAdapter(getContext(),movies);
        movieList.setAdapter(adapter);
        add = view.findViewById(R.id.menu_item_add_new_movie);
        back = view.findViewById(R.id.menu_item_back_movie);
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
