package HQ.Planner.model;

import HQ.Planner.R;
import HQ.Planner.model.abstract_class_interface.Abstract_Movie;

public class Movie extends Abstract_Movie {


    int id;
    String title;
    String year;
    String image_name;
    int image_source;

    public Movie(String title,
                 String year,
                 String image_name) {
        this.title = title;
        this.year = year;
        this.image_name = image_name;
        this.image_source = image_source;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getYear() {
        return year;
    }

    @Override
    public String getImageName() {
        return image_name;
    }


}
