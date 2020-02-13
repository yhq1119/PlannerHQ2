package HQ.Planner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import HQ.Planner.model.abstract_class_interface.Abstract_Planner;

public class Planner extends Abstract_Planner {

    List<Event> allEvent;
    List<Movie> allMovies;
    List<Attendee> allContacts;


    private static Planner shared;

    public static Planner getShared() {
        if (shared == null) {
            shared = new Planner();
            return shared;
        }
        return shared;
    }

    private Planner() {
        allEvent = new ArrayList<>();
        allMovies = new ArrayList<>();
        allContacts = new ArrayList<>();
    }

    @Override
    public void add_event(Event event) {
        allEvent.add(event);
    }

    @Override
    public void remove_event(Event event) {
        allEvent.remove(event);
    }

    @Override
    public void setAllEvent(List<Event> allEvent) {
        this.allEvent = allEvent;
    }

    @Override
    public void setAttendees(List<Attendee> attendees) {
        this.allContacts = attendees;
    }

    @Override
    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    @Override
    public void updateAllEventsId() {
        for (int i = 0; i < allEvent.size(); i++) {
            allEvent.get(i).setId(i);
        }
    }


    @Override
    public Event getEventById(int id) {
        Event temp = null;
        for (Event event : allEvent) {
            if (event.getId() == id) {
                temp = event;
                break;
            }
        }
        return temp;
    }

    @Override
    public Event getEventByStartDate(Date startDate) {

        Event temp = null;
        for (Event event : allEvent) {
            if (event.getStartDate().equals(startDate)) {
                temp = event;
                break;
            }
        }
        return temp;
    }

    @Override
    public List<Event> get3OrLessEventsByStartDate(Date startDate) {

        List<Event> temp = new ArrayList<>();

        for (Event event : allEvent) {
            if (event.getStartDate().equals(startDate)) {
                temp.add(event);
            }
        }
        return temp;
    }

//    @Override
//    public List<Event> getEventsByStartDate(Date startDate) {
//
//    }

    @Override
    public List<Attendee> getTotalContact() {
        return allContacts;
    }

    @Override
    public List<Event> getAllEvents() {
        return allEvent;
    }

    @Override
    public List<Movie> getAllMovies() {
        return allMovies;
    }

    @Override
    public boolean eventHasExisted(Event event) {

        for (Event e : allEvent) {
            if (
                    e.getTitle().equals(event.getTitle())
                            && e.getVenue().equals(event.getVenue())
                            && e.getLocation().equals(event.getLocation())
                            && e.getStartDateString().equals(event.getStartDateString())
                            && e.getEndDateString().equals(event.getEndDateString())
            ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void ascend() {
        Collections.sort(allEvent, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

    }

    @Override
    public void descend() {
        Collections.sort(allEvent, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o2.getStartDate().compareTo(o1.getStartDate());
            }
        });
    }


}
