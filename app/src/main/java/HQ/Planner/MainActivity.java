package HQ.Planner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import HQ.Planner.controller.BottomNavListener;

import HQ.Planner.database.DatabaseOpenHelper;
import HQ.Planner.model.Attendee;
import HQ.Planner.model.Event;
import HQ.Planner.model.Movie;
import HQ.Planner.model.Planner;
import HQ.Planner.utilities.MFileHandler;
import HQ.Planner.utilities.MGetContacts;
import HQ.Planner.services.MNotification;
import HQ.Planner.view.dialogs.Event_Detail_Dialog;
import HQ.Planner.view.dialogs.New_Event_Dialog;
import HQ.Planner.view.dialogs.Setting_Dialog;
import HQ.Planner.view.fragments.CalendarFragment;
import HQ.Planner.view.fragments.HomeFragment;
import HQ.Planner.view.fragments.PlacesFragment;
import HQ.Planner.view_model.PlannerViewModel;

public class MainActivity extends AppCompatActivity {


    //    private RecyclerView.LayoutManager layoutManager;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase database;
    private Planner planner = Planner.getShared();


    FragmentManager fragmentManager;
//    Toolbar homeToolbar;
    HomeFragment home;
    CalendarFragment calendar;
    PlacesFragment places;
    BottomNavigationView bottomNavigationView;
    BottomNavListener bottomListener;
    PlannerViewModel plannerViewModel;
//    GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initDatabase();
//        testData();

        MFileHandler.loadFromFile(this);
        testContact();
    }

    private void testContact(){
        List<Attendee> attendees;

        MGetContacts mGetContacts = new MGetContacts(this);
        attendees = mGetContacts.getContactList();
//        say("attendees:  "+attendees.size());
        planner.setAttendees(attendees);

    }


    private void testData() {
        List<Event> events = new ArrayList<>();

        events.add(new Event("event 1", "place 1", "location", new Date(), new Date()));
        events.add(new Event("event 2", "place 2", "location", new Date(), new Date()));
        events.add(new Event("event 3", "place 3", "location", new Date(), new Date()));

        List<Attendee> attendees = new ArrayList<>();
        attendees.add(new Attendee("Hi","12345896789"));
        attendees.add(new Attendee("H2","12341556789"));
        attendees.add(new Attendee("H3","12345678589"));
        attendees.add(new Attendee("H4","12345611789"));


//        MGetContacts mGetContacts = new MGetContacts(this);
//        attendees = mGetContacts.getContactList();
//        say("attendees:  "+attendees.size());

        List<Movie> movies1 = new ArrayList<>();
        movies1.add(new Movie("Avatar","1991","avatar.jpeg"));
        movies1.add(new Movie("Inception","1991","inception.jpg"));
        movies1.add(new Movie("Interstellar","1991","interstellar.jpg"));
        movies1.add(new Movie("Blade Runner","1991","bladerunner1982.jpg"));
        movies1.add(new Movie("Hackers","1991","hackers.jpg"));
        movies1.add(new Movie("Flipped","1991","flipped.jpg"));
        movies1.add(new Movie("fdfdff","1991","fdsf.jpg"));
//        events.add(new Event("event 4", "place 4", "location", new Date(), new Date()));
//        events.add(new Event("event 5", "place 5", "location", new Date(), new Date()));
//        events.add(new Event("event 6", "place 6", "location", new Date(), new Date()));
//        events.add(new Event("event 7", "place 7", "location", new Date(), new Date()));
//        events.add(new Event("event 8", "place 8", "location", new Date(), new Date()));


        planner.setAllEvent(events);
        planner.setAllMovies(movies1);
        planner.setAttendees(attendees);

    }

    private Cursor getContacts() {


        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection =
                new String[]{ ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME +
                " COLLATE LOCALIZED ASC";
        return managedQuery(uri, projection, selection, selectionArgs, sortOrder);

    }

    private void initDatabase() {

        planner = Planner.getShared();
        dbHelper = new DatabaseOpenHelper(getApplicationContext());
        database = dbHelper.getReadableDatabase();


    }


    private void initUI() {


        fragmentManager = getSupportFragmentManager();
        bottomListener = new BottomNavListener(this);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomListener);
        home = new HomeFragment();
        calendar = new CalendarFragment();
        places = new PlacesFragment();
//        homeToolbar = findViewById(R.id.activity_home_toolbar);

        setContentFragment(home);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    public void setContentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, fragment)
                .commit();

    }

    public void changeTo(MenuItem menuItem) {
        Fragment selected = null;
        switch (menuItem.getItemId()) {

            case R.id.nav_calendar:
                selected = calendar;

                break;

            case R.id.nav_home:
                selected = home;
                if (planner.getAllEvents().size()==4||
                        planner.getAllEvents().size()==8){
                    MNotification mNotification = new MNotification(this,"CHANNEL1");
                    mNotification.notifyMyNotification(2);
                }

                break;

            case R.id.nav_place:
                selected = places;
                break;

            default:
                Toast.makeText(this,
                        "menu id" + menuItem.getItemId(),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        setContentFragment(selected);
    }

    private void say(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();



    }

    public void openSettingTimeDialog() {
        Setting_Dialog setting_dialog = new Setting_Dialog();
        setting_dialog.show(getSupportFragmentManager(), "");

    }

    public void openNewEventDialog(Fragment fragment) {
        New_Event_Dialog new_event_dialog = new New_Event_Dialog();
        new_event_dialog.setFragment(fragment);
        new_event_dialog.show(getSupportFragmentManager(), "");
    }public void openNewEventDialog(Fragment fragment, Date date) {
        New_Event_Dialog new_event_dialog = new New_Event_Dialog();
        new_event_dialog.setFragment(fragment);
        new_event_dialog.setStartDate(date);
        new_event_dialog.show(getSupportFragmentManager(), "");
    }

    public void openEventDetailDialog(Fragment fragment, Event event) {
        Event_Detail_Dialog event_detail_dialog = new Event_Detail_Dialog();
        event_detail_dialog.setFragment(fragment);
        event_detail_dialog.setEvent(event);
        event_detail_dialog.show(getSupportFragmentManager(), "");
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.println(1, "fdaf", "on create");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.println(1, "fdaf", "on stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.println(1, "fdaf", "on destroy");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.println(1, "fdaf", "on resume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.println(1, "fdaf", "on restart");

    }

//    public Planner getPlanner() {
//        return planner;
//    }


}
