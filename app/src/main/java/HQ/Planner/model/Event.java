package HQ.Planner.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import HQ.Planner.model.abstract_class_interface.Abstract_Event;
import HQ.Planner.utilities.MDate;

public class Event extends Abstract_Event {


    int id;
    String title;
    String venue;
    String location;
    Date startDate;
    Date endDate;
    List<Movie> movies;
    List<Attendee> attendees;

    double latitude;
    double longitude;


    public Event(String title,
                 String venue,
                 String location,
                 Date startDate,
                 Date endDate) {
        this.title = title;
        this.venue = venue;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        geneLatLng();
    }

    private void geneLatLng(){
        String[] strings = location.split(", ");

        try {
            latitude = Double.parseDouble(strings[0]);
        }catch (Exception e){
            latitude = -37+new Random().nextDouble();
        }

        try {
            longitude = Double.parseDouble(strings[1]);
        }catch (Exception e){
            longitude = 144+new Random().nextDouble();
        }

    }




    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getVenue() {
        return venue;
    }

    @Override
    public String getLocation() {

        String lat = String.format("%.6f",latitude);
        String lng = String.format("%.6f",longitude);

        return lat+", "+lng;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;

    }

    @Override
    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String getStartDateString() {
        return MDate.toDate(startDate);
    }

    @Override
    public String getEndDateString() {
        return MDate.toDate(endDate);
    }

    @Override
    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public void setMovies(List<Movie> movie) {
        movies = movie;
    }

    @Override
    public void addMovie(Movie movie) {
        if (!movieHasExisted(movie)) {
            movies.add(movie);
        }
    }

    @Override
    public void deleteMovie(Movie movie) {

        if (movieHasExisted(movie)) {
            movies.remove(movie);
        }
    }

    @Override
    public boolean movieHasExisted(Movie movie) {

        boolean existed = false;

        for (Movie m : movies) {
            if (m.getTitle().equals(movie.getTitle())
                    && m.getYear().equals(movie.getYear())) {
                existed = true;
                break;
            }
        }
        return existed;
    }


    @Override
    public List<Attendee> getAttendees() {
        return attendees;
    }

    @Override
    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
    }

    @Override
    public void addAttendee(Attendee attendee) {

    }

    @Override
    public Attendee getAttendeeById(int id) {
        Attendee find = null;
        for (Attendee attendee : attendees) {
            if (attendee.getId() == id) {
                find = attendee;
                break;
            }
        }
        return find;
    }

    @Override
    public boolean attendeeHasExisted(Attendee attendee) {
        for (Attendee a : attendees) {
            if (a.getName().equals(attendee.getName())
                    && a.getPhone().equals(attendee.getPhone())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double getLatitude() {


        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }
}
