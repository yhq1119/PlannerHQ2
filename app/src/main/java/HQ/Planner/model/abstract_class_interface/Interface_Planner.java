package HQ.Planner.model.abstract_class_interface;

import java.util.Date;
import java.util.List;

import HQ.Planner.model.Attendee;
import HQ.Planner.model.Event;
import HQ.Planner.model.Movie;

public interface Interface_Planner {

    void add_event(Event event);

    void remove_event(Event event);

    void setAllEvent(List<Event> allEvent);

    void setAttendees(List<Attendee> attendees);

    void setAllMovies(List<Movie> allMovies);

    void updateAllEventsId();

    Event getEventById(int id);

    Event getEventByStartDate(Date startDate);

    List<Event> get3OrLessEventsByStartDate(Date startDate);

    List<Attendee> getTotalContact();

    List<Event> getAllEvents();

    List<Movie> getAllMovies();

    boolean eventHasExisted(Event event);


    void ascend();
    void descend();

}
