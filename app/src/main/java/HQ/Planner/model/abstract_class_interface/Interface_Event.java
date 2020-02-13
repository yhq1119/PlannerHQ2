package HQ.Planner.model.abstract_class_interface;

import android.text.method.MovementMethod;

import java.util.Date;
import java.util.List;

import HQ.Planner.model.Attendee;
import HQ.Planner.model.Movie;

public interface Interface_Event {

    int getId();
    void setId(int id);

    String getTitle();
    String getVenue();
    String getLocation();
    void setTitle(String title);
    void setVenue(String venue);
    void setLocation(String location);


    Date getStartDate();
    Date getEndDate();

    String getStartDateString();
    String getEndDateString();

    List<Movie> getMovies();
    void setMovies(List<Movie> movie);
    void addMovie(Movie movie);
    void deleteMovie(Movie movie);
    boolean movieHasExisted(Movie movie);

    List<Attendee> getAttendees();
    void removeAttendee(Attendee attendee);
    void addAttendee(Attendee attendee);
    Attendee getAttendeeById(int id);
    public boolean attendeeHasExisted(Attendee attendee);


    double getLatitude();

    double getLongitude();
}
