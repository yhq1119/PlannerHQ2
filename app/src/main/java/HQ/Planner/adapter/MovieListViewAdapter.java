package HQ.Planner.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import HQ.Planner.R;
import HQ.Planner.model.Event;
import HQ.Planner.model.Movie;

public class MovieListViewAdapter extends ArrayAdapter<Movie> {

    Context context;
    List<Movie> movies;

    public MovieListViewAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(
                            R.layout.movie_item_view_vertical_list,
                            parent, false);
        }

        TextView title = convertView.findViewById(R.id.text_view_movie_item_title_vertical);
        TextView year = convertView.findViewById(R.id.text_view_movie_item_year_vertical);
        ImageView poster = convertView.findViewById(R.id.text_view_movie_item_pic_vertical);


//        TextView positionText = convertView.findViewById(R.id.);

        title.setText(movie.getTitle());
        year.setText(movie.getYear());


        poster.setImageResource(
                getImageResId( movie.getImageName()
        ));

//        positionText.setText(String.valueOf(position));
        return convertView;
    }

    private int getImageResId(String imageName) {
        String name = imageName.split("\\.")[0];
       return context.getResources()
                .getIdentifier(
                        name.toLowerCase(),
                        "drawable",
                        context.getPackageName());
    }

}
