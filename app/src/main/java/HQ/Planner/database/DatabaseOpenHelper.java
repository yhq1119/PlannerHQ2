package HQ.Planner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import HQ.Planner.model.Attendee;
import HQ.Planner.model.Event;
import HQ.Planner.model.Movie;
import HQ.Planner.utilities.MToast;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME = "event_planner.db";
    public static final String EVENT_TABLE = "event_table";
    public static final String EVENT_ID = "EVENT_ID";
    public static final String EVENT_TITLE = "EVENT_TITLE";
    public static final String EVENT_START_DATE = "EVENT_START_DATE";
    public static final String EVENT_END_DATE = "EVENT_END_DATE";
    public static final String EVENT_VENUE = "EVENT_VENUE";
    public static final String EVENT_LOCATION = "EVENT_LOCATION";
//    public static final String EVENT_TABLE_COL8 = "EVENT_ATTENDEES_ID";


    public static final String ATTENDEES_TABLE = "attendees_table";
    public static final String ATTENDEES_ID = "ATTENDEE_ID";
    public static final String ATTENDEES_NAME = "ATTENDEE_NAME";
    public static final String ATTENDEES_PHONE = "ATTENDEE_PHONE";


    public static final String MOVIE_EVENT_TABLE = "movie_event_table";
    public static final String M_MOVIE_ID = "M_MOVIE_ID";
    public static final String M_EVENT_ID = "M_EVENT_ID";

    public static final String ATTENDEES_EVENT_TABLE = "attendees_event_table";
    public static final String A_ATTENDEE_ID = "A_ATTENDEE_ID";
    public static final String A_EVENT_ID = "A_EVENT_ID";


    public static final String MOVIE_TABLE = "movie_table";
    public static final String MOVIE_ID = "MOVIE_ID";
    public static final String MOVIE_TITLE = "MOVIE_TITLE";
    public static final String MOVIE_YEAR = "MOVIE_YEAR";
    public static final String MOVIE_IMAGE_NAME = "MOVIE_IMAGE_NAME";
    public static final String MOVIE_IMAGE_SOURCE = "MOVIE_IMAGE_SOURCE";

    public DatabaseOpenHelper(@Nullable Context context) {
        super(context,
                DATABASE_NAME,
                null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            createMovieTable(db);
            createEventTable(db);
            createAttendeesTable(db);
            createMovieEventTable(db);
            createAttendeesEventTable(db);

            MToast.T(context, "Create DB tables successfully");
        } catch (Exception e) {
            Log.e("Table creation", "Table error", e);
            //       MToast.T(context, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

    }

    public Cursor rawQuery() {
        SQLiteDatabase database =
                this.getReadableDatabase();
        return database.rawQuery(
                "SELECT * FROM " + EVENT_TABLE + " ;",
                null);
    }

    /*

    create section

     */

    private void createEventTable(SQLiteDatabase db) {
        db.execSQL("create table " + EVENT_TABLE +
                " (" + EVENT_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "" + EVENT_TITLE + " TEXT," +
                "" + EVENT_START_DATE + " TEXT," +
                "" + EVENT_END_DATE + " TEXT," +
                "" + EVENT_VENUE + " TEXT," +
                "" + EVENT_LOCATION + " LOCATION)");
    }

    private void createMovieTable(SQLiteDatabase db) {
        db.execSQL("create table " + MOVIE_TABLE +
                " (" + MOVIE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "" + MOVIE_TITLE + " TEXT," +
                "" + MOVIE_YEAR + " TEXT," +
                "" + MOVIE_IMAGE_NAME + " TEXT)");
    }

    private void createAttendeesTable(SQLiteDatabase db) {
        db.execSQL("create table " + ATTENDEES_TABLE +
                " (" + ATTENDEES_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "" + ATTENDEES_NAME + " TEXT," +
                "" + ATTENDEES_PHONE + " TEXT)");
    }

    private void createAttendeesEventTable(SQLiteDatabase db) {
        db.execSQL("create table " + ATTENDEES_EVENT_TABLE +
                " (" + A_ATTENDEE_ID +
                " INTEGER PRIMARY KEY," +
                "" + A_EVENT_ID + " INTEGER)");
    }

    private void createMovieEventTable(SQLiteDatabase db) {
        db.execSQL("create table " + MOVIE_EVENT_TABLE +
                " (" + M_MOVIE_ID +
                " INTEGER PRIMARY KEY," +
                "" + M_EVENT_ID + " INTEGER)");
    }

    /*
     *
     * insert section
     *
     *
     *
     */
    public synchronized boolean insertEvent(Event event) {

        String title = event.getTitle();
        String start_date = event.getStartDateString();
        String end_date = event.getEndDateString();
        String venue = event.getVenue();
        String location = event.getLocation();


        try {
//            String qry = "INSERT INTO " + EVENT_TABLE +
//                    " VALUES('" +
//                    title + "','" +
//                    start_date + "','" +
//                    end_date + "','" +
//                    venue + "','" +
//                    location + "')";

            ContentValues values = new ContentValues();
            values.put(EVENT_TITLE, title);
            values.put(EVENT_START_DATE, start_date);
            values.put(EVENT_END_DATE, end_date);
            values.put(EVENT_VENUE, venue);
            values.put(EVENT_LOCATION, location);

            SQLiteDatabase db = getWritableDatabase();
            db.insert(EVENT_TABLE, null, values);
            //db.execSQL(qry);
            MToast.T(context, "insert event successfully");

            return true;
        } catch (Exception e) {
            Log.e("Insert event", "error", e);
            return false;
        }
    }

    public synchronized boolean insertMovie(Movie movie) {

        String title = movie.getTitle();
        String year = movie.getYear();
        String image_name = movie.getImageName();



        try {
            ContentValues values = new ContentValues();
            values.put(MOVIE_TITLE, title);
            values.put(MOVIE_YEAR, year);
            values.put(MOVIE_IMAGE_NAME,image_name);

            SQLiteDatabase db = getWritableDatabase();
            db.insert(MOVIE_TABLE, null, values);
            //db.execSQL(qry);
            MToast.T(context, "insert movie successfully");

            return true;
        } catch (Exception e) {
            Log.e("Insert movie", "error", e);
            return false;
        }
    }

    public synchronized boolean insertAttendee(Attendee attendee) {

        String name = attendee.getName();
        String phone = attendee.getPhone();


        try {
            ContentValues values = new ContentValues();
            values.put(ATTENDEES_NAME, name);
            values.put(ATTENDEES_PHONE, phone);

            SQLiteDatabase db = getWritableDatabase();
            db.insert(ATTENDEES_TABLE,
                    null, values);
            //db.execSQL(qry);
            MToast.T(context, "insert Attendee successfully");

            return true;
        } catch (Exception e) {
            Log.e("Insert movie", "error", e);
            return false;
        }
    }


    public synchronized boolean insertAttendeeEvent(
            int A_id, int E_id) {

        try {
            ContentValues values = new ContentValues();
            values.put(A_ATTENDEE_ID, A_id);
            values.put(A_EVENT_ID, E_id);
            SQLiteDatabase db = getWritableDatabase();
            db.insert(ATTENDEES_EVENT_TABLE, null, values);
            MToast.T(context, "insert attendee_event successfully");

            return true;
        } catch (Exception e) {


            return false;
        }
    }

    public synchronized boolean insertMovieEvent(
            int M_id, int E_id) {

        try {
            ContentValues values = new ContentValues();
            values.put(M_MOVIE_ID, M_id);
            values.put(M_EVENT_ID, E_id);
            SQLiteDatabase db = getWritableDatabase();
            db.insert(MOVIE_EVENT_TABLE, null, values);
            MToast.T(context, "insert movie_event successfully");

            return true;
        } catch (Exception e) {


            return false;
        }
    }


    /*

    remove section


     */

    public void deleteEvent(Event event) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MOVIE_EVENT_TABLE, M_EVENT_ID + "=?",
                new String[]{String.valueOf(event.getId())});
        db.delete(ATTENDEES_EVENT_TABLE, A_EVENT_ID + "=?",
                new String[]{String.valueOf(event.getId())});
        db.delete(EVENT_TABLE, EVENT_ID + "=?",
                new String[]{String.valueOf(event.getId())});
    }

    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MOVIE_EVENT_TABLE, M_MOVIE_ID + "=?",
                new String[]{String.valueOf(movie.getId())});
        db.delete(MOVIE_TABLE, MOVIE_ID + "=?",
                new String[]{String.valueOf(movie.getId())});
    }

    public void deleteAttendee(Attendee attendee) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ATTENDEES_EVENT_TABLE, A_ATTENDEE_ID + "=?",
                new String[]{String.valueOf(attendee.getId())});
        db.delete(ATTENDEES_TABLE, ATTENDEES_ID + "=?",
                new String[]{String.valueOf(attendee.getId())});
    }





}
